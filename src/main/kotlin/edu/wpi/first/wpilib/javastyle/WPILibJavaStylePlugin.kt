package edu.wpi.first.wpilib.javastyle

import groovy.util.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.plugins.quality.FindBugs
import org.gradle.api.plugins.quality.FindBugsExtension
import org.gradle.api.plugins.quality.FindBugsPlugin
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.plugins.quality.PmdPlugin

class WPILibJavaStylePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = project.extensions.create("WPILibJavaStyle", WPILibJavaStylePluginExtension::class.java)

        // Only for java projects
        project.pluginManager.withPlugin("java") {
            if (extension.checkstyle) {
                project.plugins.apply(CheckstylePlugin::class.java)
                project.extensions.configure(CheckstyleExtension::class.java, { checkstyle ->
                    checkstyle.toolVersion = extension.checkstyleVersion.toString()
                    checkstyle.config = project.resources.text.fromString(WPILibJavaStylePlugin::class.java.getResource("checkstyle.xml").readText())
                })
            }

            if (extension.pmd) {
                project.plugins.apply(PmdPlugin::class.java)
                project.extensions.configure(PmdExtension::class.java, { pmd ->
                    pmd.isConsoleOutput = true
                    pmd.reportsDir = project.buildDir.resolve("reports/pmd")
                    pmd.ruleSetConfig = project.resources.text.fromString(WPILibJavaStylePlugin::class.java.getResource("pmd-ruleset.xml").readText())
                })
            }

            if (extension.findbugs) {
                project.plugins.apply(FindBugsPlugin::class.java)
                project.extensions.configure(FindBugsExtension::class.java, { findBugs ->
                    findBugs.effort = extension.findBugsEffort
                    findBugs.sourceSets = project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets
                })

                project.task("findbugsReport").doLast({
                    val files = project.extensions.getByType(FindBugsExtension::class.java).reportsDir.listFiles()
                    if (files.isNotEmpty()) {
                        files.forEach {
                            println(XmlParser().parse(it).get("BugInstance"))
                        }
                    }
                })
                project.tasks.withType(FindBugs::class.java).forEach({it.finalizedBy("findbugsReport")})
            }
        }
    }
}
