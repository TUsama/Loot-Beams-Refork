package deps

import org.gradle.api.artifacts.ExternalModuleDependency

class DependencyBuilder {
    val dependencies = mutableListOf<VersionedDependency>()

    fun modstitchCompileOnly(
        notation: String,
        options: ExternalModuleDependency.() -> Unit = {}
    ) {
        dependencies += VersionedDependency("modstitchCompileOnly", notation, options)
    }

    fun modstitchImplementation(
        notation: String,
        options: ExternalModuleDependency.() -> Unit = {}
    ) {
        dependencies += VersionedDependency("modstitchImplementation", notation, options)
    }

    fun modstitchModImplementation(notation: String, options: ExternalModuleDependency.() -> Unit = {}) {
        dependencies += VersionedDependency("modstitchModImplementation", notation, options)
    }

    fun modstitchModCompileOnly(notation: String, options: ExternalModuleDependency.() -> Unit = {}) {
        dependencies += VersionedDependency("modstitchModCompileOnly", notation, options)
    }

    fun modstitchModRuntimeOnly(notation: String, options: ExternalModuleDependency.() -> Unit = {}) {
        dependencies += VersionedDependency("modstitchModRuntimeOnly", notation, options)
    }

    fun modstitchLegacyModImplementation(
        notation: String,
        options: ExternalModuleDependency.() -> Unit = {}
    ) {
        dependencies += VersionedDependency("modImplementation", notation, options)
    }

    fun modstitchLegacyModRuntimeOnly(notation: String, options: ExternalModuleDependency.() -> Unit = {}) {
        dependencies += VersionedDependency("modRuntimeOnly", notation, options)
    }

}

fun buildDependencies(block: DependencyBuilder.() -> Unit): List<VersionedDependency> {
    return DependencyBuilder().apply(block).dependencies
}