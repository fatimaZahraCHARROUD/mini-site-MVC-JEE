<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty produit.id}">Modifier le produit</c:when>
            <c:otherwise>Nouveau produit</c:otherwise>
        </c:choose>
    </title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            display: flex;
            justify-content: center;
            padding: 40px;
            margin: 0;
            transition: background-color 0.3s, color 0.3s;
        }
        .form-card {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
        }
        h1 { text-align: center; color: #2c3e50; margin-top: 0; margin-bottom: 30px;}
        .champ { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #555; }
        input[type="text"], input[type="number"], textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-family: inherit;
            font-size: 1em;
        }
        input:focus, textarea:focus { border-color: #3498db; outline: none; }
        
        .erreur { color: #e74c3c; font-size: 0.85em; display: block; margin-top: 5px; font-weight: bold; }
        
        .actions { display: flex; justify-content: space-between; align-items: center; margin-top: 30px; }
        .btn { padding: 12px 24px; border-radius: 6px; font-weight: bold; text-decoration: none; border: none; cursor: pointer; font-size: 1em; }
        .btn-save { background-color: #2ecc71; color: white; width: 100%; }
        .btn-save:hover { background-color: #27ae60; }
        .btn-cancel { color: #7f8c8d; text-decoration: none; font-weight: bold; display: inline-block; margin-top: 15px; text-align: center; width: 100%; }
        .btn-cancel:hover { color: #333; }

        /* MODE SOMBRE */
        body.dark { background-color: #1a1a1a; color: #e0e0e0; }
        body.dark .form-card { background: #2c2c2c; box-shadow: 0 8px 24px rgba(0,0,0,0.5); }
        body.dark h1 { color: #ecf0f1; }
        body.dark label { color: #bdc3c7; }
        body.dark input, body.dark textarea { background-color: #444; border-color: #555; color: white; }
        body.dark .btn-cancel { color: #95a5a6; }
        body.dark .btn-cancel:hover { color: white; }
    </style>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <div class="form-card">
        <h1>
            <c:choose>
                <c:when test="${not empty produit.id}">✏️ Modifier le produit</c:when>
                <c:otherwise>✨ Nouveau produit</c:otherwise>
            </c:choose>
        </h1>

        <form method="post" action="${pageContext.request.contextPath}/produits">
            
            <c:if test="${not empty produit.id}">
                <input type="hidden" name="id" value="${produit.id}" />
            </c:if>

            <div class="champ">
                <label>Nom du produit :</label>
                <input type="text" name="nom" value="<c:out value='${produit.nom}'/>" required />
                <c:if test="${not empty erreurs.nom}">
                    <span class="erreur">${erreurs.nom}</span>
                </c:if>
            </div>

            <div class="champ">
                <label>Description :</label>
                <textarea name="description" rows="4"><c:out value="${produit.description}"/></textarea>
                <c:if test="${not empty erreurs.description}">
                    <span class="erreur">${erreurs.description}</span>
                </c:if>
            </div>

            <div class="champ">
                <label>Prix (en DH) :</label>
                <input type="number" step="0.01" name="prix" value="<c:out value='${produit.prix}'/>" required />
                <c:if test="${not empty erreurs.prix}">
                    <span class="erreur">${erreurs.prix}</span>
                </c:if>
            </div>

            <div class="champ">
                <label>Stock disponible :</label>
                <input type="number" name="stock" value="<c:out value='${produit.stock}'/>" />
                <c:if test="${not empty erreurs.stock}">
                    <span class="erreur">${erreurs.stock}</span>
                </c:if>
            </div>

            <div class="actions" style="display: block;">
                <button type="submit" class="btn btn-save">Enregistrer</button>
                <a href="${pageContext.request.contextPath}/produits" class="btn-cancel">Annuler</a>
            </div>
        </form>
    </div>
</body>
</html>