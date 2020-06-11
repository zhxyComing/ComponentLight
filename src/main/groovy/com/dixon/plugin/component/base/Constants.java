package com.dixon.plugin.component.base;

public interface Constants {

    /**
     * 三大属性
     * <p>
     * 共同决定模块关系
     * <p>
     * depend_component
     * 决定组件模块间的开发可见性。
     * 被depend_component修饰的模块，在本模块作为主程序打包时，将一并打包进apk，然而在开发时却不可见。
     * 也就是说：depend_component是组件的独立运行依赖，而不是开发依赖。当本模块不独立运行时，depend_component不会生效。
     * <p>
     * depend_base
     * 该模块的基础模块。
     * 作为基础模块，开发时、打包编译时均可见。
     * 注意：A模块依赖基础模块a，B模块依赖基础模块b，同时A依赖B，则基础模块b对A不可见。
     * 从意义上分析，b是B的基础，而不是A的，开发不可见正常。
     * 从原理上分析，B作为A的组件模块，开发时没有依赖，所以b也没有依赖。
     * <p>
     * module_type
     * 决定模块是否可独立运行
     * 1.主模块 application 可独立运行
     * 2.组件模块 component_run 可独立运行 会去找runAlone文件
     * 3.组件模块 component_library 不可独立运行
     * 4.基础模块 base 不可独立运行
     * 3和4的效果是一致的，但是意义是不同的。
     * component_library表示当前模块是组件，尽管它不能独立运行，但它和其它组件是平级关系，不建议作为depend_base去依赖
     * base表示当前模块是基础库，它不能独立运行，同时也不建议作为depend_component去依赖
     * [后续可能会强制执行这项规范]
     * <p>
     * 问：某些模块既然不可独立运行，为什么还要使用框架呢？
     * 答：本模块的开发可见性可以通过depend_component来设置。即使depend_component依赖的模块非框架修饰，也没问题。
     * 基础模块base提供了一种选择，使开发者在非组件模块也可以使用框架，这是框架的另一个用途：明确声明模块关系。所以建议是：
     * [在所有模块使用框架]
     */
    String MODULE_ANDROID_TYPE = "module_type";
    String MODULE_DEPEND_COMPONENT = "depend_component";
    String MODULE_DEPEND_BASE_LIBRARY = "depend_base";

    /**
     * type
     * <p>
     * 用于管理模块的类型 决定是com.android.application还是com.android.library
     * TYPE_APPLICATION：开发时和编译打包时都是com.android.application
     * TYPE_LIBRARY：开发时和编译打包时都是com.android.library
     * TYPE_COMPONENT：开发时是com.android.application，打包编译时，如果是主工程则com.android.application，否则com.android.library
     */
    String TYPE_APPLICATION = "application";
    String TYPE_COMPONENT = "component";
    String TYPE_LIBRARY = "library";
    String TYPE_BASE = "base";

    /**
     * 配置文件名
     */
    String CONFIG_FILE_NAME = "module-config.properties";

    /**
     * 包含下面关键字的任务会被识别为打包任务
     */
    String[] ASSEMBLE_TASK_TAG = {"assemble", "install"};
}
