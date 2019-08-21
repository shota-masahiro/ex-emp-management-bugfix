package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee　更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}


	/**
	 * 名前の曖昧検索を行います.
	 * 引数が空文字の場合は全件検索を行います。
	 * 
	 * @param name
	 * @return 従業員情報一覧
	 */
	public List<Employee> findByName(String name) {
		if ("".equals(name)) {
			return employeeRepository.findAll();
		}
		return employeeRepository.findByName(("%" + name +"%"));
	}


	/**
	 * 従業員情報を登録します.
	 * 
	 * @param employee 従業員情報
	 */
	public synchronized void insert(Employee employee) {
		employee.setId(employeeRepository.getMaxId());
		employeeRepository.insert(employee);
	}


	/**
	 * 開始位置から10件または10件以下の値を取得します.
	 * 
	 * @param num データを取得する開始位置
	 * @return 従業員情報
	 */
	public List<Employee> findByPage(Integer num) {
		return employeeRepository.findByPage(num);
	}


}
