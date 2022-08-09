package de.domjos.gift_app.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import de.domjos.gift_app.R
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.fragments.*
import de.domjos.gift_app.fragments.testPageImpl.*
import java.util.*

class TestAdapter(fm: FragmentManager, private var context: Context, change: Question.OnChange) : FragmentPagerAdapter(fm) {
    private var fragments: LinkedList<TestPageFragment> = LinkedList<TestPageFragment>()

    init {
        fragments.add(TestPage1Fragment(change))
        fragments.add(TestPage2Fragment(change))
        fragments.add(TestPage3Fragment(change))
        fragments.add(TestPage4Fragment(change))
        fragments.add(TestPage5Fragment(change))
        fragments.add(TestPage6Fragment(change))
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when(position) {
            0 -> return context.getString(R.string.test_page1)
            1 -> return context.getString(R.string.test_page2)
            2 -> return context.getString(R.string.test_page3)
            3 -> return context.getString(R.string.test_page4)
            4 -> return context.getString(R.string.test_page5)
            5 -> return context.getString(R.string.test_page6)
        }
        return ""
    }

    fun save() {
        for(i in 0..5) {
            fragments[i].save(this.context)
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
        results.sort()
        return results
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}