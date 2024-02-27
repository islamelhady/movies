pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "movies"
include(":app")
include(":core:base")
include(":core:data:remote")
include(":core:data:repository")
include(":core:data:local")
include(":core:domain:entities")
