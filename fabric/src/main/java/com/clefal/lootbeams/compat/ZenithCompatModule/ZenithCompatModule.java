package com.clefal.lootbeams.compat.ZenithCompatModule;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.compat.TrinketCompatModule.TrinketCompatModule;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import dev.shadowsoffire.apotheosis.adventure.affix.salvaging.SalvageItem;
import dev.shadowsoffire.apotheosis.adventure.loot.RarityRegistry;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemInstance;
import dev.shadowsoffire.apotheosis.adventure.socket.gem.GemItem;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import net.fabricmc.loader.api.FabricLoader;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;

import java.awt.*;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.*;


public class ZenithCompatModule implements ILBCompatModule {
    public final static ZenithCompatModule INSTANCE = new ZenithCompatModule();
    @Override
    public boolean shouldBeEnable() {
        return FabricLoader.getInstance().isModLoaded(Apotheosis.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Zenith, enable ZenithCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
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
                                    new Color(v.get().getColor().getValue()),
                                    v.get().ordinal()
                            )))
                    ));
        });
    }

}
