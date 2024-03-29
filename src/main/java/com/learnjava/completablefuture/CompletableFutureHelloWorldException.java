package com.learnjava.completablefuture;

import java.awt.desktop.AppReopenedEvent;
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

    public String helloworld_3_async_calls_handle(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi Completablefuture!";
        });
        String hw  = hello
                .handle((res,e)->{
                    log("res is :"+res);
                    if(e!=null) {
                        log("Exception is :" + e.getMessage());
                        return " ";
                    }else{
                        return res;
                    }
                })
                .thenCombine(world, (h,w)->h+w)
                .handle((res,e)->{
                    log("res 2 is :"+res);
                    if(e!=null) {
                        log("Exception after world is :" + e.getMessage());
                        return " ";
                    }else{
                        return res;
                    }
                })
                .thenCombine(hiCompletableFuture, (previous,current)->previous+current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;
    }

    public String helloworld_3_async_calls_exceptionally(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return " Hi Completablefuture!";
        });
        String hw = hello
                .exceptionally((e)->{
                    log("exception is :"+e.getMessage());
                    return " ";
                })
                .thenCombine(world, (h,w)->h+w)
                .exceptionally((e)->{
                    log("exeption after is :"+e.getMessage());
                    return " ";
                })
                .thenCombine(hiCompletableFuture, (previous,current)->previous+current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;
    }

    public String helloworld_3_async_calls_whenhandle(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return " Hi Completablefuture!";
        });
        String hw = hello.whenComplete((res,e)->{
            log("res is :"+res);
            if(e!=null){
                log("exception is "+e.getMessage());
            }
        })
                .thenCombine(world, (previous,current)-> previous+current)
                .whenComplete((res1,e)->{
                    log("res1 is :"+res1);
                    if(e!=null){
                        log("exception 1 is "+e.getMessage());
                    }
                })
                .exceptionally((e)->{
                    log("Exception after thenCombine is :"+e.getMessage());
                    return " ";
                })
                .thenCombine(hiCompletableFuture,(prev,curr)->prev+curr)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;

    }

}
