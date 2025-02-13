<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="upperArrow" value="&#x25B2;"/>
<c:set var="downArrow" value="&#x25BC;"/>
<c:url var="sortUrl" value="/cars/page"/>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="field" required="true" type="java.lang.String" %>

<a href="${sortUrl}/${page}?sortField=${field}&sortDir=asc"><c:out value="${upperArrow}" escapeXml="false" /></a>
<a href="${sortUrl}/${page}?sortField=${field}&sortDir=desc"><c:out value="${downArrow}" escapeXml="false"/></a>

<script>
    function callController() {
        // Get the value you want to send
        let sortField = document.getElementById("").value;

        // Make an AJAX request to the controller method
        fetch('/cars/page/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'sortField=' + encodeURIComponent(sortField)
        })
            .then(response => response.text())
            .then(data => {
                // Handle the response from the controller
                console.log(data);
            });
    }
</script>