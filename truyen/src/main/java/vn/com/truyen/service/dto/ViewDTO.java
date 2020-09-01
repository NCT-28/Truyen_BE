package vn.com.truyen.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link vn.com.truyen.domain.View} entity.
 */
public class ViewDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime dayView;

    private Integer views;


    private Long truyenId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDayView() {
        return dayView;
    }

    public void setDayView(ZonedDateTime dayView) {
        this.dayView = dayView;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Long getTruyenId() {
        return truyenId;
    }

    public void setTruyenId(Long truyenId) {
        this.truyenId = truyenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewDTO)) {
            return false;
        }

        return id != null && id.equals(((ViewDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewDTO{" +
            "id=" + getId() +
            ", dayView='" + getDayView() + "'" +
            ", views=" + getViews() +
            ", truyenId=" + getTruyenId() +
            "}";
    }
}
