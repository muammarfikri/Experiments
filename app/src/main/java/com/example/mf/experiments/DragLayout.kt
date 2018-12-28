package com.example.mf.experiments

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class DragLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    lateinit var bottomview: View
    lateinit var topview: View
    private var downState: Int = 0
    var viewDragHelper: ViewDragHelper
    var moveDetector: GestureDetectorCompat
    private var originY: Int = 0
    private var dragTopDest = 0
    private var originX: Int = 0
    private var bottomDragVisibleHeight: Int = 0
    private var bottomExtraIndicatorHeight = 0
    private var mTouchSlop = 5

    init {
        val res = context.obtainStyledAttributes(attrs, R.styleable.DragLayout, 0, 0)
        bottomDragVisibleHeight = res.getDimension(R.styleable.DragLayout_dl_bottomDragVisibleHeight, 0f).toInt()
        bottomExtraIndicatorHeight = res.getDimension(R.styleable.DragLayout_dl_bototmExtraIndicatorHeight, 0f).toInt()
        res.recycle()

        viewDragHelper = ViewDragHelper.create(this, 10f, DragHelperCallback())
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP)
        moveDetector = GestureDetectorCompat(context, MoveDetector())
        moveDetector.setIsLongpressEnabled(true)

        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomview = getChildAt(0)
        topview = getChildAt(1)
        topview.setOnClickListener {
            val state = getCurrentState()
            if (state == STATE_CLOSE) {
                smoothSlide(topview, dragTopDest)
            }
        }
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child === topview
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            if (changedView === topview) processLinkageView()
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val currentTop = child.top
            if (top > child.top) {
                return currentTop + (top - currentTop) / 2
            }
            return when {
                currentTop > DECELERATE_THRESHOLD * 3 -> currentTop + (top - currentTop) / 2
                currentTop > DECELERATE_THRESHOLD * 2 -> currentTop + (top - currentTop) / 4
                currentTop > 0 -> currentTop + (top - currentTop) / 8
                currentTop > -DECELERATE_THRESHOLD -> currentTop + (top - currentTop) / 16
                currentTop > -DECELERATE_THRESHOLD * 2 -> currentTop + (top - currentTop) / 32
                currentTop > -DECELERATE_THRESHOLD * 3 -> currentTop + (top - currentTop) / 48
                else -> currentTop + (top - currentTop) / 64
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int = child.left

        override fun getViewHorizontalDragRange(child: View): Int = 600

        override fun getViewVerticalDragRange(child: View): Int = 600

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            var finalY = originY
            if (downState == STATE_CLOSE) {
                if (originY - releasedChild.top > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel < -DRAG_SWITCH_VEL_THRESHOLD) {
                    finalY = dragTopDest
                }
            } else {
                val gotoBottom =
                    releasedChild.top - dragTopDest > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel > DRAG_SWITCH_VEL_THRESHOLD
                if (!gotoBottom) {
                    finalY = dragTopDest
                    if (dragTopDest - releasedChild.top > mTouchSlop) {
                        postResetPosition()
                        return
                    }
                }
            }

            if (viewDragHelper.smoothSlideViewTo(releasedChild, originX, finalY)) {
                ViewCompat.postInvalidateOnAnimation(this@DragLayout)
            }
        }
    }

    inner class MoveDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent, dx: Float,
            dy: Float
        ): Boolean {
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (!changed) {
            return
        }

        super.onLayout(changed, left, top, right, bottom)

        originX = topview.x.toInt()
        originY = topview.y.toInt()
        dragTopDest = bottomview.bottom - bottomDragVisibleHeight - topview.measuredHeight
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            viewDragHelper.processTouchEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val yScroll = moveDetector.onTouchEvent(ev)
        var shouldIntercept = false
        try {
            shouldIntercept = viewDragHelper.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }

        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            downState = getCurrentState()
            viewDragHelper.processTouchEvent(ev)
        }
        return shouldIntercept && yScroll
    }

    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this@DragLayout)
        }
    }

    private fun postResetPosition() {
        this.postDelayed({
            topview.offsetTopAndBottom(dragTopDest - topview.top)
        }, 500)
    }

    private fun smoothSlide(releasedChild: View, finalY: Int) {
        if (viewDragHelper.smoothSlideViewTo(releasedChild, originX, finalY)) {
            ViewCompat.postInvalidateOnAnimation(this@DragLayout)
        }
    }

    private fun getCurrentState(): Int {
        return when {
            Math.abs(topview.top - dragTopDest) <= mTouchSlop -> STATE_EXPANDED
            else -> STATE_CLOSE
        }
    }

    private fun processLinkageView() {
        if (topview.top > originY) {
            bottomview.alpha = 0f
        } else {
            bottomview.apply {
                var realAlpha = (originY - topview.top) * 0.01f
                if (realAlpha > 1f) {
                    realAlpha = 1f
                }
                this.alpha = realAlpha


                val maxDist = originY - dragTopDest
                val curDist = topview.top - dragTopDest
                var scaleRatio = 1f
                val distanceRatio = curDist.toFloat() / maxDist.toFloat()

                if (curDist > 0) {
                    scaleRatio = MIN_SCALE_RATIO + (MAX_SCALE_RATIO - MIN_SCALE_RATIO) * (1 - distanceRatio)
                }
                this.scaleX = scaleRatio
                this.scaleY = scaleRatio
            }
//            var alpha = (originY - topview!!.top) * 0.01f
//            if (alpha > 1) {
//                alpha = 1f
//            }
//            bottomview!!.alpha = alpha
//            val maxDistance = originY - dragTopDest
//            val currentDistance = topview!!.top - dragTopDest
//            var scaleRatio = 1f
//            val distanceRatio = currentDistance.toFloat() / maxDistance
//            if (currentDistance > 0) {
//                scaleRatio = MIN_SCALE_RATIO + (MAX_SCALE_RATIO - MIN_SCALE_RATIO) * (1 - distanceRatio)
//            }
//            bottomview!!.scaleX = scaleRatio
//            bottomview!!.scaleY = scaleRatio
        }
    }


    companion object {
        private const val MIN_SCALE_RATIO = 0.5f
        private const val MAX_SCALE_RATIO = 1.0f
        private const val DECELERATE_THRESHOLD = 120
        private const val STATE_CLOSE = 1
        private const val STATE_EXPANDED = 2
        private const val DRAG_SWITCH_DISTANCE_THRESHOLD = 100
        private const val DRAG_SWITCH_VEL_THRESHOLD = 800

    }
}