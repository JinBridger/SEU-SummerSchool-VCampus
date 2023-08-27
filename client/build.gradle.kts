import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.4.3"
    id("io.freefair.lombok") version "8.2.2"
    kotlin("plugin.lombok") version "1.9.0"
}

group = "app.vcampus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    api(compose.foundation)
    api(compose.animation)
    implementation("io.netty:netty-all:4.1.97.Final")
    implementation("com.google.code.gson:gson:2.10.1")

    val precompose_version = "1.4.3"
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.4.3")

    api("moe.tlaster:precompose:$precompose_version")

// api("moe.tlaster:precompose-molecule:$precompose_version") // For Molecule intergration

    api("moe.tlaster:precompose-viewmodel:$precompose_version") // For ViewModel intergration

//    val voyagerVersion = "1.0.0-rc06"
//
//    // Multiplatform
//
//    // Navigator
//    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
//
//    // BottomSheetNavigator
//    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
//
//    // TabNavigator
//    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
//
//    // Transitions
//    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

compose.desktop {
    application {
//        mainClass = "MainKt"
//        mainClass = "app.vcampus.client.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "client"
            packageVersion = "1.0.0"
        }
    }
}
