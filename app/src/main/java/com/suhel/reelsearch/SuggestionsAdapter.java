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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.suhel.reelsearch.databinding.ItemSuggestionBinding;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {

    @NonNull
    private LayoutInflater inflater;
    private List<String> data;

    public SuggestionsAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private String getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuggestionViewHolder(ItemSuggestionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class SuggestionViewHolder extends RecyclerView.ViewHolder {

        private ItemSuggestionBinding binding;

        SuggestionViewHolder(@NonNull ItemSuggestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull String value) {
            binding.tvSuggestion.setText(value);
        }

    }

}
