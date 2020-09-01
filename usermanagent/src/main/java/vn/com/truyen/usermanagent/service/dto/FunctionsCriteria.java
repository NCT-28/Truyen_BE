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
 * Criteria class for the {@link vn.com.truyen.usermanagent.domain.Functions} entity. This class is used
 * in {@link vn.com.truyen.usermanagent.web.rest.FunctionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /functions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FunctionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter code;

    private LongFilter nameId;

    public FunctionsCriteria() {
    }

    public FunctionsCriteria(FunctionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
    }

    @Override
    public FunctionsCriteria copy() {
        return new FunctionsCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FunctionsCriteria that = (FunctionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(code, that.code) &&
            Objects.equals(nameId, that.nameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        code,
        nameId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FunctionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
            "}";
    }

}
