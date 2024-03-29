package com.learnjava.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public String helloWorld_3_async_calls_logs(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return "Hi Completablefuture!";
        });
        String hw = hello
                .thenCombine(world, (h,w)->{
                    log("thenCombine h/w");
                    return h+w;
                })
                .thenCombine(hiCompletableFuture, (prev,curr)->{
                    log("thenCombine prev+curr");
                    return prev+curr;
                })
                .thenApply(s->{
                    log("then apply");
                    return s.toUpperCase();
                })
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

    public CompletableFuture<String> helloWorld_thenCompose(){
        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose((previous)->hws.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }

    public String helloWorld_3_async_calls_custom_threadPool(){
        startTimer();
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello(), es);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world(), es);

        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(
                ()->{
                    delay(1000);
                    return " Hi CompletableFuture!";
                }, es);
        String hw = hello.thenCombine(world, (h, w) -> {
            log("thenCombine h+w");
            return h + w;
        })
                .thenCombine(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine prev+curr");
                    return prev + curr;
                })
                .thenApply(s -> {
                    log("thenapply ");
                    return s.toUpperCase();
                })
                .join();
        return hw;
    }

    public String helloWorld_3_async_calls_log_async(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(()->hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(()->hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(()->{
            delay(1000);
            return " Hi Completablefuture!";
        });
        String hw = hello
                .thenCombineAsync(world, (h,w)->{
                    log("thenCombine h/w");
                    return h+w;
                }).thenCombineAsync(hiCompletableFuture, (prev,curr)->{
                    log("thenCombone prev+curr");
                    return prev+curr;
                }).thenApplyAsync(s->{
                    log("thenApply");
                    return s.toUpperCase();
                }).join();
        timeTaken();
        return hw;

    }

    public String helloWorld_3_async_calls_custom_threadpool_async() {
        startTimer();

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(), es);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(), es);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(
                () -> {
                    delay(1000);
                    return " Hi Completablefuture";
                }, es);
        String hw = hello.thenCombineAsync(world, (h, w) -> {
            log("thenCombine h+w");
            return h + w;
        }, es)
                .thenCombineAsync(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine prev+curr");
                    return prev + curr;
                }, es)
                .thenApplyAsync(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                }, es).join();
        timeTaken();
        return hw;
    }

    public String anyOf() {
        //dbcall
        CompletableFuture<String> dbCall = CompletableFuture.supplyAsync(() -> {
            delay(4000);
            log("response from db");
            return "hello world!";
        });
        //restcall
        CompletableFuture<String> restCall = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("response from restcall");
            return "hello worldrest!";
        });
        //soapcall
        CompletableFuture<String> soapCall = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("response from soapcall");
            return "hello world!";
        });

        List<CompletableFuture> cfList = List.of(dbCall, restCall, soapCall);

        CompletableFuture<Object> cfAnyOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));
        String result = (String) cfAnyOf.thenApply(v -> {
            if (v instanceof String) {
                return v;
            }
            return null;
        }).join();
        return result;
    }
}
