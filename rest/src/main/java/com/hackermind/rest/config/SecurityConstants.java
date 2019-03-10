package com.hackermind.rest.config;

public class SecurityConstants {
    public static final String SECRET = "hackermind@!2019";
    public static final long EXPIRATION_TIME = 5*60*1000; // 5 min
    public static final long EXPIRATION_TIME_ADMIN = 24 *60*60*1000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_URL = "/api/user/login";
}