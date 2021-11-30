package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "medical5", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String email;
    private String roles;
    private String password;
    private String name;
    private String photouser;

    public UserEntity() {
    }

    public UserEntity(String username, String email, String roles, String password, String name) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.name = name;
    }

    public UserEntity(String username, String email, String roles, String password, String name , String photouser) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.name = name;
        this.photouser = photouser;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 180)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "roles", nullable = false, length = 100)
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "photouser", nullable = true, length = 500)
    public String getPhotouser() {
        return photouser;
    }

    public void setPhotouser(String photouser) {
        this.photouser = photouser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(roles, that.roles) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(photouser, that.photouser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, roles, password, name, photouser);
    }
}