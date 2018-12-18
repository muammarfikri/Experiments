package com.example.mf.experiments

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import kotlin.random.Random
import kotlin.random.asJavaRandom

class TestView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
   defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    View(context, attrs, defStyleAttr, defStyleRes) {
    private val gridColor = ContextCompat.getColor(context!!, R.color.graphColor)

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = gridColor
        strokeWidth = 5f
    }
    private val gPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.CYAN
        strokeWidth = 5f
        isAntiAlias = true
    }
    private val lPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.RED
    }
    private val bPaint = Paint().apply {
        strokeWidth = 5f
        color = ContextCompat.getColor(context!!,R.color.grey)
//        alpha = 50
    }
    private val gridPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.5f
//        pathEffect = DashPathEffect(floatArrayOf(10f,15f),0f)
        color = ContextCompat.getColor(context!!,R.color.grid)
        isAntiAlias = true
    }
    private val circlePaint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val circleBorderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = gridColor
        isAntiAlias = true
    }

    private val gPath = Path()
    private val lPath = Path()
    private val bPath = Path()

    private val hBound = Point(0,0)
    private val vBound = Point(0,0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        hBound.y = heightMeasureSpec
        vBound.y = widthMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private val rect = Rect(0, 0, width, height)
    private val random = java.util.Random()

    override fun onDraw(canvas: Canvas) {

        val len = width
        val farr : Array<Float> = arrayOf(150.9f,
            296.13f,
            266.01f,
            256.88f,
            102.59f,
            343.9f,
            204.83f,
            484.3f,
            323.65f,
            262.7f,
            362.44f,
            204.93f,
            138.55f,
            450.17f,
            362.55f,
            184.35f,
            457.7f,
            231.37f,
            120.7f,
            59.41f,
            106.85f,
            474.16f,
            362.46f,
            142.95f,
            215.52f,
            470.68f,
            313.82f,
            145f,
            93.76f,
            463.5f,
            60.61f,
            71.08f,
            350.62f,
            70.44f,
            282.11f,
            350.46f,
            160.4f,
            280.97f,
            248.27f,
            148.23f,
            301.52f,
            214.4f,
            166.07f,
            312.54f,
            379.7f,
            105.11f,
            402.57f,
            207.47f,
            294.72f,
            195.33f,
            205.2f,
            223.74f,
            472.5f,
            105.28f,
            58.03f,
            87.32f,
            366.47f,
            223.48f,
            215.45f,
            307.62f,
            386.96f,
            397.18f,
            394.65f,
            190.98f,
            435.55f,
            445.8f,
            495.79f,
            318.01f,
            220.77f,
            420.97f,
            199.72f,
            226.42f,
            289.34f,
            303.71f,
            437.1f,
            405.12f,
            430.89f,
            103.29f,
            480.15f,
            83.17f,
            369.91f,
            164.25f,
            348.31f,
            99.3f,
            438.03f,
            185.46f,
            428.27f,
            291.5f,
            239.29f,
            210.63f,
            330.74f,
            406.81f,
            311.94f,
            89.79f,
            74.21f,
            169.18f,
            256.28f,
            219.48f,
            176.83f,
            150.77f,
            422.3f,
            304.58f,
            236.1f,
            472.08f,
            171.96f,
            58.76f,
            315.52f,
            295.9f,
            475.16f,
            290.11f,
            402.52f,
            263.05f,
            359.38f,
            178.26f,
            248.7f,
            88.86f,
            248.17f,
            428.31f,
            303.53f,
            241.67f,
            410.4f,
            229.49f,
            83.01f,
            378.69f,
            240.74f,
            204.73f,
            265.14f,
            373f,
            239.28f,
            419.2f,
            151.9f,
            115.65f,
            252.39f,
            275.71f,
            314.14f,
            132.83f,
            113.67f,
            182.88f,
            261.54f,
            194.72f,
            323.78f,
            443.5f,
            50.51f,
            295.47f,
            212.44f,
            361.34f,
            400.6f,
            234.89f,
            100.93f,
            63.34f,
            358.65f,
            221.13f,
            399.7f,
            61.89f,
            61.07f,
            136.66f,
            219.42f,
            219.01f,
            393.98f,
            372.87f,
            391.56f,
            226.91f,
            119.43f,
            236.63f,
            394.74f,
            294.4f,
            429.97f,
            155.3f,
            316.78f,
            58.75f,
            113.92f,
            293.76f,
            451.69f,
            359.68f,
            113.42f,
            214.87f,
            259.75f,
            138.49f,
            371.22f,
            275.99f,
            258.44f,
            303.87f,
            362.65f,
            205.27f,
            324.36f,
            63.89f,
            164.71f,
            457.19f,
            492.34f,
            71.57f,
            158.29f,
            158.53f,
            155.63f,
            214.97f,
            122.29f,
            171.56f,
            286.71f,
            469.41f,
            121.5f,

            222.5f
        )
        val iter = farr.iterator()

        gPath.moveTo(-10f, (width/2).toFloat())
        bPath.moveTo(-10f, (width/2).toFloat())
/*
        gPath.lineTo((len/4).toFloat() * 1,120f)
        gPath.lineTo((len/4).toFloat() * 2,150f)
        gPath.lineTo((len/4).toFloat() * 3,60f)
        gPath.lineTo((len/4).toFloat() * 4,90f)

        bPath.lineTo((len/4).toFloat() * 1,120f)
        bPath.lineTo((len/4).toFloat() * 2,150f)
        bPath.lineTo((len/4).toFloat() * 3,60f)
        bPath.lineTo((len/4).toFloat() * 4,90f)*/
        drawGraph(len, farr, iter)
        bPath.lineTo(width.toFloat(), height.toFloat())
        bPath.lineTo(0f,height.toFloat())
        bPath.close()

        drawGrid(canvas,5)

        canvas.drawPath(bPath,bPaint)
//        canvas.drawPath(lPath,lPaint)
        canvas.drawPath(gPath,gPaint)
/*        canvas.drawText("Sample Graph",30f,50f,gPaint)
        canvas.drawRect(0f,0f,width.toFloat(),height.toFloat(),paint)

        canvas.drawCircle((len/4).toFloat(),120f,5f,circlePaint)
        canvas.drawCircle((len/4).toFloat(),120f,5f,circleBorderPaint)*/

        invalidate()
    }

    private fun drawGraph(
        len: Int,
        farr: Array<Float>,
        iter: Iterator<Float>
    ) {
        for (i in len / 10 until len + 1 step len / 10) {
            val item = iter.next()
            if (item > height) item - item % height
            gPath.lineTo(i.toFloat(),height - item + 10f)
            bPath.lineTo(i.toFloat(), height -item + 10f)
        }
    }

    private fun drawGrid(canvas: Canvas,gridSize : Int) {
        for (i in height / gridSize until height step height / gridSize) {
//            canvas.drawText("${height-i} Unit",10f,i.toFloat()-10f,gridPaint)
            canvas.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), gridPaint)
        }
//        for (i in width / gridSize until width step width / gridSize) {
//            canvas.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), gridPaint)
//        }
    }
}