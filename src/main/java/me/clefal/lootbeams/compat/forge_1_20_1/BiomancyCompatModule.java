//? if 1.20.1 && forge {
/*package me.clefal.lootbeams.compat.forge_1_20_1;

import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import com.github.elenterius.biomancy.BiomancyMod;
import com.github.elenterius.biomancy.item.ItemTooltipStyleProvider;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;

public class BiomancyCompatModule implements ILBCompatModule {
    public static final BiomancyCompatModule INSTANCE = new BiomancyCompatModule();
    private List<String> rarities = new ArrayList<>();
    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(BiomancyMod.MOD_ID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Biomancy, enable BiomancyCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            rarities.addAll(List.of(
                    "common",
                    "uncommon",
                    "rare",
                    "very_rare",
                    "ultra_rare"
            ));
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            ItemStack item = itemEntity.getItem();
            return Option.some(item)
                    .filter(x -> x.getRarity().name().contains("biomancy_"))
                    .filter(x -> itemEntity.getItem().getItem() instanceof ItemTooltipStyleProvider)
                    .map(x -> {
                        Rarity rarity = x.getRarity();
                        ItemTooltipStyleProvider itemTooltipStyleProvider = (ItemTooltipStyleProvider) x.getItem();
                        String pureRarity = rarity.name().replace("biomancy_", "");
                        return LBItemEntity.of(itemEntity,
                                LBRarity.of(
                                        Component.translatable("lootbeams.mod_rarity." + pureRarity),
                                        LBColor.of(itemTooltipStyleProvider.getTooltipColorWithAlpha(item)),
                                        this.rarities.contains(pureRarity) ? this.rarities.indexOf(pureRarity) : -1

                                ));
                    });
        });
    }
}
*///?}