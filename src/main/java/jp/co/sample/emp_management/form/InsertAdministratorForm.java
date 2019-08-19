package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "氏名は必須です")
	private String name;

	/** メールアドレス */
	@Size(min = 1, max = 127, message = "Eメールは1文字以上127文字以内で記載してください")
	@Email(message = "Eメールの形式が不正です")
	private String mailAddress;

	/** パスワード */
	@Size(min = 1, max = 127, message = "パスワードは1文字以上127文字以内で記載してください")
	private String password;
	
	/** 確認用パスワード */
	@Size(min = 1, max = 127, message = "確認用パスワードを入力してください")
	private String confirmationPassword;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmationPassword() {
		return confirmationPassword;
	}
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", confirmationPassword=" + confirmationPassword + "]";
	}

}
