package com.example.shop_thoi_trang_mobile.model;

public class UserUpdateRequest {
    private int userid;
    private String usersName;
    private String usersPhone;
    private String usersAddress;

    public UserUpdateRequest(int userid, String usersName, String usersPhone, String usersAddress) {
        this.userid = userid;
        this.usersName = usersName;
        this.usersPhone = usersPhone;
        this.usersAddress = usersAddress;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersPhone() {
        return usersPhone;
    }

    public void setUsersPhone(String usersPhone) {
        this.usersPhone = usersPhone;
    }

    public String getUsersAddress() {
        return usersAddress;
    }

    public void setUsersAddress(String usersAddress) {
        this.usersAddress = usersAddress;
    }
}
