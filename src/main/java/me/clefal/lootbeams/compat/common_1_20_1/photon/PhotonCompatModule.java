//? if =1.20.1 {
/*package me.clefal.lootbeams.compat.common_1_20_1.photon;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.util.Walkable;

public class PhotonCompatModule implements ILBCompatModule {
    public static PhotonCompatModule INSTANCE = new PhotonCompatModule();


    public static int getRGB(int argb) {
        return argb & 0xFFFFFF;
    }

    public static int makeARGB(int alpha, int rgb) {
        return (alpha << 24) | (rgb & 0xFFFFFF);
    }

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("photon");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Photon, enable PhotonCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(PhotonCompatModule.class);
            PhotonCompatConfig.getConfig();
        }
    }

    @SubscribeEvent
    public static void onHook(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        PhotonHelper.handleFX(event);

    }

    public enum Strategies implements EnumTranslatable {
        CompletelyReplace,
        Merge;

        @Override
        public String prefix() {
            return LootBeamsConstants.MODID + ".affect_strategy";
        }
    }

    public static class ReplaceConfig implements Walkable {
        public boolean replaceAlpha = true;
        public boolean replaceColor = true;

        public ReplaceConfig() {
        }

        public ReplaceConfig(boolean replaceAlpha, boolean replaceColor) {
            this.replaceAlpha = replaceAlpha;
            this.replaceColor = replaceColor;
        }
    }

}
*///?}