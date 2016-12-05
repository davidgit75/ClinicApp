package com.davidh.davidh.clinicapp;

public abstract class UrlService {
    private static String development = "http://172.17.2.38:3000/clinicapp";
    private static String production = "";
    private static String selectedType = development;

    public static String loginUserInApp = selectedType + "/app/user/login";
    public static String registerUserInApp = selectedType + "/app/user/new";

    public static String getMedics = selectedType + "/app/medics";

    public static String checkExistPatient = selectedType + "/app/user/check";
    public static String sendNewHistory = selectedType + "/app/history/new";
    public static String getHistoryByPatient = selectedType + "/app/history/";

    public static String editProfile = selectedType + "/app/profile/edit";
}
