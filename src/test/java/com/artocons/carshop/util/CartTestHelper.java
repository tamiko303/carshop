package com.artocons.carshop.util;

import com.artocons.carshop.persistence.model.Cart;

public class CartTestHelper {

    public static Cart createTestCartItem(int quantity, Long productId) {
        Cart cartItem = new Cart();
        cartItem.setQuantity(quantity);
        cartItem.setProduct(productId);
        return cartItem;
    }
}
