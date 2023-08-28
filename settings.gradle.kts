pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version("1.9.0")
        id("org.jetbrains.compose").version("1.4.1")
    }
}

rootProject.name = "vcampus"
include("client", "server")
