package com.tp2.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // On garde les identifiants du TP2
    private static final String LOGIN_OK = "admin";
    private static final String PASS_OK  = "password";

    // Méthode appelée quand on tape l'URL /login
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // On affiche simplement la page JSP
        req.getRequestDispatcher("/WEB-INF/views/log.jsp").forward(req, resp);
    }

    // Méthode appelée quand on clique sur "Se connecter"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Pour gérer les accents correctement
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        // Si les identifiants sont bons
        if (LOGIN_OK.equals(login) && PASS_OK.equals(pass)) {
            // On crée la session et on stocke "user"
            HttpSession session = req.getSession(true);
            session.setAttribute("user", login);
            
            // On regarde si le filtre avait mémorisé une URL
            String destination = (String) session.getAttribute("redirectAfterLogin");
            if (destination != null) {
                session.removeAttribute("redirectAfterLogin"); // On nettoie
                resp.sendRedirect(destination); // On y retourne !
            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard"); // Sinon, dashboard par défaut
            }
        } else {
            // Si c'est faux, on prépare un message d'erreur
            req.setAttribute("erreur", "Identifiants incorrects.");
            req.setAttribute("login", login); // On garde ce qu'il a tapé
            
            // FORWARD : on réaffiche le formulaire avec l'erreur
            req.getRequestDispatcher("/WEB-INF/views/log.jsp").forward(req, resp);
        }
    }
}