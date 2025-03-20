<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="car" required="true" type="com.artocons.carshop.persistence.model.Car" %>
<c:url var="pdpLink" value="/product/${car.id}"/>
<tr>
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
        <input type="number" name="quantity" min="1" required value="1">
        <span id="quantityError" style="color: red" ></span>
<%--        <input type="hidden" name="product" readonly value="${car.id}">--%>
<%--        <form:hidden path="product" value="${car.id}"/>--%>
<%--        <form:input path="quantity" value="1" maxlength="5"/>--%>
<%--        <form:errors path="quantity" />--%>
    </td>
    <td>
        <button
            type="submit"
            id="bth-add"
            class="btn btn-danger btn-sm"
            onclick="addToCart()">
                Add to Cart
        </button>
<%--        <form:button>Add to Cart</form:button>--%>
    </td>
</tr>
