// Creating new shape. It will be background of dialog.
// name - dialog_fragment_background
<?xml version="1.0" encoding="utf-8"?>

<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/white" />
    <corners android:radius="30dp" />
    <padding
        android:bottom="5dp"
        android:left="5dp"
        android:right="5dp"
        android:top="5dp" />
</shape>
  
  // Make original background transparent. Put in in onViewCreated.
  dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
  
  // Apply new shape to background
      <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:background="@drawable/dialog_fragment_background">
