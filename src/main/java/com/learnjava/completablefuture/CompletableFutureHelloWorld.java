package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static org.apache.commons.lang3.StringUtils.join;

public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws){
        this.hws = hws;
    }
    /*public static void main(String args[]){
        HelloWorldService hws = new HelloWorldService();
        //method1
      *//*  CompletableFuture.supplyAsync(()->hws.helloWorld())
                .thenApply((result)->result.toUpperCase())
                .thenAccept((result)->{
                    log("result :"+result);
                }).join();*//*
        CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept((result)->{
                    log("Result is "+result);
                }).join();
        log("Done!");
        //delay(2000);
    }*/

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
               // .thenAccept((result)->{
               //     log("result is :"+result);
               // })
          //  .join();

    }

    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply((s)->s.length()+"-"+s.toUpperCase());
    }

    public String helloWorld_approach1(){
        String hello = hws.hello();
        String world = hws.world();
        return hello+" "+world;
    }

    public String helloWorld_multiple_async_calls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        String hw = hello
                .thenCombine(world, (h,w)->h+w)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;

    }

    public String hellowWorld_3_async_calls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi Completablefuture!";
        });
        String hw = hello
                .thenCombine(world,(h,w)->h+w)
                .thenCombine(hiCompletableFuture,(previous,current)->previous+current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;
    }

    public String helloWorld_4_async_calls(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());

        CompletableFuture<String> third = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi CompletableFuture";
        });
        CompletableFuture<String> fourth = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Bye";
        });

        String hw = hello
                .thenCombine(world, (h,w)->h+w)
                .thenCombine(third, (previous,current)->previous+current)
                .thenCombine(fourth, (previous1,current1)->previous1+current1)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hw;

    }
}
