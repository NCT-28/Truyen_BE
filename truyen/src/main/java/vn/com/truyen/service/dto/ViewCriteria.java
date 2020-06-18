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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link vn.com.truyen.domain.View} entity. This class is used
 * in {@link vn.com.truyen.web.rest.ViewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ViewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dayView;

    private IntegerFilter views;

    private LongFilter truyenId;

    public ViewCriteria() {
    }

    public ViewCriteria(ViewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dayView = other.dayView == null ? null : other.dayView.copy();
        this.views = other.views == null ? null : other.views.copy();
        this.truyenId = other.truyenId == null ? null : other.truyenId.copy();
    }

    @Override
    public ViewCriteria copy() {
        return new ViewCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDayView() {
        return dayView;
    }

    public void setDayView(ZonedDateTimeFilter dayView) {
        this.dayView = dayView;
    }

    public IntegerFilter getViews() {
        return views;
    }

    public void setViews(IntegerFilter views) {
        this.views = views;
    }

    public LongFilter getTruyenId() {
        return truyenId;
    }

    public void setTruyenId(LongFilter truyenId) {
        this.truyenId = truyenId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViewCriteria that = (ViewCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dayView, that.dayView) &&
            Objects.equals(views, that.views) &&
            Objects.equals(truyenId, that.truyenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dayView,
        views,
        truyenId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dayView != null ? "dayView=" + dayView + ", " : "") +
                (views != null ? "views=" + views + ", " : "") +
                (truyenId != null ? "truyenId=" + truyenId + ", " : "") +
            "}";
    }

}
