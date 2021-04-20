package com.cassini.test.enums;

public enum EndPOints {
    RESOURCE("/api/unknown"),
    USER_REGISTRATION("/api/users"),
    USER_LOGIN("/api/login");


    private String url;

    private EndPOints(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }


}
