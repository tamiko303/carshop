<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="orderi" required="true" type="com.artocons.carshop.persistence.dtos.OrderItemDTO" %>

<tr data-id="${orderi.productId}" class="data-form" >
    <td><c:out value="${orderi.brand}"/></td>
    <td><c:out value="${orderi.model}"/></td>
    <td><c:out value="${orderi.productionYear}"/></td>
    <td><c:out value="${orderi.colors}"/></td>
    <td id="input${orderi.productId}">
        <c:out value="${orderi.quantity}"/>
        <br/><span data-id="s${orderi.productId}" style="color: red" ></span>
    </td>
    <td><c:out value="${orderi.price}"/></td>
</tr>