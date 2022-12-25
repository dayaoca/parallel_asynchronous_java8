package com.learnjava.parallelstreams;

import java.util.List;

public class ReduceExample {

    public int reduce_sum_parallelstream(List<Integer> inputList){
        int sum = inputList
               .parallelStream()
               // .stream()
                .reduce(1,(x,y)->x+y);
        return sum;
    }

    public int reduce_multiply_parallelstream(List<Integer> inputList){
        int sum = inputList.parallelStream()
                .reduce(1,(x,y)->x*y);
        return sum;
    }

}
