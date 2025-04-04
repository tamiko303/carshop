<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="cart" required="true" type="com.artocons.carshop.persistence.model.Cart" %>
<c:url var="pdpLink" value="/product/${cart.id}"/>
<tr data-id="${cart.id}" class="data-form" >
    <td><c:out value="${cart.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${cart.model}"/></a></td>
    <td><c:out value="${cart.colors}"/></td>
    <td><c:out value="$ ${cart.price}"/></td>
    <td class="input">
        <input form="${cart.id}" type="number" name="quantity" min="1" required value="1">
        <br/><span data-id="s${cart.id}" style="color: red" ></span>
        <%--            <input form="${car.id}" type="hidden" name="product" readonly value="${car.id}">--%>
    </td>
    <td>
        <form id="${cart.id}">
            <button
                    type="submit"
                    class="btn btn-danger btn-sm">
                Delete
            </button>
        </form>

    </td>
</tr>