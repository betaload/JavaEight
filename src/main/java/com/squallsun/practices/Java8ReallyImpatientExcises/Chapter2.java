package com.squallsun.practices.Java8ReallyImpatientExcises;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by SJQ on 2015/2/1.
 */
public class Chapter2 {

    public static void e1() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("./JavaEight/cities.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        System.out.println("CPUs : " + Runtime.getRuntime().availableProcessors());
        long count = words.parallelStream().filter(w -> w.length() > 8).count();
        System.out.println(count);
    }

    public static void e2() {
        List<String> words = Arrays.asList("Arrays", "readAllBytes", "getRuntime", "availableProcessors", "parallelStream", "words", "System", "IOException", "static", "Each", "void");
        words.stream().filter(word -> word.length() >= 8).peek((e) -> System.out.println("run -> " + e)).limit(5).count();
        words.stream().sorted((e1, e2) -> e1.length() > e2.length() ? -1 : 1).limit(5).forEach(item -> System.out.println(item));
    }

    public static void e3() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("./JavaEight/cities.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        long singleStart = System.nanoTime();
        words.stream().filter(w -> w.length() > 2).count();
        long singleEnd = System.nanoTime();
        System.out.println("single duration: " + (singleEnd - singleStart));
        long parallelStart = System.nanoTime();
        words.parallelStream().filter(w -> w.length() > 12).count();
        long parallelEnd = System.nanoTime();
        System.out.println("parallel duration: " + (parallelEnd - parallelStart));
    }

    public static void e4() {
        int[] values = {1, 4, 9, 16};

        //Prints one object
        Stream.of(values).forEach(l -> System.out.println(l));
        Stream.of(values).forEach(l -> System.out.println(l.getClass()));

        //Now each element is int
        IntStream stream = Arrays.stream(values, 0, values.length);
        stream.forEach(i-> System.out.println(i));
    }

    public static void main(String[] args) throws IOException {
        e1();
        e2();
        e3();
        e4();
    }

}
