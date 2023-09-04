package com.assignment.rickandmorty.utils;

public enum StatusColorCode {
    ALIVE_COLOR("#00B241"),
    DEAD_COLOR("#A9A9A9"),
    UNKNOWN_COLOR("#FF5733");

    private final String code;

    StatusColorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
