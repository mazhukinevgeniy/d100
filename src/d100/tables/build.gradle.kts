plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}
sqldelight {
    databases {
        create("Database") {
            packageName.set("org.example")
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
    implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}
