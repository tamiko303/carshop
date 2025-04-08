<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="order" required="true" type="com.artocons.carshop.persistence.model.OrderItem" %>

<tr data-id="${order.product}" class="data-form" >
    <td><c:out value="${order.product.brand}"/></td>
    <td><c:out value="${order.product.model}"/></td>
    <td><c:out value="${order.product.productionYear}"/></td>
    <td><c:out value="${order.product.colors}"/></td>
    <td id="input${order.product}">
        <c:out value="${order.quantity}"/>
        <br/><span data-id="s${order.product}" style="color: red" ></span>
    </td>
    <td><c:out value="${order.product.price}"/></td>
</tr>