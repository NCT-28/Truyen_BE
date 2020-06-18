package vn.com.truyen.usermanagent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "role_function",
               joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "function_id", referencedColumnName = "id"))
    private Set<Functions> functions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GroupTranslate> names = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Users> logins = new HashSet<>();

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

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public Role content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Role locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCode() {
        return code;
    }

    public Role code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Functions> getFunctions() {
        return functions;
    }

    public Role functions(Set<Functions> functions) {
        this.functions = functions;
        return this;
    }

    public Role addFunction(Functions functions) {
        this.functions.add(functions);
        functions.getNames().add(this);
        return this;
    }

    public Role removeFunction(Functions functions) {
        this.functions.remove(functions);
        functions.getNames().remove(this);
        return this;
    }

    public void setFunctions(Set<Functions> functions) {
        this.functions = functions;
    }

    public Set<GroupTranslate> getNames() {
        return names;
    }

    public Role names(Set<GroupTranslate> groupTranslates) {
        this.names = groupTranslates;
        return this;
    }

    public Role addName(GroupTranslate groupTranslate) {
        this.names.add(groupTranslate);
        groupTranslate.getRoles().add(this);
        return this;
    }

    public Role removeName(GroupTranslate groupTranslate) {
        this.names.remove(groupTranslate);
        groupTranslate.getRoles().remove(this);
        return this;
    }

    public void setNames(Set<GroupTranslate> groupTranslates) {
        this.names = groupTranslates;
    }

    public Set<Users> getLogins() {
        return logins;
    }

    public Role logins(Set<Users> users) {
        this.logins = users;
        return this;
    }

    public Role addLogin(Users users) {
        this.logins.add(users);
        users.getRoles().add(this);
        return this;
    }

    public Role removeLogin(Users users) {
        this.logins.remove(users);
        users.getRoles().remove(this);
        return this;
    }

    public void setLogins(Set<Users> users) {
        this.logins = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", locked='" + isLocked() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
