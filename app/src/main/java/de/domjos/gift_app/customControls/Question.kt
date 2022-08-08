package de.domjos.gift_app.customControls

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import de.domjos.gift_app.R
import java.lang.Exception

class Question : ConstraintLayout {
    private lateinit var lblQuestion: TextView
    private lateinit var lblUnset: TextView
    private lateinit var lblNever: TextView
    private lateinit var lblMuchRarely: TextView
    private lateinit var lblRarely: TextView
    private lateinit var lblSometimes: TextView
    private lateinit var lblOften: TextView
    private lateinit var lblMuchOften: TextView
    private lateinit var lblAlways: TextView
    private var choice: Int = -1
    private var text: String = ""

    constructor(context: Context) : super(context) {
        this.initView()
        getAttributes(null, context)
    }

    constructor(context: Context, attr: AttributeSet? = null): super(context, attr) {
        initView()
        getAttributes(attr, context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):   super(context, attrs, defStyleAttr) {
        initView()
        getAttributes(attrs, context)
    }

    @SuppressLint("Recycle")
    private fun getAttributes(attrs: AttributeSet?, context: Context) {
        try {
            val a = context.obtainStyledAttributes(attrs, R.styleable.Question)
            if (a.hasValue(R.styleable.Question_text)) {
                text = a.getText(R.styleable.Question_text) as String
                lblQuestion.text = text
            }

            if(a.hasValue(R.styleable.Question_choice)) {
                setChoice(a.getInt(R.styleable.Question_choice, -1))
            }
        } catch (ex: Exception) {}
    }

    fun getChoice(): Int {
        return choice
    }

    fun setChoice(choice: Int) {
        this.choice = choice

        setView(lblUnset, false)
        setView(lblNever, false)
        setView(lblMuchRarely, false)
        setView(lblRarely, false)
        setView(lblSometimes, false)
        setView(lblOften, false)
        setView(lblMuchOften, false)
        setView(lblAlways, false)
        when(choice) {
            -1 ->setView(lblUnset, true)
            0 -> setView(lblNever, true)
            1 -> setView(lblMuchRarely, true)
            2 -> setView(lblRarely, true)
            3 -> setView(lblSometimes, true)
            4 -> setView(lblOften, true)
            5 -> setView(lblMuchOften, true)
            6 -> setView(lblAlways, true)
        }
    }

    fun getText(): String {
        return text
    }

    private fun initView() {
        val rootView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.question, this, true)

        lblQuestion = rootView.findViewById(R.id.lblQuestion)
        lblUnset = rootView.findViewById(R.id.lblUnset)
        lblNever = rootView.findViewById(R.id.lblNever)
        lblMuchRarely = rootView.findViewById(R.id.lblMuchRarely)
        lblRarely = rootView.findViewById(R.id.lblRarely)
        lblSometimes = rootView.findViewById(R.id.lblSometimes)
        lblOften = rootView.findViewById(R.id.lblOften)
        lblMuchOften = rootView.findViewById(R.id.lblMuchOften)
        lblAlways = rootView.findViewById(R.id.lblAlways)

        lblUnset.setOnClickListener {
            setView(lblUnset, true)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = -1
        }

        lblNever.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, true)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = 0
        }

        lblMuchRarely.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, true)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = 1
        }

        lblRarely.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, true)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = 2
        }

        lblSometimes.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, true)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = 3
        }

        lblOften.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, true)
            setView(lblMuchOften, false)
            setView(lblAlways, false)
            choice = 4
        }

        lblMuchOften.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, true)
            setView(lblAlways, false)
            choice = 5
        }

        lblAlways.setOnClickListener {
            setView(lblUnset, false)
            setView(lblNever, false)
            setView(lblMuchRarely, false)
            setView(lblRarely, false)
            setView(lblSometimes, false)
            setView(lblOften, false)
            setView(lblMuchOften, false)
            setView(lblAlways, true)
            choice = 6
        }
    }

    @Suppress("DEPRECATION")
    private fun setView(lbl: TextView, state: Boolean) {
        if(state) {
            lbl.setBackgroundResource(R.drawable.question_area_selected)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                lbl.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
            } else {
                lbl.setTextColor(resources.getColor(R.color.white))
            }
        } else {
            lbl.background = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                lbl.setTextColor(resources.getColor(R.color.black, resources.newTheme()))
            } else {
                lbl.setTextColor(resources.getColor(R.color.black))
            }
        }
    }
}