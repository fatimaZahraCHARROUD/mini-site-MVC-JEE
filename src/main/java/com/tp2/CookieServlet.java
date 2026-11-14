package com.tp2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

         if ("set".equals(action)) {
            Cookie cookie = new Cookie("theme", "dark");
            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 jours
            cookie.setPath("/"); // Valable pour tout le site
            resp.addCookie(cookie);
            
             resp.sendRedirect(req.getContextPath() + "/dashboard"); 
            return;
        }

         if ("remove".equals(action)) {
            Cookie cookie = new Cookie("theme", "");
            cookie.setMaxAge(0); // Détruit le cookie
            cookie.setPath("/");
            resp.addCookie(cookie);
            
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

         resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}