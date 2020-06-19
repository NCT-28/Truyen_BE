package vn.com.truyen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Chuong.
 */
@Entity
@Table(name = "chuong")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chuong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JsonIgnoreProperties(value = "chuongs", allowSetters = true)
    private Truyen truyen;

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

    public Chuong name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public Chuong content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Chuong locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCode() {
        return code;
    }

    public Chuong code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    

    public Chuong truyen(Truyen truyen) {
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
        if (!(o instanceof Chuong)) {
            return false;
        }
        return id != null && id.equals(((Chuong) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chuong{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
