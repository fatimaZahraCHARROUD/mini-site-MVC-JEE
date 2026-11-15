# Réponses aux questions - TP 3 : Du Servlet au MVC

## Question 1 : Pourquoi utilise-t-on un `forward` quand le login échoue, mais un `sendRedirect` quand il réussit ?

- **En cas d'échec (forward)** : On utilise un `forward` pour conserver la requête initiale (POST) et transmettre les données (le message d'erreur et le login saisi par l'utilisateur) directement à la page JSP (`login.jsp`). L'URL dans le navigateur ne change pas, et l'utilisateur peut corriger son erreur sans perdre sa saisie.
- **En cas de succès (sendRedirect)** : On utilise un `sendRedirect` pour appliquer le pattern **PRG (Post-Redirect-Get)**. Le serveur demande au navigateur d'effectuer une nouvelle requête GET vers une autre URL (ex: `/dashboard`). Cela évite le problème de double soumission si l'utilisateur appuie sur F5 : le navigateur ne renverra pas les identifiants en POST, il rechargera juste la page de destination.

## Question 2 : Que se passe-t-il si l'utilisateur appuie sur F5 juste après avoir ajouté un produit, (a) si `doPost` fait un forward vers la liste, (b) si `doPost` fait un redirect ? Comment s'appelle ce pattern ?

- **(a) Si `doPost` fait un forward** : L'URL dans le navigateur reste celle de l'action POST. Si l'utilisateur appuie sur F5, le navigateur proposera de renvoyer les données du formulaire. S'il accepte, **le produit sera créé une deuxième fois** (doublon).
- **(b) Si `doPost` fait un redirect** : Le navigateur a été redirigé vers une URL en GET (ex: `/produits`). Si l'utilisateur appuie sur F5, le navigateur va simplement recharger la page de la liste (GET), sans renvoyer le formulaire. Il n'y aura aucun doublon.
- **Ce pattern s'appelle PRG (Post-Redirect-Get).**

## Question 3 : Saisissez comme nom de produit `<script>alert('XSS')</script>`. Que se passe-t-il avec `${produit.nom}` dans la liste ? Et avec `<c:out value="${produit.nom}"/>` ? Concluez.

- **Avec `${produit.nom}`** : La JSP insère la chaîne de caractères telle quelle dans le code HTML. Le navigateur va interpréter la balise `<script>` et exécuter le code JavaScript (une alerte s'affichera). C'est une **faille XSS (Cross-Site Scripting)**.
- **Avec `<c:out value="${produit.nom}"/>`** : La balise JSTL va échapper les caractères spéciaux (`<` devient `&lt;`, `>` devient `&gt;`, etc.). Le navigateur affichera le texte littéralement sans l'exécuter. La faille est bloquée.
- **Conclusion** : Il faut toujours utiliser `<c:out>` (ou la fonction `fn:escapeXml`) pour afficher des données saisies par l'utilisateur, afin de prévenir les attaques XSS.

## Question 4 : Quel est l'avantage du filtre par rapport à la vérification dans chaque servlet ? Citez deux autres usages classiques des filtres.

- **Avantage du filtre** : La **centralisation** et la **maintenabilité**. Au lieu de dupliquer le code de vérification de session (`if (user == null) redirect(...)`) dans chaque Servlet protégée, on l'écrit une seule fois dans le filtre. On évite ainsi les oublis et on facilite les modifications futures. De plus, les Servlets restent concentrées sur leur logique métier.
- **Deux autres usages classiques des filtres** :
  1. **L'encodage des caractères** : Forcer l'encodage UTF-8 sur toutes les requêtes (`request.setCharacterEncoding("UTF-8")`) pour éviter les problèmes d'accents.
  2. **La journalisation (logging) ou la mesure de performance** : Enregistrer l'URL demandée, l'adresse IP du client, ou calculer le temps d'exécution d'une requête pour surveiller l'application.
