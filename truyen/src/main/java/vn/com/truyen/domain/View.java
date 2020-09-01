package vn.com.truyen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A View.
 */
@Entity
@Table(name = "view")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class View implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day_view")
    private ZonedDateTime dayView;

    @Column(name = "views")
    private Integer views;

    @ManyToOne
    @JsonIgnoreProperties(value = "views", allowSetters = true)
    private Truyen truyen;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDayView() {
        return dayView;
    }

    public View dayView(ZonedDateTime dayView) {
        this.dayView = dayView;
        return this;
    }

    public void setDayView(ZonedDateTime dayView) {
        this.dayView = dayView;
    }

    public Integer getViews() {
        return views;
    }

    public View views(Integer views) {
        this.views = views;
        return this;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public View truyen(Truyen truyen) {
        this.truyen = truyen;
        return this;
    }

    public void setTruyen(Truyen truyen) {
        this.truyen = truyen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof View)) {
            return false;
        }
        return id != null && id.equals(((View) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "View{" +
            "id=" + getId() +
            ", dayView='" + getDayView() + "'" +
            ", views=" + getViews() +
            "}";
    }
}
