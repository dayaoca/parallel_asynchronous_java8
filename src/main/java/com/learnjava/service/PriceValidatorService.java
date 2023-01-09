package com.learnjava.service;

import com.learnjava.domain.checkout.CartItem;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cardId = cartItem.getItemId();
        log("iscartItemInvalid :"+cartItem);
        delay(500);
        if(cardId==7 || cardId ==9 || cardId ==11){
            return true;
        }
        return false;
    }
}
