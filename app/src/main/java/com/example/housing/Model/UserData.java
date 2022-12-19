package com.example.housing.Model;

public class UserData {

    private String U_Image,U_Name,U_MobileNumber,U_Id;

    public UserData() {
    }

    public UserData(String u_Image, String u_Name, String u_MobileNumber, String u_ID) {
        U_Image = u_Image;
        U_Name = u_Name;
        U_MobileNumber = u_MobileNumber;
        U_Id = u_ID;
    }

    public String getU_Image() {
        return U_Image;
    }

    public void setU_Image(String u_Image) {
        U_Image = u_Image;
    }

    public String getU_Name() {
        return U_Name;
    }

    public void setU_Name(String u_Name) {
        U_Name = u_Name;
    }

    public String getU_MobileNumber() {
        return U_MobileNumber;
    }

    public void setU_MobileNumber(String u_MobileNumber) {
        U_MobileNumber = u_MobileNumber;
    }

    public String getU_ID() {
        return U_Id;
    }

    public void setU_ID(String u_ID) {
        U_Id = u_ID;
    }
}
