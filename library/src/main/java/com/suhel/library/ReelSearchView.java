package com.suhel.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A {@link ViewGroup} which coordinates with an underlying
 * {@link RecyclerView} and an {@link EditText} to create the illusion
 * of a reel search using the essential {@link CenteredLayoutManager}
 */
public class ReelSearchView extends ViewGroup {

    /**
     * Stores the {@link RecyclerView} that displays the suggestions list
     */
    private RecyclerView mRecyclerView;

    /**
     * Stores the {@link EditText} where the user types the query
     */
    private EditText mEditText;

    /**
     * Stores the {@link android.support.v7.widget.RecyclerView.LayoutManager}
     * of {@link RecyclerView} which is responsible for the reel effect
     */
    private CenteredLayoutManager mLayoutManager;

    /**
     * Stores the {@link android.support.v7.widget.SnapHelper} used to
     * make the items stick to the center
     */
    private LinearSnapHelper mLinearSnapHelper;

    public ReelSearchView(Context context) {
        super(context);
    }

    public ReelSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReelSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        // Ensuring child count and throwing exception otherwise
        if (getChildCount() != 2) {
            throw new IllegalStateException("Must have exactly 2 children");
        }

        View temp = getChildAt(0);

        if (temp instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) temp;
        } else {
            throw new IllegalStateException("First child must be a RecyclerView or its descendant");
        }

        temp = getChildAt(1);

        if (temp instanceof EditText) {
            mEditText = (EditText) temp;
        } else {
            throw new IllegalStateException("Second child must be an EditText or its descendant");
        }

        // Initializing all members
        mLayoutManager = new CenteredLayoutManager();
        mLayoutManager.setChildTransformer(new AlphaChildTransformer());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLinearSnapHelper = new LinearSnapHelper();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Let the default measurement take place
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Extract the width and height finally decided
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        // Make measure specs for measuring children
        final int measureSpecWidthExactly = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        final int measureSpecHeightExactly = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        final int measureSpecHeightWrap = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        // Measure and assign dimensions to children
        mRecyclerView.measure(measureSpecWidthExactly, measureSpecHeightExactly);
        mEditText.measure(measureSpecWidthExactly, measureSpecHeightWrap);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int left = getPaddingLeft();
        final int top = getPaddingTop();
        final int right = getMeasuredWidth() - getPaddingRight();
        final int bottom = getMeasuredHeight() - getPaddingBottom();

        // Layout the RecyclerView child spanning the whole view
        mRecyclerView.layout(left, top, right, bottom);

        // Calculations
        final int halfEditTextHeight = mEditText.getMeasuredHeight() / 2;
        final int centerY = (top + bottom) / 2;

        // Layout the EditText at the center
        mEditText.layout(left, centerY - halfEditTextHeight, right, centerY + halfEditTextHeight);
    }

    /**
     * @return The {@link CenteredLayoutManager} used underneath
     */
    public CenteredLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    /**
     * Sets the {@link CenteredLayoutManager.OnSelectionChangedListener}
     * of the associated {@link CenteredLayoutManager}.
     *
     * @param listener The {@link CenteredLayoutManager.OnSelectionChangedListener} to be set
     */
    public void setOnSelectionChangedListener(@Nullable CenteredLayoutManager.OnSelectionChangedListener listener) {
        getLayoutManager().setOnSelectionChangedListener(listener);
    }

    /**
     * Returns the position of item highlighted in the middle of the
     * screen from the associated {@link CenteredLayoutManager}
     *
     * @return Item index starting from 0
     */
    public int getSelection() {
        return getLayoutManager().getSelection();
    }

}
