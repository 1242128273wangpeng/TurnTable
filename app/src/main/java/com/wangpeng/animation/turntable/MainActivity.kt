package com.wangpeng.animation.turntable.turntable

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TurnTable.EndOnceTurnTableGame {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        turntable.endOnceTurnTableGame = this
        start_img.setOnClickListener({
            toast("启动转盘")
            turntable.startGame(3, 6, 3)
        });
        var localTurnItems: ArrayList<LuckyWheelInfo> = ArrayList()
        var rose = BitmapFactory.decodeResource(resources, R.drawable.rose)
        var lipstick = BitmapFactory.decodeResource(resources, R.drawable.lipstick)
        var lollies = BitmapFactory.decodeResource(resources, R.drawable.lollies)
        var kiss = BitmapFactory.decodeResource(resources, R.drawable.kiss)
        // 礼物图标有些是服务端下发,这里就直接用本地的作为例子
        var videoGiftInfo: VideoGiftInfo = VideoGiftInfo(gift_id = 1, gift_name = "棒棒糖", gift_price = "24", gift_url = "")
        localTurnItems.add(LuckyWheelInfo(fillet = 15, turn_id = 1, action = TurnTable.TXT_IMG_TXT, text = "", gift_info = videoGiftInfo, bitmap = rose))
        localTurnItems.add(LuckyWheelInfo(fillet = 10, turn_id = 2, action = TurnTable.TXT, text = "做十个\n蹲起", gift_info = videoGiftInfo, bitmap = null))
        var videoGiftInfo_2: VideoGiftInfo = videoGiftInfo.copy(gift_price = "33")
        localTurnItems.add(LuckyWheelInfo(fillet = 10, turn_id = 3, action = TurnTable.TXT_IMG_TXT, text = "", gift_info = videoGiftInfo_2, bitmap = lipstick))
        localTurnItems.add(LuckyWheelInfo(fillet = 15, turn_id = 4, action = TurnTable.TXT_IMG, text = "真心话", icon = "", gift_info = videoGiftInfo, bitmap = null))
        var videoGiftInfo_3: VideoGiftInfo = videoGiftInfo.copy(gift_price = "57")
        localTurnItems.add(LuckyWheelInfo(fillet = 20, turn_id = 5, action = TurnTable.TXT_IMG_TXT, text = "", gift_info = videoGiftInfo_3, bitmap = lollies))
        localTurnItems.add(LuckyWheelInfo(fillet = 10, turn_id = 6, action = TurnTable.TXT, text = "送个\n么么哒", gift_info = videoGiftInfo, bitmap = null))
        var videoGiftInfo_4: VideoGiftInfo = videoGiftInfo.copy(gift_price = "82")
        localTurnItems.add(LuckyWheelInfo(fillet = 10, turn_id = 7, action = TurnTable.TXT_IMG_TXT, text = "", gift_info = videoGiftInfo_4, bitmap = kiss))
        localTurnItems.add(LuckyWheelInfo(fillet = 10, turn_id = 8, action = TurnTable.TXT, text = "来一段\n热舞", gift_info = videoGiftInfo, bitmap = null))
        turntable.updateTurnItems(localTurnItems)
    }

    override fun endAction() {
        toast("结束游戏")
    }

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
