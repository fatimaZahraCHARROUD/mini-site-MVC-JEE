package com.tp2;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/succes")
public class SuccesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // getSession(false) : on ne crée PAS de session s'il n'y en a pas
        HttpSession session = req.getSession(false);
        String utilisateur  = (session != null)
                ? (String) session.getAttribute("utilisateur")
                : null;

        // Pas de session → retour au formulaire
        if (utilisateur == null) {
            resp.sendRedirect("inscription");
            return;
        }

        String email = (String) session.getAttribute("email");
        Integer age  = (Integer) session.getAttribute("age");

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<!DOCTYPE html><html><head><meta charset='UTF-8'>"
                + "<title>Succès</title></head><body>");
        resp.getWriter().println("<h1>Bienvenue " + utilisateur + " !</h1>");
        resp.getWriter().println("<ul>");
        resp.getWriter().println("  <li>Email : " + email + "</li>");
        resp.getWriter().println("  <li>Age : " + age + "</li>");
        resp.getWriter().println("</ul>");
        resp.getWriter().println("<a href='inscription'>Nouvelle inscription</a>");
        resp.getWriter().println("</body></html>");
    }
}