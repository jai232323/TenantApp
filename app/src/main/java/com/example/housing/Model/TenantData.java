package com.example.housing.Model;

public class TenantData {

    private String T_ID,T_Name,T_MobileNumber,Member_ID,Member_Name,Member_Address,Member_MobileNumber;

    public TenantData() {
    }

    public TenantData(String t_ID, String t_Name, String t_MobileNumber, String member_ID, String member_Name, String member_Address, String member_MobileNumber) {
        T_ID = t_ID;
        T_Name = t_Name;
        T_MobileNumber = t_MobileNumber;
        Member_ID = member_ID;
        Member_Name = member_Name;
        Member_Address = member_Address;
        Member_MobileNumber = member_MobileNumber;
    }

    public String getT_ID() {
        return T_ID;
    }

    public void setT_ID(String t_ID) {
        T_ID = t_ID;
    }

    public String getT_Name() {
        return T_Name;
    }

    public void setT_Name(String t_Name) {
        T_Name = t_Name;
    }

    public String getT_MobileNumber() {
        return T_MobileNumber;
    }

    public void setT_MobileNumber(String t_MobileNumber) {
        T_MobileNumber = t_MobileNumber;
    }

    public String getMember_ID() {
        return Member_ID;
    }

    public void setMember_ID(String member_ID) {
        Member_ID = member_ID;
    }

    public String getMember_Name() {
        return Member_Name;
    }

    public void setMember_Name(String member_Name) {
        Member_Name = member_Name;
    }

    public String getMember_Address() {
        return Member_Address;
    }

    public void setMember_Address(String member_Address) {
        Member_Address = member_Address;
    }

    public String getMember_MobileNumber() {
        return Member_MobileNumber;
    }

    public void setMember_MobileNumber(String member_MobileNumber) {
        Member_MobileNumber = member_MobileNumber;
    }
}
