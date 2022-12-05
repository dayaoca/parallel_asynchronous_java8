package com.learnjava.service;

import com.learnjava.domain.movie.Product;
import com.learnjava.domain.movie.ProductInfo;
import com.learnjava.domain.movie.Review;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductService {

    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId){
        stopWatch.start();

        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId);
        Review review = reviewService.retrieveReview(productId);

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {

            ProductInfoService productInfoService = new ProductInfoService();
            ReviewService reviewService = new ReviewService();

            ProductService productService =new ProductService(productInfoService, reviewService);
            String productId = "ABC123";
            Product product =productService.retrieveProductDetails(productId);
            log("Product is " + product);
    }
}
