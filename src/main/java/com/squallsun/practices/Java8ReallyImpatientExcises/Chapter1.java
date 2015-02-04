package com.squallsun.practices.Java8ReallyImpatientExcises;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJQ on 2015/2/1.
 */
public class Chapter1 {

    public static void e2() {

        //E2
        File directory = new File("./JavaEight");
        File[] allSubDirectories = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File allSubDirectory : allSubDirectories) {
            System.out.println(allSubDirectory.getName());
        }

        allSubDirectories = null;
        allSubDirectories = directory.listFiles(file -> file.isDirectory());
        for (File allSubDirectory : allSubDirectories) {
            System.out.println(allSubDirectory.getName());
        }


        allSubDirectories = null;
        allSubDirectories = directory.listFiles(File::isDirectory);
        for (File allSubDirectory : allSubDirectories) {
            System.out.println(allSubDirectory.getName());
        }

    }

    //E3
    public static void e3(String suffix) {

        //E2
        File directory = new File("./JavaEight");
        File[] allFiles = directory.listFiles(file -> file.getName().endsWith(suffix));
        for (File file : allFiles) {
            System.out.println(file.getName());
        }


    }

    //E6
    @FunctionalInterface
    private static interface RunnableEx {
        void run() throws Exception;

    }

    public static Runnable uncheck(RunnableEx runnableEx) {
        return () -> {
            try {
                runnableEx.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void e6() {
        new Thread(uncheck(() -> {
            System.out.println("Zzz");
            Thread.sleep(10000);
        })).start();
    }

    //E7
    public static void e7() {
        Runnable r = andThen(() -> System.out.println("Run this first"), () -> System.out.println("Run this second"));
        new Thread(r).run();
    }

    private static Runnable andThen(Runnable first, Runnable second) {
        return () -> {
            first.run();
            second.run();
        };
    }

    //E8

    public static void e8() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add(() -> System.out.println(name));
        }
        runners.forEach((runnable) -> runnable.run());//Works as expected
        List<Runnable> runners2 = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
        // must use a temp var which not changed in lambda.
            String name = names[i];
            runners2.add(() -> System.out.println(name));
        }
    }


    public static void main(String[] args) {
        e2();
        e3("xml");
        e6();
        e7();
        e8();
    }
}
