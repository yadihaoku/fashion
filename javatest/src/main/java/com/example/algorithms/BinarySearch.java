package com.example.algorithms;

/**
 * Created by YanYadi on 2017/3/28.
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {0, 20, 80, 102, 113, 120, 123, 134, 456, 465, 478, 512, 908};

        int key = 98;
        int left = 0;
        int right = array.length - 1;
        int runCount = 0;
        while (left <= right) {

            runCount++;
            int mid = (left + right) / 2;
            //是否命中中间的数字
//            if (array[mid] == key) {
//                System.out.println("fined  index = " + mid);
//                break;
//            } else
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (left < array.length && array[left] == key)
            System.out.println("find index index =" + left);
        else
            System.out.println("not found key=" + key);

        System.out.println("array.length =" + array.length);
        System.out.println("run count =" + runCount);
    }
}
