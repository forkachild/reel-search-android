

# Reel Search for Android

![Release](https://jitpack.io/v/forkachild/reel-search-android.svg?style=flat-square)

## Watch it in action

<img src="https://github.com/forkachild/reel-search-android/blob/master/screen.gif" alt="Screencast" width="250"/>

## Add to Gradle

Add this to your project level `build.gradle` file

<pre>
repositories {
  jcenter()
  <b>maven {  url "https://jitpack.io"  }</b>
}
</pre>

And then add this to your module level `build.gradle` file

<pre>
dependencies {
  <b>implementation "com.github.forkachild:reel-search-android:${latest-version}"</b>
}
</pre>

## How it works

The design is highly inspired from [RAMReel] by [@Ramotion], brilliant work guys.

It is based around a custom [LayoutManager] named [CenteredLayoutManager] which provides **top** and **bottom** offsets for the scrolling suggestions in a [RecyclerView]. All of it is conveniently enclosed in the [ReelSearchView] which coordinates with its children to realise the reel effect.

I am writing a detailed blog about how it was created from scratch and will post the link soon.

## How to setup

The [ReelSearchView] is a [ViewGroup] and it can contain exactly **2** views. The first being a [RecyclerView] or any subclass of it, and the second being an [EditText] or any subclass of it.

### Add it to a layout

```xml
<android.support.design.widget.CoordinatorLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"  
  android:layout_height="match_parent"
  android:background="@color/colorWhite"  
  tools:context=".SuggestionsActivity">  

  ...

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

  ...

</android.support.design.widget.CoordinatorLayout>
```

### Create a layout for an item for the [RecyclerView]
```xml
<TextView
  xmlns:android="http://schemas.android.com/apk/res/android"
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

  - Get current selected position from `ReelSearchView.getSelection()`
  - Add/Remove `OnSelectionChangedListener` with corresponding add/remove methods in
  both `ReelSearchView` and `CenteredLayoutManager`

## Thanks!

P.S You can always buy me a beer or a coffee through [PayPal]

[RAMReel]: https://github.com/Ramotion/reel-search
[@Ramotion]: https://github.com/Ramotion
[LayoutManager]: https://developer.android.com/reference/android/support/v7/widget/RecyclerView.LayoutManager
[CenteredLayoutManager]: https://github.com/forkachild/reel-search-android/blob/master/library/src/main/java/com/suhel/library/CenteredLayoutManager.java
[RecyclerView]: https://developer.android.com/reference/android/support/v7/widget/RecyclerView
[ReelSearchView]: https://github.com/forkachild/reel-search-android/blob/master/library/src/main/java/com/suhel/library/ReelSearchView.java
[ViewGroup]: https://developer.android.com/reference/android/view/ViewGroup
[EditText]: https://developer.android.com/reference/android/widget/EditText
[SuggestionsAdapter]: https://github.com/forkachild/reel-search-android/blob/master/app/src/main/java/com/suhel/reelsearch/SuggestionsAdapter.java
[PayPal]: https://paypal.me/suhelchakraborty
