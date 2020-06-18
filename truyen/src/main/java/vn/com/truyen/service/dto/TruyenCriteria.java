package vn.com.truyen.service.dto;

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
 * Criteria class for the {@link vn.com.truyen.domain.Truyen} entity. This class is used
 * in {@link vn.com.truyen.web.rest.TruyenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /truyens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TruyenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter soChuong;

    private StringFilter nguon;

    private BooleanFilter fulls;

    private BooleanFilter hot;

    private BooleanFilter news;

    private BooleanFilter locked;

    private StringFilter code;

    private LongFilter chuongId;

    private LongFilter viewId;

    private LongFilter feadbackId;

    private LongFilter authorId;

    private LongFilter nameId;

    public TruyenCriteria() {
    }

    public TruyenCriteria(TruyenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.soChuong = other.soChuong == null ? null : other.soChuong.copy();
        this.nguon = other.nguon == null ? null : other.nguon.copy();
        this.fulls = other.fulls == null ? null : other.fulls.copy();
        this.hot = other.hot == null ? null : other.hot.copy();
        this.news = other.news == null ? null : other.news.copy();
        this.locked = other.locked == null ? null : other.locked.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.chuongId = other.chuongId == null ? null : other.chuongId.copy();
        this.viewId = other.viewId == null ? null : other.viewId.copy();
        this.feadbackId = other.feadbackId == null ? null : other.feadbackId.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
    }

    @Override
    public TruyenCriteria copy() {
        return new TruyenCriteria(this);
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

    public IntegerFilter getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(IntegerFilter soChuong) {
        this.soChuong = soChuong;
    }

    public StringFilter getNguon() {
        return nguon;
    }

    public void setNguon(StringFilter nguon) {
        this.nguon = nguon;
    }

    public BooleanFilter getFulls() {
        return fulls;
    }

    public void setFulls(BooleanFilter fulls) {
        this.fulls = fulls;
    }

    public BooleanFilter getHot() {
        return hot;
    }

    public void setHot(BooleanFilter hot) {
        this.hot = hot;
    }

    public BooleanFilter getNews() {
        return news;
    }

    public void setNews(BooleanFilter news) {
        this.news = news;
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

    public LongFilter getChuongId() {
        return chuongId;
    }

    public void setChuongId(LongFilter chuongId) {
        this.chuongId = chuongId;
    }

    public LongFilter getViewId() {
        return viewId;
    }

    public void setViewId(LongFilter viewId) {
        this.viewId = viewId;
    }

    public LongFilter getFeadbackId() {
        return feadbackId;
    }

    public void setFeadbackId(LongFilter feadbackId) {
        this.feadbackId = feadbackId;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
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
        final TruyenCriteria that = (TruyenCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(soChuong, that.soChuong) &&
            Objects.equals(nguon, that.nguon) &&
            Objects.equals(fulls, that.fulls) &&
            Objects.equals(hot, that.hot) &&
            Objects.equals(news, that.news) &&
            Objects.equals(locked, that.locked) &&
            Objects.equals(code, that.code) &&
            Objects.equals(chuongId, that.chuongId) &&
            Objects.equals(viewId, that.viewId) &&
            Objects.equals(feadbackId, that.feadbackId) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(nameId, that.nameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        soChuong,
        nguon,
        fulls,
        hot,
        news,
        locked,
        code,
        chuongId,
        viewId,
        feadbackId,
        authorId,
        nameId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TruyenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (soChuong != null ? "soChuong=" + soChuong + ", " : "") +
                (nguon != null ? "nguon=" + nguon + ", " : "") +
                (fulls != null ? "fulls=" + fulls + ", " : "") +
                (hot != null ? "hot=" + hot + ", " : "") +
                (news != null ? "news=" + news + ", " : "") +
                (locked != null ? "locked=" + locked + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (chuongId != null ? "chuongId=" + chuongId + ", " : "") +
                (viewId != null ? "viewId=" + viewId + ", " : "") +
                (feadbackId != null ? "feadbackId=" + feadbackId + ", " : "") +
                (authorId != null ? "authorId=" + authorId + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
            "}";
    }

}
