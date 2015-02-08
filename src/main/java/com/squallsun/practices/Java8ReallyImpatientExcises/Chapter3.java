package com.squallsun.practices.Java8ReallyImpatientExcises;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Chapter3 {

	// e1
	public static void logIf(Level level, Supplier<Boolean> condition,
			Supplier<String> message) {
		Logger logger = Logger.getGlobal();
		logger.setLevel(Level.WARNING);
		if (logger.isLoggable(level) && condition.get()) {
			logger.log(level, message.get());
		}
	}

	public static void e1() {
		System.out.println("e1");
		logIf(Level.FINE, () -> true, () -> "Test");
	}

	// e2
	public static void e2(ReentrantLock lock, Runnable runnable) {
		lock.lock();
		try {
			runnable.run();
		} finally {
			lock.unlock();
		}
	}

	// e3
	public static void e3(BooleanSupplier assertion) {
		if (!assertion.getAsBoolean()) {
			throw new AssertionError();
		}
	}

	// e5

	public static Image transform(Image in, ColorTransformer f) {
		int width = (int) in.getWidth();
		int height = (int) in.getHeight();
		WritableImage out = new WritableImage(width, height);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				out.getPixelWriter().setColor(x, y,
						f.apply(x, y, in.getPixelReader().getColor(x, y)));
		return out;
	}

	public static Comparator<String> e6(Comparator<String> c1,
			Comparator<String> c2) {
		return (s1, s2) -> {
			int r1 = c1.compare(s1, s2);
			return r1 == 0 ? c2.compare(s1, s2) : r1;

		};
	}

	// e9
	public <T> Comparator<T> lexographicComparator(String... fieldNames) {
		return (x, y) -> {
			for (String fieldName : fieldNames) {
				try {
					Field field = x.getClass().getDeclaredField(fieldName);
					field.setAccessible(true);
					Object valueX = field.get(x);
					Object valueY = field.get(y);
					if (valueX == null && valueY == null)
						continue;
					if (valueX == null || valueY == null)
						return valueX == null ? 1 : -1;
					int compareResult = field.get(x).toString()
							.compareTo(field.get(y).toString());
					if (compareResult != 0) {
						return compareResult;
					}
				} catch (Exception e) {
					InvocationTargetException targetEx = (InvocationTargetException) e;
					Throwable t = targetEx.getTargetException();
					System.out.println(t.getMessage());
				}
			}
			return 0;
		};
	}

	// e20
	public static <T, U> List<U> map(List<T> list, Function<T, U> function) {
		return list.stream().map(function).collect(Collectors.toList());
	}

	// e21

	public static <T, U> Future<U> map(Future<T> future, Function<T, U> mapper) {
		return new Future<U>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return future.cancel(mayInterruptIfRunning);
			}

			@Override
			public boolean isCancelled() {
				return future.isCancelled();
			}

			@Override
			public boolean isDone() {
				return future.isDone();
			}

			@Override
			public U get() throws InterruptedException, ExecutionException {
				return mapper.apply(future.get());
			}

			@Override
			public U get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException,
					TimeoutException {
				return mapper.apply(future.get(timeout, unit));
			}
		};
	}

	public static void main(String[] args) {
		e1();
		e2(new ReentrantLock(), () -> System.out.println("e2"));
		e3(() -> 1 + 1 == 10);
	}
}

@FunctionalInterface
interface ColorTransformer {
	Color apply(int x, int y, Color colorAtXY);
}
