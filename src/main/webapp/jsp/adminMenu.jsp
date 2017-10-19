<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.admin_menu" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.admin_page" bundle="${rb}"/></title>
  <!-- Bootstrap Core CSS -->
  <link rel='stylesheet' href="${pageContext.request.contextPath}/css/bootstrap.min.css" type='text/css' media='all'>
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/hostel.css" rel="stylesheet">
</head>

<body>	
  
  <%@ include file="common/navigation.jsp"%>

<div class="container">	

	<!-- ERROR BLOCK -->	
	<c:choose>
		<c:when test = "${adminError != null}">    
		<%@ include file="common/errorBlock.jsp"%>        
        </c:when>              
    </c:choose>  
    
     <!-- INFO BLOCK -->	
	<c:choose>
        <c:when test = "${adminInfo != null}">  
        <%@ include file="common/infoBlock.jsp"%>            
        </c:when> 
    </c:choose>  
    
    <!-- ADMIN MENU FIND USER -->
    <div class="box">    	
      <div class="col-lg-12 text-center">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="admin.menu.find_user" bundle="${rb}"/></h2>
        <hr>
        <p class="small_text"><fmt:message key="admin.menu.set_one_field" bundle="${rb}"/></p>
        <form method="post" action="${pageContext.request.contextPath}/controller?command=find_user">
         <div class="form-group row">
            <label for="findUserId" class="col-sm-2 col-form-label"><fmt:message key="admin.id" bundle="${rb}"/></label>
            <div class="col-lg-6">
              <div>
                <input type="text" name="findUserId" class="form-control" id="findUserId" placeholder="">
              </div>
            </div>
          </div>
          <div class="form-group row">
            <label for="findUserEmail" class="col-sm-2 col-form-label"><fmt:message key="register.email" bundle="${rb}"/></label>
            <div class="col-lg-6">
              <div>
                <input type="text" name="findUserEmail" class="form-control" id="findUserEmail" placeholder="">
              </div>
            </div>
          </div>
          <div class="form-group row">
            <label for="findUserPhone" class="col-sm-2 col-form-label"><fmt:message key="register.phone" bundle="${rb}"/></label>
            <div class="col-lg-6">
              <div>
                <input type="text" name="findUserPhone" class="form-control" id="findUserPhone" placeholder="">
              </div>
            </div>
          </div>
          <!-- BUTTON -->
          <div class="col-lg-12">
            <button type="submit" class="btn btn-primary btn-block text-center center-block request_button" id="findUserButton"><fmt:message key="admin.menu.find_user" bundle="${rb}"/></button>
          </div>
        </form>
      </div>
    </div>
    
     <!-- ADMIN MENU LINKS -->
    <div class="box">    	
      <div class="col-lg-12 text-center">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="admin.menu.links" bundle="${rb}"/></h2>
        <hr>
        <div class="form-group col-lg-3">
          <a href="${pageContext.request.contextPath}/controller?command=get_not_approved_requests"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.button_get_not_approved_requests" bundle="${rb}"/></button></a>
        </div> 
        <div class="form-group col-lg-3">
          <a href="${pageContext.request.contextPath}/controller?command=get_today_requests"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.button_get_today_requests" bundle="${rb}"/></button></a>
        </div>     
      </div>
    </div>
     
</div>
  <!-- /.container -->

<%@ include file="common/footer.jsp"%>

  <!-- jQuery -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!-- Bootstrap Core JavaScript -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>    
</body>

</html>