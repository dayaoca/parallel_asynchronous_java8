package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService pis = new ProductInfoService();
    private ReviewService rs = new ReviewService();
    private InventoryService is = new InventoryService();
    private ProductServiceUsingCompletableFuture pscf
            = new ProductServiceUsingCompletableFuture(pis,rs,is);

    @Mock
    InventoryService inventoryService = mock(InventoryService.class);

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

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
        //given
        String productId = "ABC123";
        //when
        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
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

    @Test
    void retrieveProductDetailsWithInventory_withException(){
        //given
        String productId = "ABC123";
        when(inventoryService.retrieveInventory(any())).thenThrow(new RuntimeException("exception occurred in inventory"));
        //when
        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>1);
        //assert the inventrory service
        product.getProductInfo().getProductOptions()
                .forEach(productOption->{
                    assertNotNull(productOption.getInventory());
                    assertEquals(2, productOption.getInventory().getCount());
                });
        assertNotNull(product.getReview());

    }
}