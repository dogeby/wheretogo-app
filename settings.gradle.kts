pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://repository.map.naver.com/archive/maven")
    }
}

rootProject.name = "Wheretogo"
include(":app")
include(":core:ui")
include(":feature:contents")
include(":feature:festivals")
include(":feature:search")
include(":feature:searchresult")
include(":feature:contentdetail")
include(":feature:reviewedit")
include(":feature:profile")
include(":feature:profileedit")
include(":feature:home")
include(":feature:regionselection")
include(":core:network")
include(":core:common")
include(":core:model")
include(":core:data")
include(":core:datastore")
include(":core:domain")
include(":core:database")
