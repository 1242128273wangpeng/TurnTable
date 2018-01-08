package com.wangpeng.animation.turntable.turntable

import android.content.Context

/**
 * Created by wangpeng on 2018/1/6.
 */
class DensityUtil {
    companion object {
        fun dip2px(var0: Context, var1: Float): Float {
            val var2 = var0.resources.displayMetrics.density
            return (var1 * var2 + 0.5f)
        }

        fun px2dip(var0: Context, var1: Float): Float {
            val var2 = var0.resources.displayMetrics.density
            return (var1 / var2 + 0.5f)
        }
    }
}