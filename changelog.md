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
1. Completely refactor the code, including 
    * Loot Beam:
      1) Add related event, allowing other developers to easily make compat.
      2) Delete NBT compat.
      3) Drastically decrease the number of vertexes needed by render.
      4) Remove 80% of the unnecessary matrix transformation
      5) Remove configs: WHITE_CENTER, WHITE_RARITIES, VANILLA_RARITIES, ADVANCED_TOOLTIPS, WORLDSPACE_TOOLTIPS, DMCLOOT_COMPAT_RARITY, SCREEN_TOOLTIPS_REQUIRE_CROUCH, COMBINE_NAME_AND_RARITY, RENDER_NAME_COLOR, WHITE_CENTER, GLOWING_BEAM, ANIMATE_GLOW, RENDER_DISTANCE
      6) add new configs: ENABLE_RARITY, ENABLE_TOOLTIPS, ENABLE_BEAM, ENABLE_DYNAMIC_PROVIDER, HALF_ROUND_TICKS, BEAM_FADE_IN_TIME
      7) change old configs: CUSTOM_RARITIES

     * Tooltip:
      1) Name Tag:
         2) rewrite render code.
    