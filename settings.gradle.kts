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

rootProject.name = "Currention"
include(":app")
include(":feature")
include(":feature:rates")
include(":feature:convert")
include(":feature:parameters")
include(":feature:onboarding")
include(":commonui")
include(":db")
include(":commonres")
include(":commonmodels")
