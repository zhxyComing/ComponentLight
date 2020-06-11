package com.dixon.plugin.component.bean;

import java.util.List;

/**
 * 保存模块配置信息
 * <p>
 * 用户配置的数据 不代表最终部署形式 最终部署数据保存在DeployData中
 */
public class ModuleConfig {

    private List<String> component;
    private List<String> base;
    private String type;

    public ModuleConfig() {
    }

    public ModuleConfig(List<String> component, List<String> base, String type) {
        this.component = component;
        this.base = base;
        this.type = type;
    }

    public List<String> getComponent() {
        return component;
    }

    public void setComponent(List<String> component) {
        this.component = component;
    }

    public List<String> getBase() {
        return base;
    }

    public void setBase(List<String> base) {
        this.base = base;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ModuleConfig{" +
                "component=" + component +
                ", base=" + base +
                ", type='" + type + '\'' +
                '}';
    }
}
