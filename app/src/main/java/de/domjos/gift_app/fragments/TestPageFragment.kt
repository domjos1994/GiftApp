package de.domjos.gift_app.fragments

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import de.domjos.gift_app.customControls.Question
import java.util.*

open class TestPageFragment() : Fragment() {
    protected var cl: ConstraintLayout? = null

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
}