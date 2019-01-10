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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import com.suhel.library.AlphaChildTransformer;
import com.suhel.library.CenteredLayoutManager;
import com.suhel.reelsearch.databinding.ActivitySuggestionsBinding;
import com.suhel.reelsearch.utils.RxUtils;
import io.reactivex.disposables.CompositeDisposable;

public class SuggestionsActivity extends AppCompatActivity {

    private ActivitySuggestionsBinding binding;

    private CompositeDisposable disposable = new CompositeDisposable();
    private DictionaryManager dictionaryManager;
    private boolean isDictionaryLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suggestions);

        dictionaryManager = new DictionaryManager(this);

        disposable.add(dictionaryManager.loadDictionary()
                .doOnSubscribe(d -> {
                    binding.txtQuery.setEnabled(false);
                    binding.txtQuery.setHint("Loading dictionary");
                })
                .doOnComplete(() -> {
                    binding.txtQuery.setEnabled(true);
                    binding.txtQuery.setHint("Start typing");
                })
                .subscribe(() -> isDictionaryLoaded = true, Throwable::printStackTrace));

        final SuggestionsAdapter adapter = new SuggestionsAdapter(this);

        final CenteredLayoutManager centeredLayoutManager = new CenteredLayoutManager();
        centeredLayoutManager.setChildTransformer(new AlphaChildTransformer());

        new LinearSnapHelper().attachToRecyclerView(binding.lstSuggestions);

        binding.lstSuggestions.setLayoutManager(centeredLayoutManager);
        binding.lstSuggestions.setAdapter(adapter);

        disposable.add(RxUtils.onTextChange(binding.txtQuery)
                .filter(in -> isDictionaryLoaded)
                .concatMapSingle(in -> dictionaryManager.query(in))
                .subscribe(adapter::setData, Throwable::printStackTrace));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        disposable.clear();
    }

}
