package vn.com.truyen.usermanagent.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link vn.com.truyen.usermanagent.domain.UserInfo} entity.
 */
public class UserInfoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String fullName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String initials;

    @NotNull
    private String comment;

    @NotNull
    private Integer mobile;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long usersId) {
        this.userId = usersId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String usersLogin) {
        this.userLogin = usersLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }

        return id != null && id.equals(((UserInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", initials='" + getInitials() + "'" +
            ", comment='" + getComment() + "'" +
            ", mobile=" + getMobile() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
