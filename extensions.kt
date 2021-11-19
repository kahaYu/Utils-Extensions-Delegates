// Rounds double to next integer.
fun Double.roundToNextInt(): Int {
    when {
        this == 0.0 -> return 0
        this >= 0.0 -> {
            if (this > this.toInt()) {
                var result = this.toInt() + 1
                return result
            } else {
                var result = this.toInt()
                return result
            }
        }
        else -> throw Exception("Input for function roundToNextInt can't be less than 0")
    }
}

// Hides system UI in Activity. Doesn't support versions lower than API23 Marshmallow. 
fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE // When bars is hidden, user can access them by swipe. 
            //No way to remove them permanently. At least by swipe, but they should present.
            it.hide(WindowInsets.Type.navigationBars()) // Hide navigation bar. Status bar remains. 
            // To make status bar transparent, we open current themes xml:
            // <item name="android:statusBarColor" tools:targetApi="l">@android:color/transparent</item>
            // This regulates color of text of status bar. If LightStatusBar this means the text will be dark and vice versa.
            // <item name="android:windowLightStatusBar" tools:targetApi="23">true</item>
            // This makes content of layout not to appear behind status bar.
            // <item name="android:fitsSystemWindows">false</item>
            // This appoints backgroud of layout and it will appear behind status bar. 
            // <item name="android:windowBackground">@color/iconOnClick</item>

        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Methods above absent in Android lower than R. So for lower version this else block exists. 
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE // Removes navigation bar. Status bar presents. 
                //or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // This removes navigation & status bars. Should stick with line below.
                //View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // This regulates color of text of status bar. 
                // If LightStatusBar this means the text will be dark and vice versa. On R version and higher we can do this in xml themes.
                
                // This joint of 3 lines keep the app content behind the bars even if user swipes them up. This pointed in docs, but it is displayed weird on me. 
                //or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}

// Hides system UI in DialogFragment.
fun DialogFragment.hideSystemUI() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        dialog?.window?.insetsController?.let {
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE // When bars is hidden, user can access them by swipe. 
            //No way to remove them permanently. At least by swipe, but they should present.
            it.hide(WindowInsets.Type.navigationBars()) // Hide navigation bar. Status bar remains. 
            // To make status bar transparent, we open current themes xml:
            // <item name="android:statusBarColor" tools:targetApi="l">@android:color/transparent</item>
            // This regulates color of text of status bar. If LightStatusBar this means the text will be dark and vice versa.
            // <item name="android:windowLightStatusBar" tools:targetApi="23">true</item>
            // This makes content of layout not to appear behind status bar.
            // <item name="android:fitsSystemWindows">false</item>
            // This appoints backgroud of layout and it will appear behind status bar. 
            // <item name="android:windowBackground">@color/iconOnClick</item>
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        dialog?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE // Removes navigation bar. Status bar presents. 
                //or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // This removes navigation & status bars. Should stick with line below.
                //View.SYSTEM_UI_FLAG_FULLSCREEN 
                // This joint of 3 lines keep the app content behind the bars even if user swipes them up. This pointed in docs, but it is displayed weird on me. 
                //or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}



