package com.tp2.web;

import com.tp2.model.Produit;
import com.tp2.model.ProduitDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private ProduitDAO dao;

    //when create servlet , call dao
     @Override
    public void init() {
         dao = (ProduitDAO) getServletContext().getAttribute("produitDAO");
    }

     
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");

        // action = new (url : /produits?action=new)
        if ("new".equals(action)) {
            req.setAttribute("produit", new Produit()); // Objet vide pour la JSP
            req.getRequestDispatcher("/WEB-INF/views/products/form.jsp").forward(req, resp);
            return;
        }

        // action = edit, 
        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            try {
                Long id = Long.parseLong(idStr);
                Produit p = dao.findById(id);
                
                if (p == null) {
                    // Si l'ID n'existe pas, on renvoie une erreur 404
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                
                req.setAttribute("produit", p); // On envoie le produit trouvé à la JSP
                req.getRequestDispatcher("/WEB-INF/views/products/form.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND); // ID invalide
            }
            return;
        }

        //pas d'action (url : /produits) -> AFFICHAGE DE LA LISTE
        String recherche = req.getParameter("q"); // On récupère ce que l'utilisateur a tapé
        java.util.List<Produit> liste = dao.findAll(); // On récupère tous les produits

        // Si la recherche n'est pas vide, on filtre la liste
        if (recherche != null && !recherche.trim().isEmpty()) {
            String motCle = recherche.trim().toLowerCase(); // On met en minuscule pour ignorer la casse
            
            // On crée une nouvelle liste avec seulement les produits qui contiennent le mot-clé
            java.util.List<Produit> listeFiltree = new java.util.ArrayList<>();
            for (Produit p : liste) {
                if (p.getNom() != null && p.getNom().toLowerCase().contains(motCle)) {
                    listeFiltree.add(p);
                }
            }
            liste = listeFiltree; // On remplace la liste complète par la liste filtrée
        }

        // On envoie la liste (filtrée ou complète) à la JSP
        req.setAttribute("produits", liste);
        req.getRequestDispatcher("/WEB-INF/views/products/liste.jsp").forward(req, resp);  
    }

    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
         req.setCharacterEncoding("UTF-8");
        
        String action = req.getParameter("action");

        //delete
         if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                dao.delete(Long.parseLong(idStr));
                 req.getSession().setAttribute("flash", "Produit supprimé avec succès.");
            }
             resp.sendRedirect(req.getContextPath() + "/produits");
            return;
        }


         //edit
        String idStr = req.getParameter("id");
        Long id = (idStr != null && !idStr.isEmpty()) ? Long.parseLong(idStr) : null;

        //creer produit avec data taper par user
        Produit p = new Produit();
        p.setId(id);
        p.setNom(req.getParameter("nom"));
        p.setDescription(req.getParameter("description"));

        //  Map pour stocker les erreurs
        Map<String, String> erreurs = new HashMap<>();

        // valider nom
        String nom = p.getNom();
        if (nom == null || nom.trim().isEmpty()) {
            erreurs.put("nom", "Le nom est obligatoire.");
        } else if (nom.trim().length() < 2 || nom.trim().length() > 50) {
            erreurs.put("nom", "Le nom doit faire entre 2 et 50 caractères.");
        } else {
            p.setNom(nom.trim());
        }

        // valider prix
        String prixStr = req.getParameter("prix");
        if (prixStr == null || prixStr.trim().isEmpty()) {
            erreurs.put("prix", "Le prix est obligatoire.");
        } else {
            try {
                BigDecimal prix = new BigDecimal(prixStr.trim());
                if (prix.compareTo(BigDecimal.ZERO) <= 0) {
                    erreurs.put("prix", "Le prix doit être strictement positif.");
                } else {
                    p.setPrix(prix);
                }
            } catch (NumberFormatException e) {
                erreurs.put("prix", "Le prix doit être un nombre valide.");
            }
        } 

        // valider stock
        String stockStr = req.getParameter("stock");
        if (stockStr == null || stockStr.trim().isEmpty()) {
            p.setStock(0); // Vide = 0
        } else {
            try {
                int stock = Integer.parseInt(stockStr.trim());
                if (stock < 0) {
                    erreurs.put("stock", "Le stock doit être supérieur ou égal à 0.");
                } else {
                    p.setStock(stock);
                }
            } catch (NumberFormatException e) {
                erreurs.put("stock", "Le stock doit être un nombre entier.");
            }
        }

        // valider desc
        String desc = p.getDescription();
        if (desc != null && desc.length() > 255) {
            erreurs.put("description", "La description ne doit pas dépasser 255 caractères.");
        }

        //  SI err On re affiche le formulaire  
        if (!erreurs.isEmpty()) {
            req.setAttribute("erreurs", erreurs);
            req.setAttribute("produit", p); // On renvoie l'objet pour que les champs restent remplis
            req.getRequestDispatcher("/produits/form.jsp").forward(req, resp);
            return;
        }

        // if all ok save
        dao.save(p);
        
        // msg comme alert
        req.getSession().setAttribute("flash", "Le produit « " + p.getNom() + " » a été enregistré.");
        
         resp.sendRedirect(req.getContextPath() + "/produits");
    }
}