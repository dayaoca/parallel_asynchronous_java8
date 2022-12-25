package com.learnjava.parallelstreams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReduceExampleTest {

    ReduceExample reduceExample = new ReduceExample();

    @Test
    public void reduce_parallelStream(){

        //given
        List<Integer> inputList = List.of(1,2,3,4,5,6,7,8);

        //when
        int result = reduceExample.reduce_sum_parallelstream(inputList);

        //then
        assertEquals(36, result);

    }

    @Test
    public void reduce_parallelstream_emptyList(){
        //given
        List<Integer> inputList = new ArrayList<>();

        //when
        int result = reduceExample.reduce_sum_parallelstream(inputList);

        //then
        assertEquals(0, result);
    }

    @Test
    void reduce_multiply(){
        //given
        List<Integer> inputList = List.of(1,2,3,4);

        //when
        int result = reduceExample.reduce_multiply_parallelstream(inputList);

        //then
        assertEquals(24, result);
    }

    @Test
    void reduce_multiple_emptyList(){
        //given
        List<Integer> inputList = new ArrayList<>();
        //when
        int result = reduceExample.reduce_multiply_parallelstream(inputList);

        //then
        assertEquals(1, result);;

    }
}
