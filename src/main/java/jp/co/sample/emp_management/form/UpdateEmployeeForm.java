package jp.co.sample.emp_management.form;

import javax.validation.constraints.Pattern;

/**
 * 従業員情報更新時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class UpdateEmployeeForm {
	/** id */
	private String id;
	/** 扶養人数 */
	@Pattern(regexp = "^[0-9]+$", message = "扶養人数は数値で入力してください")
	private String dependentsCount;

	/**
	 * IDを数値として返します.
	 * 
	 * @return 数値のID
	 */
	public Integer getIntId() {
		return Integer.parseInt(id);
	}

	/**
	 * 扶養人数を数値として返します.
	 * 
	 * @return 数値の扶養人数
	 */
	public Integer getIntDependentsCount() {
		return Integer.parseInt(dependentsCount);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the dependentsCount
	 */
	public String getDependentsCount() {
		return dependentsCount;
	}

	/**
	 * @param dependentsCount the dependentsCount to set
	 */
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

}
