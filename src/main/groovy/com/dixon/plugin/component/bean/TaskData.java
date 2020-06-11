package com.dixon.plugin.component.bean;

import java.util.Set;

/**
 * 保存Task信息
 */
public class TaskData {

    /**
     * 当前module要执行的所有任务
     */
    private Set<String> moduleTasks;

    /**
     * 当前module要执行的所有打包任务
     */
    private Set<String> moduleAssembleTasks;

    /**
     * 所有打包任务
     */
    private Set<String> allAssembleTasks;

    public Set<String> getModuleTasks() {
        return moduleTasks;
    }

    public void setModuleTasks(Set<String> moduleTasks) {
        this.moduleTasks = moduleTasks;
    }

    public Set<String> getModuleAssembleTasks() {
        return moduleAssembleTasks;
    }

    public void setModuleAssembleTasks(Set<String> moduleAssembleTasks) {
        this.moduleAssembleTasks = moduleAssembleTasks;
    }

    public Set<String> getAllAssembleTasks() {
        return allAssembleTasks;
    }

    public void setAllAssembleTasks(Set<String> allAssembleTasks) {
        this.allAssembleTasks = allAssembleTasks;
    }

    @Override
    public String toString() {
        return "TaskConfig{" +
                "moduleTasks=" + moduleTasks +
                ", moduleAssembleTasks=" + moduleAssembleTasks +
                ", allAssembleTasks=" + allAssembleTasks +
                '}';
    }
}
