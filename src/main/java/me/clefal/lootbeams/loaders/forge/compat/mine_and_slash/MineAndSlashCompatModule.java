//? if forge {

package me.clefal.lootbeams.loaders.forge.compat.mine_and_slash;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.events.RegisterLBRarityEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.robertx22.addons.orbs_of_crafting.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRarityItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraftforge.fml.ModList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.clefal.nirvana_lib.relocated.io.vavr.API.*;

public class MineAndSlashCompatModule implements ILBCompatModule {

    public final static MineAndSlashCompatModule INSTANCE = new MineAndSlashCompatModule();

    private final List<String> rarities = new ArrayList<>();

    public static LBRarity getNonSoulRarity() {
        return LBRarity.of(Component.translatable("lootbeams.mod_rarity.non_soul"), LBColor.of(FastColor.ARGB32.color(255, 121, 121, 121)), -1);
    }

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(SlashRef.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Mine and Slash, enable MineAndSlashCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            INSTANCE.rarities.add("common");
            INSTANCE.rarities.add("uncommon");
            INSTANCE.rarities.add("rare");
            INSTANCE.rarities.add("epic");
            INSTANCE.rarities.add("legendary");
            INSTANCE.rarities.add("mythic");
            INSTANCE.rarities.add("unique");
            INSTANCE.rarities.add("runeword");

        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity ->

                Match(Match(itemEntity.getItem()).option(
                        //ItemEntity -> GearRarity
                        Case($(stack -> StackSaving.GEARS.has(stack)), stack -> StackSaving.GEARS.loadFrom(stack).getRarity()),
                        Case($(stack -> ICommonDataItem.load(stack) != null), stack -> ICommonDataItem.load(stack).getRarity()),
                        Case($(stack -> stack.getItem() instanceof IRarityItem), stack -> ((IRarityItem) stack.getItem()).getItemRarity(stack)),
                        Case($(stack -> stack.getItem() instanceof GemItem), stack -> ((GemItem) stack.getItem()).getBaseGem().getRarity()),
                        Case($(stack -> StackSaving.OMEN.has(stack)), stack -> StackSaving.OMEN.loadFrom(stack).getRarity())

                )).option(
                        //GearRarity -> LBRarity
                        Case($(option -> !option.isEmpty()), option -> LBItemEntity.of(itemEntity, LBRarity.of(
                                option.get().locName(),
                                LBColor.of(option.get().textFormatting().getColor()),
                                rarities.indexOf(option.get().guid)
                        ))),
                        Case($(option -> GearSlot.getSlotOf(itemEntity.getItem()) != null), option -> LBItemEntity.of(itemEntity, getNonSoulRarity()))
                ));

    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterConfigConditionEvent.RegisterEquipmentItemEvent event) {
        event.conditions.add(lbItemEntity -> {
            if (lbItemEntity.item().getItem().getClass().toString().contains("com.robertx22.mine_and_slash.vanilla_mc.items.gearitems"))
                return true;
            GearSlot slotOf = GearSlot.getSlotOf(lbItemEntity.item().getItem());
            return (slotOf != null && slotOf.fam != SlotFamily.NONE);
        });
    }

    @SubscribeEvent
    public void registerWhitelistCondition(RegisterConfigConditionEvent.RegisterWhitelistEvent event) {
        //currenccy, I prefer to show the currency always.
        event.conditions.add(lbItemEntity -> lbItemEntity.item().getItem().getItem() instanceof IItemAsCurrency);
    }

}
//?}

