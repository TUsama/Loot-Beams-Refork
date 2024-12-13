package com.clefal.lootbeams.utils;
@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Throwable;

}
