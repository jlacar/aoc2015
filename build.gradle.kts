plugins {
    kotlin("jvm") version "2.0.0"
}

group = "lacar.junilu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}