<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>

<%@ attribute name="isAdmin" required="true" type="java.lang.Boolean" %>

<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
    <div class="btn-group w-75" role="group" aria-label="First group"></div>
    <common:userAdmin/>
    <common:logout/>
</div>