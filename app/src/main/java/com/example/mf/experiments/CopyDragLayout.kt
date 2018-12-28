package com.example.mf.experiments


import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

/**
 * 尽量考虑了所有操作系统版本的分辨率适配
 * Created by xmuSistone on 2016/9/18.
 */
class CopyDragLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val bottomDragVisibleHeight: Int // 滑动可见的高度
    private val bototmExtraIndicatorHeight: Int // 底部指示器的高度
    private var dragTopDest = 0 // 顶部View滑动的目标位置
    private var downState: Int = 0 // 按下时的状态

    private val mDragHelper: ViewDragHelper
    private val moveDetector: GestureDetectorCompat
    private var mTouchSlop = 5 // 判定为滑动的阈值，单位是像素
    private var originX: Int = 0
    private var originY: Int = 0 // 初始状态下，topView的坐标
    private var bottomView: View? = null
    private var topView: View? = null // FrameLayout的两个子View

    private var gotoDetailListener: GotoDetailListener? = null

    /**
     * 获取当前状态
     */
    private val currentState: Int
        get() {
            val state: Int
            if (Math.abs(topView!!.top - dragTopDest) <= mTouchSlop) {
                state = STATE_EXPANDED
            } else {
                state = STATE_CLOSE
            }
            return state
        }

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CopyDragLayout, 0, 0)
        bottomDragVisibleHeight = a.getDimension(R.styleable.CopyDragLayout_bottomDragVisibleHeight, 0f).toInt()
        bototmExtraIndicatorHeight = a.getDimension(R.styleable.CopyDragLayout_bototmExtraIndicatorHeight, 0f).toInt()
        a.recycle()

        mDragHelper = ViewDragHelper
            .create(this, 10f, DragHelperCallback())
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP)
        moveDetector = GestureDetectorCompat(context, MoveDetector())
        moveDetector.setIsLongpressEnabled(false) // 不处理长按事件

        // 滑动的距离阈值由系统提供
        val configuration = ViewConfiguration.get(getContext())
        mTouchSlop = configuration.scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomView = getChildAt(0)
        topView = getChildAt(1)

        topView!!.setOnClickListener {
            // 点击回调
            val state = currentState
            if (state == STATE_CLOSE) {
                // 点击时为初始状态，需要展开
                if (mDragHelper.smoothSlideViewTo(topView!!, originX, dragTopDest)) {
                    ViewCompat.postInvalidateOnAnimation(this@CopyDragLayout)
                }
            } else {
                // 点击时为展开状态，直接进入详情页
                gotoDetailActivity()
            }
        }
    }

    // 跳转到下一页
    private fun gotoDetailActivity() {
        if (null != gotoDetailListener) {
            gotoDetailListener!!.gotoDetail()
        }
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            if (changedView === topView) {
                processLinkageView()
            }
        }

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return if (child === topView) {
                true
            } else false
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val currentTop = child.top
            if (top > child.top) {
                // 往下拉的时候，阻力最小
                return currentTop + (top - currentTop) / 2
            }

            val result: Int
            if (currentTop > DECELERATE_THRESHOLD * 3) {
                result = currentTop + (top - currentTop) / 2
            } else if (currentTop > DECELERATE_THRESHOLD * 2) {
                result = currentTop + (top - currentTop) / 4
            } else if (currentTop > 0) {
                result = currentTop + (top - currentTop) / 8
            } else if (currentTop > -DECELERATE_THRESHOLD) {
                result = currentTop + (top - currentTop) / 16
            } else if (currentTop > -DECELERATE_THRESHOLD * 2) {
                result = currentTop + (top - currentTop) / 32
            } else if (currentTop > -DECELERATE_THRESHOLD * 3) {
                result = currentTop + (top - currentTop) / 48
            } else {
                result = currentTop + (top - currentTop) / 64
            }
            return result
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return child.left
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return 600
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return 600
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            var finalY = originY
            if (downState == STATE_CLOSE) {
                // 按下的时候，状态为：初始状态
                if (originY - releasedChild.top > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel < -DRAG_SWITCH_VEL_THRESHOLD) {
                    finalY = dragTopDest
                }
            } else {
                // 按下的时候，状态为：展开状态
                val gotoBottom =
                    releasedChild.top - dragTopDest > DRAG_SWITCH_DISTANCE_THRESHOLD || yvel > DRAG_SWITCH_VEL_THRESHOLD
                if (!gotoBottom) {
                    finalY = dragTopDest

                    // 如果按下时已经展开，又向上拖动了，就进入详情页
                    if (dragTopDest - releasedChild.top > mTouchSlop) {
                        gotoDetailActivity()
                        postResetPosition()
                        return
                    }
                }
            }

            if (mDragHelper.smoothSlideViewTo(releasedChild, originX, finalY)) {
                ViewCompat.postInvalidateOnAnimation(this@CopyDragLayout)
            }
        }
    }


    private fun postResetPosition() {
        this.postDelayed({ topView!!.offsetTopAndBottom(dragTopDest - topView!!.top) }, 500)
    }

    /**
     * 顶层ImageView位置变动，需要对底层的view进行缩放显示
     */
    private fun processLinkageView() {
        if (topView!!.top > originY) {
            bottomView!!.alpha = 0f
        } else {
            var alpha = (originY - topView!!.top) * 0.01f
            if (alpha > 1) {
                alpha = 1f
            }
            bottomView!!.alpha = alpha
            val maxDistance = originY - dragTopDest
            val currentDistance = topView!!.top - dragTopDest
            var scaleRatio = 1f
            val distanceRatio = currentDistance.toFloat() / maxDistance
            if (currentDistance > 0) {
                scaleRatio = MIN_SCALE_RATIO + (MAX_SCALE_RATIO - MIN_SCALE_RATIO) * (1 - distanceRatio)
            }
            bottomView!!.scaleX = scaleRatio
            bottomView!!.scaleY = scaleRatio
        }
    }

    internal inner class MoveDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent, dx: Float,
            dy: Float
        ): Boolean {
            // 拖动了，touch不往下传递
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop
        }
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (!changed) {
            return
        }

        super.onLayout(changed, left, top, right, bottom)

        originX = topView!!.x.toInt()
        originY = topView!!.y.toInt()
        dragTopDest = bottomView!!.bottom - bottomDragVisibleHeight - topView!!.measuredHeight
    }

    /* touch事件的拦截与处理都交给mDraghelper来处理 */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 1. detector和mDragHelper判断是否需要拦截
        val yScroll = moveDetector.onTouchEvent(ev)
        var shouldIntercept = false
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }

        // 2. 触点按下的时候直接交给mDragHelper
        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            downState = currentState
            mDragHelper.processTouchEvent(ev)
        }

        return shouldIntercept && yScroll
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // bottomMarginTop高度的计算，还是需要有一个清晰的数学模型才可以。
        // 实现的效果，是topView.top和bottomView.bottom展开前、与展开后都整体居中
        val bottomMarginTop =
            (bottomDragVisibleHeight + topView!!.measuredHeight / 2 - bottomView!!.measuredHeight / 2) / 2 - bototmExtraIndicatorHeight
        val lp1 = bottomView!!.layoutParams as FrameLayout.LayoutParams
        lp1.setMargins(0, bottomMarginTop, 0, 0)
        bottomView!!.layoutParams = lp1
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // 统一交给mDragHelper处理，由DragHelperCallback实现拖动效果
        try {
            mDragHelper.processTouchEvent(e)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return true
    }

    fun setGotoDetailListener(gotoDetailListener: GotoDetailListener) {
        this.gotoDetailListener = gotoDetailListener
    }

    interface GotoDetailListener {
        fun gotoDetail()
    }

    companion object {
        private val DECELERATE_THRESHOLD = 120
        private val DRAG_SWITCH_DISTANCE_THRESHOLD = 100
        private val DRAG_SWITCH_VEL_THRESHOLD = 800

        private val MIN_SCALE_RATIO = 0.5f
        private val MAX_SCALE_RATIO = 1.0f

        private val STATE_CLOSE = 1
        private val STATE_EXPANDED = 2
    }
}
