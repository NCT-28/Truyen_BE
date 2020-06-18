package vn.com.truyen.usermanagent.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link vn.com.truyen.usermanagent.domain.UserInfo} entity. This class is used
 * in {@link vn.com.truyen.usermanagent.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter initials;

    private StringFilter comment;

    private IntegerFilter mobile;

    private LongFilter userId;

    public UserInfoCriteria() {
    }

    public UserInfoCriteria(UserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.initials = other.initials == null ? null : other.initials.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getInitials() {
        return initials;
    }

    public void setInitials(StringFilter initials) {
        this.initials = initials;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public IntegerFilter getMobile() {
        return mobile;
    }

    public void setMobile(IntegerFilter mobile) {
        this.mobile = mobile;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(initials, that.initials) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        firstName,
        lastName,
        initials,
        comment,
        mobile,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (initials != null ? "initials=" + initials + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
