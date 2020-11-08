package com.github.mrbean355.android.lint

import com.android.tools.lint.client.api.IssueRegistry

// Referenced in build.gradle.
@Suppress("UnstableApiUsage", "unused")
class MyIssueRegistry : IssueRegistry() {

    // Return our custom issues.
    override val issues = listOf(
            BaseActivityDetector.ISSUE
    )
}