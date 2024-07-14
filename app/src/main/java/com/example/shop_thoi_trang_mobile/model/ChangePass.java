package com.example.shop_thoi_trang_mobile.model;

public class ChangePass {
    private String email;
    private String oldPass;
    private String newPass;

    public ChangePass(String email, String oldPass, String newPass) {
        this.email = email;
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
