<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="cart" required="true" type="com.artocons.carshop.persistence.dtos.CartItemDTO" %>
<c:url var="pdpLink" value="/product/${cart.product}"/>
<tr data-id="${cart.product}" class="data-form" >
    <td><c:out value="${cart.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${cart.model}"/></a></td>
    <td><c:out value="${cart.productionYear}"/></td>
    <td><c:out value="${cart.colors}"/></td>
    <td><c:out value="${cart.price}"/></td>
    <td class="input">
        <input type="number" name="quantity" min="1" required value="${cart.quantity}">
        <br/><span data-id="s${cart.product}" style="color: red" ></span>
    </td>
    <td>
        <form action="/${cart.product}/delete" method="post">
            <input type="hidden" name="id" value="${cart.product}"/>
            <button type="submit" class="btn btn-danger btn-sm">
                Delete
            </button>
        </form>
    </td>
</tr>