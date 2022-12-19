package com.example.housing.Model;

public class BuildingData {

    private String P_ID,B_ID,B_Image,B_Name,B_Address,B_PostalCode;

    public BuildingData() {
    }

    public BuildingData(String p_ID, String b_ID, String b_Image, String b_Name, String b_Address, String b_PostalCode) {
        P_ID = p_ID;
        B_ID = b_ID;
        B_Image = b_Image;
        B_Name = b_Name;
        B_Address = b_Address;
        B_PostalCode = b_PostalCode;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getB_ID() {
        return B_ID;
    }

    public void setB_ID(String b_ID) {
        B_ID = b_ID;
    }

    public String getB_Image() {
        return B_Image;
    }

    public void setB_Image(String b_Image) {
        B_Image = b_Image;
    }

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String b_Name) {
        B_Name = b_Name;
    }

    public String getB_Address() {
        return B_Address;
    }

    public void setB_Address(String b_Address) {
        B_Address = b_Address;
    }

    public String getB_PostalCode() {
        return B_PostalCode;
    }

    public void setB_PostalCode(String b_PostalCode) {
        B_PostalCode = b_PostalCode;
    }
}
