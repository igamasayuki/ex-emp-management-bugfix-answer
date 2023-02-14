package com.example.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.form.InsertEmployeeForm;
import com.example.repository.EmployeeRepository;

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
	 * 従業員情報を全件取得します.<br>
	 * ページング機能で使わなくなりました
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
	 * @throws org.springframework.dao.DataAccessException 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * 従業員情報を登録します.<br>
	 * 画像ファイルはBase64形式に変換します。
	 * 
	 * @param form フォーム
	 * @param fileExtension ファイルの拡張子
	 * @return インサートした従業員情報
	 * @throws IOException 不正なファイルが渡ってきた場合に発生
	 */
	public Employee insert(InsertEmployeeForm form, String fileExtension) throws IOException {
		
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);

		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(form.getImageFile().getBytes());
		if ("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if ("png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		employee.setImage(base64FileString);
		
		return employeeRepository.insert(employee);
	}

	/**
	 * メールアドレスから従業員情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 従業員情報 存在しない場合はnullを返します
	 */
	public Employee findByMailAddress(String mailAddress) {
		return employeeRepository.findByMailAddress(mailAddress);
	}
	
	/**
	 * 名前から従業員を(曖昧)検索します.
	 * 
	 * @param name
	 *            従業員の名前
	 * @return 検索された従業員の情報一覧
	 */
	public List<Employee> searchByNameContaining(String name) {
		return employeeRepository.searchByNameContaining(name);
	}
	
	
	/**
	 * ページング用メソッド.
	 * @param page 表示させたいページ数
	 * @param size １ページに表示させる従業員数
	 * @param employeeList 絞り込み対象リスト
	 * @return １ページに表示されるサイズ分の従業員一覧情報
	 */
	public Page<Employee> showListPaging(int page, int size, List<Employee> employeeList) {
	    // 表示させたいページ数を-1しなければうまく動かない
	    page--;
	    // どの従業員から表示させるかと言うカウント値
	    int startItemCount = page * size;
	    // 絞り込んだ後の従業員リストが入る変数
	    List<Employee> list;

	    if (employeeList.size() < startItemCount) {
	    	// (ありえないが)もし表示させたい従業員カウントがサイズよりも大きい場合は空のリストを返す
	        list = Collections.emptyList();
	    } else {
	    	// 該当ページに表示させる従業員一覧を作成
	        int toIndex = Math.min(startItemCount + size, employeeList.size());
	        list = employeeList.subList(startItemCount, toIndex);
	    }

	    // 上記で作成した該当ページに表示させる従業員一覧をページングできる形に変換して返す
	    Page<Employee> employeePage
	      = new PageImpl<Employee>(list, PageRequest.of(page, size), employeeList.size());
	    return employeePage;
	}

	/**
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作ります.
	 * 
	 * @param employeeList 従業員一覧
	 * @return　オートコンプリート用JavaScriptの配列の文字列
	 * 　　　　　(例) "渡辺三郎","佐藤次郎","山本八郎","小林九子"
	 */
	public StringBuilder getEmployeeListForAutocomplete(List<Employee> employeeList) {
		StringBuilder employeeListForAutocomplete = new StringBuilder();
		for (int i = 0; i < employeeList.size(); i++) {
			if (i != 0) {
				employeeListForAutocomplete.append(",");
			}
			Employee employee = employeeList.get(i);
			employeeListForAutocomplete.append("\"");
			employeeListForAutocomplete.append(employee.getName());
			employeeListForAutocomplete.append("\"");
		}
		return employeeListForAutocomplete;
	}

}
