package com.jarvis.com.jarvis.java8;

import java.util.Optional;

/**
 * Created by Jarvis on 9/25/16.
 */
public class OptionalTest1 {
    public static void main(String[] args) {
        final String text = "Hallo world!";
        Optional.ofNullable(text)//显示创建一个Optional壳
                .map(OptionalTest1::print).map(OptionalTest1::print).ifPresent(System.out::println);
        Optional.ofNullable(text).map(s -> {
            System.out.println(s);
            return s.substring(6);
        }).map(s -> null)//返回 null
                .ifPresent(System.out::println);
    } // 打印并截取str[5]之后的字符串

    private static String print(String str) {
        System.out.println(str);
        return str.substring(6);
    }
}
