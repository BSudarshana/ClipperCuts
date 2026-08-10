package com.clippercuts.dto;

public class LookupResponse {

    private Integer id;
    private String name;

    public LookupResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}