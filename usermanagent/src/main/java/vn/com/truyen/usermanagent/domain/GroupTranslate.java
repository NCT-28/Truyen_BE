package vn.com.truyen.usermanagent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GroupTranslate.
 */
@Entity
@Table(name = "group_translate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupTranslate implements Serializable {

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

    @Column(name = "code")
    private String code;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "group_translate_user",
               joinColumns = @JoinColumn(name = "group_translate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<Users> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "group_translate_role",
               joinColumns = @JoinColumn(name = "group_translate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

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

    public GroupTranslate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GroupTranslate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public GroupTranslate code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public GroupTranslate users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public GroupTranslate addUser(Users users) {
        this.users.add(users);
        users.getLogins().add(this);
        return this;
    }

    public GroupTranslate removeUser(Users users) {
        this.users.remove(users);
        users.getLogins().remove(this);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public GroupTranslate roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public GroupTranslate addRole(Role role) {
        this.roles.add(role);
        role.getNames().add(this);
        return this;
    }

    public GroupTranslate removeRole(Role role) {
        this.roles.remove(role);
        role.getNames().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupTranslate)) {
            return false;
        }
        return id != null && id.equals(((GroupTranslate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupTranslate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
