package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static java.util.stream.Collectors.summingDouble;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;


    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                //.stream()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        timeTaken();
    if(priceValidationList.size()>0){
        return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
    }
    double finalPrice = calculateFinalPrice(cart);
    return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }

    private Double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem-> cartItem.getQuantity()* cartItem.getRate())
               // .collect(summingDouble(Double::doubleValue));
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private Double calculateFinalPrice_reduce(Cart cart){
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem->cartItem.getQuantity()*cartItem.getRate())
                .reduce(0.0,(x,y)->x+y);
    }
}
