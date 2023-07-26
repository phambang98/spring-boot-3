package com.example.core.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

public class CookieUtils {

    public static Cookie getCookie(HttpServletRequest request, String name) {
        var cookies = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals(name)).findFirst();
        if (cookies.isPresent()) {
            return cookies.get();
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        var cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        var cookies = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals(name)).findFirst();
        if (cookies.isPresent()) {
            cookies.get().setValue("/");
            cookies.get().setPath("/");
            cookies.get().setHttpOnly(true);
            cookies.get().setMaxAge(0);
            response.addCookie(cookies.get());
        }
    }

    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize((Serializable) object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
