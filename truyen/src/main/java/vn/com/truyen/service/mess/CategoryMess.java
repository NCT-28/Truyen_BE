package vn.com.truyen.service.mess;

import java.io.Serializable;
import java.util.List;

import vn.com.truyen.domain.Author;
import vn.com.truyen.domain.Category;

public class CategoryMess implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private long totalCategorys;
	private List<Category> listCategorys;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalCategorys() {
		return totalCategorys;
	}

	public void setTotalCategorys(long totalCategorys) {
		this.totalCategorys = totalCategorys;
	}

	public List<Category> getListCategorys() {
		return listCategorys;
	}

	public void setListCategorys(List<Category> listCategorys) {
		this.listCategorys = listCategorys;
	}

}
