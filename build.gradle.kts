import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.task

repositories {
    mavenCentral()
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.1.4-3"
    id("com.gradle.plugin-publish") version "0.9.7"
    id("maven-publish")
}

group = "edu.wpi.first.wpilib"
version = "1.0.0-SNAPSHOT"

dependencies {
    compileOnly(gradleApi())
    compileOnly(kotlin("gradle-plugin", "1.1.4-3"))
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("mavenJar") {
            from(components.getByName("java"))
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/AustinShalit/wpilib-javastyle-plugin"
    website = vcsUrl
    description = "WPILibJavaStylePlugin"
    tags = listOf("kotlin", "linting", "java", "checkstyle", "pmd", "findbugs")

    (plugins) {
        "WPILibJavaStylePlugin" {
            id = "edu.wpi.first.wpilib.WPILibJavaStylePlugin"
            displayName = "WPILib Java Style Plugin"
        }
    }
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.1"
}
