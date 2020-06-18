package vn.com.truyen.usermanagent.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link vn.com.truyen.usermanagent.domain.GroupTranslate} entity.
 */
public class GroupTranslateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String code;

    private Set<UsersDTO> users = new HashSet<>();
    private Set<RoleDTO> roles = new HashSet<>();
    
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<UsersDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersDTO> users) {
        this.users = users;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupTranslateDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupTranslateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupTranslateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            ", users='" + getUsers() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }
}
