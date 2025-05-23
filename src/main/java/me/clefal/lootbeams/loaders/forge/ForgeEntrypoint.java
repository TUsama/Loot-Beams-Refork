//? if forge {
package me.clefal.lootbeams.loaders.forge;

import com.mojang.logging.LogUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.LootBeamsRefork;
import me.clefal.lootbeams.config.configs.ConfigManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

@Mod(LootBeamsConstants.MODID)
public class ForgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeEntrypoint() {
        LootBeamsRefork.initialize();
        getModLoadingContext().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ConfigManager::init);
    }

    public static ModLoadingContext getModLoadingContext(){
        return ModLoadingContext.get();
    }
}
//?}
