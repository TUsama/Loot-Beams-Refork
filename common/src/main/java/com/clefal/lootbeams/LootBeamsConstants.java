package com.clefal.lootbeams;

import com.clefal.lootbeams.events.LBEventBus;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.BusBuilderImpl;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootBeamsConstants {

	public static final String MOD_NAME = "ExampleMod";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String MODID = "lootbeams";
    public static final ResourceLocation LOOT_DROP = new ResourceLocation(MODID, "loot_drop");
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    public static final LBEventBus EVENT_BUS = new LBEventBus(new BusBuilderImpl());
}