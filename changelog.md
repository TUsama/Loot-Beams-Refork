### V3.4.5
fix a bug that the compass will be rendered incorrectly after it got enchant by Lodestone, this will affect all version below 1.21.10.

### V3.4.4
add simple swords compat
fix crash on java 17(1.20.1)

### V3.4.3
fix beam top position
upgrade to 1.21.11

### V3.4.2
fix beam top direction render issue when shader is enabled

### V3.4.1
fix an upload bug that will crash the game when install with Malum
upgrade to 3.4.1 cuz curseforge won't allow same version appeared.

### V3.4.0
**Expected bugs!!!!**
refactor beam render, add Iris compat
now beam will look a bit different because beam now use texture, the old beam is just a gradient color.
render beam as particle when shader is enabled.

### V3.3.2
add three configs that allow players to control the tooltips style, In Custom style, players can modify the Tooltips position
lower the fzzy config dep version

### V3.3.1
fix multiversion

### V3.3.0
upgrade to 1.21.8 and 1.21.10
refactor for multiversion.

### V3.2.10
delay init time, fix a crash with Malum

### V3.2.9
fix compat with Subtle Effect and Biomancy

### V3.2.8
downgrade forge(1.20.1 forge), upgrade nirvana lib

### V3.2.7
add a config that allows player to use item name color as beam color, when the beam can't find any color provided by internal providers. This can prevent the situations that the beam becomes fully invisible.

### V3.2.6
(1.21.1 Neoforge) fix malum compat

### V3.2.5
(all 1.20.1)fix mixin crash on production build

### V3.2.4
(all Fabric)fix mixin crash on production build

### V3.2.3
(all Fabric)fix versioned code

### V3.2.2
(all) fix a bug that the loot information can't render properly
(1.20.1, 1.21.1 Neoforge) add Malum compat
(1.20.1 Forge) add Biomancy compat

### V3.2.1
fix reforged compat

### V3.2.0
change the version for the consistency

### V2.7.0
update to multiversion. If you experience any bugs, please report on GitHub!
add 1.21.4 support

### V2.6.2
fix a spam log bug when installed with Subtle Effect

### V2.6.1
Disable SubtleEffect ItemRarity feature, add a config to allow player to re-enable it.
upgrade the neoforge and fabric version the mod used.

### V2.6.0
**Only compatible with Nirvana Library 1.2.1 and above!!**
This is a compatible build for Nirvana Library 1.2.1. Nothing really changes.

### V2.5.11
(Fabric)prob fix the crash when user installs Tiered Reforged.
(Forge)add Tiered Reforged compat

### V2.5.10
(Fabric) fix TieredZ compat crash

### V2.5.9
(Common) fix an issue that renders the item name incorrectly due to method misuse
(Fabric) add TieredZ compat

### V2.5.8
(Common)add two new configs, now you can choose if show item name and rarity for all items, despite the beam config.
(Common)rename the Tooltips Config to Loot Information Config.

### V2.5.7
(Common) refactor the color holder, now supports mutable color, like rainbow
(NeoForge) add Apotheosis compat

### V2.5.6
(Fabric) Support 1.21 Fabric.

### V2.5.5
(Common)fix a bug that no equipment can be recognized as equipment.

### V2.5.4
(Common) add Nirvana Library to dependency to avoid potential dependency conflict.

### V2.5.3
(common)optimize the config check performance. Now the config check should cost constant time, the config size will not increase the check time.

### V2.5.2
(Common) implement the equipment config check, now you can add any equipment that isn't covered by internal equipment check to it.
(Common) fix an issue that can't detect the vanilla equipment.
(Common) remove some sound filter entry, now it follow the light effect config. You can now only control the blacklist.
It's recommended that back up and delete your modified config files to let the mod regenerate the new configs.

### V2.5.1
add three trinket api mods compat.

### V2.5.0
re-implement the config filter. now it is more clear and understandable
and don't use the color override config, this config option is currently broken.
it could work, but it won't save the modification you made.

fix a issue that the name tag can't properly render

### V2.4.3
refactor the color_override
add several comment

### V2.4.1
add Fabric support!
fix a config issue that the equipment and rare condition can't work together correctly

### V2.4.1
now support 1.21

### V2.4.0
add 1.21.1 neoforge support!
change the beam facing way

### V2.3.0

use [Fzzy Config](https://www.curseforge.com/minecraft/mc-mods/fzzy-config/files/5969656) to handle config, so remember to add this mod!
update forge version to 47.3.5

### V2.2.1

port the project to MultiLoader Template
fix an issue that cannot recognize MNS staff as equipment
fix an issue that name tag will drop the rarity when ObscureTooltips installed

### V2.1.3

relocate the Neoforged EventBus

### V2.1.2

refactor the name and rarity collect pipeline, allowing additional external manipulation.
fix an upload issue

### V2.1.1

fix a double rarity text issue with Obscure Tooltips
add Non soul rarity for MNS

### V2.1.1

fix an issue when legendary tooltips is installed
fix a crash when mod try to wrap MNS's GearRarity to LBRarity.

### V2.1.0

re-implement the tooltips module.

### V2.0.0

1. Completely refactor the code, including:

* Loot Beam:
    1) Add related event, allowing other developers to easily make compat.
    2) Delete NBT compat.
    3) Drastically decrease the number of vertexes needed by render.
    4) Remove 80% of the unnecessary matrix transformation
    5) Remove configs: WHITE_CENTER, WHITE_RARITIES, VANILLA_RARITIES, ADVANCED_TOOLTIPS, WORLDSPACE_TOOLTIPS,
       DMCLOOT_COMPAT_RARITY, SCREEN_TOOLTIPS_REQUIRE_CROUCH, COMBINE_NAME_AND_RARITY, RENDER_NAME_COLOR, WHITE_CENTER, GLOWING_BEAM, ANIMATE_GLOW, RENDER_DISTANCE
    6) add new configs: ENABLE_RARITY, ENABLE_TOOLTIPS, ENABLE_BEAM, ENABLE_DYNAMIC_PROVIDER, HALF_ROUND_TICKS, BEAM_FADE_IN_TIME
    7) change old configs: CUSTOM_RARITIES

    * Tooltip:

    1) Name Tag:
        1) rewrite render code.
    