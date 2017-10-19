<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.not_approved" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.not_approved" bundle="${rb}"/></title>
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

	<!-- ADMIN SHOW NOT APPROVED REQUESTS -->
    <div class="box">
      <div class="col-lg-12">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="admin.na.title" bundle="${rb}"/></h2>
        <hr>
        <!-- TABLE -->
        <table class="table table-bordered not_approved_requests_table center-block text-center">
          <thead>
          <tr>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="admin.na.price" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="admin.na.date_from" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="admin.na.date_to" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="admin.na.seat_count" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="admin.na.registration_date" bundle="${rb}"/></th>            
          </tr>
          </thead>
          <tbody>
          <c:forEach var="request" items="${notApprovedRequests}">
	          <tr>
	            <td class="col-lg-1">
	              <fmt:setLocale value="en_US" />
	           	  <fmt:formatNumber pattern="##,##" value="${request.price}" />	           	  	
	           	  <fmt:setLocale value="${locale}"/> 
	           	  <fmt:message key="other.rubles" bundle="${rb}"/>    
	            </td>
	            <td class="col-lg-1">
	            	<fmt:formatDate type = "date" value = "${request.dateFrom}"/>
	            </td>
	            <td class="col-lg-1">
	            	<fmt:formatDate type = "date" value = "${request.dateTo}"/>	
	            </td>
	            <td class="col-lg-1">
	              	${request.seatCount}
	            </td>
	            <td class="col-lg-1">
	            	<fmt:formatDate type = "both" value = "${request.registrationDate}"/>
	            </td>
	            <td class="col-lg-1">
	            	<a href="${pageContext.request.contextPath}/controller?command=approve_request&id=${request.id}"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.button_approve" bundle="${rb}"/></button></a>
	            </td>
	            <td class="col-lg-1">
	            	<a href="${pageContext.request.contextPath}/controller?command=deny_request&id=${request.id}"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.button_deny" bundle="${rb}"/></button></a>
	            </td>
	            <td class="col-lg-1">
	              	<a href="${pageContext.request.contextPath}/controller?command=find_user&findUserId=${request.userId}"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.show_user" bundle="${rb}"/></button></a>
	            </td>
	          </tr>
          </c:forEach>
          </tbody>
        </table>
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