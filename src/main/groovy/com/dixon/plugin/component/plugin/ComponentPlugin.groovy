package com.dixon.plugin.component.plugin

import com.dixon.plugin.component.base.Constants
import com.dixon.plugin.component.bean.ModuleConfig
import com.dixon.plugin.component.bean.TaskData

import com.dixon.plugin.component.control.deploy.DeployService
import com.dixon.plugin.component.control.parse.DataParser
import com.dixon.plugin.component.control.parse.ModuleConfigParser
import com.dixon.plugin.component.control.parse.TaskMessageParser
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComponentPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        // 通过解析器获取module-config.properties配置的参数
        DataParser<ModuleConfig> moduleParser = new ModuleConfigParser()
        ModuleConfig moduleConfig = moduleParser.parse(project)
        if (moduleConfig == null) {
            throw new IllegalArgumentException("请正确配置 " + Constants.CONFIG_FILE_NAME + " 参数")
        }

        // 通过解析器获取任务参数
        DataParser<TaskData> taskParser = new TaskMessageParser()
        TaskData taskData = taskParser.parse(project)
        if (taskData == null) {
            throw new IllegalArgumentException("Task解析异常")
        }

        // 部署服务
        DeployService.getDeploy(project, taskData, moduleConfig).deploy()
    }
}