package com.dixon.plugin.component.util

class FormatPrint {

    private static final String TAG = "*** component ***"

    static void pl(String tag, String text) {
        println(TAG + " " + tag + ":\n\t" + text)
    }
}
