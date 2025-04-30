<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="orderi" required="true" type="com.artocons.carshop.persistence.model.OrderItem" %>

<tr data-id="${orderi.product}" class="data-form" >
    <td><c:out value="${orderi.product.brand}"/></td>
    <td><c:out value="${orderi.product.model}"/></td>
    <td><c:out value="${orderi.product.productionYear}"/></td>
    <td><c:out value="${orderi.product.colors}"/></td>
    <td id="input${orderi.product}">
        <c:out value="${orderi.quantity}"/>
        <br/><span data-id="s${orderi.product}" style="color: red" ></span>
    </td>
    <td><c:out value="${orderi.product.price}"/></td>
</tr>