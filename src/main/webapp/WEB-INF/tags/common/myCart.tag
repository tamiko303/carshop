<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="btn-group" role="group" aria-label="Third group">
        <form action="/cart" method="get">
            <button type="submit" class="btn btn-secondary btn-sm" >
                My Cart: <b><span id="count">${cartCount}</span></b> items <b><span id="total">${cartTotalCost}</span></b>$
            </button>
        </form>
    </div>