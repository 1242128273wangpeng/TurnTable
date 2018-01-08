package com.wangpeng.animation.turntable.turntable

import android.graphics.Bitmap

/**
 * Created by wangpeng on 2018/1/5.
 */
data class LuckyWheelInfo constructor(
        var fillet: Int = 0, // 转盘项占比
        var turn_id: Int = 0, // 转盘项标识ID  1：橘色1、2：黄色1、3：蓝色、4：黄色2、5：橘色2、6：黄色3
        var action: String , // 动作（类型）text、gift、turth
        var text: String,// 做挑逗的表情 //标题如果action = gift 表示是礼物不显示这个
        var icon: String = "",// 真心话的icon
        var question: String = "",// 该字段暂时不用
        var value: String = "",// 该字段暂时不用
        var gift_info: VideoGiftInfo, // 转盘礼物
        var bitmap: Bitmap?) {
}