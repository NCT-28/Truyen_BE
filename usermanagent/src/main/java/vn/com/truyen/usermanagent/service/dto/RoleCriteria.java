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
 * Criteria class for the {@link vn.com.truyen.usermanagent.domain.Role} entity. This class is used
 * in {@link vn.com.truyen.usermanagent.web.rest.RoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter content;

    private BooleanFilter locked;

    private StringFilter code;

    private LongFilter functionId;

    private LongFilter nameId;

    private LongFilter loginId;

    public RoleCriteria() {
    }

    public RoleCriteria(RoleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.locked = other.locked == null ? null : other.locked.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.functionId = other.functionId == null ? null : other.functionId.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
        this.loginId = other.loginId == null ? null : other.loginId.copy();
    }

    @Override
    public RoleCriteria copy() {
        return new RoleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public BooleanFilter getLocked() {
        return locked;
    }

    public void setLocked(BooleanFilter locked) {
        this.locked = locked;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getFunctionId() {
        return functionId;
    }

    public void setFunctionId(LongFilter functionId) {
        this.functionId = functionId;
    }

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
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
        final RoleCriteria that = (RoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(content, that.content) &&
            Objects.equals(locked, that.locked) &&
            Objects.equals(code, that.code) &&
            Objects.equals(functionId, that.functionId) &&
            Objects.equals(nameId, that.nameId) &&
            Objects.equals(loginId, that.loginId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        content,
        locked,
        code,
        functionId,
        nameId,
        loginId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (locked != null ? "locked=" + locked + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (functionId != null ? "functionId=" + functionId + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
                (loginId != null ? "loginId=" + loginId + ", " : "") +
            "}";
    }

}
