package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigCustomRarity extends ModifyingConfigHandler {
    public final static String langKeyFormat = "lootbeams.fake_rarity.";
    private final Set<ResourceLocation> customRarity = new HashSet<>();


    public ConfigCustomRarity() {
        /*this.customRarity = ConfigurationManager.<List<String>>request(Config.CUSTOM_RARITIES)
                .stream()
                .filter(x -> !x.contains("#"))
                .map(x -> x.replace("#", ""))
                .map(ResourceLocation::tryParse)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());*/
    }

    @Override
    public LBItemEntity modify(LBItemEntity lbItemEntity) {
        if (customRarity.isEmpty()) return lbItemEntity;
        /*ItemStack item = lbItemEntity.item().getItem();
        AtomicReference<LBItemEntity> newEntity = new AtomicReference<>();
        item.getTags()
                .map(x -> Pair.of(customRarity.contains(x.location()), x.location()))
                .filter(Pair::getFirst)
                .findFirst()
                .ifPresentOrElse(x -> {
                    //provide custom rarity
                    ResourceLocation Location = x.getSecond();
                    String rarity = Location.getPath();
                    MutableComponent newName = Component.literal(I18n.exists(langKeyFormat + rarity) ? I18n.get(langKeyFormat + rarity) : rarity);
                    LBRarity old = lbItemEntity.rarity();
                    //todo all custom rarities' ordinal is 4, could this be changed?
                    newEntity.set(lbItemEntity.to(LBRarity.of(newName, old.color(), 4)));

                }, () -> {
                    //if this item doesn't have a custom rarity, use the built-in rarity checker instead.
                    newEntity.set(lbItemEntity);
                });*/

        return lbItemEntity;
    }
}
