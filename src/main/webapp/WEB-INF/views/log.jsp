<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            transition: background-color 0.3s, color 0.3s;
        }
        .login-box {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.1);
            width: 300px;
        }
        h1 { text-align: center; margin-top: 0; color: #2c3e50; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 0.9em; }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 1em;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 1em;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.2s;
        }
        button:hover { background-color: #27ae60; }
        .error { color: #e74c3c; text-align: center; margin-bottom: 15px; font-weight: bold; }

        /* MODE SOMBRE */
        body.dark { background-color: #1a1a1a; color: #e0e0e0; }
        body.dark .login-box { background: #2c2c2c; box-shadow: 0 8px 24px rgba(0,0,0,0.5); }
        body.dark h1 { color: #ecf0f1; }
        body.dark input { background-color: #444; border-color: #555; color: white; }
        body.dark button { background-color: #27ae60; }
    </style>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <div class="login-box">
        <h1>Connexion</h1>
        
        <c:if test="${not empty erreur}">
            <p class="error">${erreur}</p>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label>Login :</label>
                <input type="text" name="login" value="${login}" required />
            </div>
            
            <div class="form-group">
                <label>Mot de passe :</label>
                <input type="password" name="password" required />
            </div>
            
            <button type="submit">Se connecter</button>
        </form>
    </div>
</body>
</html>