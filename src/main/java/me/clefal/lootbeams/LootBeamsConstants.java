package me.clefal.lootbeams;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.BusBuilder;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.IEventBus;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootBeamsConstants {

    public static final String MOD_NAME = "Loot Beams Refork";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String MODID = "lootbeams";
    public static final ResourceLocation LOOT_DROP = ResourceLocation.tryBuild(MODID, "loot_drop");
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    public static final IEventBus EVENT_BUS = BusBuilder.builder().setExceptionHandler((iEventBus, event, eventListeners, i, throwable) -> {
        try {
            throw throwable;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }).build();
}