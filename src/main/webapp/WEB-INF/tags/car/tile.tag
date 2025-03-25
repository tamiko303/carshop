<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="car" required="true" type="com.artocons.carshop.persistence.model.Car" %>
<c:url var="pdpLink" value="/product/${car.id}"/>
<tr data-id="${car.id}" class="data-form" >
    <td>
        <a href="${pdpLink}">
            <img src="<c:url value="https://raw.githubusercontent.com/Alex-Job/img/refs/heads/main/car.png"/>" width="100" height="100" alt="Car image">
        </a>
    </td>
    <td><c:out value="${car.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${car.model}"/></a></td>
    <td><c:out value="${car.colors}"/></td>
    <td><c:out value="$ ${car.price}"/></td>
    <td id="input">
            <input form="${car.id}" type="number" name="quantity" min="1" required value="1">
            <br/><span id="quantityError" style="color: red" ></span>
<%--            <input form="${car.id}" type="hidden" name="product" readonly value="${car.id}">--%>
    </td>
    <td>
        <form id="${car.id}">
            <button
                type="submit"
                class="btn btn-danger btn-sm">
                    Add to Cart
            </button>
        </form>

    </td>
</tr>
