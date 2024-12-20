package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.CustomConfig;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class EquipmentConditions extends PersistentConfigData<RegisterConfigConditionEvent.RegisterEquipmentItemEvent>{
    public final static EquipmentConditions INSTANCE = new EquipmentConditions();

    public EquipmentConditions() {
        super();
        CustomConfig.EquipmentRegister equipmentRegister = CustomConfig.customConfig.equipmentRegister;
        super.conditions.add(itemStack -> equipmentRegister.by_name.get().stream().anyMatch(resourceLocation -> Checker.checkItemEquality(itemStack, resourceLocation)));
        super.conditions.add(itemStack -> equipmentRegister.by_tag.get().stream().anyMatch(string -> Checker.checkTagContainItem(itemStack, ResourceLocationHelper.fromWholeName(string.replace("#", "")))));
        super.conditions.add(itemStack -> equipmentRegister.by_modid.get().stream().anyMatch(string -> Checker.checkIsThisMod(itemStack, string)));

    }

    public static boolean isEquipment(ItemStack itemStack) {
        Set<? extends ResourceLocation> resourceLocations = CustomConfig.customConfig.equipmentRegister.block_item_in_modid.get();
        var item = itemStack.getItem();
        return resourceLocations
                .stream()
                .noneMatch(x -> Checker.checkItemEquality(itemStack, x))
                && (INSTANCE.conditions
                .stream()
                .anyMatch(x -> x.test(itemStack)) || item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShieldItem || item instanceof BowItem || item instanceof CrossbowItem);
    }

    @Override
    public RegisterConfigConditionEvent.RegisterEquipmentItemEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterEquipmentItemEvent();
    }
}
