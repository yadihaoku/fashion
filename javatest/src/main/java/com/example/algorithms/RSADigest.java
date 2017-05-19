package com.example.algorithms;

/**
 * Created by YanYadi on 2017/5/19.
 * RSA 加密算法 原理
 */
public class RSADigest {
    // 质数 1
    static int p = 17;
    // 质数 2
    static int q = 19;
    // 两个质数的乘积
    static int n = p * q;

    static int e = (p-1) * (p + 1);
    // 随意一个数字， (必须与 (p-1)*(q-1) 互为质数）我们取 (p-1) * (q-1) - 1;
    static int e1 = e - 1;
    // e2, 要求 (e2 * e1) % ( (p-1) * (q-1) ) = 1 。 我们取 e2 * 2 + 1;
    static int e2 = e1 * 2 + 1;


    public static void main(String[] args) {
        //验证
        int rsaNum = 20;
        //加密后
        int encNum = ((Double)Math.pow(rsaNum, e1)).intValue() % n;
        System.out.println(encNum);
        int desNum = ((Double)Math.pow(encNum,e2)).intValue() % n;
        System.out.println(desNum);
    }

}
