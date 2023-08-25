plugins {
    id("java")
    id("io.freefair.lombok") version "8.2.2"
}

group = "app.vcampus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.97.Final")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:jcl-over-slf4j:2.0.7")
    implementation("ch.qos.logback:logback-core:1.4.11")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.hibernate.orm:hibernate-core:6.2.7.Final")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.springframework.security:spring-security-crypto:6.1.3")
    implementation("org.bouncycastle:bcprov-jdk18on:1.76")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "app.vcampus.server.Main"
    }
}
