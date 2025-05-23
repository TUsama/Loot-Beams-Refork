### V3.1.5
(Fabric) add Tierify compat

### V3.1.4
fix crash

### V3.1.3
fix start up crash

### V3.1.2
add Subtle Effect compat
add some config localization

### V3.1.1
add Beam Object color replacement

### V3.1.0
### **Backup your Config!!**
(Both) add affectStrategy to allow player to control if the FX fully replace the original loot beam. Rename some configs

### V3.0.1
(Fabric) add Photon compat
(Both) add ifThisNameShouldBeReplacedColor three default values

### V3.0.0
### **Backup your Config!!**
Introducing [Photon](https://www.curseforge.com/minecraft/mc-mods/photon) compat, now the beam effect is all painted by you!
Please check out the new config section appeared after you installing Photon to see how you can make your loot beam FX.
You can also use the default FX provided by Clefal to check the In-game visual effect.

### V2.5.12
(Forge)implement Tiered Reforged compat, the rare sound effect is unavailable for this mod.

### V2.5.11
(Forge)fix orbs of crafting compat

### V2.5.10
(Forge) fix MNS compat, add orb of crafting compat

### V2.5.9
(Common) fix an issue that renders the item name incorrectly due to method misuse
(Fabric) add TieredZ compat

### V2.5.8
(Forge) fix MNS compat

### V2.5.7
(Common)add two new configs, now you can choose if show item name and rarity for all items, despite the beam config.
(Common)rename the Tooltips Config to Loot Information Config.

### V2.5.6
(Common)fix a bug that no equipment can be recognized as equipment.

### V2.5.5
(Common)add NirvanaLib as dependency.

### V2.5.4
(Fabric)add zenith compat

### V2.5.3
(common)optimize the config check performance. Now the config check should cost constant time, the config size will not increase the check time.


### V2.5.2
(Common) implement the equipment config check, now you can add any equipment that isn't covered by internal equipment check to it.
(Common) fix an issue that can't detect the vanilla equipment.
(Common) remove some sound filter entry, now it follow the light effect config. You can now only control the blacklist.
(Forge) add Spartan Weaponry, Balkon's WeaponMod: Legacy compat.
It's recommended that back up and delete your modified config files to let the mod regenerate the new configs.


### V2.5.1
(Forge) add curios compat, now item that can be seen as trinket will also be seen as equipment, this will affect the config only_equipment.
(Forge) improve the mns compat module
(Fabric) add Trinket compat
(Fabric) fix an upload issue

### V2.5.0
re-implement the config filter. now it is more clear and understandable
and don't use the color override config, this config option is currently broken.
it could work, but it won't save the modification you made.

### V2.4.2
refactor the color_override
add several comment

### V2.4.1
add Fabric support!
fix a config issue that the equipment and rare condition can't work together correctly

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
    