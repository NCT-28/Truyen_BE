package vn.com.truyen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "topic", nullable = false)
	private String topic;

	@Column(name = "name_user_send")
	private String nameUserSend;

	@NotNull
	@Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "content")
	private String content;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "code")
	private String code;

	@Column(name = "creaded_day")
	private ZonedDateTime creadedDay;

	@ManyToOne
	@JsonIgnoreProperties(value = "feadbacks", allowSetters = true)
	private Truyen truyen;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public Feedback topic(String topic) {
		this.topic = topic;
		return this;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getNameUserSend() {
		return nameUserSend;
	}

	public Feedback nameUserSend(String nameUserSend) {
		this.nameUserSend = nameUserSend;
		return this;
	}

	public void setNameUserSend(String nameUserSend) {
		this.nameUserSend = nameUserSend;
	}

	public String getEmail() {
		return email;
	}

	public Feedback email(String email) {
		this.email = email;
		return this;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public Feedback content(String content) {
		this.content = content;
		return this;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean isStatus() {
		return status;
	}

	public Feedback status(Boolean status) {
		this.status = status;
		return this;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public Feedback code(String code) {
		this.code = code;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ZonedDateTime getCreadedDay() {
		return creadedDay;
	}

	public Feedback creadedDay(ZonedDateTime creadedDay) {
		this.creadedDay = creadedDay;
		return this;
	}

	public void setCreadedDay(ZonedDateTime creadedDay) {
		this.creadedDay = creadedDay;
	}

	public Feedback truyen(Truyen truyen) {
		this.truyen = truyen;
		return this;
	}

	public void setTruyen(Truyen truyen) {
		this.truyen = truyen;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Feedback)) {
			return false;
		}
		return id != null && id.equals(((Feedback) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Feedback{" + "id=" + getId() + ", topic='" + getTopic() + "'" + ", nameUserSend='" + getNameUserSend()
				+ "'" + ", email='" + getEmail() + "'" + ", content='" + getContent() + "'" + ", status='" + isStatus()
				+ "'" + ", code='" + getCode() + "'" + ", creadedDay='" + getCreadedDay() + "'" + "}";
	}
}
