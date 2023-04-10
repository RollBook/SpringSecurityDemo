package com.fall.securitydemo02.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FAll
 * @date 2023/4/4 18:33
 */
public class WebUtils {

    public static void renderingString(HttpServletResponse response,String string,Integer status) {
        try {
            response.setStatus(status);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
