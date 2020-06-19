package vn.com.truyen.service.mess;

import java.util.List;

import vn.com.truyen.domain.Feedback;

public class FeedbackMess {
	private static final long serialVersionUID = 1L;
	private String message;
	private long totalFeedbacks;
	private List<Feedback> listFedbacks;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotalFeedbacks() {
		return totalFeedbacks;
	}

	public void setTotalFeedbacks(long totalFeedbacks) {
		this.totalFeedbacks = totalFeedbacks;
	}

	public List<Feedback> getListFedbacks() {
		return listFedbacks;
	}

	public void setListFedbacks(List<Feedback> listFedbacks) {
		this.listFedbacks = listFedbacks;
	}

}
