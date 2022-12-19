package com.example.housing.Model;

public class PortionsData {

    private String B_ID,P_ID,P_Name,P_Floor,P_EBCard,B_Name;

    public PortionsData() {
    }

    public PortionsData(String b_ID, String p_ID, String p_Name, String p_Floor, String p_EBCard, String b_Name) {
        B_ID = b_ID;
        P_ID = p_ID;
        P_Name = p_Name;
        P_Floor = p_Floor;
        P_EBCard = p_EBCard;
        B_Name = b_Name;
    }

    public String getB_ID() {
        return B_ID;
    }

    public void setB_ID(String b_ID) {
        B_ID = b_ID;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public String getP_Floor() {
        return P_Floor;
    }

    public void setP_Floor(String p_Floor) {
        P_Floor = p_Floor;
    }

    public String getP_EBCard() {
        return P_EBCard;
    }

    public void setP_EBCard(String p_EBCard) {
        P_EBCard = p_EBCard;
    }

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String b_Name) {
        B_Name = b_Name;
    }
}
