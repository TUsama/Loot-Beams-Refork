//? if forge {

package me.clefal.lootbeams.loaders.forge.compat.apothesis;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemItem;

import net.minecraftforge.fml.ModList;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.*;


public class ApotheosisCompatModule implements ILBCompatModule {
    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Apotheosis, enable ApotheosisCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(new ApotheosisCompatModule());
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();
            return Match(Match(stack).of(
                    //ItemStack -> LootRarity
                    Case($(AffixHelper::hasAffixes), AffixHelper::getRarity),
                    Case($(v -> v.getItem() instanceof GemItem), v -> GemInstance.unsocketed(v).rarity()),
                    Case($(v -> (v.getItem() instanceof SalvageItem)), v -> RarityRegistry.getMaterialRarity(v.getItem())),
                    Case($(), v -> RarityRegistry.INSTANCE.emptyHolder())
            )).of(
                    //LootRarity -> ILBRarity
                    Case($(v -> v.is(RarityRegistry.INSTANCE.emptyHolder().getId())), v -> Option.none()),
                    Case($(v -> !v.isBound()), v -> Option.none()),
                    Case($(), v -> Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    v.get().toComponent(),
                                    LBColor.of(v.get().getColor().getValue()),
                                    v.get().ordinal()
                            )))
                    ));
        });
    }
}

 //?}

