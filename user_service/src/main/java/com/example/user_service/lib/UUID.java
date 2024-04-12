package com.example.user_service.lib;

public class UUID {
    public static String generateID(){
        java.util.UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }
}
