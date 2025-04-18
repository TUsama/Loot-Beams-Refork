package com.clefal.lootbeams.modules.compat.photon;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.platform.Services;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.util.Walkable;
import org.jetbrains.annotations.NotNull;

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
        return Services.PLATFORM.isModLoaded("photon");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Photon, enable PhotonCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
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
        public @NotNull String prefix() {
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
