<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="upperArrow" value="&#x25B2;"/>
<c:set var="downArrow" value="&#x25BC;"/>
<c:url var="sortUrl" value="/cars/page"/>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="field" required="true" type="java.lang.String" %>
<%@ attribute name="query" required="false" type="java.lang.String" %>

<a href="${sortUrl}/${page}?query=${query}&sortField=${field}&sortDir=asc"><c:out value="${upperArrow}" escapeXml="false" /></a>
<a href="${sortUrl}/${page}?query=${query}&sortField=${field}&sortDir=desc"><c:out value="${downArrow}" escapeXml="false"/></a>