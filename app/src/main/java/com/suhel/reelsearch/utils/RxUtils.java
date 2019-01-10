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

package com.suhel.reelsearch.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class RxUtils {

    private RxUtils() {

    }

    public static <U> CompletableTransformer composeCompletable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static <U> SingleTransformer<U, U> composeSingle() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static <U> ObservableTransformer<U, U> composeObservable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static <U> FlowableTransformer<U, U> composeFlowable() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static <U> MaybeTransformer<U, U> composeMaybe() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static Observable<String> onTextChange(@NonNull EditText editText) {
        return Observable.create(new TextWatcherObservable(editText));
    }

    private static class TextWatcherObservable implements ObservableOnSubscribe<String>, Disposable, TextWatcher {

        @NonNull
        private EditText mEditText;
        private ObservableEmitter<String> mEmitter;
        private boolean mIsDisposed;

        TextWatcherObservable(@NonNull EditText editText) {
            mEditText = editText;
            mIsDisposed = false;
        }

        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            mEmitter = emitter;
            mEditText.addTextChangedListener(this);
        }

        @Override
        public void dispose() {
            mEditText.removeTextChangedListener(this);
            mIsDisposed = true;
        }

        @Override
        public boolean isDisposed() {
            return mIsDisposed;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEmitter.onNext(s.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

}
