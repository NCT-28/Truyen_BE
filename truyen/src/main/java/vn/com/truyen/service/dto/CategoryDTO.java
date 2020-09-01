package vn.com.truyen.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link vn.com.truyen.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean locked;

    private String code;

    private Set<TruyenDTO> truyens = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<TruyenDTO> getTruyens() {
        return truyens;
    }

    public void setTruyens(Set<TruyenDTO> truyens) {
        this.truyens = truyens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            ", truyens='" + getTruyens() + "'" +
            "}";
    }
}
