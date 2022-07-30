package com.tcc.simpledocapi.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

public class Utils {

    public static String getBaseUrl(HttpServletRequest request) {

        return ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }

}
