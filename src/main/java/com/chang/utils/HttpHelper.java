package com.chang.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpHelper
 * ll.z
 * 2018.04.21
 */
public class HttpHelper {

    public static boolean isAjaxRequest(HttpServletRequest req) {
        boolean isAjaxRequest = false;
        String header = req.getHeader("x-requested-with");
        if (StringUtils.isNotBlank(header) && header.equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }
}
