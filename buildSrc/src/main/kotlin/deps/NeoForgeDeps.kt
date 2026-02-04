package deps

object NeoForgeDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{
            when (minecraft){
                "1.21.1" -> {
                    val enableApotheosis = false
                    val enableMalum = false

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

                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:1cLpeR9D")


                    if (enableMalum){
                        modstitchModImplementation ("curse.maven:lodestone-616457:7264731")
                        modstitchModImplementation ("curse.maven:malum-484064:7307339")
                    } else{
                        modstitchModCompileOnly ("curse.maven:malum-484064:7307339")
                    }

                    //modstitchModRuntimeOnly ("curse.maven:monocle-1007288:6803201")
                    //modstitchModRuntimeOnly ("curse.maven:embeddium-908741:6118392")
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:6661598")
                    modstitchModRuntimeOnly ("curse.maven:irisshaders-455508:6661598")
                    modstitchModRuntimeOnly ("curse.maven:sodium-394468:6382651")

                    modstitchModCompileOnly ("curse.maven:simply-swords-659887:6958145")
                    modstitchModRuntimeOnly ("curse.maven:simply-swords-659887:6958145")
                    modstitchModRuntimeOnly ("curse.maven:architectury-api-419699:5786327")
                }

                "1.21.4" -> {
                    modstitchModCompileOnly ("maven.modrinth:curios:10.0.1+1.21.4")
                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:Z4pLufCX")
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:6213645")
                    modstitchModRuntimeOnly ("curse.maven:irisshaders-455508:6213645")
                    modstitchModRuntimeOnly ("curse.maven:sodium-394468:6382663")
                }

                "1.21.8" ->{
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:7088025")
                }

                "1.21.10" -> {
                    modstitchModRuntimeOnly("maven.modrinth:kotlin-for-forge:6.0.0")
                    modstitchModCompileOnly("curse.maven:irisshaders-455508:7351307")
                    //modstitchModRuntimeOnly("curse.maven:irisshaders-455508:7351307")
                    //modstitchModRuntimeOnly("curse.maven:sodium-394468:7207595")
                }

                "1.21.11" -> {
                    modstitchModCompileOnly("curse.maven:irisshaders-455508:7525081")
                    //modstitchModRuntimeOnly("curse.maven:irisshaders-455508:7525081")
                    //modstitchModRuntimeOnly("curse.maven:sodium-394468:7527475")
                }
            }
        }
    }
}