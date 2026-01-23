package deps

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

object LoomDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{
            when (minecraft) {
                ("1.20.1") -> {
                    modstitchModCompileOnly ("dev.emi:trinkets:3.7.2")

                    modstitchModCompileOnly ("curse.maven:zenith-620928:5904438")
                    modstitchModCompileOnly ("curse.maven:zenith-attributes-910078:5918684")
                    modstitchModCompileOnly ("curse.maven:fakerlib-853197:5503724")


                    modstitchModCompileOnly ("curse.maven:tieredz-615948:5233351")
                    modstitchModCompileOnly ("curse.maven:photon-871522:6373235")
                    modstitchModCompileOnly ("curse.maven:ldlib-626676:6417171")

                    modstitchModImplementation ("curse.maven:modmenu-308702:5162837")

                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:ITeLaH6q")

                    modstitchModCompileOnly ("curse.maven:tierify-974356:5803071")
                    modstitchModCompileOnly ("curse.maven:necronomicon-586157:5772682")
                    modstitchModCompileOnly (("maven.modrinth:libz:1.0.2+1.20.1")) {
                        exclude(mapOf<String, String>("group" to "net.fabricmc.fabric-api"))
                    }

                    modstitchModCompileOnly ("curse.maven:malum-484064:5718977")
                    modstitchModCompileOnly ("curse.maven:lodestone-616457:6070172")
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:6258195")
                }

                ("1.21.1") -> {
                    modstitchModCompileOnly ("dev.emi:trinkets:3.10.0")
                    modstitchModCompileOnly ("curse.maven:accessories-938917:5727156")
                    //modImplementation ("curse.maven:charm-of-undying-316873:5159191")

                    modstitchModCompileOnly ("curse.maven:tieredz-615948:5934487")

                    modstitchModCompileOnly ("curse.maven:tiered-forge-453889:6206677")
                    modstitchModCompileOnly ("curse.maven:unionlib-367806:5997472")
                    modstitchImplementation ("org.tomlj:tomlj:1.1.1")

                    modstitchModImplementation ("maven.modrinth:subtle-effects:1.13.1")

                    modstitchModRuntimeOnly("curse.maven:irisshaders-455508:6213635")
                    modstitchModRuntimeOnly("curse.maven:sodium-394468:6382649")

                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:6213635")
                }

                ("1.21.4") -> {
                    modstitchModCompileOnly ("maven.modrinth:subtle-effects:NA1rBYFJ")
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:6213644")
                }

                "1.21.8" -> {
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:7088024")
                }

                "1.21.10" ->{
                    modstitchModCompileOnly ("curse.maven:irisshaders-455508:7351304")
                }
            }
        }
    }

}