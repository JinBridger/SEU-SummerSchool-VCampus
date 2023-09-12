plugins {
    id("java") apply true
    kotlin("jvm") version "1.9.0" apply true
    id("io.freefair.lombok") version "8.2.2" apply true
    kotlin("plugin.lombok") version "1.9.0" apply true
    id("org.jetbrains.dokka") version "1.9.0" apply true
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    tasks.withType<Jar> {
        isZip64 = true

        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    }

    group = "app.vcampus"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.jetbrains.dokka")

    dependencies {
        dokkaPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.0")
    }
}
