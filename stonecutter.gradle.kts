plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.1-neoforge"

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) { 
    group = "project"
    ofTask("build")
}

stonecutter registerChiseled tasks.register("chiseledBuildAndCollect", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://maven.blamejared.com")


        maven("https://cursemaven.com")
        maven("https://oss.sonatype.org/content/repositories/snapshots")

        maven("https://maven.parchmentmc.org")

        maven("https://api.modrinth.com/maven")

        maven("https://maven.fzzyhmstrs.me/")

        maven("https://thedarkcolour.github.io/KotlinForForge/")

        maven("https://maven.terraformersmc.com/")

        maven("https://maven.ladysnake.org/releases")

        maven("https://maven.theillusivec4.top/")

        maven ("https://maven.shedaniel.me/")
    }
}