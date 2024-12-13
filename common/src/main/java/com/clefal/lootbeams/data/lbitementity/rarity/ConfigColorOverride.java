package com.clefal.lootbeams.data.lbitementity.rarity;

import com.clefal.lootbeams.Constants;
import com.google.common.collect.ImmutableMap;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.utils.Attempt;
import com.mojang.datafixers.util.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigColorOverride extends ModifyingConfigHandler {


    private final List<String> list;

    private final Map<Order, List<Pair<String, Color>>> configMap;

    private final List<String> overrides = ConfigurationManager.request(Config.COLOR_OVERRIDES);
    private final List<String> order = ConfigurationManager.request(Config.COLOR_APPLY_ORDER);

    public ConfigColorOverride() {
        List<String> overrides = ConfigurationManager.request(Config.COLOR_OVERRIDES);
        this.list = Arrays.stream(Order.values())
                .map(Enum::toString)
                .toList();

        if (overrides.isEmpty()) {
            this.configMap = ImmutableMap.of();
        } else {
            this.configMap = overrides.stream()
                    .filter((s) -> (!s.isBlank()))
                    .map(s -> s.split("="))
                    .filter(strings -> strings.length == 2)
                    //validate color
                    .filter(strings -> !Attempt.hasException(() -> Color.decode(strings[1]), () -> Constants.LOGGER.error(String.format("Color overrides error! \"%s\" is not a valid hex color for \"%s\"", strings[1], strings[0]))))
                    .map(strings -> Pair.of(strings[0], Color.decode(strings[1])))
                    .collect(Collectors.groupingBy(x -> {
                        String name = x.getFirst();
                        if (!name.contains(":")) return Order.MODID;
                        if (name.startsWith("#")) return Order.TAG;
                        return Order.ITEM;
                    }, () -> new EnumMap<>(Order.class), Collectors.toList()));
        }
    }


    @Override
    public LBItemEntity modify(LBItemEntity lbItemEntity) {
        if (!overrides.isEmpty()) {

            return order.stream()
                    .filter(x -> !list.contains(x))
                    .map(Order::valueOf)
                    .filter(order1 -> configMap.get(order1) != null)
                    .map(order1 -> order1.mutate.apply(configMap.get(order1), lbItemEntity))
                    .findFirst()
                    .orElse(lbItemEntity);


        }
        return lbItemEntity;
    }
}
