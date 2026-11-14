package com.tp2;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

 @Override
 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
 throws IOException, ServletException {

 Part filePart = req.getPart("file");
 String fileName = filePart.getSubmittedFileName();

 // 1. Obtenir le chemin absolu vers le dossier webapp (qui contient WEB-INF)
 String webAppPath = getServletContext().getRealPath("");
 
 // 2. Construire le chemin absolu vers le dossier "uploads"
 File uploadDir = new File(webAppPath, "uploads");
 
 // 3. Créer le dossier s'il n'existe pas
 if (!uploadDir.exists()) {
     uploadDir.mkdirs();
 }

 // 4. Construire le chemin complet du fichier et l'écrire
 File fileToSave = new File(uploadDir, fileName);
 filePart.write(fileToSave.getAbsolutePath());
 
 resp.setContentType("text/html");
 resp.getWriter().println("Fichier uploade: " + fileName);
 }
}