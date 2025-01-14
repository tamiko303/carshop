<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="upperArrow" value="&#x25B2;"/>
<c:set var="downArrow" value="&#x25BC;"/>
<c:url var="sortUrl" value="/cars"/>
<a href="${sortUrl}"><c:out value="${upperArrow}" escapeXml="false"/></a>
<a href="${sortUrl}"><c:out value="${downArrow}" escapeXml="false"/></a>