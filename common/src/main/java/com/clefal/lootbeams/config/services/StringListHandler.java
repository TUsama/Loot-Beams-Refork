package com.clefal.lootbeams.config.services;

import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.data.LBItemEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.vavr.API.For;

public class StringListHandler {

    public enum Case {
        RESOURCE_LOCATION(string -> ResourceLocation.read(string).result().isPresent(), PlatformChecker.PLATFORM::checkItemEquality),
        TAG(string -> string.charAt(0) == '#' && ResourceLocation.read(string.replace("#", "")).result().isPresent(), PlatformChecker.PLATFORM::checkTagContainItem),
        MOD_ID(string -> !string.contains(":") && !string.contains("#"), PlatformChecker.PLATFORM::checkIsThisMod);

        public final Predicate<String> is;
        public final BiPredicate<LBItemEntity, String> in;

        Case(Predicate<String> is, BiPredicate<LBItemEntity, String> in) {
            this.is = is;
            this.in = in;
        }
    }

    public static class RenderList {
        private final static EnumMap<Case, Set<String>> whiteList;
        private final static EnumMap<Case, Set<String>> blackList;

        static {
            List<String> white = ConfigurationManager.<List<String>>request(Config.WHITELIST);
            List<String> black = ConfigurationManager.<List<String>>request(Config.BLACKLIST);
            List<Case> cases = Arrays.stream(Case.values()).toList();


            whiteList = white.stream()
                    .filter(x -> !x.isBlank())
                    .map(x -> Pair.of(cases.stream()
                            .filter(aCase -> aCase.is.test(x))
                            .findFirst(), x))
                    .filter(x -> x.getFirst().isPresent())
                    .map(x -> Pair.of(x.getFirst().get(), x.getSecond()))
                    .collect(Collectors.groupingBy(x -> x.getFirst(), () -> new EnumMap<>(Case.class), Collectors.mapping(x -> x.getSecond(), Collectors.toSet())));


            blackList = black.stream()
                    .filter(x -> !x.isBlank())
                    .map(x -> Pair.of(cases.stream()
                            .filter(aCase -> aCase.is.test(x))
                            .findFirst(), x))
                    .filter(x -> x.getFirst().isPresent())
                    .map(x -> Pair.of(x.getFirst().get(), x.getSecond()))
                    .collect(Collectors.groupingBy(x -> x.getFirst(), () -> new EnumMap<>(Case.class), Collectors.mapping(x -> x.getSecond(), Collectors.toSet())));


        }

        public static boolean checkInWhiteList(LBItemEntity lbItemEntity) {


            return For(whiteList.entrySet(), entry ->
                    For(entry.getValue())
                            .yield(string -> {
                                Case aCase = entry.getKey();
                                return aCase.in.test(lbItemEntity, string);
                            }))
                    .filter(x -> x)
                    .getOrElse(false);
        }

        public static boolean checkInBlackList(LBItemEntity lbItemEntity) {
            return For(blackList.entrySet(), entry ->
                    For(entry.getValue())
                            .yield(string -> {
                                Case aCase = entry.getKey();
                                return aCase.in.test(lbItemEntity, string);
                            }))
                    .filter(x -> x)
                    .getOrElse(false);
        }
    }

    public static class SoundList {
        private final static EnumMap<Case, Set<String>> whiteList;
        private final static EnumMap<Case, Set<String>> blackList;

        static {
            List<String> white = ConfigurationManager.<List<String>>request(Config.SOUND_ONLY_WHITELIST);
            List<String> black = ConfigurationManager.<List<String>>request(Config.SOUND_ONLY_BLACKLIST);
            List<Case> cases = Arrays.stream(Case.values()).toList();


            whiteList = white.stream()
                    .filter(x -> !x.isBlank())
                    .map(x -> Pair.of(cases.stream()
                            .filter(aCase -> aCase.is.test(x))
                            .findFirst(), x))
                    .filter(x -> x.getFirst().isPresent())
                    .map(x -> Pair.of(x.getFirst().get(), x.getSecond()))
                    .collect(Collectors.groupingBy(x -> x.getFirst(), () -> new EnumMap<>(Case.class), Collectors.mapping(x -> x.getSecond(), Collectors.toSet())));

            blackList = black.stream()
                    .filter(x -> !x.isBlank())
                    .map(x -> Pair.of(cases.stream()
                            .filter(aCase -> aCase.is.test(x))
                            .findFirst(), x))
                    .filter(x -> x.getFirst().isPresent())
                    .map(x -> Pair.of(x.getFirst().get(), x.getSecond()))
                    .collect(Collectors.groupingBy(x -> x.getFirst(), () -> new EnumMap<>(Case.class), Collectors.mapping(x -> x.getSecond(), Collectors.toSet())));
        }

        public static boolean checkInWhiteList(LBItemEntity lbItemEntity) {


            return For(whiteList.entrySet(), entry ->
                    For(entry.getValue())
                            .yield(string -> {
                                Case aCase = entry.getKey();
                                return aCase.in.test(lbItemEntity, string);
                            }))
                    .filter(x -> x)
                    .getOrElse(false);
        }

        public static boolean checkInBlackList(LBItemEntity lbItemEntity) {
            return For(blackList.entrySet(), entry ->
                    For(entry.getValue())
                            .yield(string -> {
                                Case aCase = entry.getKey();
                                return aCase.in.test(lbItemEntity, string);
                            }))
                    .filter(x -> x)
                    .getOrElse(false);
        }
    }
}

