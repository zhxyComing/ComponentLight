package com.dixon.plugin.component.control.deploy

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.dixon.plugin.component.bean.DeployData
import org.gradle.api.Project

class RealDeploy implements Deploy {

    private Project project
    private DeployData data

    RealDeploy(Project project, DeployData data) {
        this.project = project
        this.data = data
    }

    @Override
    void deploy() {
        // moduleType AndroidModule只有俩种 Application Or Library
        if (data.moduleType == DeployData.MODULE_TYPE_APPLICATION) {
            project.getPlugins().apply(AppPlugin)
        } else {
            project.getPlugins().apply(LibraryPlugin)
        }
        // runAlone
        if (data.runAlone) {
            configRunAlone()
        }
        // component module
        if (data.component) {
            data.component.each { String component ->
                project.dependencies.add("implementation", project.project(':' + component))
            }
        }
        // base module
        if (data.baseLibrary) {
            data.baseLibrary.each { String base ->
                project.dependencies.add("api", project.project(':' + base))
            }
        }
    }

    private void configRunAlone() {
        project.android.sourceSets {
            main {
                manifest.srcFile "src/main/runalone/AndroidManifest.xml"
                java.srcDir "src/main/runalone/java"
                res.srcDir "src/main/runalone/res"
                assets.srcDir "src/main/runalone/assets"
                jniLibs.srcDir '../app/src/main/jniLibs'
            }
        }
    }
}
