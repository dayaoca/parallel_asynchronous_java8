package com.learnjava.service;

import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.util.LoggerUtil;


import java.util.Arrays;
import java.util.List;

import static com.learnjava.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String producId){
        delay(1000);

        List<ProductOption> productOptions = Arrays.asList(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99),
                new ProductOption(3, "128GB", "Black", 849.99),
                new ProductOption(4, "128GB", "Black", 949.99));
        LoggerUtil.log("retrieveProductInfo after Delay");
        return ProductInfo.builder().productId(producId)
                .productOptions(productOptions)
                .build();
    }


}


