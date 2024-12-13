package com.clefal.lootbeams.config;

import java.util.List;

public enum Config {

    ITEMS_GLOW(Boolean.class),
    ALL_ITEMS(Boolean.class),
    ONLY_EQUIPMENT(Boolean.class),
    ONLY_RARE(Boolean.class),

    WHITELIST(List.class),
    BLACKLIST(List.class),
    COLOR_OVERRIDES(List.class),



    COLOR_APPLY_ORDER(List.class),



    ENABLE_BEAM(Boolean.class),
    RENDER_NAME_COLOR(Boolean.class),
    RENDER_RARITY_COLOR(Boolean.class),
    BEAM_RADIUS(Double.class),
    BEAM_HEIGHT(Double.class),
    COMMON_SHORTER_BEAM(Boolean.class),
    BEAM_Y_OFFSET(Double.class),
    BEAM_ALPHA(Double.class),
    BEAM_FADE_DISTANCE(Double.class),
    BEAM_FADE_IN_TIME(Integer.class),

    SOLID_BEAM(Boolean.class),
    REQUIRE_ON_GROUND(Boolean.class),

    GLOW_EFFECT(Boolean.class),
    GLOW_EFFECT_RADIUS(Double.class),

    PARTICLES(Boolean.class),

    ENABLE_TOOLTIPS(Boolean.class),



    BORDERS(Boolean.class),
    RENDER_NAMETAGS(Boolean.class),
    RENDER_NAMETAGS_ONLOOK(Boolean.class),
    RENDER_STACKCOUNT(Boolean.class),
    NAMETAG_LOOK_SENSITIVITY(Double.class),
    NAMETAG_TEXT_ALPHA(Double.class),
    NAMETAG_BACKGROUND_ALPHA(Double.class),
    NAMETAG_SCALE(Double.class),
    NAMETAG_Y_OFFSET(Double.class),

    CUSTOM_RARITIES(List.class),


    PARTICLE_SIZE(Double.class),
    PARTICLE_SPEED(Double.class),
    PARTICLE_RADIUS(Double.class),
    PARTICLE_COUNT(Double.class),
    PARTICLE_LIFETIME(Integer.class),
    RANDOMNESS_INTENSITY(Double.class),
    PARTICLE_RARE_ONLY(Boolean.class),
    PARTICLE_DIRECTION_X(Double.class),
    PARTICLE_DIRECTION_Y(Double.class),
    PARTICLE_DIRECTION_Z(Double.class),
    SPIN_AROUND_BEAM(Boolean.class),
    TRAILS(Boolean.class),
    TRAIL_CHANCE(Double.class),
    TRAIL_PARTICLES_INVISIBLE(Boolean.class),
    TRAIL_WIDTH(Double.class),
    TRAIL_LENGTH(Integer.class),
    TRAIL_FREQUENCY(Integer.class),

    SOUND(Boolean.class),
    SOUND_VOLUME(Double.class),
    SOUND_ONLY_RARE(Boolean.class),
    SOUND_ONLY_EQUIPMENT(Boolean.class),
    SOUND_ONLY_WHITELIST(List.class),
    SOUND_ONLY_BLACKLIST(List.class),
    SOUND_ALL_ITEMS(Boolean.class),
    RARE_ORDINAL_MIN(Integer.class),



    ENABLE_DYNAMIC_PROVIDER(Boolean.class),
    HALF_ROUND_TICKS(Integer.class),


    SCREEN_TOOLTIPS_REQUIRE_CROUCH(Boolean.class);
    //used to notice me which type the config is.
    private final Class<?> type;

    Config(Class<?> type) {
        this.type = type;
    }

}
