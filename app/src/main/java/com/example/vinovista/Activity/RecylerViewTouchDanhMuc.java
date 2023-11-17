package com.example.vinovista.Activity;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecylerViewTouchDanhMuc extends ItemTouchHelper.SimpleCallback {
    private ItemTouchListenerHelper listenerHelper;

    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    public RecylerViewTouchDanhMuc(int dragDirs, int swipeDirs,  ItemTouchListenerHelper listenerHelper) {
        super(dragDirs, swipeDirs);
        this.listenerHelper = listenerHelper;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listenerHelper!=null)
        {
            listenerHelper.onSwiped(viewHolder);
        }
    }
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null)
        {
            View layout = ((DanhMucAdapter.DanhMucViewHolder)viewHolder).layout;
            getDefaultUIUtil().onSelected(layout);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View layout = ((DanhMucAdapter.DanhMucViewHolder)viewHolder).layout;
        getDefaultUIUtil().onDraw(c,recyclerView,layout,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View layout = ((DanhMucAdapter.DanhMucViewHolder)viewHolder).layout;
        getDefaultUIUtil().onDrawOver(c,recyclerView,layout,dX,dY,actionState,isCurrentlyActive);

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View layout = ((DanhMucAdapter.DanhMucViewHolder)viewHolder).layout;
        getDefaultUIUtil().clearView(layout);
    }
}
