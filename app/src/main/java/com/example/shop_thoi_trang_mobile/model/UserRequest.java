package com.example.shop_thoi_trang_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserRequest implements Parcelable   {
    private String usersName;
    private String usersEmail;
    private String usersPhone;
    private String usersAddress;
    private String passwords;
    public UserRequest(){}
    public UserRequest(String usersName, String usersEmail, String usersPhone, String usersAddress, String passwords) {
        this.usersName = usersName;
        this.usersEmail = usersEmail;
        this.usersPhone = usersPhone;
        this.usersAddress = usersAddress;
        this.passwords = passwords;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
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

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    protected UserRequest(Parcel in) {
        usersName = in.readString();
        usersEmail = in.readString();
        usersPhone = in.readString();
        usersAddress = in.readString();
        passwords = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(usersName);
        dest.writeString(usersEmail);
        dest.writeString(usersPhone);
        dest.writeString(usersAddress);
        dest.writeString(passwords);
    }
    public static final Creator<UserRequest> CREATOR = new Creator<UserRequest>() {
        @Override
        public UserRequest createFromParcel(Parcel in) {
            return new UserRequest(in);
        }

        @Override
        public UserRequest[] newArray(int size) {
            return new UserRequest[size];
        }
    };
}
