<div class="row">
      <div class="box">
        <div class="col-lg-12">
          <hr>
          <h2 class="intro-text text-center"><fmt:message key="info.title" bundle="${rb}"/></h2>
          <hr>
          <div class="col-lg-12 text-center">    
          	 <!-- SHOW INFO -->  
          	 <c:if test = "${registerInfo != null}"> 
          	 	<span class="info_block_message"><fmt:message key="${registerInfo}" bundle="${rb}"/></span> 
          	 </c:if>
          	 <c:if test = "${loginInfo != null}"> 
          	 	<span class="info_block_message"><fmt:message key="${loginInfo}" bundle="${rb}"/></span>  
          	 </c:if>
          	 <c:if test = "${requestInfo != null}"> 
          	 	<span class="info_block_message"><fmt:message key="${requestInfo}" bundle="${rb}"/></span> 
          	 </c:if>
          	 <c:if test = "${adminInfo != null}"> 
          	 	<span class="info_block_message"><fmt:message key="${adminInfo}" bundle="${rb}"/></span>  
          	 </c:if>          	              
             <!-- DELETING -->   
             <c:remove var="registerInfo"/>
             <c:remove var="loginInfo"/>
             <c:remove var="requestInfo"/>
             <c:remove var="adminInfo"/>
          </div>
        </div>
      </div>
    </div>   