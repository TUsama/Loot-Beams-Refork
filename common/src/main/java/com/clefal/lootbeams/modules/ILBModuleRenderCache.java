package com.clefal.lootbeams.modules;

import java.util.function.BiConsumer;

public interface ILBModuleRenderCache<T extends ILBModulePersistentData, E> {

    BiConsumer<T, E> getDataHandler();

    default void handle(T data, E obj, boolean alreadyRun) {
        getDataHandler().accept(data, obj);
    }
}
