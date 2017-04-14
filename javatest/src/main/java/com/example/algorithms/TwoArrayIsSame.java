package com.example.algorithms;

import java.util.Arrays;

/**
 * Created by YanYadi on 2017/4/12.
 */

public class TwoArrayIsSame {
    public static void main(String[] args) {

        int[] array = {1, 2, 56, 7, 9, 22, 34, 24, 0};
        int[] array1 = {1, 24, 24, 2, 56, 7, 9, 22, 34, 24, 0};
        int[] array2 = {56, 1, 2, 24, 7, 9, 22, 34, 0};
        int[] array3 = {56, 1, 2, 24, 7, 9, 22, 34, 0, 123};

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array1));
        System.out.println("isSame = " + isSameArray(array, array1));
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array2));
        System.out.println("isSame = " + isSameArray(array, array2));

    }

    static boolean isSameArray(int[] array1, int[] array2) {
        if (array1.length != array2.length) return false;
        byte[] flags = new byte[array1.length];
        boolean isEqual = false;
        for (int outNum : array1) {
            int i = 0;
            isEqual = false;
            for (; i < array2.length; i++) {
                if (outNum == array2[i] && flags[i] == 0) {
                    flags[i] = 1;
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual) return false;
        }
        return isEqual;
    }
}
