<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="loginLink" value="/login"/>

    <div class="btn-group" role="group" aria-label="Second group">
        <a href="<c:url value="${loginLink}"/>">
            <button type="button" class="btn btn-primary btn-sm">Login</button>
        </a>
    </div>