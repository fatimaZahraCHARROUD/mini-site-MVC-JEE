<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogue de produits</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            padding: 40px;
            margin: 0;
            transition: background-color 0.3s, color 0.3s;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
        }
        h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; }
        
        .toolbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .btn { padding: 10px 15px; border-radius: 6px; text-decoration: none; font-weight: bold; display: inline-block; border: none; cursor: pointer; }
        .btn-primary { background-color: #3498db; color: white; }
        .btn-primary:hover { background-color: #2980b9; }
        .btn-secondary { background-color: #95a5a6; color: white; text-decoration: none; padding: 10px 15px; border-radius: 6px; font-weight: bold;}
        
        .search-form { display: flex; gap: 10px; }
        .search-form input { padding: 10px; border: 1px solid #ccc; border-radius: 6px; width: 250px; }
        .search-form button { padding: 10px 15px; background-color: #2c3e50; color: white; border: none; border-radius: 6px; cursor: pointer; }

        .alert-success { background-color: #d4edda; color: #155724; padding: 15px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #c3e6cb; }
        .alert-danger { background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 6px; margin-bottom: 20px; }

        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #f8f9fa; color: #2c3e50; font-weight: bold; }
        tr:hover { background-color: #f1f1f1; }
        
        .actions { display: flex; gap: 10px; align-items: center; }
        .actions a { color: #3498db; text-decoration: none; font-weight: bold; }
        .actions button { background: none; border: none; color: #e74c3c; cursor: pointer; font-weight: bold; font-size: 1em; padding: 0; }
        .rouge { color: #e74c3c; font-weight: bold; background: #fadbd8; padding: 4px 8px; border-radius: 4px; }

        /* MODE SOMBRE */
        body.dark { background-color: #1a1a1a; color: #e0e0e0; }
        body.dark .container { background: #2c2c2c; box-shadow: 0 8px 24px rgba(0,0,0,0.5); }
        body.dark h1 { color: #ecf0f1; border-bottom-color: #444; }
        body.dark th { background-color: #333; color: #ecf0f1; }
        body.dark th, body.dark td { border-bottom-color: #444; }
        body.dark tr:hover { background-color: #383838; }
        body.dark .search-form input { background-color: #444; border-color: #555; color: white; }
        body.dark .btn-secondary { background-color: #7f8c8d; }
    </style>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <div class="container">
        <h1>Catalogue de produits</h1>

        <c:if test="${not empty sessionScope.flash}">
            <div class="alert-success">${sessionScope.flash}</div>
            <c:remove var="flash" scope="session"/>
        </c:if>

        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/produits?action=new" class="btn btn-primary">➕ Ajouter un produit</a>
            
            <form method="get" action="${pageContext.request.contextPath}/produits" class="search-form">
                <input type="text" name="q" value="${param.q}" placeholder="Rechercher un produit..." />
                <button type="submit">Chercher</button>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty produits}">
                <div class="alert-danger">Aucun produit n'est disponible pour le moment.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Description</th>
                            <th>Prix</th>
                            <th>Stock</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${produits}">
                            <tr>
                                <td><c:out value="${p.nom}"/></td>
                                <td><c:out value="${p.description}"/></td>
                                <td><strong><fmt:formatNumber value="${p.prix}" type="number" minFractionDigits="2" maxFractionDigits="2" /> DH</strong></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.stock == 0}">
                                            <span class="rouge">Rupture</span>
                                        </c:when>
                                        <c:otherwise>${p.stock}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="actions">
                                        <a href="${pageContext.request.contextPath}/produits?action=edit&id=${p.id}">Éditer</a>
                                        <form method="post" action="${pageContext.request.contextPath}/produits" onsubmit="return confirm('Supprimer ce produit ?');">
                                            <input type="hidden" name="action" value="delete" />
                                            <input type="hidden" name="id" value="${p.id}" />
                                            <button type="submit">Supprimer</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <br>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn-secondary">⬅ Retour au tableau de bord</a>
    </div>
</body>
</html>