package com.github.mrbean355.android.lint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// 'MainActivity' should be highlighted with a warning.
// You can ALT+Enter on the warning to apply the quick-fix.
// If you don't see it, try building the project (hammer button).
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}