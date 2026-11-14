package com.tp2.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import com.tp2.model.ProduitDAO;

@WebListener
public class AppInitListener implements ServletContextListener {

    //  appelée UNE SEULE FOIS quand Tomcat démarre l'application
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
         ProduitDAO dao = new ProduitDAO();
        
        // On le stocke dans le ServletContext (qui est partagé par toutes les Servlets)
        sce.getServletContext().setAttribute("produitDAO", dao);
        
        System.out.println(" DAO initialisé avec succès au démarrage de l'application.");
    }

   
}