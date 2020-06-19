package vn.com.truyen.service.mess;

import java.io.Serializable;
import java.util.List;

import vn.com.truyen.domain.Truyen;

public class TruyenMess implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private long totalTruyens;
	private List<Truyen> listTruyens;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalTruyens() {
		return totalTruyens;
	}

	public void setTotalTruyens(long totalTruyens) {
		this.totalTruyens = totalTruyens;
	}

	public List<Truyen> getListTruyens() {
		return listTruyens;
	}

	public void setListTruyens(List<Truyen> listTruyens) {
		this.listTruyens = listTruyens;
	}

}
