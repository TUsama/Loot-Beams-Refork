package deps

object ForgeDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{

            modstitchModCompileOnly ("curse.maven:apotheosis-313970:5753183")
            modstitchModCompileOnly ("curse.maven:placebo-283644:5414631")
            modstitchModCompileOnly ("curse.maven:patchouli-306770:4966125")
            modstitchModCompileOnly ("curse.maven:apothic-attributes-898963:5634071")



            modstitchModCompileOnly ("curse.maven:library-of-exile-398780:6107923")
            modstitchModCompileOnly ("curse.maven:mine-and-slash-reloaded-306575:6107937")
            modstitchModCompileOnly ("curse.maven:orbs-of-crafting-1182250:6107927")
            modstitchLegacyModRuntimeOnly ("curse.maven:playeranimator-658587:4587214")

            modstitchModCompileOnly ("curse.maven:legendary-tooltips-532127:4662781")
            /*modstitchModRuntimeOnly ("curse.maven:prism-lib-638111:4650325")
            modstitchModRuntimeOnly ("curse.maven:iceberg-520110:5838149")*/

            modstitchModCompileOnly ("curse.maven:obscure-tooltips-715660:4686579")

            modstitchModCompileOnly ("curse.maven:spartan-weaponry-278141:5597663")

            modstitchLegacyModImplementation("thedarkcolour:kotlinforforge:4.11.0")

            modstitchLegacyModRuntimeOnly ("top.theillusivec4.curios:curios-forge:5.14.1+1.20.1")
            modstitchModCompileOnly ("top.theillusivec4.curios:curios-forge:5.14.1+1.20.1:api")
/*
            modstitchModRuntimeOnly ("curse.maven:balkons-weaponmod-legacy-1033985:5853414")
            modstitchModRuntimeOnly ("curse.maven:architectury-api-419699:5137938")
            modstitchModRuntimeOnly ("curse.maven:cloth-config-348521:5729105")
            modstitchModRuntimeOnly ("curse.maven:crafttweaker-239197:5880672")
*/
            modstitchModCompileOnly ("curse.maven:tiered-forge-453889:6549635")
            modstitchModCompileOnly ("curse.maven:unionlib-367806:6585053")

            modstitchLegacyModImplementation ("curse.maven:ldlib-626676:6337779")
            modstitchLegacyModImplementation ("curse.maven:photon-871522:6373238")

            modstitchLegacyModImplementation ("maven.modrinth:subtle-effects:y1lCS0Hd")

            val enableMalum = false
            val enableBio =false
            if (enableMalum){
                modstitchLegacyModImplementation("curse.maven:malum-484064:6213791")
                modstitchLegacyModImplementation("curse.maven:lodestone-616457:6213794")
            } else {
                modstitchModCompileOnly("curse.maven:malum-484064:6213791")
                modstitchModCompileOnly("curse.maven:lodestone-616457:6213794")
            }


            if (enableBio){
                modstitchLegacyModImplementation ("maven.modrinth:biomancy:2.8.19.0")
                modstitchLegacyModImplementation ("maven.modrinth:geckolib:g4k8xf2k")
            } else {
                modstitchModCompileOnly ("maven.modrinth:biomancy:2.8.19.0")
                modstitchModCompileOnly ("maven.modrinth:geckolib:g4k8xf2k")
            }

        }
    }
}