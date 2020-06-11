package com.dixon.plugin.component.control.parse

import com.dixon.plugin.component.base.Constants
import com.dixon.plugin.component.bean.ModuleConfig
import com.dixon.plugin.component.util.FormatPrint
import org.gradle.api.Project

class ModuleConfigParser implements DataParser<ModuleConfig> {

    @Override
    ModuleConfig parse(Project project) {
        ModuleConfig data
        Properties properties = new Properties()
        properties.load(project.file(Constants.CONFIG_FILE_NAME).newDataInputStream())
        data = new ModuleConfig()
        data.with {
            data.type = properties.get(Constants.MODULE_ANDROID_TYPE)
            data.component = parseComponent(properties)
            data.base = parseBase(properties)
        }
        FormatPrint.pl("ModuleConfig", data.toString())
        return data
    }

    /**
     * 解析depend_component
     * @param properties
     * @return
     */
    private List<String> parseComponent(Properties properties) {
        List<String> compArray = []
        String components = properties.get(Constants.MODULE_DEPEND_COMPONENT)
        if (components) {
            compArray = components.split(',')
        }
        return compArray
    }

    /**
     * 解析depend_base
     * @param properties
     * @return
     */
    private List<String> parseBase(Properties properties) {
        List<String> baseArray = []
        String bases = properties.get(Constants.MODULE_DEPEND_BASE_LIBRARY)
        if (bases) {
            baseArray = bases.split(',')
        }
        return baseArray
    }
}
