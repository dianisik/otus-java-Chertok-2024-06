/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.otus;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        List<String> myList = Lists.newArrayList();
        myList.add("Shop");
        myList.add("Stage");
        myList.add("Trend");

        System.out.println(myList);
    }
}
