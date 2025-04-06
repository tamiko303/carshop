<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-4 offset-10">
        <button type="button" class="btn btn-secondary btn-sm" >
            My Cart: <b><span id="count">${cartCount}</span></b> items <b><span id="total">${cartTotalCost}</span></b>$
        </button>
    </div>
</div>