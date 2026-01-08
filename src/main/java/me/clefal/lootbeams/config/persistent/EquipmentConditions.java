package me.clefal.lootbeams.config.persistent;

import me.clefal.lootbeams.config.configs.Checker;
import me.clefal.lootbeams.config.configs.CustomConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.client.Minecraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
//? >=1.21.8 {
/*import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.Equippable;
*///?}
import java.util.Set;
import java.util.function.Supplier;

public class EquipmentConditions extends PersistentConfigData<RegisterConfigConditionEvent.RegisterEquipmentItemEvent> {
    public final static EquipmentConditions INSTANCE = new EquipmentConditions();

    public EquipmentConditions() {
        super();
        CustomConfig.EquipmentRegister equipmentRegister = CustomConfig.customConfig.equipmentRegister;

        super.conditions.add((item) -> Checker.checkItemInItemList(item, equipmentRegister.by_name));
        super.conditions.add((item) -> Checker.checkItemHasTagInTagList(item, equipmentRegister.by_tag));
        super.conditions.add((item) -> Checker.checkIsInThisModList(item, equipmentRegister.by_modid));

    }

    public static boolean isEquipment(LBItemEntity lbItemEntity) {
        Set<? extends ResourceLocation> resourceLocations = CustomConfig.customConfig.equipmentRegister.blacklist_by_name.get();
        var item = lbItemEntity.item().getItem().getItem();
        //? if <=1.21.1
        boolean b = item instanceof TieredItem;
        //? if >1.21.1
        //boolean b = false;
        // Mace was added since 1.20.5

        //? if >=1.20.5
        b = b || item instanceof MaceItem;
        return !resourceLocations.contains(lbItemEntity.resourceLocation())
                && (b
                //? < 1.21.8 {
                || item instanceof ArmorItem
                //?} else {
                /*|| ((Supplier<Boolean>) () -> {
            Equippable equippable = lbItemEntity.item().getItem().get(DataComponents.EQUIPPABLE);
            if (equippable != null && Minecraft.getInstance().player != null){
                return equippable.canBeEquippedBy(Minecraft.getInstance().player.getType());
            }
            return false;
        }).get()
                *///?}
                || item instanceof ShieldItem
                || item instanceof BowItem
                || item instanceof CrossbowItem
                || item instanceof TridentItem
                //? if >=1.20.5
                || item instanceof MaceItem
                || (INSTANCE.conditions.stream().anyMatch(x -> x.test(lbItemEntity))));
    }

    @Override
    public RegisterConfigConditionEvent.RegisterEquipmentItemEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterEquipmentItemEvent();
    }
}
