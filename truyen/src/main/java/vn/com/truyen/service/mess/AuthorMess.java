package vn.com.truyen.service.mess;

import java.io.Serializable;
import java.util.List;

import vn.com.truyen.domain.Author;

public class AuthorMess implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private long totalAuthors;
	private List<Author> listAuthors;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalAuthors() {
		return totalAuthors;
	}

	public void setTotalAuthors(long totalAuthors) {
		this.totalAuthors = totalAuthors;
	}

	public List<Author> getListAuthors() {
		return listAuthors;
	}

	public void setListAuthors(List<Author> listAuthors) {
		this.listAuthors = listAuthors;
	}


}
