package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.config.configs.CustomConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.Set;

public class EquipmentConditions extends PersistentConfigData<RegisterConfigConditionEvent.RegisterEquipmentItemEvent> {
    public final static EquipmentConditions INSTANCE = new EquipmentConditions();

    public EquipmentConditions() {
        super();
        CustomConfig.EquipmentRegister equipmentRegister = CustomConfig.customConfig.equipmentRegister;

        super.conditions.add((item) -> Checker.checkItemInItemSet(item, equipmentRegister.by_name));
        super.conditions.add((item) -> Checker.checkItemHasTagInTagSet(item, equipmentRegister.by_tag));
        super.conditions.add((item) -> Checker.checkIsInThisModSet(item, equipmentRegister.by_modid));

    }

    public static boolean isEquipment(LBItemEntity lbItemEntity) {
        Set<? extends ResourceLocation> resourceLocations = CustomConfig.customConfig.equipmentRegister.blacklist_by_name.get();
        var item = lbItemEntity.item().getItem().getItem();
        return !resourceLocations.contains(lbItemEntity.resourceLocation())
                && (item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShieldItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem || (INSTANCE.conditions
                .stream()
                .anyMatch(x -> x.test(lbItemEntity))));
    }

    @Override
    public RegisterConfigConditionEvent.RegisterEquipmentItemEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterEquipmentItemEvent();
    }
}
