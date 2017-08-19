<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.index" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <title><fmt:message key="header.hostel_name" bundle="${rb}"/></title>
  <!-- Bootstrap Core CSS -->
  <link rel='stylesheet' href="${pageContext.request.contextPath}/css/bootstrap.min.css" type='text/css' media='all'>
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/hostel.css" rel="stylesheet">
</head>

<body>	
	<!-- NAVIGATION PANEL -->
	<%@ include file="jsp/common/navigation.jsp"%>  
<div class="container">	

	<!-- ERROR BLOCK -->	
	<c:choose>
        <c:when test = "${loginError != null}">  
        <%@ include file="jsp/common/errorBlock.jsp"%>            
        </c:when>   
    </c:choose>  
    
    <!-- INFO BLOCK -->	
	<c:choose>
        <c:when test = "${loginInfo != null}">  
        <%@ include file="jsp/common/infoBlock.jsp"%>            
        </c:when>   
        <c:when test = "${registerInfo != null}">  
        <%@ include file="jsp/common/infoBlock.jsp"%>           
        </c:when>   
        <c:when test = "${requestInfo != null}">  
        <%@ include file="jsp/common/infoBlock.jsp"%>            
        </c:when>         
    </c:choose>  
  
    <!-- LOGIN -->
     <c:if test="${user == null}">
     <c:if test="${admin == null}">
    <div class="row">
      <div class="login_box">
        <div class="col-lg-12 text-center">
          <form class="login_form" method="POST" action="${pageContext.request.contextPath}/controller?command=login">
            <h2 class="intro-text text-center login_title"><fmt:message key="login.enter" bundle="${rb}"/></h2>
            <div class="row">
              <div class="form-group col-lg-4">
                <label><fmt:message key="login.email" bundle="${rb}"/></label>
                <input type="text" name="email" class="form-control">
              </div>
              <div class="form-group col-lg-4">
                <label><fmt:message key="login.password" bundle="${rb}"/></label>
                <input type="password" name="password" class="form-control">
              </div>
              <div class="form-group col-lg-2 login_btn">
                <button type="submit" class="btn btn-default btn-block"><fmt:message key="login.login_button" bundle="${rb}"/></button>
              </div>
              <div class="form-group col-lg-2 login_btn">
                <a href="${pageContext.request.contextPath}/registration"><button type="button" class="btn btn-default btn-block"><fmt:message key="login.register_button" bundle="${rb}"/></button></a>
              </div>
            </div>
          </form>	
        </div>
      </div>
    </div>
    </c:if>
    </c:if>

    <!-- USER PANEL -->
    <c:if test="${user != null}">
    <div class="row">
      <div class="box">
        <div class="col-lg-12 text-center">
          <h2 class="intro-text"><fmt:message key="panel.welcome" bundle="${rb}"/> ${user.firstName} ${user.lastName}</h2>            
          <div class="form-group col-lg-4">           
            <a href="${pageContext.request.contextPath}/controller?command=show_user_requests"><button type="button" class="btn btn-default btn-block"><fmt:message key="panel.user_page" bundle="${rb}"/></button></a>
          </div>
          <div class="form-group col-lg-4">
            <a href="${pageContext.request.contextPath}/user/request"><button type="button" class="btn btn-default btn-block"><fmt:message key="panel.new_request" bundle="${rb}"/></button></a>
          </div>
          <div class="form-group col-lg-4">          	
            <a href="${pageContext.request.contextPath}/controller?command=logout"><button type="submit" class="btn btn-default btn-block"><fmt:message key="panel.logout" bundle="${rb}"/></button></a>            
          </div>
        </div>
      </div>
    </div>
    </c:if>
    
    <!-- ADMIN PANEL -->
    <c:if test="${admin != null}">
    <div class="row">
      <div class="box">
        <div class="col-lg-12 text-center">
          <h2 class="intro-text"><fmt:message key="panel.welcome" bundle="${rb}"/> ${admin.firstName} ${admin.lastName}</h2> 
          <div class="form-group col-lg-4">
            <a href="${pageContext.request.contextPath}/adminMenu"><button type="button" class="btn btn-default btn-block"><fmt:message key="panel.admin_page" bundle="${rb}"/></button></a>
          </div>
          <div class="form-group col-lg-4">          	
            <a href="${pageContext.request.contextPath}/controller?command=logout"><button type="submit" class="btn btn-default btn-block"><fmt:message key="panel.logout" bundle="${rb}"/></button></a>            
          </div>
        </div>
      </div>
    </div>
    </c:if>

    <!-- WELCOME BLOCK -->
    <div class="row">
      <div class="box">
        <div class="col-lg-12 text-center">
          <div id="carousel-example-generic" class="carousel slide">
            <!-- Indicators -->
            <ol class="carousel-indicators hidden-xs">
              <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
              <li data-target="#carousel-example-generic" data-slide-to="1"></li>
              <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>
            <!-- Wrapper for slides -->
            <div class="carousel-inner">
              <div class="item active">
                <img class="img-responsive img-full" src="img/slide-1.jpg" alt="">
              </div>
              <div class="item">
                <img class="img-responsive img-full" src="img/slide-2.jpg" alt="">
              </div>
              <div class="item">
                <img class="img-responsive img-full" src="img/slide-3.jpg" alt="">
              </div>
            </div>
            <!-- Controls -->
            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                            <span class="icon-prev"></span>
                        </a>
            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                            <span class="icon-next"></span>
                        </a>
          </div> 
        </div>
        <hr class="tagline-divider">
        <h2 class="brand-before text-center"><fmt:message key="index.welcome" bundle="${rb}"/></h2>
          <span class="text-left short_text"><fmt:message key="index.description" bundle="${rb}"/></span>
       </div>
    </div>
   
</div>
  <!-- /.container -->

<%@ include file="jsp/common/footer.jsp"%>

    <!-- jQuery -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!-- Bootstrap Core JavaScript -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <!-- Script to Activate the Carousel -->
   <script src="${pageContext.request.contextPath}/js/main_page_carousel.js"></script> 
</body>

</html>