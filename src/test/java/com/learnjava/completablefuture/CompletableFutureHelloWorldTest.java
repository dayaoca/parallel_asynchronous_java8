package com.learnjava.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

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


}