package jp.co.sample.emp_management.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.domain.LoginAdministrator;
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

	// 1ページに表示する従業員数は10名
	private static final int VIEW_SIZE = 10;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @param page 出力したいページ数
	 * @param searchName 検索文字列
	 * @param loginAdministrator ログイン情報をコントローラで取得
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model, Integer page, String searchName, @AuthenticationPrincipal LoginAdministrator loginAdministrator) {
		
		// ログイン情報をコントローラで取得するサンプル
		System.out.println(loginAdministrator.getAdministrator().getName() + "さんがログイン中");
		
		// ページング機能追加
		if (page == null) {
			// ページ数の指定が無い場合は1ページ目を表示させる
			page = 1;
		}
		List<Employee> employeeList = null;
		if(searchName == null) {
			// 検索文字列が空なら全件検索
			employeeList = employeeService.showList();
		} else {
			// 検索文字列があれば曖昧検索
			employeeList = employeeService.searchByNameContaining(searchName);
			// ページングの数字からも検索できるように検索文字列をスコープに格納しておく
			model.addAttribute("searchName", searchName);
		}
		// ページング機能追加のためコメントアウト
		// model.addAttribute("employeeList", employeeList);

		// 表示させたいページ数、ページサイズ、従業員リストを渡し１ページに表示させる従業員リストを絞り込み
		Page<Employee> employeePage = employeeService.showListPaging(page, VIEW_SIZE, employeeList);
		model.addAttribute("employeePage", employeePage);

		// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
		List<Integer> pageNumbers = calcPageNumbers(model, employeePage);
		model.addAttribute("pageNumbers", pageNumbers);

		// オートコンプリート用にJavaScriptの配列の中身を文字列で作ってスコープへ格納
		StringBuilder employeeListForAutocomplete = employeeService.getEmployeeListForAutocomplete(employeeList);
		model.addAttribute("employeeListForAutocomplete", employeeListForAutocomplete);
		
		return "employee/list";
	}



	/**
	 * ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
	 * 
	 * @param model        モデル
	 * @param employeePage ページング情報
	 */
	private List<Integer> calcPageNumbers(Model model, Page<Employee> employeePage) {
		int totalPages = employeePage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
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
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員情報を登録する
	/////////////////////////////////////////////////////
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertEmployeeForm setUpInsertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/**
	 * 従業員情報登録画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		// 性別セレクトボックス
		Map<String, String> genderMap = new LinkedHashMap<>();
		genderMap.put("男性", "男性");
		genderMap.put("女性", "女性");
		model.addAttribute("genderMap", genderMap);

		// 扶養人数セレクトボックス
		Map<Integer, Integer> dependentsCountMap = new LinkedHashMap<>();
		for (int i = 0; i < 10; i++) {
			dependentsCountMap.put(i, i);
		}
		model.addAttribute("dependentsCountMap", dependentsCountMap);

		return "employee/insert";
	}

	/**
	 * 従業員情報を登録します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertEmployeeForm form, BindingResult result, Model model) throws IOException {

		// メールアドレスが重複している場合の処理
		Employee existEmployee = employeeService.findByMailAddress(form.getMailAddress());
		if (existEmployee != null) {
			result.rejectValue("mailAddress", "", "そのメールアドレスは既に登録されています");
		}

		// 画像ファイル形式チェック
		MultipartFile imageFile = form.getImageFile();
		String fileExtension = null;
		try {
			fileExtension = getExtension(imageFile.getOriginalFilename());

			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("imageFile", "", "拡張子は.jpgか.pngのみに対応しています");
		}

		// 一つでもエラーがあれば入力画面へ戻りエラーメッセージを出す
		if (result.hasErrors()) {
			return toInsert(model);
		}

		// DBインサート
		employeeService.insert(form, fileExtension);

		return "redirect:/employee/showList";
	}

	/*
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
	 */
	private String getExtension(String originalFileName) throws Exception {
		if (originalFileName == null) {
			throw new FileNotFoundException();
		}
		int point = originalFileName.lastIndexOf(".");
		if (point == -1) {
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}
}
