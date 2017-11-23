package com.utils;

import android.content.Context;

public final class uptext {
    public static Context mApplicationContext;

    public static void 置全局上下文(Context param) {
        mApplicationContext = param;
    }

    public static Context 取全局上下文() {
        return mApplicationContext;
    }
}
