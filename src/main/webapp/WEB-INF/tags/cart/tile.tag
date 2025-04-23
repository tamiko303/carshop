<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="cart" required="true" type="com.artocons.carshop.persistence.dtos.CartItemDTO" %>

<c:url var="pdpLink" value="/product/${cart.product}"/>
<c:url var="removeLink" value="/cart/${cart.product}/remove"/>

<tr data-id="${cart.product}" data-qty="0${cart.quantity}" class="data-form" >
    <td><c:out value="${cart.brand}"/></td>
    <td><a href="${pdpLink}"><c:out value="${cart.model}"/></a></td>
    <td><c:out value="${cart.productionYear}"/></td>
    <td><c:out value="${cart.colors}"/></td>
    <td><c:out value="${cart.price}"/></td>
    <td id="input${cart.product}">
        <input class="qty"
               type="number"
               name="quantity"
               min="1" required
               value="${cart.quantity}">
        <br/><span data-id="s${cart.product}" style="color: red" ></span>
    </td>
    <td>
        <form action="${removeLink}" method="post">
            <input type="hidden" name="id" value="${cart.product}"/>
            <button type="submit" class="btn btn-danger btn-sm">
                Delete
            </button>
        </form>
    </td>
</tr>