package com.learnjava.completablefuture;

import com.learnjava.domain.movie.Product;
import com.learnjava.domain.movie.ProductInfo;
import com.learnjava.domain.movie.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {

    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId){
        stopWatch.start();
        //ProductInfo productInfo = productInfoService.retrieveProductInfo(productId);
        //Review review = reviewService.retrieveReview(productId);
        CompletableFuture<ProductInfo> cfProductInfo
                = CompletableFuture.supplyAsync(()->productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> cfReviews
                = CompletableFuture.supplyAsync(()->reviewService.retrieveReview(productId));
        Product product = cfProductInfo
                .thenCombine(cfReviews, (productInfo,review)-> new Product(productId, productInfo, review))
                .join();
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public static void main(String[] args) {

            ProductInfoService productInfoService = new ProductInfoService();
            ReviewService reviewService = new ReviewService();

            ProductServiceUsingCompletableFuture productService =new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
            String productId = "ABC123";
            Product product =productService.retrieveProductDetails(productId);
            log("Product is " + product);
    }
}
