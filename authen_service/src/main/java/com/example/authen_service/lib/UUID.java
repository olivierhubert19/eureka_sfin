package com.example.authen_service.lib;

public class UUID {
    public static String genID(){
        java.util.UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }
}
