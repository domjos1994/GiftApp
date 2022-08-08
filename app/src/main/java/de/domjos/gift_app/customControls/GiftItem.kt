package de.domjos.gift_app.customControls

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
            if (a.hasValue(R.styleable.GiftItem_header)) {
                lblHeader.text = a.getString(R.styleable.GiftItem_header)
            }
            if (a.hasValue(R.styleable.GiftItem_description)) {
                lblDescription.text = a.getString(R.styleable.GiftItem_description)
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