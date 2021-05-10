package com.github.mrbean355.android.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Severity.WARNING
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage")
class BaseActivityDetector : Detector(), SourceCodeScanner {

    companion object {

        // An 'Issue' describes the Lint issue we want to report.
        val ISSUE = Issue.create(
                id = "BaseActivityDetector",
                briefDescription = "Extending the wrong base activity",
                explanation = "The base activity class should be extended because it has important stuff in it.",
                category = CORRECTNESS,
                priority = 7,
                severity = WARNING,
                implementation = Implementation(BaseActivityDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )

        private const val ANDROID_ACTIVITY = "android.app.Activity"
        private const val BASE_ACTIVITY = "com.github.mrbean355.android.lint.BaseActivity"

    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        // Return the source code elements you want to check.
        // In this case, we want to check class definitions.
        return listOf(UClass::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {

            // Called when visiting a class definition.
            override fun visitClass(node: UClass) {
                // We don't care about classes that don't ever extend the base Activity class.
                // They aren't activities and can extend anything they want.
                if (!context.evaluator.extendsClass(node, ANDROID_ACTIVITY)) {
                    return
                }

                // If the class extends Activity but not our BaseActivity, report the issue.
                if (!context.evaluator.extendsClass(node, BASE_ACTIVITY)) {
                    val parentClass = node.uastSuperTypes.first()

                    context.report(ISSUE, context.getNameLocation(node), "Extending the wrong base class", fix()
                            .name("Replace with BaseActivity")
                            .replace()
                            .range(context.getLocation(parentClass))
                            .pattern(".*")
                            .with(BASE_ACTIVITY)
                            .shortenNames()
                            .build()
                    )
                }
            }
        }
    }
}