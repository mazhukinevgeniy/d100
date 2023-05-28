plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":tables")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}
