package de.domjos.gift_app.customControls

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import de.domjos.gift_app.R

class Question : ConstraintLayout {

    constructor(context: Context) : super(context) {
        this.initView()
    }

    constructor(context: Context, attr: AttributeSet? = null): super(context, attr) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):   super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val rootView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.question, this, true)

    }
}