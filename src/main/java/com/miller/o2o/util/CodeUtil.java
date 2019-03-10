package com.miller.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by miller on 2019/2/27
 *
 * @author Miller
 */
public class CodeUtil {




    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeExpected.equalsIgnoreCase(verifyCodeActual)) {
            return false;
        }
        return true;
     }
}
