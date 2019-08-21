package jp.co.sample.emp_management.form;

import org.springframework.web.multipart.MultipartFile;

public class InsertEmployeeForm {

	/** id */
	private String id;

	/** 従業員名 */
	private String name;

	/** 画像 */
	private MultipartFile image;

	/** 性別 */
	private String gender;

	/** 入社日 */
	private String hireDate;

	/** メールアドレス */
	private String mailAddress;

	/** 郵便番号 */
	private String zipCode;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String telephone;

	/** 給料 */
	private String salary;

	/** 特性 */
	private String characteristics;

	/** 扶養人数 */
	private String dependentsCount;


	@Override
	public String toString() {
		return "InsertEmployeeForm [id=" + id + ", name=" + name + ", image=" + image + ", gender=" + gender
				+ ", hireDate=" + hireDate + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address="
				+ address + ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
				+ ", dependentsCount=" + dependentsCount + "]";
	}

	public Integer getIntId() {
		return Integer.parseInt(this.id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}


	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}


	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIntSalary() {
		return Integer.parseInt(this.salary);
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}


	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}


	public Integer getIntDependentsCount() {
		return Integer.parseInt(this.dependentsCount);
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

}
