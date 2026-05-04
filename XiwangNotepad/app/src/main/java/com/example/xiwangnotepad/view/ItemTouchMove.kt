package com.example.xiwangnotepad.view

import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.xiwangnotepad.R
import kotlin.math.abs

/**   
 * 包名称： com.example.xiwangnotepad
 * 类名称：ItemTouchMoveListener
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-02 22:58
 *
 */
interface ItemTouchMoveListener {
    fun onItemMove(fromPosition:Int,toPosition:Int): Boolean
    fun onItemRemove(position:Int): Boolean
    fun onItemComplete()
}
class ItemTouchHelperCallback(val moveListener: ItemTouchMoveListener) : ItemTouchHelper.Callback(){
    override fun getMovementFlags(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder
    ): Int {
        val dragFlags= ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags=  ItemTouchHelper.LEFT
        val flags=makeMovementFlags(dragFlags,swipeFlags)
        return flags
    }
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
    override fun onMove(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        if(p1.itemViewType!=p2.itemViewType){
            return false
        }
        return moveListener.onItemMove(p1.bindingAdapterPosition,p2.bindingAdapterPosition)
    }

    override fun onSwiped(
        p0: RecyclerView.ViewHolder,
        p1: Int
    ) {
        moveListener.onItemRemove(p0.bindingAdapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(viewHolder!=null) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.purple_200))
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE)
        viewHolder.itemView.alpha= 1.toFloat()
        recyclerView.post {
            moveListener.onItemComplete()
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val alpha: Float=1- abs(dX)/viewHolder.itemView.width
        if(actionState== ItemTouchHelper.ACTION_STATE_SWIPE){
            viewHolder.itemView.alpha=alpha
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}


