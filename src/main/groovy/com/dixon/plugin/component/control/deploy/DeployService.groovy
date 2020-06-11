package com.dixon.plugin.component.control.deploy

import com.dixon.plugin.component.base.Constants
import com.dixon.plugin.component.bean.DeployData
import com.dixon.plugin.component.bean.ModuleConfig
import com.dixon.plugin.component.bean.TaskData
import com.dixon.plugin.component.util.FormatPrint
import org.gradle.api.Project

class DeployService {

    private TaskData mTaskData
    private ModuleConfig mModuleConfig
    private Project mProject
    private DeployData mDeployData = new DeployData()

    private DeployService(Project project, TaskData taskData, ModuleConfig moduleConfig) {
        mProject = project
        mTaskData = taskData
        mModuleConfig = moduleConfig
    }

    static Deploy getDeploy(Project project, TaskData taskData, ModuleConfig moduleConfig) {
        DeployService service = new DeployService(project, taskData, moduleConfig)
        DeployData deployData = service.getDeployData()
        FormatPrint.pl("DeployData", deployData.toString())
        Deploy deploy = new RealDeploy(project, deployData)
        return deploy
    }

    private DeployData getDeployData() {
        loadModuleTypeAndRunAlone()
        loadComponentModule()
        loadBaseModule()
        return mDeployData
    }

    /**
     * 决定最终的组件形式是Application还是Library
     * 决定最终是否配置runAlone
     * @return
     */
    private void loadModuleTypeAndRunAlone() {
        String type = mModuleConfig.type
        // 打包任务为空 可能在sync
        // 打包任务>1 可能在rebuild
        if (mTaskData.allAssembleTasks.empty || mTaskData.allAssembleTasks.size() > 1) {
            switch (type) {
                case Constants.TYPE_APPLICATION:
                    mDeployData.runAlone = false
                    mDeployData.moduleType = DeployData.MODULE_TYPE_APPLICATION
                    break
                case Constants.TYPE_COMPONENT:
                    mDeployData.runAlone = true
                    mDeployData.moduleType = DeployData.MODULE_TYPE_APPLICATION
                    break
                case Constants.TYPE_LIBRARY:
                case Constants.TYPE_BASE:
                    mDeployData.runAlone = false
                    mDeployData.moduleType = DeployData.MODULE_TYPE_LIBRARY
                    break
            }
        } else {
            String taskName = mTaskData.allAssembleTasks.iterator().next()
            if (isCurProjectAssemble(mProject.getName(), taskName, mTaskData.moduleAssembleTasks)) {
                // 如果当前project是主模块 则设置com.android.application
                mDeployData.moduleType = DeployData.MODULE_TYPE_APPLICATION
                if (type == Constants.TYPE_COMPONENT) {
                    mDeployData.runAlone = true
                } else {
                    mDeployData.runAlone = false
                }
            } else {
                if (type == Constants.TYPE_APPLICATION) {
                    // apk主工程仍然是com.android.application
                    mDeployData.runAlone = false
                    mDeployData.moduleType = DeployData.MODULE_TYPE_APPLICATION
                } else {
                    // 非主模块则是Library
                    mDeployData.runAlone = false
                    mDeployData.moduleType = DeployData.MODULE_TYPE_LIBRARY
                }
            }
        }
    }

    /**
     * 判断是不是当前project执行打包
     * <p>
     * 如果是当前project执行打包，则当前project可能是主工程，也可能是component
     */
    private boolean isCurProjectAssemble(String projectName, String taskName, Set<String> curModuleAssembleTasks) {
        // 如果是 [:moduleName:taskName] 的形式
        String[] split = taskName.split(":")
        if (taskName.startsWith(":") && split.size() == 3) {
            return projectName == split[1]
        }
        // 也可能是 [taskName] 的形式
        // 如果当前模块的打包任务中包含此任务 则也返回true
        for (String curModuleAssembleTask : curModuleAssembleTasks) {
            if (taskName == curModuleAssembleTask) {
                return true
            }
        }
        return false
    }

    /**
     * 决定最终依赖的组件
     * @return
     */
    private boolean loadComponentModule() {
        // 只有一个打包任务 说明是真正的打包 此时才添加组件依赖
        if (mTaskData.allAssembleTasks.empty || mTaskData.allAssembleTasks.size() > 1) {
            return
        }
        mDeployData.component = mModuleConfig.component
    }

    /**
     * 决定基础模块依赖
     * @return
     */
    private boolean loadBaseModule() {
        mDeployData.baseLibrary = mModuleConfig.base
    }
}
