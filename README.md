

# Reel Search for Android

[![Release](https://jitpack.io/v/forkachild/reel-search-android.svg?style=flat-square)](https://jitpack.io/#forkachild/reel-search-android)

## Watch it in action

<img src="https://github.com/forkachild/reel-search-android/blob/master/screen.gif" alt="Screencast" width="250"/>

## Add to Gradle

Add this to your project level `build.gradle` file

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

And then add this to your module level `build.gradle` file

```gradle
dependencies {
    implementation "com.github.forkachild:reel-search-android:${latest-version}"
}
```

## How it works

The design is highly inspired from [RAMReel] by [@Ramotion], brilliant work guys.

It is based around a custom `LayoutManager` named [CenteredLayoutManager] which provides **top** and **bottom** offsets for the scrolling suggestions in a `RecyclerView`. All of it is conveniently enclosed in the [ReelSearchView] which coordinates with its children to realise the reel effect.

A detailed blog on the making of this is available at [Reel Search and LayoutManager: An Android journey].

## How to setup

The [ReelSearchView] is a `ViewGroup` and it can contain exactly **2** views. The first being a `RecyclerView` or any subclass of it, and the second being an `EditText` or any subclass of it.

### Add it to a layout

```xml
<android.support.design.widget.CoordinatorLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorWhite"
    tools:context=".SuggestionsActivity">

    <com.suhel.library.ReelSearchView
        android:id="@+id/reelSearch"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
  
        <android.support.v7.widget.RecyclerView
            android:id="@+id/lstSuggestions"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:overScrollMode="never"
            android:paddingStart="8dp"
            android:paddingEnd="8dp" />
  
        <EditText
            android:id="@+id/txtQuery"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@color/colorTransparent"
            android:fontFamily="sans-serif-light"
            android:hint="Start typing"
            android:paddingStart="16dp"
            android:paddingEnd="16dp"
            android:textColor="@color/colorBlack"
            android:textColorHint="@color/colorGrey"
            android:textSize="32sp" />

    </com.suhel.library.ReelSearchView>

</android.support.design.widget.CoordinatorLayout>
```

### Create a layout for an item for the `RecyclerView`
```xml
<TextView
    android:id="@+id/tvSuggestion"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fontFamily="sans-serif-light"
    android:paddingStart="8dp"
    android:paddingTop="2dp"
    android:paddingEnd="8dp"
    android:paddingBottom="2dp"
    android:textColor="@color/colorGrey"
    android:textSize="32sp" />
```

### Create an adapter

I am not going to guide through this part as it is out of scope. Please refer to [SuggestionsAdapter] or google.

### Use it in the Activity

```java

ReelSearchView reelSearch = findViewById(R.id.reelSearch);
RecyclerView lstSuggestions = findViewById(R.id.lstSuggestions);
EditText txtQuery = findViewById(R.id.txtQuery);

SuggestionsAdapter adapter = new SuggestionsAdapter(this);
TextWatcher textWatcher = new TextWatcher() {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Fetch list here and assign data to adapter
    }
    
    @Override
    public void afterTextChanged(Editable s) {
    }

}

txtQuery.addTextChangedListener(textWatcher);
lstSuggestions.setAdapter(adapter);

```
### Features

#### Get current selected position

The item selected, in the middle underlying the query `EditText`, can be found through a simple method

```java
int position = ReelSearchView.getSelection()
```

#### Set OnSelectionChangedListener

```java
ReelSearchView.setOnSelectionChangedListener(listener);
```

#### Child transformer

What if I told you that the soft gradient of alpha is configurable. `ChildTransformer` is an interface settable through `setChildTransformer()` call.

It contains a method

```java
void onApplyTransform(@NonNull View child,
                              @IntRange(from = 0, to = Integer.MAX_VALUE) int index,
                              @IntRange(from = 0, to = Integer.MAX_VALUE) int screenPosition,
                              @FloatRange(from = -1.0f, to = 1.0f) float centerOffset);
```

which gets called on each child when they are laid out. [AlphaChildTransformer] is already attached as a `ChildTransformer` which accomplishes the alpha gradient. You are only limited by your imagination

## Thanks!

P.S You can always buy me a beer or a coffee through [PayPal]

## LICENSE

```
MIT License

Copyright (c) 2019 Suhel Chakraborty

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

[RAMReel]: https://github.com/Ramotion/reel-search
[@Ramotion]: https://github.com/Ramotion
[CenteredLayoutManager]: https://github.com/forkachild/reel-search-android/blob/master/library/src/main/java/com/suhel/library/CenteredLayoutManager.java
[ReelSearchView]: https://github.com/forkachild/reel-search-android/blob/master/library/src/main/java/com/suhel/library/ReelSearchView.java
[SuggestionsAdapter]: https://github.com/forkachild/reel-search-android/blob/master/app/src/main/java/com/suhel/reelsearch/SuggestionsAdapter.java
[AlphaChildTransformer]: https://github.com/forkachild/reel-search-android/blob/master/library/src/main/java/com/suhel/library/AlphaChildTransformer.java
[Reel Search and LayoutManager: An Android journey]: https://medium.com/@suhelchakraborty/reel-search-and-layoutmanager-an-android-journey-e2f925c8410f
[PayPal]: https://paypal.me/suhelchakraborty
