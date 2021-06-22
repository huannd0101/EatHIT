package com.example.eathit.ui.slideshow;

import java.sql.Timestamp;

public class Friend {
    private String id;
    private String username;
    private String role;
    private String fullname;
    private String gender;
    private boolean status;
    private String linkAvt;
    private String email;

    private String creaetAt;
    private String updateAt;


    public Friend() {
    }

    public Friend(String id, String username, String role, String fullname, String gender, boolean status, String linkAvt, String email, String createAt, String updateAt) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.fullname = fullname;
        this.gender = gender;
        this.status = status;
        this.linkAvt = linkAvt;
        this.email = email;
        this.creaetAt= createAt;
        this.updateAt= updateAt;
    }

    public String getCreateAt() {
        return creaetAt;
    }

    public void setCreateAt(String createAt) {
        this.creaetAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
