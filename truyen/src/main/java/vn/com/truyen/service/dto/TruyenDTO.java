package vn.com.truyen.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link vn.com.truyen.domain.Truyen} entity.
 */
public class TruyenDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private Integer soChuong;

    private String nguon;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Boolean fulls;

    private Boolean hot;

    private Boolean news;

    private Boolean locked;

    private String code;


    private Long authorId;

    private String authorName;
    
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

    public Integer getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(Integer soChuong) {
        this.soChuong = soChuong;
    }

    public String getNguon() {
        return nguon;
    }

    public void setNguon(String nguon) {
        this.nguon = nguon;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isFulls() {
        return fulls;
    }

    public void setFulls(Boolean fulls) {
        this.fulls = fulls;
    }

    public Boolean isHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Boolean isNews() {
        return news;
    }

    public void setNews(Boolean news) {
        this.news = news;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TruyenDTO)) {
            return false;
        }

        return id != null && id.equals(((TruyenDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TruyenDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", soChuong=" + getSoChuong() +
            ", nguon='" + getNguon() + "'" +
            ", image='" + getImage() + "'" +
            ", fulls='" + isFulls() + "'" +
            ", hot='" + isHot() + "'" +
            ", news='" + isNews() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            ", authorId=" + getAuthorId() +
            ", authorName='" + getAuthorName() + "'" +
            "}";
    }
}
