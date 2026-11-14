package com.tp2;

import java.io.IOException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String erreur = req.getParameter("erreur");
        String message = "";

        if (erreur != null) {
            switch (erreur) {
                case "nom_vide":         message = "Le nom est obligatoire."; break;
                case "email_invalide":   message = "L'email est invalide."; break;
                case "age_min":          message = "Vous devez avoir au moins 18 ans."; break;
                case "age_invalide":     message = "L'âge doit être un nombre."; break;
                default:                 message = "Erreur inconnue.";
            }
        }

         resp.getWriter().println("<!DOCTYPE html><html><head><meta charset='UTF-8'>"
                + "<title>Inscription</title></head><body>");
        if (!message.isEmpty()) {
            resp.getWriter().println("<p style='color:red;'>" + message + "</p>");
        }
        resp.getWriter().println(
                "<h1>Inscription</h1>"
              + "<form method='post' action='inscription'>"
              + "  <p><input type='text' name='nom' placeholder='Nom' required></p>"
              + "  <p><input type='email' name='email' placeholder='Email' required></p>"
              + "  <p><input type='number' name='age' placeholder='Age' min='0' max='120'></p>"
              + "  <button type='submit'>S'inscrire</button>"
              + "</form>"
              + "</body></html>");
    }

     
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Encodage AVANT tout getParameter
        req.setCharacterEncoding("UTF-8");

        // 2. Récupération
        String nom    = req.getParameter("nom");
        String email  = req.getParameter("email");
        String ageStr = req.getParameter("age");

        // 3. Validation
        if (nom == null || nom.trim().isEmpty()) {
            resp.sendRedirect("inscription?erreur=nom_vide");
            return;
        }

        if (email == null || !email.contains("@")) {
            resp.sendRedirect("inscription?erreur=email_invalide");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 18) {
                resp.sendRedirect("inscription?erreur=age_min");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("inscription?erreur=age_invalide");
            return;
        }

        // 4. Création session + stockage
        HttpSession session = req.getSession(true);
        session.setAttribute("utilisateur", nom);
        session.setAttribute("email", email);
        session.setAttribute("age", age);

        // 5. Redirection vers la page de succès
        resp.sendRedirect("succes");
    }
}