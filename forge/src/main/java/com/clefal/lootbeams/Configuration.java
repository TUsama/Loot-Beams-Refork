package com.clefal.lootbeams;

import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.config.IConfigurationProvider;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.clefal.lootbeams.data.rarity.ConfigCustomRarity;
import com.clefal.lootbeams.data.rarity.Order;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration implements IConfigurationProvider {

	public static ForgeConfigSpec CLIENT_CONFIG;
	public static ForgeConfigSpec.BooleanValue ENABLE_BEAM;
	public static ForgeConfigSpec.BooleanValue ITEMS_GLOW;
	public static ForgeConfigSpec.BooleanValue ALL_ITEMS;
	public static ForgeConfigSpec.BooleanValue ONLY_EQUIPMENT;
	public static ForgeConfigSpec.BooleanValue ONLY_RARE;
	public static ForgeConfigSpec.ConfigValue<List<String>> WHITELIST;
	public static ForgeConfigSpec.ConfigValue<List<String>> BLACKLIST;
	public static ForgeConfigSpec.ConfigValue<List<String>> COLOR_OVERRIDES;
	public static ForgeConfigSpec.ConfigValue<List<String>> COLOR_APPLY_ORDER;

	public static ForgeConfigSpec.BooleanValue RENDER_NAME_COLOR;
	public static ForgeConfigSpec.BooleanValue RENDER_RARITY_COLOR;
	public static ForgeConfigSpec.DoubleValue BEAM_RADIUS;
	public static ForgeConfigSpec.DoubleValue BEAM_HEIGHT;
	public static ForgeConfigSpec.BooleanValue COMMON_SHORTER_BEAM;
	public static ForgeConfigSpec.DoubleValue BEAM_Y_OFFSET;
	public static ForgeConfigSpec.DoubleValue BEAM_ALPHA;
	public static ForgeConfigSpec.DoubleValue BEAM_FADE_DISTANCE;

	public static ForgeConfigSpec.BooleanValue SOLID_BEAM;
	public static ForgeConfigSpec.BooleanValue REQUIRE_ON_GROUND;

	public static ForgeConfigSpec.BooleanValue GLOW_EFFECT;
	public static ForgeConfigSpec.DoubleValue GLOW_EFFECT_RADIUS;

	public static ForgeConfigSpec.BooleanValue PARTICLES;

	public static ForgeConfigSpec.EnumValue<TooltipsEnableStatus.TooltipsStatus> ENABLE_TOOLTIPS;
	public static ForgeConfigSpec.BooleanValue BORDERS;
	public static ForgeConfigSpec.BooleanValue RENDER_NAMETAGS;
	public static ForgeConfigSpec.BooleanValue RENDER_NAMETAGS_ONLOOK;
	public static ForgeConfigSpec.BooleanValue RENDER_STACKCOUNT;
	public static ForgeConfigSpec.DoubleValue NAMETAG_LOOK_SENSITIVITY;
	public static ForgeConfigSpec.DoubleValue NAMETAG_TEXT_ALPHA;
	public static ForgeConfigSpec.DoubleValue NAMETAG_BACKGROUND_ALPHA;
	public static ForgeConfigSpec.DoubleValue NAMETAG_SCALE;
	public static ForgeConfigSpec.DoubleValue NAMETAG_Y_OFFSET;
	public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_RARITIES;
	public static ForgeConfigSpec.BooleanValue SCREEN_TOOLTIPS_REQUIRE_CROUCH;



	public static ForgeConfigSpec.DoubleValue PARTICLE_SIZE;
	public static ForgeConfigSpec.DoubleValue PARTICLE_SPEED;
	public static ForgeConfigSpec.DoubleValue PARTICLE_RADIUS;
	public static ForgeConfigSpec.DoubleValue PARTICLE_COUNT;
	public static ForgeConfigSpec.IntValue PARTICLE_LIFETIME;
	public static ForgeConfigSpec.DoubleValue RANDOMNESS_INTENSITY;
	public static ForgeConfigSpec.BooleanValue PARTICLE_RARE_ONLY;
	public static ForgeConfigSpec.DoubleValue PARTICLE_DIRECTION_X;
	public static ForgeConfigSpec.DoubleValue PARTICLE_DIRECTION_Y;
	public static ForgeConfigSpec.DoubleValue PARTICLE_DIRECTION_Z;
	public static ForgeConfigSpec.BooleanValue SPIN_AROUND_BEAM;
	public static ForgeConfigSpec.BooleanValue TRAILS;
	public static ForgeConfigSpec.DoubleValue TRAIL_CHANCE;
	public static ForgeConfigSpec.BooleanValue TRAIL_PARTICLES_INVISIBLE;
	public static ForgeConfigSpec.DoubleValue TRAIL_WIDTH;
	public static ForgeConfigSpec.IntValue TRAIL_LENGTH;
	public static ForgeConfigSpec.IntValue TRAIL_FREQUENCY;

	public static ForgeConfigSpec.BooleanValue SOUND;
	public static ForgeConfigSpec.DoubleValue SOUND_VOLUME;
	public static ForgeConfigSpec.BooleanValue SOUND_ONLY_RARE;
	public static ForgeConfigSpec.BooleanValue SOUND_ONLY_EQUIPMENT;
	public static ForgeConfigSpec.ConfigValue<List<String>> SOUND_ONLY_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<List<String>> SOUND_ONLY_BLACKLIST;
	public static ForgeConfigSpec.BooleanValue SOUND_ALL_ITEMS;
	public static ForgeConfigSpec.IntValue RARE_ORDINAL_MIN;

	public static ForgeConfigSpec.BooleanValue ENABLE_DYNAMIC_PROVIDER;
	public static ForgeConfigSpec.IntValue HALF_ROUND_TICKS;
	public static ForgeConfigSpec.IntValue BEAM_FADE_IN_TIME;





	static {
		ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();

		clientBuilder.comment("Beam Config").push("Loot Beams");
		ENABLE_BEAM = clientBuilder.define("enable_beam", true);

		BEAM_FADE_IN_TIME = clientBuilder.comment("The ticks of beam fade in.").defineInRange("beam_fade_in_time", 10, 1, 100);


		COLOR_OVERRIDES = clientBuilder.comment("Overrides an item's beam color with hex color. Must follow the specific format: (registryname=hexcolor) Or (#tagname=hexcolor). Example: \"minecraft:stone=0xFFFFFF\". This also accepts modids.").define("color_overrides", new ArrayList<>());


		COLOR_APPLY_ORDER = clientBuilder.comment("This is the order of how COLOR_OVERRIDES works. lootbeams will use the first validated color.").define("color_apply_order", Arrays.stream(Order.values()).map(Enum::toString).toList());


		clientBuilder.comment("Beam Configuration").push("Beam");
		BEAM_RADIUS = clientBuilder.comment("The radius of the Loot Beam.").defineInRange("beam_radius", 0.55D, 0D, 5D);


		BEAM_HEIGHT = clientBuilder.comment("The height of the Loot Beam.").defineInRange("beam_height", 1.5D, 0D, 10D);


		BEAM_Y_OFFSET = clientBuilder.comment("The Y-offset of the loot beam.").defineInRange("beam_y_offset", 0.5D, -30D, 30D);


		COMMON_SHORTER_BEAM = clientBuilder.comment("If the Loot Beam should be shorter for common items.").define("common_shorter_beam", true);


		BEAM_ALPHA = clientBuilder.comment("Transparency of the Loot Beam.").defineInRange("beam_alpha", 0.75D, 0D, 1D);


		BEAM_FADE_DISTANCE = clientBuilder.comment("The distance from the player the beam should start fading.").defineInRange("beam_fade_distance", 15D, 0D, 100D);


		SOLID_BEAM = clientBuilder.comment("If the Loot Beam should use a solid texture or the beacon style texture.").define("solid_beam", true);


		GLOW_EFFECT = clientBuilder.comment("If the Loot Beam should have a glow effect around the base of the item.").define("glow_effect", true);


		GLOW_EFFECT_RADIUS = clientBuilder.comment("The radius of the glow effect.").defineInRange("glow_effect_radius", 0.5D, 0.00001D, 1D);




		REQUIRE_ON_GROUND = clientBuilder.comment("If the item must be on the ground to render a beam.").define("require_on_ground", true);


		clientBuilder.pop();

		clientBuilder.comment("Particle Config").push("Particles");
		PARTICLES = clientBuilder.comment("If particles should appear around the item.").define("particles", true);


		PARTICLE_SIZE = clientBuilder.comment("The size of the particles.").defineInRange("particle_size", 0.25D, 0.00001D, 10D);


		PARTICLE_SPEED = clientBuilder.comment("The speed of the particles.").defineInRange("particle_speed", 0.2D, 0.00001D, 10D);


		PARTICLE_RADIUS = clientBuilder.comment("The radius of the particles.").defineInRange("particle_radius", 0.1D, 0.00001D, 10D);


		RANDOMNESS_INTENSITY = clientBuilder.comment("The intensity of the randomness of the particles.").defineInRange("randomness_intensity", 0.05D, 0D, 1D);


		PARTICLE_COUNT = clientBuilder.comment("The amount of particles to spawn per second.").defineInRange("particle_count", 15D, 1D, 20D);


		PARTICLE_LIFETIME = clientBuilder.comment("The lifetime of the particles in ticks.").defineInRange("particle_lifetime", 15, 1, 100);


		PARTICLE_RARE_ONLY = clientBuilder.comment("If particles should only appear on rare items.").define("particle_rare_only", true);


		PARTICLE_DIRECTION_X = clientBuilder.comment("The direction of the particles on the X axis.").defineInRange("particle_direction_x", 0D, -1D, 1D);


		PARTICLE_DIRECTION_Y = clientBuilder.comment("The direction of the particles on the Y axis.").defineInRange("particle_direction_y", 1D, -1D, 1D);


		PARTICLE_DIRECTION_Z = clientBuilder.comment("The direction of the particles on the Z axis.").defineInRange("particle_direction_z", 0D, -1D, 1D);


		SPIN_AROUND_BEAM = clientBuilder.comment("If the particles should spin around the beam.").define("spin_around_beam", true);


		TRAILS = clientBuilder.comment("If the particles should leave a trail.").define("trails", true);


		TRAIL_CHANCE = clientBuilder.comment("The chance of a particle leaving a trail.").defineInRange("trail_chance", 0.5D, 0D, 1D);


		TRAIL_PARTICLES_INVISIBLE = clientBuilder.comment("If the particles with a trail should be invisible.").define("trail_particles_invisible", true);


		TRAIL_WIDTH = clientBuilder.comment("The width of the trail.").defineInRange("trail_width", 0.2D, 0.00001D, 10D);


		TRAIL_LENGTH = clientBuilder.comment("The length of the trail.").defineInRange("trail_length", 30, 1, 200);


		TRAIL_FREQUENCY = clientBuilder.comment("The frequency of the trail. The maximum value this should be is the length. The lower the frequency, the smoother the trail.").defineInRange("trail_frequency", 1, 1, 200);


		clientBuilder.pop();

		clientBuilder.comment("Item Config").push("Items");
		ITEMS_GLOW = clientBuilder.comment("If items should glow.").define("items_glow", false);


		ALL_ITEMS = clientBuilder.comment("If all Items Loot Beams should be rendered. Has priority over only_equipment and only_rare.").define("all_items", false);


		ONLY_RARE = clientBuilder.comment("If Loot Beams should only be rendered on items with rarity.").define("only_rare", true);

		RARE_ORDINAL_MIN = clientBuilder.comment("Should be noticed that this also control the sound.").defineInRange("rare_ordinal_min", 3, 0, 100);


		ONLY_EQUIPMENT = clientBuilder.comment("If Loot Beams should only be rendered on equipment. (Equipment includes: Swords, Tools, Armor, Shields, Bows, Crossbows, Tridents, Arrows, and Fishing Rods)").define("only_equipment", true);


		WHITELIST = clientBuilder.comment("Registry names of items that Loot Beams should render on. Example: \"minecraft:stone\", \"minecraft:iron_ingot\", You can also specify modids for a whole mod's items.").define("whitelist", new ArrayList<>());


		BLACKLIST = clientBuilder.comment("Registry names of items that Loot Beams should NOT render on. This has priority over everything. You can also specify modids for a whole mod's items.").define("blacklist", new ArrayList<>());


		clientBuilder.pop();




		clientBuilder.comment("Item Tooltips").push("Tooltips");

		ENABLE_TOOLTIPS = clientBuilder.comment("If tooltips feature should be enabled.").defineEnum("enable_tooltips", TooltipsEnableStatus.TooltipsStatus.NAME_AND_RARITY);

		SCREEN_TOOLTIPS_REQUIRE_CROUCH = clientBuilder.comment("if tooltips should be rendered only when player is crouching").define("screen_tooltips_require_crouch", true);


		BORDERS = clientBuilder.comment("Render nametags as bordered. Set to false for flat nametag with background.").define("borders", true);


		RENDER_NAMETAGS = clientBuilder.comment("If Item nametags should be rendered.").define("render_nametags", true);


		RENDER_NAMETAGS_ONLOOK = clientBuilder.comment("If Item nametags should be rendered when looking at items.").define("render_nametags_onlook", true);


		RENDER_STACKCOUNT = clientBuilder.comment("If the count of item's should also be shown in the nametag.").define("render_stackcount", true);


		NAMETAG_LOOK_SENSITIVITY = clientBuilder.comment("How close the player has to look at the item to render the nametag.").defineInRange("nametag_look_sensitivity", 0.018D, 0D, 5D);


		NAMETAG_TEXT_ALPHA = clientBuilder.comment("Transparency of the nametag text.").defineInRange("nametag_text_alpha", 1D, 0D, 1D);


		NAMETAG_BACKGROUND_ALPHA = clientBuilder.comment("Transparency of the nametag background/border.").defineInRange("nametag_background_alpha", 0.5D, 0D, 1D);


		NAMETAG_SCALE = clientBuilder.comment("Scale of the nametag.").defineInRange("nametag_scale", 1, -10D, 10D);


		NAMETAG_Y_OFFSET = clientBuilder.comment("The Y-offset of the nametag.").defineInRange("nametag_y_offset", 0.75D, -30D, 30D);




		CUSTOM_RARITIES = clientBuilder.comment("Add custom rarity text for your modpack. To use this you need to declare the new rarity with the format of Tag, like '#rarity:mythic'. You also need to declare its color in COLOR_OVERRIDES config. You can also provide the lang if you want it can be localized, the format is '" + ConfigCustomRarity.langKeyFormat + "xxx'. This is really only used for modpacks.").define("custom_rarities", new ArrayList<>());






		clientBuilder.pop();

		clientBuilder.comment("Sounds").push("Sounds");
		SOUND = clientBuilder.comment("If sounds should be played when items are dropped up.").define("play_sounds", true);


		SOUND_VOLUME = clientBuilder.comment("The volume of the sound.").defineInRange("sound_volume", 1D, 0D, 1D);


		SOUND_ALL_ITEMS = clientBuilder.comment("If sounds should play on all items. Has priority over sound_only_equipment and sound_only_rare.").define("sound_all_items", false);


		SOUND_ONLY_RARE = clientBuilder.comment("If sounds should only be played on items with rarity.").define("sound_only_rare", true);


		SOUND_ONLY_EQUIPMENT = clientBuilder.comment("If sounds should only be played on equipment. (Equipment includes: Swords, Tools, Armor, Shields, Bows, Crossbows, Tridents, Arrows, and Fishing Rods)").define("sound_only_equipment", false);


		SOUND_ONLY_WHITELIST = clientBuilder.comment("Registry names of items that sounds should additionally play on. Example: \"minecraft:stone\", \"minecraft:iron_ingot\", You can also specify modids for a whole mod's items.").define("sound_whitelist", new ArrayList<>());


		SOUND_ONLY_BLACKLIST = clientBuilder.comment("Registry names of items that sounds should NOT play on. This has priority over everything. You can also specify modids for a whole mod's items.").define("sound_blacklist", new ArrayList<>());




		clientBuilder.pop();
		clientBuilder.comment("Dynamic").push("Dynamic");

		ENABLE_DYNAMIC_PROVIDER = clientBuilder.comment("If beam and glow should be dynamic.").define("enable_dynamic", true);
		HALF_ROUND_TICKS = clientBuilder.comment("the ticks needed for half round").defineInRange("half_round_ticks", 30, 20, 100);


		clientBuilder.pop();

		CLIENT_CONFIG = clientBuilder.build();

		ConfigurationManager.fill = Configuration::registerToManager;
	}

	public static void registerToManager() {
		ConfigurationManager.insert(Config.ENABLE_BEAM, () -> ENABLE_BEAM.get());
		ConfigurationManager.insert(Config.RENDER_NAME_COLOR, () -> RENDER_NAME_COLOR.get());
		ConfigurationManager.insert(Config.RENDER_RARITY_COLOR, () -> RENDER_RARITY_COLOR.get());
		ConfigurationManager.insert(Config.COLOR_OVERRIDES, () -> COLOR_OVERRIDES.get());
		ConfigurationManager.insert(Config.COLOR_APPLY_ORDER, () -> COLOR_APPLY_ORDER.get());
		ConfigurationManager.insert(Config.BEAM_RADIUS, () -> BEAM_RADIUS.get());
		ConfigurationManager.insert(Config.BEAM_HEIGHT, () -> BEAM_HEIGHT.get());
		ConfigurationManager.insert(Config.BEAM_Y_OFFSET, () -> BEAM_Y_OFFSET.get());
		ConfigurationManager.insert(Config.COMMON_SHORTER_BEAM, () -> COMMON_SHORTER_BEAM.get());
		ConfigurationManager.insert(Config.BEAM_ALPHA, () -> BEAM_ALPHA.get());
		ConfigurationManager.insert(Config.BEAM_FADE_DISTANCE, () -> BEAM_FADE_DISTANCE.get());
		ConfigurationManager.insert(Config.SOLID_BEAM, () -> SOLID_BEAM.get());
		ConfigurationManager.insert(Config.GLOW_EFFECT, () -> GLOW_EFFECT.get());
		ConfigurationManager.insert(Config.GLOW_EFFECT_RADIUS, () -> GLOW_EFFECT_RADIUS.get());
		ConfigurationManager.insert(Config.REQUIRE_ON_GROUND, () -> REQUIRE_ON_GROUND.get());
		ConfigurationManager.insert(Config.PARTICLES, () -> PARTICLES.get());
		ConfigurationManager.insert(Config.PARTICLE_SIZE, () -> PARTICLE_SIZE.get());
		ConfigurationManager.insert(Config.PARTICLE_SPEED, () -> PARTICLE_SPEED.get());
		ConfigurationManager.insert(Config.PARTICLE_RADIUS, () -> PARTICLE_RADIUS.get());
		ConfigurationManager.insert(Config.RANDOMNESS_INTENSITY, () -> RANDOMNESS_INTENSITY.get());
		ConfigurationManager.insert(Config.PARTICLE_COUNT, () -> PARTICLE_COUNT.get());
		ConfigurationManager.insert(Config.PARTICLE_LIFETIME, () -> PARTICLE_LIFETIME.get());
		ConfigurationManager.insert(Config.PARTICLE_RARE_ONLY, () -> PARTICLE_RARE_ONLY.get());
		ConfigurationManager.insert(Config.PARTICLE_DIRECTION_X, () -> PARTICLE_DIRECTION_X.get());
		ConfigurationManager.insert(Config.PARTICLE_DIRECTION_Y, () -> PARTICLE_DIRECTION_Y.get());
		ConfigurationManager.insert(Config.PARTICLE_DIRECTION_Z, () -> PARTICLE_DIRECTION_Z.get());
		ConfigurationManager.insert(Config.SPIN_AROUND_BEAM, () -> SPIN_AROUND_BEAM.get());
		ConfigurationManager.insert(Config.TRAILS, () -> TRAILS.get());
		ConfigurationManager.insert(Config.TRAIL_CHANCE, () -> TRAIL_CHANCE.get());
		ConfigurationManager.insert(Config.TRAIL_PARTICLES_INVISIBLE, () -> TRAIL_PARTICLES_INVISIBLE.get());
		ConfigurationManager.insert(Config.TRAIL_WIDTH, () -> TRAIL_WIDTH.get());
		ConfigurationManager.insert(Config.TRAIL_LENGTH, () -> TRAIL_LENGTH.get());
		ConfigurationManager.insert(Config.TRAIL_FREQUENCY, () -> TRAIL_FREQUENCY.get());
		ConfigurationManager.insert(Config.ITEMS_GLOW, () -> ITEMS_GLOW.get());
		ConfigurationManager.insert(Config.ALL_ITEMS, () -> ALL_ITEMS.get());
		ConfigurationManager.insert(Config.ONLY_RARE, () -> ONLY_RARE.get());
		ConfigurationManager.insert(Config.ONLY_EQUIPMENT, () -> ONLY_EQUIPMENT.get());
		ConfigurationManager.insert(Config.WHITELIST, () -> WHITELIST.get());
		ConfigurationManager.insert(Config.BLACKLIST, () -> BLACKLIST.get());

		ConfigurationManager.insert(Config.BORDERS, () -> BORDERS.get());
		ConfigurationManager.insert(Config.RENDER_NAMETAGS, () -> RENDER_NAMETAGS.get());
		ConfigurationManager.insert(Config.RENDER_NAMETAGS_ONLOOK, () -> RENDER_NAMETAGS_ONLOOK.get());
		ConfigurationManager.insert(Config.RENDER_STACKCOUNT, () -> RENDER_STACKCOUNT.get());
		ConfigurationManager.insert(Config.NAMETAG_LOOK_SENSITIVITY, () -> NAMETAG_LOOK_SENSITIVITY.get());
		ConfigurationManager.insert(Config.NAMETAG_TEXT_ALPHA, () -> NAMETAG_TEXT_ALPHA.get());
		ConfigurationManager.insert(Config.NAMETAG_BACKGROUND_ALPHA, () -> NAMETAG_BACKGROUND_ALPHA.get());
		ConfigurationManager.insert(Config.NAMETAG_SCALE, () -> NAMETAG_SCALE.get());
		ConfigurationManager.insert(Config.NAMETAG_Y_OFFSET, () -> NAMETAG_Y_OFFSET.get());

		ConfigurationManager.insert(Config.CUSTOM_RARITIES, () -> CUSTOM_RARITIES.get());

		ConfigurationManager.insert(Config.SOUND, () -> SOUND.get());
		ConfigurationManager.insert(Config.SOUND_VOLUME, () -> SOUND_VOLUME.get());
		ConfigurationManager.insert(Config.SOUND_ALL_ITEMS, () -> SOUND_ALL_ITEMS.get());
		ConfigurationManager.insert(Config.SOUND_ONLY_RARE, () -> SOUND_ONLY_RARE.get());
		ConfigurationManager.insert(Config.SOUND_ONLY_EQUIPMENT, () -> SOUND_ONLY_EQUIPMENT.get());
		ConfigurationManager.insert(Config.SOUND_ONLY_WHITELIST, () -> SOUND_ONLY_WHITELIST.get());
		ConfigurationManager.insert(Config.SOUND_ONLY_BLACKLIST, () -> SOUND_ONLY_BLACKLIST.get());
		ConfigurationManager.insert(Config.ENABLE_TOOLTIPS, () -> ENABLE_TOOLTIPS.get());
		ConfigurationManager.insert(Config.ENABLE_DYNAMIC_PROVIDER, () -> ENABLE_DYNAMIC_PROVIDER.get());
		ConfigurationManager.insert(Config.HALF_ROUND_TICKS, () -> HALF_ROUND_TICKS.get());
		ConfigurationManager.insert(Config.BEAM_FADE_IN_TIME, () -> BEAM_FADE_IN_TIME.get());
		ConfigurationManager.insert(Config.RARE_ORDINAL_MIN, () -> RARE_ORDINAL_MIN.get());
		ConfigurationManager.insert(Config.SCREEN_TOOLTIPS_REQUIRE_CROUCH, () -> SCREEN_TOOLTIPS_REQUIRE_CROUCH.get());
	}
}
