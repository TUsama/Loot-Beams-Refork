package deps

import org.gradle.api.artifacts.ExternalModuleDependency

object DependencyConfig {
    fun getDependencies(platform: Loaders, minecraft: String): List<VersionedDependency> {
        return when (platform) {
            Loaders.LOOM -> LoomDeps.get(minecraft)
            Loaders.FORGE -> ForgeDeps.get(minecraft)
            Loaders.NEOFORGE -> NeoForgeDeps.get(minecraft)
        }
    }
}

data class VersionedDependency(
    val configuration: String,
    val notation: String,
    val options: ExternalModuleDependency.() -> Unit = {}
)


