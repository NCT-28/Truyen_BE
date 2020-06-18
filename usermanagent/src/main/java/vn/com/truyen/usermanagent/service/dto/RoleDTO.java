package vn.com.truyen.usermanagent.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link vn.com.truyen.usermanagent.domain.Role} entity.
 */
public class RoleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String content;

    private Boolean locked;

    private String code;

    private Set<FunctionsDTO> functions = new HashSet<>();
    
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Set<FunctionsDTO> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<FunctionsDTO> functions) {
        this.functions = functions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleDTO)) {
            return false;
        }

        return id != null && id.equals(((RoleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            ", functions='" + getFunctions() + "'" +
            "}";
    }
}
