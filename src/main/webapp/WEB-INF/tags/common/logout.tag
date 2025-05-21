<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="logoutLink" value="/logout"/>
<div class="btn-group" role="group" aria-label="Third group">
    <a href="<c:url value="${logoutLink}"/>">
        <button type="button" class="btn btn-primary btn-sm">Logout</button>
    </a>
</div>