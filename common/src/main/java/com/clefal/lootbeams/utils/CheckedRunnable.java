package com.clefal.lootbeams.utils;

@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}
