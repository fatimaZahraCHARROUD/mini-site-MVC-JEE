package com.tp2.web;

import java.io.IOException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
 @Override
 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
 throws IOException, ServletException {
 
	 
      
	 // TODO 1 : récupérer "user" dans la session (sans créer de session)

HttpSession session = req.getSession(false);
 String user = (String) session.getAttribute("user");

 // TODO 2 : le placer dans la requête : req.setAttribute("user", ...)

 req.setAttribute("user", user);

 // TODO 3 : forward vers /WEB-INF/views/dashboard.jsp
 req.getRequestDispatcher("/WEB-INF/views/dash.jsp")
 .forward(req, resp);

 }
}
