<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <style>
        /* STYLE GLOBAL */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            transition: background-color 0.3s, color 0.3s;
        }
        .card {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
        }
        h1 { margin-top: 0; color: #2c3e50; }
        .theme-switch { margin-bottom: 25px; font-size: 0.9em; }
        .theme-switch a { text-decoration: none; color: #3498db; font-weight: bold; }
        .theme-switch a:hover { text-decoration: underline; }
        ul { list-style: none; padding: 0; }
        li { margin: 15px 0; }
        .btn {
            display: block;
            padding: 12px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
            transition: background 0.2s;
        }
        .btn:hover { background-color: #2980b9; }
        .btn-danger { background-color: #e74c3c; }
        .btn-danger:hover { background-color: #c0392b; }

        /* MODE SOMBRE */
        body.dark { background-color: #1a1a1a; color: #e0e0e0; }
        body.dark .card { background: #2c2c2c; box-shadow: 0 8px 24px rgba(0,0,0,0.5); }
        body.dark h1 { color: #ecf0f1; }
        body.dark .theme-switch a { color: #5dade2; }
        body.dark .btn { background-color: #2980b9; }
        body.dark .btn-danger { background-color: #c0392b; }
    </style>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <div class="card">
        <h1>Bienvenue, ${user} !</h1>
        
        <div class="theme-switch">
            <strong>Changer de thème :</strong><br>
            <a href="${pageContext.request.contextPath}/cookies?action=set">🌙 Mode Sombre</a> | 
            <a href="${pageContext.request.contextPath}/cookies?action=remove">☀️ Mode Clair</a>
        </div>

        <ul>
            <li><a href="${pageContext.request.contextPath}/produits" class="btn">📦 Gérer les produits</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">🚪 Se déconnecter</a></li>
        </ul>
    </div>
</body>
</html>