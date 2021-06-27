package com.example.eathit.modules;

import android.os.Parcel;
import android.os.Parcelable;

public class Person {
    private String id, userName, role, fullName, gender, linkAvatar, email, createAt, updateAt;
    private Boolean status;

    public Person() {
    }

    public Person(String id, String userName, String role, String fullName, String gender, String linkAvatar, String email, String createAt, String updateAt, Boolean status) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.fullName = fullName;
        this.gender = gender;
        this.linkAvatar = linkAvatar;
        this.email = email;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }

    public Person(String fullName, String linkAvatar) {
        this.fullName = fullName;
        this.linkAvatar = linkAvatar;
    }
//    public static final Creator<Person> CREATOR = new Creator<Person>() {
//        @Override
//        public Person createFromParcel(Parcel source) {
//            return new Person(source);
//        }
//
//        @Override
//        public Person[] newArray(int size) {
//            return new Person[size];
//        }
//    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", linkAvatar='" + linkAvatar + '\'' +
                ", email='" + email + '\'' +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", status=" + status +
                '}';
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
}
