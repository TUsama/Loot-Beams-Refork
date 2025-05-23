import deps.DependencyConfig
import deps.Loaders

plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
    id("dev.isxander.modstitch.publishing") version "0.5.12"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)?.let(consumer)
}


val modv = "2.0.0"


val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    modstitch.isModDevGradleLegacy -> "forge"
    else -> throw IllegalStateException("Unsupported loader")
}

val minecraft = property("deps.minecraft") as String

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaTarget = when (minecraft) {
        "1.20.1" -> 17
        "1.21.1" -> 21
        "1.21.4" -> 21
        else -> throw IllegalArgumentException("Please store the java version for ${property("deps.minecraft")} in build.gradle.kts!")
    }

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") {
            if (minecraft == "1.21.1") minecraftVersion.set("1.21")
            mappingsVersion = it
        }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    val mid = "lootbeams"
    metadata {
        modId = mid
        modName = "Loot Beams Refork"
        modVersion = modv
        modGroup = "com.clefal"
        modAuthor = "Clefal"
        modDescription =
            "Loot items, guided by light!"
        modLicense = "MIT"
        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }
        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/TUsama/Loot-Beams-Refork/issues")
            put(
                "pformat", when (property("deps.minecraft")) {
                    "1.20.1" -> 15
                    "1.21.1" -> 34
                    "1.21.4" -> 46
                    else -> throw IllegalArgumentException("Please store the resource pack version for ${property("deps.minecraft")} in build.gradle.kts! https://minecraft.wiki/w/Pack_format")
                }.toString()
            )

            put("target_minecraft", minecraft)
            //put("target_lib", property("deps.lib") as String)
            put(
                "target_loader", when (loader) {
                    "neoforge" -> property("deps.neoforge") as String
                    else -> ""
                }
            )
            put("loader", loader)
            put(
                "target_fabricloader", when (loader) {
                    "fabric" -> property("deps.fabric_loader") as String
                    else -> ""
                }
            )
            put("fzzy_config_version", property("deps.fzzy_config_version") as String)
        }
    }

    // Fabric Loom (Fabric)
    loom {
        // It's not recommended to store the Fabric Loader version in properties.
        // Make sure its up to date.
        fabricLoaderVersion = "0.16.10"
        configureLoom {
            runs {
                all {
                    runDir = "../../run"
                    ideConfigGenerated(true)
                }
                //accessWidenerPath.set(file("../../src/main/resources/${mid}.accesswidener"))
            }
        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        enable {
            prop("deps.forge") { forgeVersion = it }
            prop("deps.neoform") { neoFormVersion = it }
            prop("deps.neoforge") { neoForgeVersion = it }
            prop("deps.mcp") { mcpVersion = it }
        }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
        configureNeoforge {
            //setAccessTransformers("../../src/main/resources/META-INF/accesstransformer.cfg")
            validateAccessTransformers = false
            runs.all {
                disableIdeRun()
                gameDirectory = file("../../run")
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true
        configs.register(mid)

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}
base {
    val meta = modstitch.metadata
    archivesName = "${meta.modName.get()}-${loader}-${minecraft}"
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to constraint.equals("fabric"),
        "neoforge" to constraint.equals("neoforge"),
        "forge" to constraint.equals("forge"),
        "vanilla" to constraint.equals("vanilla")
    )
}

tasks.named<Copy>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.register<Copy>("buildAndCollect") {
    dependsOn("build")
    group = "build"
    from(modstitch.finalJarTask.map { it.archiveFile }.get())
    into(rootProject.layout.buildDirectory.file("libs/${modv}"))
}


// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    val loaderEnum = when {
        modstitch.isLoom -> Loaders.LOOM
        modstitch.isModDevGradleLegacy -> Loaders.FORGE
        modstitch.isModDevGradleRegular -> Loaders.NEOFORGE
        else -> throw IllegalArgumentException("unknown loader")
    }

    val fzzyConfigVersion = findProperty("deps.fzzy_config_version")
    val fzzyMinecraftVersion = when (minecraft) {
        "1.21.1" -> "1.21"
        "1.21.4" -> "1.21.3"
        else -> minecraft
    }
    val libVersion = property("deps.lib_version") as String
    //fzzy
    modstitch.loom {
        val fabricApi = property("deps.fabric_api") as String
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApi}+${minecraft}")
        modstitchModImplementation("me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}")
    }

    modstitch.moddevgradle {

        if (modstitch.isModDevGradleLegacy) {
            modstitchModImplementation(("me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}+forge"))
        } else {
            modstitchModImplementation(("me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}+neoforge"))
        }

    }

    modstitchModImplementation("maven.modrinth:nirvana-library:${loader}-${minecraft}-${libVersion}")

    //loader-specified deps
    DependencyConfig.getDependencies(loaderEnum, minecraft).forEach { dep ->
        dependencies.add(dep.configuration, dep.notation, dep.options)
    }
    //lombok
    modstitchCompileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

    //shadow dep
    modstitchImplementation("io.vavr:vavr:0.10.6")
    modstitchImplementation("net.neoforged:bus:8.0.2")


    // Anything else in the dependencies block will be used for all platforms.
}
/*
msPublishing {
    val finalFileTree = rootProject.layout.buildDirectory.files("libs/${modv}").asFileTree.files

    mpp {
        changelog = file("../../changelog.md")
            .readLines()
            .joinToString("\n") { line ->
                if (line.isNotBlank()) {
                    "$line</br>"
                } else {
                    line
                }
            }
        type = STABLE
        //I think this is provided by modstich or stonecutter. So we can't add this otherwise the upload will fail.
        //modLoaders.add(loader)
        val finalFile = finalFileTree.filter { it.name.contains(minecraft) && it.name.contains(loader) }.first()
        file.set(finalFile)
        displayName = file.map { it.asFile.name }
        println(displayName.get())
        //dryRun = true
        val cfOptions = curseforgeOptions {
            accessToken = file("D:\\curseforge-key.txt").readText()
            projectId = "1164411"
            minecraftVersions.add(minecraft)
            requires("nirvana-library")
        }

        // Modrinth options used by both Fabric and Forge
        val mrOptions = modrinthOptions {
            accessToken = file("D:\\modrinth-key.txt").readText()
            version = "${loader}-${modstitch.metadata.modVersion.get()}"
            projectId = "6gKEW2ql"
            minecraftVersions.add(minecraft)
            requires("nirvana-library")
        }

        curseforge("toCurseForge") {
            from(cfOptions)
        }


        modrinth("toModrinth") {
            from(mrOptions)
        }


    }

}*/
