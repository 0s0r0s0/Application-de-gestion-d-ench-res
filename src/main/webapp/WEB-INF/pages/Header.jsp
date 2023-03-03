<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
      <h5 class="my-0 mr-md-auto font-weight-normal">
       <c:if test="${!empty sessionScope.user}"><a href="<c:out value="${pageContext.servletContext.contextPath}"/>">ENI-Enchères</a></c:if>
       <c:if test="${empty sessionScope.user}"><a href="<c:out value="${pageContext.servletContext.contextPath}"/>/connexion">ENI-Enchères</a></c:if>
      </h5>
      
      <nav class="my-2 my-md-0 mr-md-3">
        <c:if test="${empty sessionScope.user}">
        	<a class="p-2 navLink" href="<c:out value="${pageContext.servletContext.contextPath}"/>/inscription">S'inscrire  </a>
        	<a class="p-2 navLink" href="<c:out value="${pageContext.servletContext.contextPath}"/>/connexion">Se connecter  </a>
        </c:if>
        <c:if test="${!empty sessionScope.user}">
       		 Bienvenue ${sessionScope.user.pseudo} !
			<a class="p-2 navLink" href="<c:out value="${pageContext.servletContext.contextPath}"/>/nouveau">Vendre</a>
        	<a class="p-2 navLink" href="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/<c:out value="${sessionScope.user.pseudo}"/>">Mon profil</a>
        	<a class="p-2 navLink" href="<c:out value="${pageContext.servletContext.contextPath}"/>/deconnexion">Se déconnecter</a>        
        </c:if>
        
      </nav>
    </div>