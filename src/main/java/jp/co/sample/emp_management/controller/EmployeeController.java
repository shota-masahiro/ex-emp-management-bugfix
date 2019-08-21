package jp.co.sample.emp_management.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {

		Integer page = (Integer) session.getAttribute("page");

		if (page == null) {
			page = 0;
		}

		List<Employee> employeeList = employeeService.findByPage(page);
		model.addAttribute("employeeList", employeeList);

		session.setAttribute("page", page);

		return "employee/list";
	}

	/**
	 * 「次の10件」と「前の10件」の値を取得します.
	 * 
	 * @param page ページ番号
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/nextToBack")
	public String nextToBackPage(Integer page, Model model) {
		int i = 1;
		int offset = page;
		while (true) {

			if (page == 0) {
				offset = 0;
				break;
			}

			if (page == i) {
				offset =+ i*10;
				break;
			}
			i++;
		}
		List<Employee> employeeList = employeeService.findByPage(offset);
		model.addAttribute("employeeList", employeeList);
		session.setAttribute("page", page);
		session.setAttribute("size", employeeList.size());
		return "employee/list";
	}


	/**
	 * 名前の曖昧検索を行い、従業員一覧画面を出力します。
	 * 
	 * @param name 名前の一部
	 * @param model リクエストパラメータ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/findByName")
	public String findByName(String name, Model model) {
		List<Employee> employeeList = employeeService.findByName(name);

		if (employeeList == null) {
			employeeList = employeeService.showList();
			model.addAttribute("employeeList", employeeList);
			model.addAttribute("message", "1件もありませんでした。");
			return "employee/list";
		}

		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}


	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form
	 *            従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}


	/**
	 * 従業員登録画面を出力します.
	 * 
	 * @return 従業員登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		Map<Integer, String> genderMap = new LinkedHashMap<>();
		genderMap.put(1, "男性");
		genderMap.put(2, "女性");
		model.addAttribute("genderMap", genderMap);
		return "employee/employee-insert";
	}


	/**
	 * 従業員情報を挿入します.
	 * 
	 * @param form リクエストパラメータ
	 * @return リダイレクト処理 従業員一覧画面
	 * @throws ParseException
	 */
	@RequestMapping("/insert")
	public String insert(InsertEmployeeForm form, HttpServletRequest request) throws ParseException {
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		
		//画像ファイルをimgディレクトリに追加している処理
		String destination = "/src/main/resources/static/img/";
		MultipartFile multipartFile = form.getImage();

		Path fileNameAndPath = Paths.get(System.getProperty("user.dir")+destination, multipartFile.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		employee.setImage(form.getImage().getOriginalFilename());
		employee.setSalary(form.getIntSalary());
		employee.setDependentsCount(form.getIntDependentsCount());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(form.getHireDate());
		employee.setHireDate(date);

		employeeService.insert(employee);

		return "redirect:/employee/showList";
	}


}