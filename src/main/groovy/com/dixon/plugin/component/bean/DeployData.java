package com.dixon.plugin.component.bean;

import java.util.List;

/**
 * 部署数据
 */
public class DeployData {

    public static final String MODULE_TYPE_APPLICATION = "application";
    public static final String MODULE_TYPE_LIBRARY = "library";

    /**
     * 决定该project最终是application还是library
     */
    private String moduleType;

    /**
     * 决定该project最终要依赖的组件
     * <p>
     * 开发时组件无依赖 所以component为空
     */
    private List<String> component;

    /**
     * 决定该project最终要依赖的基础库
     */
    private List<String> baseLibrary;

    /**
     * 是否配置runAlone
     */
    private boolean runAlone;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public List<String> getComponent() {
        return component;
    }

    public void setComponent(List<String> component) {
        this.component = component;
    }

    public List<String> getBaseLibrary() {
        return baseLibrary;
    }

    public void setBaseLibrary(List<String> baseLibrary) {
        this.baseLibrary = baseLibrary;
    }

    public boolean isRunAlone() {
        return runAlone;
    }

    public void setRunAlone(boolean runAlone) {
        this.runAlone = runAlone;
    }

    @Override
    public String toString() {
        return "DeployData{" +
                "moduleType='" + moduleType + '\'' +
                ", component=" + component +
                ", baseLibrary=" + baseLibrary +
                ", runAlone=" + runAlone +
                '}';
    }
}
