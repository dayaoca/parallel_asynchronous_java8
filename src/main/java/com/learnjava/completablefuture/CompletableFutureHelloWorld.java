package com.learnjava.completablefuture;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
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
}
