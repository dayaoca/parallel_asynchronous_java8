package com.learnjava.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();

    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

    @Test
    void helloWorld(){
        //given
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld();
        //then
        completableFuture
                .thenAccept(s->{
                    assertEquals("HELLO WORLD",s);
                }).join();
    }

    @Test
    void helloWorld_withSize(){
        //given
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_withSize();
        //then
        completableFuture
                .thenAccept(s->{
                    assertEquals("11-HELLO WORLD",s);
                }).join();

    }


    @Test
    void helloWorld_approach1() {
        //given
        startTimer();
        //when
        String str = cfhw.helloWorld_approach1();
        //then
        assertEquals("hello world", str);
        timeTaken();
    }

    @Test
    void helloWorld_multiple_async_calls() {
        //given
        //when
        String helloWorld = cfhw.helloWorld_multiple_async_calls();
        //then
        assertEquals("HELLOWORLD", helloWorld);
    }

    @Test
    void hellowWorld_3_async_calls() {
        //given
        //when
        String helloWorld = cfhw.hellowWorld_3_async_calls();
        //then
        assertEquals("HELLOWORLD!HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_4_async_calls() {
        //given
        //when
        String hw = cfhw.helloWorld_4_async_calls();
        //then
        assertEquals("HELLOWORLDHI COMPLETABLEFUTUREBYE",hw);
    }

    @Test
    void helloWorld_thenCompose() {
        //given
        startTimer();
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_thenCompose();
        //then
        completableFuture.thenAccept(s->{
            assertEquals("HELLOWORLD!", s);
        }).join();
        timeTaken();
    }

    @Test
    void helloWorld_3_async_calls_logs() {
        //given
        //when
        String helloWorld = cfhw.helloWorld_3_async_calls_logs();
        //then
        assertEquals("HELLOWORLD!HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_custom_threadPool() {
        //given
        //when
        String helloWorld = cfhw.helloWorld_3_async_calls_custom_threadPool();
        //then
        assertEquals("HELLOWORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_log_async() {
        //given
        //when
        String helloWorld = cfhw.helloWorld_3_async_calls_log_async();
        //then
        assertEquals("HELLOWORLD! HI COMPLETABLEFUTURE!", helloWorld);
    }

    @Test
    void helloWorld_3_async_calls_custom_threadpool_async() {
        //given
        //when
        String helloWorld = cfhw.helloWorld_3_async_calls_custom_threadpool_async();
        //then
        assertEquals("HELLOWORLD! HI COMPLETABLEFUTURE", helloWorld);
    }
}