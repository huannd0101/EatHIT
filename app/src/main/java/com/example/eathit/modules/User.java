package com.example.eathit.modules;

public class User {
    private String userId;
    private String fullName;
//    private String userName;
//    private String email;
//    private String gender;
//    private String birthDay;
//    private String phoneNumber;
    private String profilePic;
    private String isOnline;

//    public User(String userId, String fullName, String userName, String email, String gender, String birthDay, String phoneNumber, String profilePic, String isOnline) {
//        this.userId = userId;
//        this.fullName = fullName;
//        this.userName = userName;
//        this.email = email;
//        this.gender = gender;
//        this.birthDay = birthDay;
//        this.phoneNumber = phoneNumber;
//        this.profilePic = profilePic;
//        this.isOnline = isOnline;
//    }


    public User(String userId, String fullName, String profilePic, String isOnline) {
        this.userId = userId;
        this.fullName = fullName;
        this.profilePic = profilePic;
        this.isOnline = isOnline;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", isOnline='" + isOnline + '\'' +
                '}';
    }
}
