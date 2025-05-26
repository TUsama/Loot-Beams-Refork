//? if =1.21.1 && neoforge {
/*package me.clefal.lootbeams.compat.neoforged_1_21_1;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.socket.gem.GemItem;
import net.neoforged.fml.ModList;

import java.awt.*;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.*;

public class ApotheosisCompatModule implements ILBCompatModule {
    public final static ApotheosisCompatModule INSTANCE = new ApotheosisCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Apotheosis, enable ApotheosisCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();

            return Match(Match(stack).option(
                    //ItemStack -> LootRarity
                    Case($(AffixHelper::hasAffixes), AffixHelper::getRarity),
                    Case($(v -> (v.getItem() instanceof SalvageItem)), v -> RarityRegistry.getMaterialRarity(v.getItem()))
            )).of(
                    //LootRarity -> ILBRarity
                    Case($(v -> v.isEmpty() || v.get().is(RarityRegistry.INSTANCE.emptyHolder().getId())), v -> Option.none()),
                    Case($(v -> !v.get().isBound()), v -> Option.none()),
                    Case($(), v -> Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    v.get().get().toComponent(),
                                    LBColor.ofMutable(v.get().get().color()),
                                    v.get().get().sortIndex()
                            )))
                    ));
        });
        //handle new gem item.
        event.register(itemEntity -> {
            var stack = itemEntity.getItem();
            return Option.of(stack)
                    .filter(x -> x.getItem() instanceof GemItem)
                    .map(x -> GemInstance.unsocketed(x).purity())
                    .map(x -> LBItemEntity.of(
                            itemEntity,
                            LBRarity.of(
                                    x.toComponent(),
                                    LBColor.ofMutable(x.getColor()),
                                    x.ordinal()
                            )
                    ));
        });

    }
    //put gem to whitelist
    @SubscribeEvent
    public void registerWhitelist(RegisterConfigConditionEvent.RegisterWhitelistEvent event){
        event.conditions.add(x -> x.item().getItem().getItem() instanceof GemItem || x.item().getItem().getItem() instanceof SalvageItem ||AffixHelper.hasAffixes(x.item().getItem()));
    }
}
*///?}