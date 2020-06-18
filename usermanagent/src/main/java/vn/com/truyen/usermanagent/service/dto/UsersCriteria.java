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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link vn.com.truyen.usermanagent.domain.Users} entity. This class is used
 * in {@link vn.com.truyen.usermanagent.web.rest.UsersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter passwordHash;

    private StringFilter email;

    private StringFilter imageUrl;

    private BooleanFilter activated;

    private BooleanFilter locked;

    private BooleanFilter canChange;

    private BooleanFilter mustChange;

    private StringFilter activationKey;

    private StringFilter resetKey;

    private ZonedDateTimeFilter resetDate;

    private StringFilter code;

    private LongFilter roleId;

    private LongFilter loginId;

    public UsersCriteria() {
    }

    public UsersCriteria(UsersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.locked = other.locked == null ? null : other.locked.copy();
        this.canChange = other.canChange == null ? null : other.canChange.copy();
        this.mustChange = other.mustChange == null ? null : other.mustChange.copy();
        this.activationKey = other.activationKey == null ? null : other.activationKey.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
        this.loginId = other.loginId == null ? null : other.loginId.copy();
    }

    @Override
    public UsersCriteria copy() {
        return new UsersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public BooleanFilter getLocked() {
        return locked;
    }

    public void setLocked(BooleanFilter locked) {
        this.locked = locked;
    }

    public BooleanFilter getCanChange() {
        return canChange;
    }

    public void setCanChange(BooleanFilter canChange) {
        this.canChange = canChange;
    }

    public BooleanFilter getMustChange() {
        return mustChange;
    }

    public void setMustChange(BooleanFilter mustChange) {
        this.mustChange = mustChange;
    }

    public StringFilter getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(StringFilter activationKey) {
        this.activationKey = activationKey;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTimeFilter getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTimeFilter resetDate) {
        this.resetDate = resetDate;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }

    public LongFilter getLoginId() {
        return loginId;
    }

    public void setLoginId(LongFilter loginId) {
        this.loginId = loginId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UsersCriteria that = (UsersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(locked, that.locked) &&
            Objects.equals(canChange, that.canChange) &&
            Objects.equals(mustChange, that.mustChange) &&
            Objects.equals(activationKey, that.activationKey) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(code, that.code) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(loginId, that.loginId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        passwordHash,
        email,
        imageUrl,
        activated,
        locked,
        canChange,
        mustChange,
        activationKey,
        resetKey,
        resetDate,
        code,
        roleId,
        loginId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (locked != null ? "locked=" + locked + ", " : "") +
                (canChange != null ? "canChange=" + canChange + ", " : "") +
                (mustChange != null ? "mustChange=" + mustChange + ", " : "") +
                (activationKey != null ? "activationKey=" + activationKey + ", " : "") +
                (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
                (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
                (loginId != null ? "loginId=" + loginId + ", " : "") +
            "}";
    }

}
