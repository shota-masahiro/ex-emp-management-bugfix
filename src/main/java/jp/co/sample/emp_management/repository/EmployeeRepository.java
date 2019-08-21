package jp.co.sample.emp_management.repository;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
	
	
	/**
	 * 名前の曖昧検索を行い、該当する従業員情報を取得します.
	 * 
	 * @param name 名前の一部
	 * @return 従業員情報一覧(該当しない場合はnullを返す)
	 */
	public List<Employee> findByName(String name) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees WHERE name LIKE :name ORDER BY hire_date");
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<Employee> employeeList = template.query(sql.toString(), param, EMPLOYEE_ROW_MAPPER);
		
		if (employeeList.size() == 0) {
			return null;
		}
		return employeeList;
	}
	
	
	/**
	 * 従業員情報を挿入します.
	 * 
	 * @param employee 従業員情報
	 */
	
	public synchronized void insert(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO employees(id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count) ");
		sql.append("VALUES(:id, :name, :image, :gender, :hireDate, :mailAddress, :zipCode, :address, :telephone, :salary, :characteristics, :dependentsCount)");
		template.update(sql.toString(), param);
	}
	
	
	/**
	 * IDの上限+1の数字を取得します.
	 * 
	 * @return IDの上限+1の数字
	 */
	public Integer getMaxId() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT max(id) + 1 FROM employees");
		
		SqlParameterSource param = new MapSqlParameterSource();
		
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	
	
	/**
	 * 開始位置から10件または10件以下の値を取得します.
	 * 
	 * @param num データを取得する開始位置
	 * @return 従業員情報
	 */
	public List<Employee> findByPage(Integer num) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count ");
		sql.append("FROM employees ORDER BY hire_date LIMIT 10 OFFSET :num");
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("num", num);
		
		List<Employee> employeeList = template.query(sql.toString(), param, EMPLOYEE_ROW_MAPPER);
		
		return employeeList;
	}
	
	
}
