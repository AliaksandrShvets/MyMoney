package com.goodsoft.mymoney.widgets.funds

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import com.goodsoft.mymoney.util.px


class FoundsDiagram(context: Context, attrs: AttributeSet? = null) : AppCompatImageView(context, attrs) {

    private var selectedPosition: Int = 0
    private var analyticsListPercents = listOf<Float>()

    @ColorInt
    private var selectedColor = Color.RED

    @ColorInt
    private var unselectedColor = Color.WHITE

    private val columnWidth by lazy { width / 13f }
    private val columnCornerRadius by lazy { width / 26f }


    private val rect = RectF()
    private val paint by lazy { Paint() }
    private val paintWithStroke by lazy {
        Paint().apply {
            val radius = (width / 26f - 1.px.toFloat()) * Math.PI.toFloat()
            pathEffect = DashPathEffect(floatArrayOf(radius * 1f / 6, radius * 1f / 6), 0f)
            style = Paint.Style.STROKE
            strokeWidth = 1.px.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in analyticsListPercents.indices) {
            if (analyticsListPercents[i] > 0) {
                paint.color = if (selectedPosition == i) selectedColor else unselectedColor
                val left = i * 2 * columnWidth
                rect.set(left,
                        (height - columnWidth) * (1 - analyticsListPercents[i]),
                        left + columnWidth,
                        height.toFloat())
                canvas?.drawRoundRect(rect, columnCornerRadius, columnCornerRadius, paint)
            } else {
                paintWithStroke.color = if (selectedPosition == i) selectedColor else unselectedColor
                canvas?.drawCircle(
                        i * 2 * columnWidth + columnCornerRadius,
                        height - columnCornerRadius,
                        columnCornerRadius - 1.px.toFloat() / 2,
                        paintWithStroke
                )
            }
        }
    }

    fun setSelectedColor(@ColorRes color: Int) {
        selectedColor = color
    }

    fun setUnselectedColor(@ColorRes color: Int) {
        unselectedColor = color
    }

    fun drawDiagram(analyticsList: List<DayAmountSpent>, selectedPosition: Int) {
        this.analyticsListPercents = getAnalyticsPercents(analyticsList)
        this.selectedPosition = selectedPosition
        invalidate()
    }

    private fun getAnalyticsPercents(analyticsList: List<DayAmountSpent>): List<Float> {
        val maxAmount = analyticsList.maxBy { it.amount }?.amount ?: 0.0
        return analyticsList.map {
            (it.amount / maxAmount).toFloat()
        }
    }
}