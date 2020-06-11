package com.dixon.plugin.component.control.parse

import com.dixon.plugin.component.base.Constants
import com.dixon.plugin.component.bean.TaskData
import com.dixon.plugin.component.util.FormatPrint
import org.gradle.api.Project

/**
 * Task解析器
 * 将所有任务解析为以下三种任务，方便后续处理
 * 1.所有打包任务
 * 2.所有当前模块任务
 * 3.所有当前模块的打包任务
 */
class TaskMessageParser implements DataParser<TaskData> {

    @Override
    TaskData parse(Project project) {
        TaskData data
        // 所有任务
        Set<String> allTask = new HashSet<>(project.gradle.startParameter.taskNames)
        Set<String> allAssembleTasks = parseAllAssembleTask(allTask)
        Set<String> moduleTasks = parseModuleTask(project, allTask)
        Set<String> moduleAssembleTasks = parseModuleAssembleTask(allAssembleTasks, moduleTasks)

        // 赋值给TaskMessage
        data = new TaskData()
        data.setAllAssembleTasks(allAssembleTasks)
        data.setModuleTasks(moduleTasks)
        data.setModuleAssembleTasks(moduleAssembleTasks)

        FormatPrint.pl("TaskData", data.toString())
        return data
    }

    /**
     * 所有的打包任务
     * 带有"assemble", "install"这俩个关键字的任务会被当作打包任务
     * @param allTask
     * @return
     */
    private Set<String> parseAllAssembleTask(Set<String> allTask) {
        Set<String> allAssembleTasks = []
        String[] assembleTaskTags = Constants.ASSEMBLE_TASK_TAG
        for (String task : allTask) {
            // 一般task格式 :moduleName:taskName 但是也有可能是 taskName
            for (String assembleTaskTag : assembleTaskTags) {
                if (task.toLowerCase().contains(assembleTaskTag)) {
                    allAssembleTasks.add(task)
                }
            }
        }
        return allAssembleTasks
    }

    /**
     * 所有当前模块的任务
     * @param project
     * @param allTask
     * @return
     */
    private Set<String> parseModuleTask(Project project, Set<String> allTask) {
        Set<String> moduleTasks = []
        for (String task : allTask) {
            if (isCurModuleTask(project, task)) {
                moduleTasks.add(task)
            }
        }
        return moduleTasks
    }

    /**
     * 判断一个任务是不是当前project的任务
     * 方式有俩种：
     * 1.任务名中包含当前projectName
     * 2.projectName等于启动Task的文件夹名称
     * @param project
     * @param taskName
     * @return
     */
    private boolean isCurModuleTask(Project project, String taskName) {
        // 当格式为 :moduleName:taskName 可以这样解析
        // run 或 rebuild 都是这种格式
        String[] split = taskName.split(':')
        if (taskName.startsWith(":") && split.size() == 3) {
            return project.getName() == split[1]
        }
        // 可能是右侧Gradle任务启动 或 命令行执行task
        // 获取启动Task的文件夹的名称
        String moduleName = project.gradle.startParameter.currentDir.name
        if (moduleName == null) {
            return false
        }
        return project.getName() == moduleName
    }

    /**
     * 当前模块的所有打包任务
     * 实际就是求所有打包任务和所有当前模块任务的交集
     * @param allAssembleTask moduleTask
     * @return
     */
    private Set<String> parseModuleAssembleTask(Set<String> allAssembleTask, Set<String> moduleTask) {
        Set<String> moduleAssembleTask = new HashSet<>()
        moduleAssembleTask.addAll(allAssembleTask)
        moduleAssembleTask.retainAll(moduleTask)
        return moduleAssembleTask
    }
}
