package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {

    private HelloWorldService hws;

    public CompletableFutureHelloWorldException(HelloWorldService hws){
        this.hws = hws;
    }

    public String helloworld_2_async_calls_handle(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi Completablefuture!";
        });

        String hw = hello
                .handle((res,e)->{
                    log("Exception is :"+e.getMessage());
                    return " ";
                })
                .thenCombine(world, (h,w)->h+w)
                .thenCombine(hiCompletableFuture, (previous,current)->previous+current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;

    }

}
