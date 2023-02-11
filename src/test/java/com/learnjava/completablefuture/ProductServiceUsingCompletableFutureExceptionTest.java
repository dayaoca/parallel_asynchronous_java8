package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUsingCompletableFutureExceptionTest {

    @InjectMocks
    ProductServiceUsingCompletableFuture pscf;

    @Mock
    private ProductInfoService pisMock;

    @Mock
    private ReviewService rsMock;

    @Mock
    private InventoryService isMock;

    @Test
    void retrieveProductDetailsWithInventory_approach2(){
        //given
        String productId= "ABC123";

        when(pisMock.retrieveProductInfo(any())).thenCallRealMethod();

        when(rsMock.retrieveReview(any())).thenThrow(new RuntimeException("exception occurred"));

        when(isMock.retrieveInventory(any())).thenCallRealMethod();
        //when
        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });

        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());

    }

    @Test
    void retrieveProductDetailsWithInventory_ProductInfoServiceError(){
        //given
        String productId = "ABC123";

        when(pisMock.retrieveProductInfo(any())).thenThrow(new RuntimeException("exception occurred"));
        when(rsMock.retrieveReview(any())).thenCallRealMethod();
      //  when(isMock.retrieveInventory(any())).thenCallRealMethod();
        //when
       // Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);

        //then
       // assertNotNull(product);
       // assertTrue(product.getProductInfo().getProductOptions().size()>0);
       // assertNotNull(product.getReview());
        Assertions.assertThrows(RuntimeException.class, ()->pscf.retrieveProductDetailsWithInventory_approach2(productId));
    }
}
