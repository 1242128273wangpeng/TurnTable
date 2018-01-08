package com.wangpeng.animation.turntable.turntable

/**
 * Created by wangpeng on 2018/1/5.
 */
data class VideoGiftInfo constructor(
        var gift_id: Int = 0,
        var gift_name: String? = null, // 礼物名
        var gift_price: String? = null, // 价格
        var gift_unit: String? = null,
        var gift_url: String? = null, // 图标
        var send_num: String? = null, // 服务者收到的礼物数
        var isSelect: Boolean = false,
        var gift_animate_type: Int = 0,
        var animateType: String? = null, // 礼物动画
        var gift_value: String? = null,
        var add_price: String? = null) {
}