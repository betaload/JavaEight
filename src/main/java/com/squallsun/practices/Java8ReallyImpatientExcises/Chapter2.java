package com.squallsun.practices.Java8ReallyImpatientExcises;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
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

    public static Stream<Long> e5(long seed, long a, long c, long m) {
        return Stream.iterate(seed, x -> (a * x + c) % m);
    }

    public static void e6(String s) {
        Stream s1 = Stream.iterate(0, n -> n + 1).limit(s.length()).map(i -> s.charAt(i));
        Stream s2 = IntStream.range(0,s.length()).mapToObj(x->s.charAt(x));
        s1.forEach(System.out::print);
        s2.forEach(System.out::print);
    }


    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> iterSecond = second.iterator();
        return first.flatMap(t -> {
                if (iterSecond.hasNext()) {
                    return Arrays.asList(t, iterSecond.next()).stream();
                } else {
                    first.close();
                    return null;
                }
            }
        );
    }
    public static void e8() {
        Stream<String> first = Stream.of("1", "2", "3", "4", "5", "6");
        Stream<String> second = Stream.of("a", "b", "c", "d", "e");
        Iterator<String> iterSecond = second.iterator();
        first.flatMap(t -> {
                if (iterSecond.hasNext()) {
                    return Arrays.asList(t, iterSecond.next()).stream();
                } else {
                    first.close();
                    return null;
                }
            }
        ).forEach(System.out::print);
    }

    public static void e9() {
        Stream<String> first = Stream.of("1", "2", "3", "4", "5", "6");
        Stream<String> second = Stream.of("a", "b", "c", "d", "e");
        Iterator<String> iterSecond = second.iterator();
        first.flatMap(t -> {
                if (iterSecond.hasNext()) {
                    return Arrays.asList(t, iterSecond.next()).stream();
                } else {
                    first.close();
                    return null;
                }
            }
        ).forEach(System.out::print);
    }

    public static void main(String[] args) throws IOException {
//        e1();
//        e2();
//        e3();
//        e4();
//        e5(0, 25214903917L, 11, (2 ^ 48)).limit(5).forEach(System.out::println);
//        e6("test");
        e8();
    }

}
