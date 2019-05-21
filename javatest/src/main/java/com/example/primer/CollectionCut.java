package com.example.primer;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YanYadi on 2017/6/28.
 *
 * 筛选 List 中数据，并删除的方式。有两种。
 * 1.使用 iterator
 * 2.每次循环都计算  list.size() 动态删除
 *
 */
public class CollectionCut {
    CollectionCut(){
        //全部内存
       long size = Runtime.getRuntime().totalMemory();
        System.err.println(size / 1024 /1024);
        //可用内存
        long freeSize = Runtime.getRuntime().freeMemory();
        //最大 可用 内存
        long usedSize = Runtime.getRuntime().maxMemory();
        System.err.println(freeSize / 1024 /1024);
        System.err.println(usedSize / 1024 /1024);

        final int MB = 1024 * 1024;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

//        // 操作系统
        String osName = System.getProperty("os.name");
//        // 总的物理内存
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / MB;
//        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / MB;
//        // 已使用的物理内存
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
                .getFreePhysicalMemorySize())
                / MB;

        System.out.println(" os =" + osName);
        System.out.println(" totalMemorySize =" + totalMemorySize);
        System.out.println(" usedMemory =" + usedMemory);
    }
    public static void main(String[] args) {
        List data = new ArrayList();
        data.add("string");
        data.add("double");
        data.add("stringBuffer");
        data.add("stringBuilder");
        data.add("int");
        data.add("integer");
        data.add("long");
        // 删除以 string 开头的字符串
        //方式1
        way1UseIterator(data, "string");
        //删除以 int 开头的字符串
        //方式2
        way2UseDynamicSize(data,"int");

        new CollectionCut();

    }

    static void way1UseIterator(List list, String prefix){
        Iterator iterator = list.iterator();

        while(iterator.hasNext()){
            String s = iterator.next().toString();
            if(s.startsWith(prefix)) {
                System.out.println("remove ->" + s);
                iterator.remove();
            }
        }

        System.out.println("--------------- list --------------");
        System.out.println(list);

    }

    static void way2UseDynamicSize(List list, String prefix){
        // 终止 条件 i < list.size() ;  实时计算 size 大小
        for(int i=0 ; i < list.size(); i++){
            String s = (String)list.get(i);
            if(s.startsWith(prefix)){
                System.out.println("remove -->" +s);
                list.remove(i);
                //已经删除1 个元素，所以索引减 1
                i--;
            }
        }
        System.out.println("--------------- list --------------");
        System.out.println(list);
    }
}
