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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import com.suhel.reelsearch.databinding.ActivitySuggestionsBinding;
import com.suhel.reelsearch.utils.RxUtils;
import io.reactivex.disposables.CompositeDisposable;

public class SuggestionsActivity extends AppCompatActivity {

    private ActivitySuggestionsBinding mBinding;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private DictionaryManager mDictionaryManager;
    private SuggestionsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_suggestions);
        mDictionaryManager = new DictionaryManager(this);
        mAdapter = new SuggestionsAdapter(this);
        mBinding.lstSuggestions.setAdapter(mAdapter);
        mBinding.btnSelect.setOnClickListener(v -> {
            final int selectedPosition = mBinding.reelSearch.getLayoutManager().getSelection();

            Snackbar.make(mBinding.btnSelect,
                    "Selected position " + selectedPosition + " item " + mAdapter.getItem(selectedPosition),
                    Snackbar.LENGTH_SHORT).show();
        });
        mBinding.txtQuery.setFilters(new InputFilter[]{
                (source, start, end, dest, dstart, dend) -> source.toString().toLowerCase().trim()
        });
        mBinding.reelSearch.setOnSelectionChangedListener((prevSelection, newSelection) -> {
            Log.e("Selection", "Changed to " + newSelection + " from " + prevSelection);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposable.add(mDictionaryManager.loadDictionary()
                .doOnSubscribe(d -> {
                    mBinding.txtQuery.setEnabled(false);
                    mBinding.txtQuery.setHint("Loading dictionary");
                })
                .doOnComplete(() -> {
                    mBinding.txtQuery.setEnabled(true);
                    mBinding.txtQuery.setHint("Start typing");
                })
                .subscribe(() -> {
                }, Throwable::printStackTrace));

        mDisposable.add(RxUtils.onTextChange(mBinding.txtQuery)
                .filter(in -> mDictionaryManager.isLoaded())
                .concatMapSingle(in -> mDictionaryManager.query(in))
                .doOnNext(in -> mBinding.btnSelect.setEnabled(!in.isEmpty()))
                .subscribe(mAdapter::setData, Throwable::printStackTrace));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mDisposable.clear();
    }

}
