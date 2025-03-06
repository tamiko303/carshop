<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="car" required="true" type="com.artocons.carshop.persistence.model.Car" %>
<c:url var="pdpLink" value="/cars/${car.id}"/>
<tr>
<%--    <form action="${url}/${car.id}?quantity=${quantity}" method="post" >--%>
    <td>
        <a href="${pdpLink}">
            <img src="<c:url value="https://raw.githubusercontent.com/Alex-Job/img/refs/heads/main/car.png"/>" width="100" height="100" alt="Car image">
        </a>
    </td>
    <td><c:out value="${car.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${car.model}"/></a></td>
    <td><c:out value="${car.colors}"/></td>
    <td><c:out value="$ ${car.price}"/></td>
    <td>
        <label>
            <input type="number" name="quantity" min="1" required value=${quantity}>
        </label>
        <%--        <form:input path="quantity" maxlength="5"/>--%>
    </td>
    <td>
        <button type="submit" class="btn btn-danger btn-sm">Add to Cart</button>
<%--        <form:button>Add to Cart</form:button>--%>
    </td>
<%--    </form>--%>
</tr>
