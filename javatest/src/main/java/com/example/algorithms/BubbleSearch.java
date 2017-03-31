package com.example.algorithms;

import java.util.Arrays;

/**
 * Created by YanYadi on 2017/3/28.
 */
public class BubbleSearch {
    public static void main(String[] args) {
        int [] array = {123,543,56567,3,224,32645,224,2,24,6,5786,12,3,6,7,8,9,3343,2,58,2,1};

        int  runCount =0;
        for(int i =0; i < array.length; i++){

            for(int j = i + 1; j < array.length ;j++){
                if(array[j] < array[i]){
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
                    runCount ++;
            }
        }
        System.out.println("runCount = " + runCount);
        System.out.println(array.length);
        System.out.println(Arrays.toString(array));
    }
}
