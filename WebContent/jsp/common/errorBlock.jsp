<div class="row">
      <div class="box">
        <div class="col-lg-12">
          <hr>
          <h2 class="intro-text text-center"><fmt:message key="error.title" bundle="${rb}"/></h2>
          <hr>
          <div class="col-lg-12 text-center">    
          	 <!-- SHOW ERROR -->  
          	 <c:if test = "${registerError != null}"> 
          	 	<span class="error_block_message"><fmt:message key="${registerError}" bundle="${rb}"/></span> 
          	 </c:if>     
          	 <c:if test = "${loginError != null}"> 
          	 	<span class="error_block_message"><fmt:message key="${loginError}" bundle="${rb}"/></span> 
          	 </c:if>  
          	 <c:if test = "${requestError != null}"> 
          	 	<span class="error_block_message"><fmt:message key="${requestError}" bundle="${rb}"/></span> 
          	 </c:if>  
          	 <c:if test = "${adminError != null}"> 
          	 	<span class="error_block_message"><fmt:message key="${adminError}" bundle="${rb}"/></span>  
          	 </c:if>                
             <!-- DELETING -->   
             <c:remove var="registerError"/>
             <c:remove var="loginError"/>
             <c:remove var="requestError"/>
             <c:remove var="adminError"/>
          </div>
        </div>
      </div>
    </div>   