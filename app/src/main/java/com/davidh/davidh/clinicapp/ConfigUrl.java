package com.davidh.davidh.clinicapp;

public abstract class ConfigUrl {
    private static String development = "http://192.168.0.21:3000/clinicapp";
    private static String production = "";
    private static String selectedType = development;

    public static String loginUserInApp = selectedType + "/app/user/login";
    public static String registerUserInApp = selectedType + "/app/user/new";
}
