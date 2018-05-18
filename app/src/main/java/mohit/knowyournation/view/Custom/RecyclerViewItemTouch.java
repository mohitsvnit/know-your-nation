package mohit.knowyournation.view.Custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import mohit.knowyournation.R;
import mohit.knowyournation.support.Helper;

/**
 * Created by mohit on 8/12/17.
 */

public class RecyclerViewItemTouch extends ItemTouchHelper.SimpleCallback {

    private OnItemTouchListener itemTouchListener;
    private Paint paint;
    private float MAX_SWIPE_LEFT = 100f;
    private Context context;

    public RecyclerViewItemTouch(int dragDirs, int swipeDirs, Context context) {
        super(dragDirs, swipeDirs);
        this.itemTouchListener = itemTouchListener;
        paint = new Paint();
        this.context = context;
    }

    public void setItemTouchListener(OnItemTouchListener itemTouchListener) {
        this.itemTouchListener = itemTouchListener;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(dX < 0){
            View itemView = viewHolder.itemView;
            float len = Math.abs(dX);
            len = 0.8f*len;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float maxLen = 0.7f*height;
            if(len >= maxLen){
                len = maxLen;
                dX = -(len/0.7f);
            }
            float width = Math.abs(dX);
            float sidePadding = (width-len)/2f;
            float heightPadding = (height/2f)-(len/2f);
            float top = itemView.getTop()+heightPadding;
            float bottom = itemView.getBottom()-heightPadding;
            float left = itemView.getRight()-width+sidePadding;
            float right = itemView.getRight()-sidePadding;


            paint.setColor(context.getResources().getColor(R.color.colorPrimary));
            c.drawRect(itemView.getRight()-width,itemView.getTop(),itemView.getRight(),itemView.getBottom(),paint);
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_details);
            RectF iconPlace = new RectF(left,top,right,bottom);
            c.drawBitmap(icon,null, iconPlace, null);

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Helper.log("Draw over");
    }

    private String ftos(int left) {
        return String.valueOf(left);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction == ItemTouchHelper.LEFT){
            Helper.log("Left");
            if(itemTouchListener != null){
                itemTouchListener.onSwiped(viewHolder,direction);
            }
        }
    }

    public interface OnItemTouchListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int dir);
    }
}
