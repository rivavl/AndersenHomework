package com.marina.andersenhomework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class CustomClock(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private var hours = 0f
    private var minutes = 0f
    private var seconds = 0

    private var hourHandSize = 0f
    private var minuteHandSize = 0f
    private var secondHandSize = 0f

    private var radius = 0f

    private var refreshThread: Thread? = null
    private var mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    invalidate()
                }
            }
        }
    }

    // разные кисти для стрелок и окружности с засечками
    private var paintSecond = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }
    private var paintMinute = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 15F
    }
    private var paintHour = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 20F
    }
    private var paintBlack = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 20F
    }


    //========================================
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttribute: Int) : this(
        context,
        attributeSet,
        defStyleAttribute,
        R.style.DefaultClockStyle
    )

    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        R.attr.customClockStyle
    )

    constructor(context: Context) : this(context, null)
    //==========================================


    init {
        if (attributeSet != null) {
            initAttributes(attributeSet, defStyleAttr, defStyleRes)
        }
    }

    private fun initAttributes(
        attributeSet: AttributeSet?,
        defStyleAttribute: Int,
        defStyleRes: Int,
    ) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomClock,
            defStyleAttribute,
            defStyleRes
        )
        paintSecond.color = typedArray.getColor(R.styleable.CustomClock_secondColor, Color.BLACK)
        paintMinute.color = typedArray.getColor(R.styleable.CustomClock_minuteColor, Color.BLACK)
        paintHour.color = typedArray.getColor(R.styleable.CustomClock_hourColor, Color.BLACK)

        hourHandSize = typedArray.getDimension(R.styleable.CustomClock_hourSize, 100F)
        minuteHandSize = typedArray.getDimension(R.styleable.CustomClock_minuteSize, 100F)
        secondHandSize = typedArray.getDimension(R.styleable.CustomClock_secondSize, 100F)

        typedArray.recycle()
    }


    private fun drawScale(canvas: Canvas?) {
        var scaleLength: Float?
        canvas?.save()
        for (i in 0..12) {
            paintBlack.strokeWidth = 15f
            scaleLength = 40f

            canvas?.drawLine(
                width / 2.toFloat(),
                height / 2 - radius,
                width / 2.toFloat(),
                (height / 2 - radius + scaleLength),
                paintBlack
            )
            canvas?.rotate(
                360 / 12.toFloat(),
                width / 2.toFloat(),
                height / 2.toFloat()
            )
        }
        canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val result =
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                200
            } else {
                Math.min(widthSpecSize, heightSpecSize)
            }
        radius = (result / 3).toFloat()
        setMeasuredDimension(result, result)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        refreshThread = Thread {
            while (true) {
                try {
                    Thread.sleep(1000)
                    mHandler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    break
                }
            }
        }
        refreshThread?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
        refreshThread?.interrupt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        getCurrentTime()
        drawMainCircle(canvas)
        drawScale(canvas)
        drawHand(canvas)
    }

    private fun drawMainCircle(canvas: Canvas?) {
        canvas?.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            radius,
            paintBlack
        )
    }

    private fun drawHand(canvas: Canvas?) {
        drawHour(canvas, paintHour)
        drawMinute(canvas, paintMinute)
        drawSecond(canvas, paintSecond)
    }

    private fun getCurrentTime() {
        val calendar = Calendar.getInstance()
        seconds = calendar.get(Calendar.SECOND)
        minutes = calendar.get(Calendar.MINUTE) + seconds.toFloat() / 60
        hours = calendar.get(Calendar.HOUR) + minutes / 60
    }

    private fun drawSecond(canvas: Canvas?, paint: Paint) {
        val longR = secondHandSize
        val shortR = longR / 4
        val startX =
            (width / 2 - shortR * sin(seconds.times(Math.PI / 30))).toFloat()
        val startY =
            (height / 2 + shortR * cos(seconds.times(Math.PI / 30))).toFloat()
        val endX =
            (width / 2 + longR * sin(seconds.times(Math.PI / 30))).toFloat()
        val endY =
            (height / 2 - longR * cos(seconds.times(Math.PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }

    private fun drawMinute(canvas: Canvas?, paint: Paint) {
        val longR = minuteHandSize
        val shortR = longR / 4
        val startX =
            (width / 2 - shortR * sin(minutes.times(Math.PI / 30))).toFloat()
        val startY =
            (height / 2 + shortR * cos(minutes.times(Math.PI / 30))).toFloat()
        val endX =
            (width / 2 + longR * sin(minutes.times(Math.PI / 30))).toFloat()
        val endY =
            (height / 2 - longR * cos(minutes.times(Math.PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }

    private fun drawHour(canvas: Canvas?, paint: Paint) {
        val longR = hourHandSize
        val shortR = longR / 4
        val startX =
            (width / 2 - shortR * sin(hours.times(Math.PI / 6))).toFloat()
        val startY =
            (height / 2 + shortR * cos(hours.times(Math.PI / 6))).toFloat()
        val endX =
            (width / 2 + longR * sin(hours.times(Math.PI / 6))).toFloat()
        val endY =
            (height / 2 - longR * cos(hours.times(Math.PI / 6))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }
}