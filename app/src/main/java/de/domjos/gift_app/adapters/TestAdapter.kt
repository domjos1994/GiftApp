package de.domjos.gift_app.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import de.domjos.gift_app.R
import de.domjos.gift_app.fragments.*
import java.util.*

class TestAdapter : FragmentPagerAdapter {
    private var fragments: LinkedList<TestPageFragment>
    private var context: Context

    constructor(fm: FragmentManager, context: Context) : super(fm) {
        this.context = context
        fragments = LinkedList<TestPageFragment>()
        fragments.add(TestPage1Fragment())
        fragments.add(TestPage2Fragment())
        fragments.add(TestPage3Fragment())
        fragments.add(TestPage4Fragment())
        fragments.add(TestPage5Fragment())
        fragments.add(TestPage6Fragment())
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

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}