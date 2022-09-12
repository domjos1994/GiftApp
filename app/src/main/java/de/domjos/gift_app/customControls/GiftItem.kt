package de.domjos.gift_app.customControls

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import de.domjos.gift_app.Helper
import de.domjos.gift_app.R
import java.lang.Exception

class GiftItem : ConstraintLayout {
    private lateinit var lblHeader: TextView
    private lateinit var lblDescription: TextView
    private lateinit var lblKeyword: TextView
    private lateinit var lblTarget: TextView
    private lateinit var lblNegative: TextView
    private lateinit var lblBible: TextView

    constructor(context: Context) : super(context) {
        this.initView()
        getAttributes(null, context)
    }

    constructor(context: Context, attr: AttributeSet? = null): super(context, attr) {
        initView()
        getAttributes(attr, context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):   super(context, attrs, defStyleAttr) {
        initView()
        getAttributes(attrs, context)
    }

    @SuppressLint("Recycle")
    private fun getAttributes(attrs: AttributeSet?, context: Context) {
        try {
            val a = context.obtainStyledAttributes(attrs, R.styleable.GiftItem)
            var points: Int = 0
            if(a.hasValue(R.styleable.GiftItem_points)) {
                points = a.getInt(R.styleable.GiftItem_points, 0)
            }
            if (a.hasValue(R.styleable.GiftItem_header)) {
                val format: String = ""
                lblHeader.text = a.getString(R.styleable.GiftItem_header)
            }
            if (a.hasValue(R.styleable.GiftItem_description)) {
                val content: Spanned? = a.getString(R.styleable.GiftItem_description)
                    ?.let { Helper.showHtml(it) }
                lblDescription.text = content
            }
            if (a.hasValue(R.styleable.GiftItem_keyword)) {
                lblKeyword.text = a.getString(R.styleable.GiftItem_keyword)
            }
            if (a.hasValue(R.styleable.GiftItem_target)) {
                lblTarget.text = a.getString(R.styleable.GiftItem_target)
            }
            if (a.hasValue(R.styleable.GiftItem_negative)) {
                lblNegative.text = a.getString(R.styleable.GiftItem_negative)
            }
            if (a.hasValue(R.styleable.GiftItem_bible)) {
                lblBible.text = a.getString(R.styleable.GiftItem_bible)
            }
        } catch (ex: Exception) {}
    }

    private fun initView() {
        val rootView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.gift_item, this, true)

        lblHeader = rootView.findViewById(R.id.lblHeader)
        lblDescription = rootView.findViewById(R.id.lblDescription)
        lblKeyword = rootView.findViewById(R.id.lblKeywordContent)
        lblTarget = rootView.findViewById(R.id.lblTargetContent)
        lblNegative = rootView.findViewById(R.id.lblNegativeContent)
        lblBible = rootView.findViewById(R.id.lblBibleContent)
    }
}