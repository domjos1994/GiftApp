package de.domjos.gift_app.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import de.domjos.gift_app.services.Settings
import de.domjos.gift_app.customControls.Question
import java.util.*

open class TestPageFragment(private val page: String) : Fragment() {
    protected var cl: ConstraintLayout? = null
    private var change: Question.OnChange? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = Settings(view.context)
        var i = 0
        this.cl?.forEach {
            val item: Question = it as Question
            item.setChoice(settings.getSetting("$page$i", -1))
            if(this.change != null) {
                item.setChange(this.change!!)
            }
            i++
        }
        this.change?.change()
    }

    fun setChange(change: Question.OnChange) {
        this.change = change
    }

    fun countUnsetQuestions(): Int {
        var counter = 0

        cl?.children?.forEach { item ->
            if((item as Question).getChoice()==-1) {
                counter++
            }
        }
        return counter
    }

    fun getResult(): LinkedList<Int> {
        val results: LinkedList<Int> = LinkedList<Int>()
        var counter = 0
        cl?.children?.forEach { item ->
            results.add((item as Question).getChoice())

            counter++
        }
        return results
    }

    fun save(context: Context) {
        val settings = Settings(context)
        var i = 0
        this.cl?.forEach {
            val item: Question = it as Question
            settings.saveSetting("$page$i", item.getChoice())

            i++
        }
    }
}