package com.clefal.lootbeams;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkConstants;

@Mod(Constants.MODID)
public class LootBeams {

	public LootBeams() {

		getModLoadingContext().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

		getModLoadingContext().registerConfig(ModConfig.Type.CLIENT, Configuration.CLIENT_CONFIG);

		//FMLJavaModLoadingContext.get().getModEventBus().addListener(com.lootbeams.ClientSetup::init);


	}

	public static ModLoadingContext getModLoadingContext(){
		return ModLoadingContext.get();
	}

}
