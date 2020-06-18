package vn.com.truyen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Truyen.
 */
@Entity
@Table(name = "truyen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Truyen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "so_chuong")
    private Integer soChuong;

    @Column(name = "nguon")
    private String nguon;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "fulls")
    private Boolean fulls;

    @Column(name = "hot")
    private Boolean hot;

    @Column(name = "news")
    private Boolean news;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "truyen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chuong> chuongs = new HashSet<>();

    @OneToMany(mappedBy = "truyen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<View> views = new HashSet<>();

    @OneToMany(mappedBy = "truyen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Feedback> feadbacks = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "truyens", allowSetters = true)
    private Author author;

    @ManyToMany(mappedBy = "truyens")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Category> names = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Truyen name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Truyen description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSoChuong() {
        return soChuong;
    }

    public Truyen soChuong(Integer soChuong) {
        this.soChuong = soChuong;
        return this;
    }

    public void setSoChuong(Integer soChuong) {
        this.soChuong = soChuong;
    }

    public String getNguon() {
        return nguon;
    }

    public Truyen nguon(String nguon) {
        this.nguon = nguon;
        return this;
    }

    public void setNguon(String nguon) {
        this.nguon = nguon;
    }

    public byte[] getImage() {
        return image;
    }

    public Truyen image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Truyen imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isFulls() {
        return fulls;
    }

    public Truyen fulls(Boolean fulls) {
        this.fulls = fulls;
        return this;
    }

    public void setFulls(Boolean fulls) {
        this.fulls = fulls;
    }

    public Boolean isHot() {
        return hot;
    }

    public Truyen hot(Boolean hot) {
        this.hot = hot;
        return this;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Boolean isNews() {
        return news;
    }

    public Truyen news(Boolean news) {
        this.news = news;
        return this;
    }

    public void setNews(Boolean news) {
        this.news = news;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Truyen locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCode() {
        return code;
    }

    public Truyen code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Chuong> getChuongs() {
        return chuongs;
    }

    public Truyen chuongs(Set<Chuong> chuongs) {
        this.chuongs = chuongs;
        return this;
    }

    public Truyen addChuong(Chuong chuong) {
        this.chuongs.add(chuong);
        chuong.setTruyen(this);
        return this;
    }

    public Truyen removeChuong(Chuong chuong) {
        this.chuongs.remove(chuong);
        chuong.setTruyen(null);
        return this;
    }

    public void setChuongs(Set<Chuong> chuongs) {
        this.chuongs = chuongs;
    }

    public Set<View> getViews() {
        return views;
    }

    public Truyen views(Set<View> views) {
        this.views = views;
        return this;
    }

    public Truyen addView(View view) {
        this.views.add(view);
        view.setTruyen(this);
        return this;
    }

    public Truyen removeView(View view) {
        this.views.remove(view);
        view.setTruyen(null);
        return this;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public Set<Feedback> getFeadbacks() {
        return feadbacks;
    }

    public Truyen feadbacks(Set<Feedback> feedbacks) {
        this.feadbacks = feedbacks;
        return this;
    }

    public Truyen addFeadback(Feedback feedback) {
        this.feadbacks.add(feedback);
        feedback.setTruyen(this);
        return this;
    }

    public Truyen removeFeadback(Feedback feedback) {
        this.feadbacks.remove(feedback);
        feedback.setTruyen(null);
        return this;
    }

    public void setFeadbacks(Set<Feedback> feedbacks) {
        this.feadbacks = feedbacks;
    }

    public Author getAuthor() {
        return author;
    }

    public Truyen author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getNames() {
        return names;
    }

    public Truyen names(Set<Category> categories) {
        this.names = categories;
        return this;
    }

    public Truyen addName(Category category) {
        this.names.add(category);
        category.getTruyens().add(this);
        return this;
    }

    public Truyen removeName(Category category) {
        this.names.remove(category);
        category.getTruyens().remove(this);
        return this;
    }

    public void setNames(Set<Category> categories) {
        this.names = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truyen)) {
            return false;
        }
        return id != null && id.equals(((Truyen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Truyen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", soChuong=" + getSoChuong() +
            ", nguon='" + getNguon() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", fulls='" + isFulls() + "'" +
            ", hot='" + isHot() + "'" +
            ", news='" + isNews() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
