package vn.com.truyen.usermanagent.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "can_change")
    private Boolean canChange;

    @Column(name = "must_change")
    private Boolean mustChange;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private ZonedDateTime resetDate;

    @Column(name = "code")
    private String code;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "users_role",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GroupTranslate> logins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Users login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Users passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public Users email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Users imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Users activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean isLocked() {
        return locked;
    }

    public Users locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isCanChange() {
        return canChange;
    }

    public Users canChange(Boolean canChange) {
        this.canChange = canChange;
        return this;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean isMustChange() {
        return mustChange;
    }

    public Users mustChange(Boolean mustChange) {
        this.mustChange = mustChange;
        return this;
    }

    public void setMustChange(Boolean mustChange) {
        this.mustChange = mustChange;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public Users activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public Users resetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public Users resetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public String getCode() {
        return code;
    }

    public Users code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Users roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Users addRole(Role role) {
        this.roles.add(role);
        role.getLogins().add(this);
        return this;
    }

    public Users removeRole(Role role) {
        this.roles.remove(role);
        role.getLogins().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<GroupTranslate> getLogins() {
        return logins;
    }

    public Users logins(Set<GroupTranslate> groupTranslates) {
        this.logins = groupTranslates;
        return this;
    }

    public Users addLogin(GroupTranslate groupTranslate) {
        this.logins.add(groupTranslate);
        groupTranslate.getUsers().add(this);
        return this;
    }

    public Users removeLogin(GroupTranslate groupTranslate) {
        this.logins.remove(groupTranslate);
        groupTranslate.getUsers().remove(this);
        return this;
    }

    public void setLogins(Set<GroupTranslate> groupTranslates) {
        this.logins = groupTranslates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + isActivated() + "'" +
            ", locked='" + isLocked() + "'" +
            ", canChange='" + isCanChange() + "'" +
            ", mustChange='" + isMustChange() + "'" +
            ", activationKey='" + getActivationKey() + "'" +
            ", resetKey='" + getResetKey() + "'" +
            ", resetDate='" + getResetDate() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
