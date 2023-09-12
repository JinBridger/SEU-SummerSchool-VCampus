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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.alibaba:easyexcel:3.3.2")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    dokkaPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "app.vcampus.server.Main"
    }

    doFirst {
        from(configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        })

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
