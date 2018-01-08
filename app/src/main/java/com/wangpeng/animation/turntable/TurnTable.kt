package com.wangpeng.animation.turntable.turntable

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import java.util.ArrayList
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation


/**
 * Created by wangpeng on 2018/1/5.
 */
class TurnTable @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        var TXT: String = "text"
        var TXT_IMG: String = "truth"
        var TXT_IMG_TXT: String = "gift"
    }

    private var bgPaint: Paint
    private var textPaint: TextPaint
    private var rectF: RectF
    private var mDrawFilter: DrawFilter
    private var mRadius: Int = 0
    private var isCustomerOrServer: Boolean = false
    // 服务端传过来的是百分比
    private var translate: Float = 360 / 100.0f
    private var stringBuilder_gift: StringBuilder
    private var ratio: Int = 1
    private var turth_local_bitmap: Bitmap
    var endOnceTurnTableGame: EndOnceTurnTableGame? = null
    var turnItems: List<LuckyWheelInfo> = ArrayList<LuckyWheelInfo>()

    init {
        rectF = RectF()
        bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
        textPaint = TextPaint()
        stringBuilder_gift = StringBuilder("¥")
        turth_local_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shipinliaotian_zhenxinhua);
        setWillNotDraw(false);
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val width = this.width
        val height = this.height
        rectF.set(paddingLeft.toFloat(), paddingTop.toFloat(), width.toFloat(), height.toFloat())
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val curr_width = childView.measuredWidth
            val curr_height = childView.measuredHeight
            val start_x = ((width - curr_width) / 2.0f).toInt()
            val start_y = ((height - curr_height) / 2.0f).toInt()
            val end_x = start_x + curr_width
            val end_y = start_y + curr_height
            childView.layout(start_x, start_y, end_x, end_y)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var mWidthMeasureSpec = widthMeasureSpec
        var mheightMeasureSpec = heightMeasureSpec
        // 父容器传过来的宽度方向上的模式
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        // 父容器传过来的高度方向上的模式
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        // 父容器传过来的宽度的值
        var width = (View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft
                - paddingRight)
        // 父容器传过来的高度的值
        var height = (View.MeasureSpec.getSize(heightMeasureSpec) - paddingLeft
                - paddingRight)
        if (widthMode == View.MeasureSpec.EXACTLY
                && heightMode != View.MeasureSpec.EXACTLY && ratio.toFloat() != 0.0f) {
            // 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；
            // 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量
            // 且图片的宽高比已经赋值完毕，不再是0.0f
            // 表示宽度确定，要测量高度
            height = (width / ratio + 0.5f).toInt()
            mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        } else if (widthMode != View.MeasureSpec.EXACTLY
                && heightMode == View.MeasureSpec.EXACTLY && ratio.toFloat() != 0.0f) {
            // 判断条件跟上面的相反，宽度方向和高度方向的条件互换
            // 表示高度确定，要测量宽度
            width = (height * ratio + 0.5f).toInt()
            mheightMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        }
        super.onMeasure(mWidthMeasureSpec, mheightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var contentWidth: Int = width - paddingLeft - paddingRight
        var contentHeight: Int = height - paddingTop - paddingBottom
        val MinValue = Math.min(contentWidth, contentHeight)
        val radius = MinValue / 2
        var angle: Float = -90.0f
        if (turnItems != null && turnItems.size > 0 && turnItems.get(0) != null) {
            angle += (-turnItems.get(0).fillet * translate / 2.0f)
        } else {
            return
        }
        var arcAngle: Float = angle
        mRadius = Math.min(contentHeight, contentWidth)
        canvas.drawFilter = mDrawFilter
        for (luckWhellInfo in turnItems.iterator()) {
            var sweepAngle: Float = luckWhellInfo.fillet * translate
            if (TextUtils.equals(luckWhellInfo.action, TurnTable.TXT)) {
                bgPaint.color = context.resources.getColor(R.color.bgorange)
            } else if (TextUtils.equals(luckWhellInfo.action, TurnTable.TXT_IMG)) {
                bgPaint.color = context.resources.getColor(R.color.bgblue)
            } else if (TextUtils.equals(luckWhellInfo.action, TurnTable.TXT_IMG_TXT)) {
                bgPaint.color = context.resources.getColor(R.color.bgyellow)
            }
            canvas.drawArc(rectF, arcAngle, sweepAngle, true, bgPaint)
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())//将绘图原点移到画布中点
            canvas.rotate(sweepAngle)//旋转角度
            canvas.translate((-(width / 2)).toFloat(), (-(height / 2)).toFloat())//将画布原点移动
        }
        canvas.save()
        for (i in turnItems.indices) {
            var turnItem_next: LuckyWheelInfo? = null
            val temp = i + 1
            if (temp < turnItems.size) {
                turnItem_next = turnItems[temp]
            }
            val turnItem = turnItems[i]
            if (turnItem != null) {
                if (TurnTable.TXT == turnItem.action) {
                    textPaint.textSize = DensityUtil.dip2px(context, 14.0f)
                    textPaint.color = Color.WHITE
                    var contentWidth: Float = textPaint.measureText(turnItem.text)
                    var side = ""
                    if (isCustomerOrServer) {
                        side = "对方"
                    } else {
                        side = "你来"
                    }
                    val width_side = textPaint.measureText(side)
                    val start_side = (width - width_side) / 2.0f
                    canvas.drawText(side, start_side, radius * 0.2f, textPaint)
                    val text_content = turnItem.text
                    if (text_content.contains("\n")) {
                        val result = text_content.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (j in result.indices) {
                            val width_temp = textPaint.measureText(result[j])
                            val start_temp = (width - width_temp) / 2.0f
                            canvas.drawText(result[j], start_temp, radius * 0.2f + (j + 1) * DensityUtil.dip2px(context, 14.0f), textPaint)
                        }
                    } else {
                        val content_start = (width - contentWidth) / 2.0f
                        canvas.drawText(turnItem.text, content_start, radius * 0.2f + DensityUtil.dip2px(context, 14.0f), textPaint)
                    }
                } else if (TurnTable.TXT_IMG == turnItem.action) {
                    textPaint.textSize = DensityUtil.dip2px(context, 14.0f)
                    textPaint.color = Color.WHITE
                    var contentWidth: Float = textPaint.measureText(turnItem.text)
                    val content_start = (width - contentWidth) / 2.0f
                    canvas.drawText(turnItem.text, content_start, radius * 0.3f, textPaint)
                    val currBitmap = turnItem.bitmap
                    if (currBitmap != null) {
                        val bitmap_start = (width - currBitmap.width) / 2.0f
                        canvas.drawBitmap(currBitmap, bitmap_start, radius * 0.26f, textPaint)
                    } else {
                        val bitmap_start = (width - turth_local_bitmap.width) / 2.0f
                        canvas.drawBitmap(turth_local_bitmap, bitmap_start, radius * 0.35f, textPaint)
                    }
                } else if (TurnTable.TXT_IMG_TXT == turnItem.action) {
                    textPaint.textSize = DensityUtil.dip2px(context, 12.0f)
                    stringBuilder_gift.append(turnItem.gift_info.gift_price)
                    var priceWidth: Float = textPaint.measureText(stringBuilder_gift.toString())
                    val price_start = (width - priceWidth) / 2.0f
                    textPaint.color = resources.getColor(R.color.pricetext)
                    canvas.drawText(stringBuilder_gift.toString(), price_start, radius * 0.55f, textPaint)
                    stringBuilder_gift.delete(1, stringBuilder_gift.length)
                    val sendOrReceive: String
                    if (isCustomerOrServer) {
                        sendOrReceive = "送出"
                    } else {
                        sendOrReceive = "得到"
                    }
                    var sendWidth: Float = textPaint.measureText(sendOrReceive)
                    val send_start = (width - sendWidth) / 2.0f
                    val lineHeight = DensityUtil.dip2px(context, 14.0f)
                    var mDrawable: Drawable = resources.getDrawable(R.drawable.sendbg)
                    mDrawable.setBounds(-20, 0, (20 + sendWidth).toInt(), (lineHeight + 10).toInt())
                    canvas.translate((width - sendWidth) / 2.0f, lineHeight / 2.0f - 4)
                    mDrawable.draw(canvas)
                    canvas.translate(-(width - sendWidth) / 2.0f, -(lineHeight / 2.0f - 4))
                    canvas.drawText(sendOrReceive, send_start, radius * 0.15f, textPaint)
                    val currBitmap = turnItem.bitmap
                    if (currBitmap != null) {
                        val fitHeight = radius * 0.32f
                        val scale = currBitmap.height / fitHeight
                        val real = Bitmap.createScaledBitmap(currBitmap, (currBitmap.width / scale).toInt(), fitHeight.toInt(), false)
                        if (real != null) {
                            val bitmap_start = (width - real.width) / 2.0f
                            canvas.drawBitmap(real, bitmap_start, radius * 0.16f, textPaint)
                        }
                    }
                }

                canvas.translate((width / 2).toFloat(), (height / 2).toFloat())//将绘图原点移到画布中点
                if (turnItem_next != null) {
                    val sweepAngle = (turnItem.fillet * translate + turnItem_next.fillet * translate) / 2.0f
                    canvas.rotate(sweepAngle)//旋转角度
                }
                canvas.translate((-(width / 2)).toFloat(), (-(height / 2)).toFloat())//将画布原点移动
            }
        }
        canvas.restore()
    }

    fun updateTurnItems(turnItems: ArrayList<LuckyWheelInfo>): Unit {
        this.turnItems = turnItems
        postInvalidate()
    }

    /**
     * 开始转盘游戏
     * @param result_position 最终结果
     * @param turns           动画转的圈数
     * @param seconds         动画持续时间
     */
    fun startGame(result_position: Int, turns: Int, seconds: Int) {
        var result_position = result_position
        var endAngle = 0.0f
        // 位置转成下标
        result_position = result_position - 1
        for (i in result_position downTo 1) {
            val (fillet) = turnItems[i]
            val (fillet1) = turnItems[i - 1]
            endAngle += 360.0f - (fillet * translate / 2.0f + fillet1 * translate / 2.0f)
        }
        endAngle += (turns * 360).toFloat()
        val rotate = RotateAnimation(0.0f, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.duration = (seconds * 1000).toLong()//设置动画持续时间
        rotate.fillAfter = true
        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                endOnceTurnTableGame?.endAction()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        this.animation = rotate
        this.startAnimation(rotate)
    }


    interface EndOnceTurnTableGame {
        fun endAction()
    }

}