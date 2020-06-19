package vn.com.truyen.service.mess;

import java.io.Serializable;
import java.util.List;

import vn.com.truyen.domain.View;

public class ViewMess implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private long totalViews;
	private List<View> listViews;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(long totalViews) {
		this.totalViews = totalViews;
	}

	public List<View> getListViews() {
		return listViews;
	}

	public void setListViews(List<View> listViews) {
		this.listViews = listViews;
	}

}
