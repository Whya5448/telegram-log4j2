import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.6.7"
}

group = "org.metalscraps.log"
version = "0.0.1"

repositories {
    mavenCentral()
}

val log4j2Version = "2.17.2"

dependencies {
    implementation("org.telegram:telegrambots:6.1.0") {
        exclude("org.json:json") // Cxdb5a1032-eda2
    }
    implementation("org.json:json:20200518")

    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")

    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-log4j2")
    testImplementation("org.apache.logging.log4j:log4j-spring-boot:$log4j2Version")
}

tasks.withType<Jar> {
    enabled = true
    archiveClassifier.set("")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withJavadocJar()
    withSourcesJar()

}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}