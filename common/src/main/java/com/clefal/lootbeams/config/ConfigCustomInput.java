package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.PlatformChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.google.common.base.Objects;
import me.fzzyhmstrs.fzzy_config.util.Walkable;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ConfigCustomInput implements Walkable {
    public String input;
    public InputType type;

    public ConfigCustomInput(String input) {
        this.input = input;
        this.type = Arrays.stream(InputType.values()).filter(x -> x.is.test(input)).findFirst().orElse(InputType.INVALID);
    }

    public enum InputType {
        RESOURCE_LOCATION(string -> ResourceLocation.read(string).result().isPresent(), PlatformChecker.PLATFORM::checkItemEquality),
        TAG(string -> string.charAt(0) == '#' && ResourceLocation.read(string.replace("#", "")).result().isPresent(), PlatformChecker.PLATFORM::checkTagContainItem),
        MOD_ID(string -> !string.contains(":") && !string.contains("#"), PlatformChecker.PLATFORM::checkIsThisMod),
        INVALID(null, null);

        public final Predicate<String> is;
        public final BiPredicate<LBItemEntity, String> in;

        InputType(Predicate<String> is, BiPredicate<LBItemEntity, String> in) {
            this.is = is;
            this.in = in;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ConfigCustomInput that)) return false;
        return Objects.equal(input, that.input) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(input, type);
    }
}
