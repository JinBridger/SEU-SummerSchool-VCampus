import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.compose") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

dependencies {
    implementation(project(":server"))

    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    api(compose.foundation)
    api(compose.animation)
    implementation("io.netty:netty-all:4.1.97.Final")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    dokkaPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.0")

    val precompose_version = "1.5.0"
    implementation(
        "org.jetbrains.compose.material:material-icons-extended-desktop:1.5.0"
    )
    implementation("com.seanproctor:data-table-material:0.5.1")
    implementation("org.jetbrains.compose.material3:material3-desktop:1.5.0")

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

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "app.vcampus.client.MainKt"
    }

    doFirst {
        from(configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        })

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}