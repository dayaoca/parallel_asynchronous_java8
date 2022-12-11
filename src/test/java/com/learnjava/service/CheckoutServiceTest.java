package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void no_of_cores(){
        System.out.println("no of cores {}"+Runtime.getRuntime().availableProcessors());
    }

    @Test
    void checkout_6_items(){
        //given
        Cart cart = DataSet.createCart(4);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_13_items(){
        //given
        Cart cart = DataSet.createCart(13);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_24_items(){
        //given
        Cart cart  = DataSet.createCart(24);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

    }

}