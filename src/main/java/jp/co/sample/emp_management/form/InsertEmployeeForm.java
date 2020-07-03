package jp.co.sample.emp_management.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

/**
 * 従業員情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertEmployeeForm {
	/** id */
	private Integer id;
	/** 従業員名 */
	@NotBlank(message = "従業員名を入力してください")
	private String name;
	/** 画像 */
	private MultipartFile imageFile;
	/** 性別 */
	@NotBlank(message = "性別を入力してください")
	private String gender;
	/** 入社日 */
	@Pattern(message = "入社日の入力形式が不正です", regexp = "[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]")
	private String hireDateString;
	/** メールアドレス */
	@Email(message = "メールアドレスの形式ではありません")
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	private String telephone;
	/** 給料 */
	@NotBlank(message = "給料を入力してください")
	private String salaryString;
	/** 特性 */
	@NotBlank(message = "特性を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@NotBlank(message = "扶養人数を入力してください")
	private String dependentsCountString;

	/**
	 * 入社年月日をDate型で返します.
	 * 
	 * @return 入社年月日
	 */
	public Date getHireDate() throws ParseException {
		System.out.println(hireDateString);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date formatDate = sdf.parse(hireDateString);
		return formatDate;
	}

	/**
	 * 給料をInteger型で返します.
	 * 
	 * @return 給料
	 */
	public Integer getSalary() {
		return Integer.parseInt(salaryString);
	}

	/**
	 * 扶養人数をInteger型で返します.
	 * 
	 * @return 扶養人数
	 */
	public Integer getDependentsCount() {
		return Integer.parseInt(dependentsCountString);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the imageFile
	 */
	public MultipartFile getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the hireDateString
	 */
	public String getHireDateString() {
		return hireDateString;
	}

	/**
	 * @param hireDateString the hireDateString to set
	 */
	public void setHireDateString(String hireDateString) {
		this.hireDateString = hireDateString;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the salaryString
	 */
	public String getSalaryString() {
		return salaryString;
	}

	/**
	 * @param salaryString the salaryString to set
	 */
	public void setSalaryString(String salaryString) {
		this.salaryString = salaryString;
	}

	/**
	 * @return the characteristics
	 */
	public String getCharacteristics() {
		return characteristics;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @return the dependentsCountString
	 */
	public String getDependentsCountString() {
		return dependentsCountString;
	}

	/**
	 * @param dependentsCountString the dependentsCountString to set
	 */
	public void setDependentsCountString(String dependentsCountString) {
		this.dependentsCountString = dependentsCountString;
	}

	@Override
	public String toString() {
		return "InsertEmployeeForm [id=" + id + ", name=" + name + ", imageFile=" + imageFile + ", gender=" + gender
				+ ", hireDateString=" + hireDateString + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode
				+ ", address=" + address + ", telephone=" + telephone + ", salaryString=" + salaryString
				+ ", characteristics=" + characteristics + ", dependentsCountString=" + dependentsCountString + "]";
	}

}
