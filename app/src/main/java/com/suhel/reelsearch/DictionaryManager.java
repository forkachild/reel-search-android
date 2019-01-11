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

package com.suhel.reelsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import com.suhel.reelsearch.utils.RxUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DictionaryManager {

    private final List<String> mWords = new ArrayList<>();
    private volatile boolean mIsLoaded = false;
    private Context mContext;

    public DictionaryManager(@NonNull Context context) {
        mContext = context;
    }

    private void clearWords() {
        synchronized (mWords) {
            mWords.clear();
        }
    }

    private void addWord(@NonNull String word) {
        synchronized (mWords) {
            mWords.add(word);
        }
    }

    public boolean isLoaded() {
        return mIsLoaded;
    }

    public Completable loadDictionary() {
        return Completable.fromRunnable(() -> {

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(mContext.getResources().openRawResource(R.raw.words)));

            String word;

            try {

                clearWords();

                while ((word = br.readLine()) != null) {
                    addWord(word.toLowerCase());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            mIsLoaded = true;

        }).compose(RxUtils.composeCompletable());
    }

    public Single<List<String>> query(@NonNull String startsWith) {
        if (!mIsLoaded || startsWith.isEmpty()) {
            return Single.just(new ArrayList<>());
        } else {
            return Observable.fromIterable(mWords)
                    .filter(in -> in.startsWith(startsWith))
                    .toList()
                    .compose(RxUtils.composeSingle());
        }
    }

}
