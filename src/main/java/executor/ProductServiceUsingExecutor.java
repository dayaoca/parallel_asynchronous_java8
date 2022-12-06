package executor;

import com.learnjava.domain.movie.Product;
import com.learnjava.domain.movie.ProductInfo;
import com.learnjava.domain.movie.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import com.learnjava.thread.ProductServiceUsingThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingExecutor {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException{
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(()->productInfoService.retrieveProductInfo(productId));

        ProductServiceUsingThread.ProductInfoRunnable productInfoRunnable = new ProductServiceUsingThread.ProductInfoRunnable(productId);
        ProductServiceUsingThread.ReviewRunnable reviewRunnable = new ProductServiceUsingThread.ReviewRunnable(productId);

        Thread productInfoThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfoRunnable.productInfo, reviewRunnable.review);
    }

    private class ProductInfoRunnable implements Runnable{
        private String productId;
        private ProductInfo productInfo;

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public ProductInfoRunnable(String productId){
            this.productId = productId;
        }


        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable{
        private String productId;
        private Review review;
        public ReviewRunnable(String productId){
            this.productId=productId;
        }

        public Review getReview() {
            return review;
        }

        public void run() {
            review = reviewService.retrieveReview(productId);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();

        ProductServiceUsingThread productServiceUsingThread = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productServiceUsingThread.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
