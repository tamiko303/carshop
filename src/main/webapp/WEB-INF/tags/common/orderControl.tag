<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="controlLink" value="/admin/orders"/>

<div class="btn-group" role="group" aria-label="Second group">
    <a href="<c:url value="${controlLink}"/>">
        <button type="button" class="btn btn-light btn-sm">Order control</button>
    </a>
</div>