/*
 * MIT License
 *
 * Copyright (c) 2019 Suhel Chakraborty
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.suhel.library;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * This applies a gradient of alpha to the children
 * <p>
 * The further away the child is from the center, the lighter it gets
 */
public class AlphaChildTransformer implements CenteredLayoutManager.ChildTransformer {

    @Override
    public void onApplyTransform(@NonNull View child, int index, int screenPosition, float centerOffset) {
        child.setAlpha(Math.max(1.0f - (1.2f * Math.abs(centerOffset)), 0.0f));
    }

}
