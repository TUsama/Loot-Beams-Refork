package com.clefal.lootbeams.utils;

import java.util.Objects;
import java.util.function.Function;

public interface Attempt {

    static boolean hasException(CheckedRunnable runnable) {
        try {
            runnable.run();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    static boolean hasException(CheckedRunnable runnable, Runnable runWhenFail) {
        try {
            runnable.run();
            return false;
        } catch (Exception e) {
            runWhenFail.run();
            return true;
        }
    }

    static <T, R> Function<T, R> apply(CheckedFunction<T, R> function) {
        Objects.requireNonNull(function);

        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    static <T, R> Function<T, R> apply(CheckedFunction<T, R> function, Function<T, R> handlerWhenFail) {
        Objects.requireNonNull(function);

        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable ex) {
                handlerWhenFail.apply(t);
                throw new RuntimeException(ex);
            }
        };
    }

    static <T, R> Function<T, R> apply(CheckedFunction<T, R> function, Function<T, R> handlerWhenFail, Function<T, Runnable> runWhenFail) {
        Objects.requireNonNull(function);

        return t -> {
            try {
                return function.apply(t);
            } catch (Throwable ex) {
                handlerWhenFail.apply(t);
                runWhenFail.apply(t).run();
                throw new RuntimeException(ex);
            }
        };
    }

}
