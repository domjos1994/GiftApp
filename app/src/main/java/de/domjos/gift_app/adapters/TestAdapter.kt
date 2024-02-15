package de.domjos.gift_app.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.fragments.*
import de.domjos.gift_app.fragments.testPageImpl.*
import java.util.*

class TestAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private var context: Context, change: Question.OnChange)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragments: LinkedList<TestPageFragment> = LinkedList<TestPageFragment>()

    init {
        fragments.add(TestPage1Fragment())
        fragments.add(TestPage2Fragment())
        fragments.add(TestPage3Fragment())
        fragments.add(TestPage4Fragment())
        fragments.add(TestPage5Fragment())
        fragments.add(TestPage6Fragment())

        for(i in 0..5) {
            fragments[i].setChange(change)
        }
    }

    fun setChange(change: Question.OnChange) {
        for(i in 0..5) {
            fragments[i].setChange(change)
        }
        change.change()
    }

    fun save() {
        for(i in 0..5) {
            fragments[i].save(this.context)
        }
    }

    fun reset() {
        fragments.forEach { item ->
            item.reset()
        }
    }

    fun getUnsetCount(position: Int): Int {
        return fragments[position].countUnsetQuestions()
    }

    fun getResults(): LinkedList<Int> {
        val results: LinkedList<Int> = LinkedList<Int>()
        for(i in 0..14) {
            results.add(0)
        }

        fragments.forEach {
            val list = it.getResult()
            for(i in 0..14) {
                results[i] = results[i] + list[i]
            }
        }
        return results
    }
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}