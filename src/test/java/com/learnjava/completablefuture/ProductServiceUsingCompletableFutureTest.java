package com.learnjava.completablefuture;

import com.learnjava.domain.movie.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService pis = new ProductInfoService();
    private ReviewService rs = new ReviewService();
    private InventoryService is = new InventoryService();
    private ProductServiceUsingCompletableFuture pscf
            = new ProductServiceUsingCompletableFuture(pis,rs,is);

    @Test
    void retrieveProductDetails(){
        //given
        String productId = "ABC123";
        //when
        Product product = pscf.retrieveProductDetails(productId);
        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2(){
        //given
        String productId = "ABC123";
        //when
         CompletableFuture<Product> productcf = pscf.retrieveProductDetails_approach2(productId);
       /* productcf.thenAccept(product->{
             assertNotNull(product);
             assertTrue(product.getProductInfo().getProductOptions().size()>0);
             assertNotNull(product.getReview());
         }).join();*/
         Product product = productcf.join();
         assertNotNull(product);
         assertTrue(product.getProductInfo().getProductOptions().size()>0);
         assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory() {
        //given
        String productId = "ABC123";
        //when
        Product product = pscf.retrieveProductDetailsWithInventory(productId);
        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>1);
        //lets assert the inventory service
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
    }
}