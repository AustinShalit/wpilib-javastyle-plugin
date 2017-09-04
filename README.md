# WPILib Versioning Plugin

[![Build Status](https://travis-ci.org/AustinShalit/wpilib-version-plugin.svg)](https://travis-ci.org/AustinShalit/wpilib-version-plugin)

This Gradle plugin is responsible for running Java style checks. This is used by most of the Java WPILib projects.

## Building

To build, run the following command:

```bash
./gradlew build
```

For a full list of tasks, see the output of:

```bash 
./gradlew tasks
```

Note that you will not be able to publish to plugins.gradle.org. To get a new version uploaded, contact austinshalit.

## Usage

To use this plugin in your program with Gradle version >= 2.1, use the following snippit:

```gradle
plugins {
  // NOTE: Substitute latest-version with the latest published version
  id "edu.wpi.first.wpilib.WPILibVersioningPlugin" version "latest-version"
}
```

For other versions use the following (note that using this plugin with less than Gradle 3.0 hasn't been tested):

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    // NOTE: Substitute latest-version with the latest published version
    classpath 'edu.wpi.first.wpilib:WPILibJavaStylePlugin:latest-version'
  }
}

apply plugin: 'WPILibJavaStylePlugin'
```

This plugin introduces the following extension:


```gradle
WPILibJavaStyle {
    checkstyle = true
    checkstyleVersion = 8.1

    pmd = true

    findbugs = true
    findBugsEffort = "max"
}
```
