package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;
import static org.apache.commons.lang3.StringUtils.join;

public class CompletableFutureHelloWorld {
    public static void main(String args[]){
        HelloWorldService hws = new HelloWorldService();
        CompletableFuture.supplyAsync(()->hws.helloWorld())
                .thenAccept((result)->{
                    log("result :"+result);
                }).join();

        log("Done!");
        //delay(2000);


    }
}
