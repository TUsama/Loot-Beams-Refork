package deps

object NeoForgeDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{
            when (minecraft){
                "1.21.1" -> {
                    val enableApotheosis = false
                    val enableMalum = true
                    modstitchModCompileOnly ("curse.maven:adorned-1036809:5740650")
                    modstitchModCompileOnly ("curse.maven:accessories-938917:5727153")
                    modstitchModCompileOnly ("curse.maven:curios-continuation-1037991:5747224")
                    modstitchModImplementation ("maven.modrinth:curios:9.5.1+1.21.1")

                    if (enableApotheosis){
                        modstitchModImplementation ("curse.maven:apotheosis-313970:6023693")
                        modstitchModImplementation ("curse.maven:placebo-283644:6446766")
                        modstitchModRuntimeOnly ("curse.maven:apothic-spawners-986583:6430294")
                        modstitchModRuntimeOnly ("curse.maven:apothic-enchanting-1063926:6514634")
                        modstitchModRuntimeOnly ("curse.maven:apothic-attributes-898963:6514649")
                    } else{
                        modstitchModCompileOnly ("curse.maven:apotheosis-313970:6023693")
                        modstitchModCompileOnly ("curse.maven:placebo-283644:6446766")
                    }

                    modstitchModCompileOnly ("curse.maven:tiered-forge-453889:6206636")
                    modstitchModCompileOnly ("curse.maven:unionlib-367806:5997453")

                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:TZo5xb5m")
                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:bAQ7woyE")
                    if (enableMalum){
                        modstitchModImplementation ("curse.maven:lodestone-616457:6630047")
                        modstitchModImplementation ("curse.maven:malum-484064:6655805")
                    } else{
                        modstitchModCompileOnly ("curse.maven:malum-484064:6655805")
                    }

                }

                "1.21.4" -> {
                    modstitchModCompileOnly ("maven.modrinth:curios:10.0.1+1.21.4")
                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:bAQ7woyE")
                }
            }
        }
    }
}