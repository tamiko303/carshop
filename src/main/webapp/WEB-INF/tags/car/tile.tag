<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="car" required="true" type="com.artocons.carshop.persistence.model.Car" %>
<c:url var="pdpLink" value="/cars/${car.id}"/>
<tr>
    <td>
        <a href="${pdpLink}">
            <img src="<c:url value="https://raw.githubusercontent.com/Alex-Job/img/refs/heads/main/car.png"/>" width="100" height="100" alt="Car image">
        </a>
    </td>
    <td><c:out value="${car.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${car.model}"/></a></td>
    <td><c:out value="$ ${car.price}"/></td>
</tr>