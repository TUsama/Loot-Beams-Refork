//? (1.20.1 && forge) || (1.21.1 && neoforge){
package me.clefal.lootbeams.compat.multiversion_compat;

import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import net.minecraft.network.chat.Component;
import org.yanbwe.raritycore.api.RarityCoreAPI;
import org.yanbwe.raritycore.util.RarityColorUtil;

public class RarityCoreCompatModule implements ILBCompatModule {
    public final static RarityCoreCompatModule INSTANCE = new RarityCoreCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("raritycore");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Rarity Core, enable RarityCoreCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            var stack = itemEntity.getItem().getItem();
            int rarity = RarityCoreAPI.getRarity(stack);
            return Option.some(LBItemEntity.of(itemEntity, LBRarity.of(Component.literal(RarityCoreAPI.getLocalizedTooltip(stack)), LBColor.fromRGB(RarityColorUtil.getRarityRgbColor(rarity)), rarity)));
        });
    }
}
//?}
