<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.user_requests" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.user_requests" bundle="${rb}"/></title>
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
		<c:when test = "${userError != null}">    
		<%@ include file="common/errorBlock.jsp"%>        
        </c:when>              
    </c:choose>  
    
     <!-- INFO BLOCK -->	
	<c:choose>
        <c:when test = "${userInfo != null}">  
        <%@ include file="common/infoBlock.jsp"%>            
        </c:when> 
    </c:choose>  

	<!-- SHOW USER ACTUAL REQUESTS -->
    <div class="box">
      <div class="col-lg-12">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="su.title_actual" bundle="${rb}"/></h2>
        <hr>
        <!-- TABLE -->
        <table class="table table-bordered not_approved_requests_table center-block text-center">
          <thead>
          <tr>
          	<th class="col-lg-1 text-center" align="center"><fmt:message key="su.status" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.date_from" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.date_to" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.seat_count" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.price" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.registration_date" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.beds" bundle="${rb}"/></th>            
          </tr>
          </thead>
          <tbody>
          <c:forEach var="entry" items="${userActualRequests}">
	          <tr>
	          	<td class="col-lg-1">
	          	<c:choose>
					<c:when test = "${entry.key.statusId == 0}">    
						<fmt:message key="su.status_unread" bundle="${rb}"/>    
        			</c:when>  
					<c:when test = "${entry.key.statusId == 1}">    
						<fmt:message key="su.status_approved" bundle="${rb}"/>     
        			</c:when>   
        			<c:when test = "${entry.key.statusId == 2}">    
						<fmt:message key="su.status_denied" bundle="${rb}"/>    
        			</c:when>   
        			<c:when test = "${entry.key.statusId == 3}">    
						<fmt:message key="su.status_paid" bundle="${rb}"/>    
        			</c:when>  
        			<c:when test = "${entry.key.statusId == 4}">    
						<fmt:message key="su.status_canceled" bundle="${rb}"/>    
        			</c:when>              
   				</c:choose>
	            </td>
	            <td class="col-lg-1">
	             <fmt:formatDate type = "date" value = "${entry.key.dateFrom}"/>	              
	            </td>
	            <td class="col-lg-1">
	            <fmt:formatDate type = "date" value = "${entry.key.dateTo}"/>		              
	            </td>
	            <td class="col-lg-1">
	              ${entry.key.seatCount}
	            </td>
	            <td class="col-lg-1">
	            	<fmt:setLocale value="en_US" />
	           	 	<fmt:formatNumber pattern="###,##" value="${entry.key.price}" />	           	  	
	           	 	<fmt:setLocale value="${locale}"/> 
	           	 	<fmt:message key="other.rubles" bundle="${rb}"/>    	              
	            </td>
	            <td class="col-lg-1">
	              <fmt:formatDate type = "both" value = "${entry.key.registrationDate}"/>
	            </td>	            
	            <td class="col-lg-1">
	            	<c:forEach var="beds" items="${entry.value}">
	              	${beds.id}; 
	              	</c:forEach>
	            </td>      
	            <td class="col-lg-1">
	            <a href="${pageContext.request.contextPath}/controller?command=cancel_request&id=${entry.key.id}"><button type="button" class="btn btn-default btn-block"><fmt:message key="su.cancel_button" bundle="${rb}"/></button></a>
	            </td>	            
	          </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    
    <!-- SHOW USER OLD REQUESTS -->
    <div class="box">
      <div class="col-lg-12">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="su.title_not_actual" bundle="${rb}"/></h2>
        <hr>
        <!-- TABLE -->
        <table class="table table-bordered not_approved_requests_table center-block text-center">
          <thead>
          <tr>
          	<th class="col-lg-1 text-center" align="center"><fmt:message key="su.status" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.date_from" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.date_to" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.seat_count" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.price" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.registration_date" bundle="${rb}"/></th>
            <th class="col-lg-1 text-center" align="center"><fmt:message key="su.beds" bundle="${rb}"/></th>            
          </tr>
          </thead>
          <tbody>
          <c:forEach var="entry" items="${userRequests}">
	          <tr>
	          	<td class="col-lg-1">
	          	<c:choose>
					<c:when test = "${entry.key.statusId == 0}">    
						<fmt:message key="su.status_unread" bundle="${rb}"/>    
        			</c:when>  
					<c:when test = "${entry.key.statusId == 1}">    
						<fmt:message key="su.status_approved" bundle="${rb}"/>     
        			</c:when>   
        			<c:when test = "${entry.key.statusId == 2}">    
						<fmt:message key="su.status_denied" bundle="${rb}"/>    
        			</c:when>   
        			<c:when test = "${entry.key.statusId == 3}">    
						<fmt:message key="su.status_paid" bundle="${rb}"/>    
        			</c:when>  
        			<c:when test = "${entry.key.statusId == 4}">    
						<fmt:message key="su.status_canceled" bundle="${rb}"/>    
        			</c:when>              
   				</c:choose>
	            </td>
	            <td class="col-lg-1">
	             <fmt:formatDate type = "date" value = "${entry.key.dateFrom}"/>	              
	            </td>
	            <td class="col-lg-1">
	            <fmt:formatDate type = "date" value = "${entry.key.dateTo}"/>		              
	            </td>
	            <td class="col-lg-1">
	              ${entry.key.seatCount}
	            </td>
	            <td class="col-lg-1">
	            	<fmt:setLocale value="en_US" />
	           	 	<fmt:formatNumber pattern="##,##" value="${entry.key.price}" />	           	  	
	           	 	<fmt:setLocale value="${locale}"/> 
	           	 	<fmt:message key="other.rubles" bundle="${rb}"/>    	              
	            </td>
	            <td class="col-lg-1">
	              <fmt:formatDate type = "both" value = "${entry.key.registrationDate}"/>
	            </td>	            
	            <td class="col-lg-1">
	            	<c:forEach var="beds" items="${entry.value}">
	              	${beds.id}; 
	              	</c:forEach>
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