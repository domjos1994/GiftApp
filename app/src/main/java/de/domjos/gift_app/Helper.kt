package de.domjos.gift_app

import android.os.Build
import android.text.Html
import android.text.Spanned

class Helper {

    companion object {
        @Suppress("deprecation")
        fun showHtml(content: String): Spanned? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(content)
            }
        }
    }
}