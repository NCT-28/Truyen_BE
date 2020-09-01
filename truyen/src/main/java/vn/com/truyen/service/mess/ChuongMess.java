package vn.com.truyen.service.mess;

import java.io.Serializable;
import java.util.List;

import vn.com.truyen.domain.Chuong;
import vn.com.truyen.domain.Truyen;

public class ChuongMess implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private long totalChuongs;
	private List<Chuong> listChuongs;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalChuongs() {
		return totalChuongs;
	}

	public void setTotalChuongs(long totalChuongs) {
		this.totalChuongs = totalChuongs;
	}

	public List<Chuong> getListChuongs() {
		return listChuongs;
	}

	public void setListChuongs(List<Chuong> listChuongs) {
		this.listChuongs = listChuongs;
	}

}
