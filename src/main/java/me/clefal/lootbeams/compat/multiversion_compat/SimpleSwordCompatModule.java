//? simplesword{
package me.clefal.lootbeams.compat.multiversion_compat;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
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
import net.minecraft.network.chat.TextColor;
import net.sweenus.simplyswords.item.RunicSwordItem;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.Styles;

public class SimpleSwordCompatModule implements ILBCompatModule {
    public static final SimpleSwordCompatModule INSTANCE = new SimpleSwordCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("simplyswords");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Simple Sword, enable SimpleSwordCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            if (itemEntity.getItem().getItem() instanceof UniqueSwordItem) {
                TextColor color = itemEntity.getItem().getHoverName().getStyle().getColor();
                if (color != null) {
                    return API.Match(color.getValue())
                            .option(
                                    API.Case(API.$(x -> x.equals(Styles.COMMON.getColor().getValue())), x -> Tuple.of(Component.translatable("lootbeams.mod_rarity.simple_swords.common"), x)),
                                    API.Case(API.$(x -> x.equals(Styles.UNIQUE.getColor().getValue())), x -> Tuple.of(Component.translatable("lootbeams.mod_rarity.simple_swords.unique"), x)),
                                    API.Case(API.$(x -> x.equals(Styles.LEGENDARY.getColor().getValue())), x -> Tuple.of(Component.translatable("lootbeams.mod_rarity.simple_swords.legendary"), x))
                            ).map(x -> {
                                return LBItemEntity.of(itemEntity, LBRarity.of(x._1, LBColor.of(x._2), -1));
                            });
                }
            } else if (itemEntity.getItem().getItem() instanceof RunicSwordItem) {
                return Option.some(LBItemEntity.of(itemEntity, LBRarity.of(Component.translatable("lootbeams.mod_rarity.simple_swords.common"), LBColor.of(Styles.RUNIC.getColor().getValue()), 0)));
            }

            return Option.none();

        });
    }
}
//?}