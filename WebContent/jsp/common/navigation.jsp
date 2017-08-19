<div class="brand"><fmt:message key="header.hostel_name" bundle="${rb}"/></div>
<div class="address-bar"><fmt:message key="header.address" bundle="${rb}"/></div>

  <!-- Navigation -->
  <nav class="navbar navbar-default" role="navigation">
    <div class="container">     
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only"><fmt:message key="" bundle="${rb}"/></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>        
        <a class="navbar-brand" href="${pageContext.request.contextPath}/"><fmt:message key="header.hostel_name" bundle="${rb}"/></a>
      </div>      
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li>          
            <a href="${pageContext.request.contextPath}/"><fmt:message key="header.home" bundle="${rb}"/></a>
          </li>
          <li>
            	<a href="${pageContext.request.contextPath}/controller?command=show_user_requests"><fmt:message key="header.user_page" bundle="${rb}"/></a>
          </li>         
          <c:if test="${user == null}">
          	<c:if test="${admin == null}">
          		<li>
            		<a href="${pageContext.request.contextPath}/registration"><fmt:message key="header.registration" bundle="${rb}"/></a>
          		</li>
          	</c:if>
          </c:if>
          <li>
            	<a href="${pageContext.request.contextPath}/adminMenu"><fmt:message key="header.admin_menu" bundle="${rb}"/></a>
          </li>         
          <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                     <fmt:message key="header.change_language" bundle="${rb}"/>
                     <span class="caret"></span>
                </a>
          		<ul class="dropdown-menu">
              		<li><a href="${pageContext.request.contextPath}/controller?command=change_language&locale=en"><fmt:message key="header.english" bundle="${rb}"/></a></li>
              		<li><a href="${pageContext.request.contextPath}/controller?command=change_language&locale=ru"><fmt:message key="header.russian" bundle="${rb}"/></a></li>
              </ul>
          </li>         
        </ul>
      </div>   
    </div>  
  </nav>