package com.dixon.plugin.component.control.parse;

import org.gradle.api.Project;

/**
 * 数据解析
 */
public interface DataParser<Bean> {

    Bean parse(Project project);
}
