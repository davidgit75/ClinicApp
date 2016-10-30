package com.davidh.davidh.clinicapp;

public class ConfigUrl {
    private static String development = "http//localhost:3000/clinicapp";
    private static String production = "";
    private static String selectedType = development;

    public static String checkUserInMedicalCenter = selectedType + "/medicalcenter/checkUser";
    public static String checkUserInApp = selectedType + "/app/checkUser";
    public static String loginUserInApp = selectedType + "/app/loginUser";
    public static String registerUserInApp = selectedType + "/app/registerUser";
}
