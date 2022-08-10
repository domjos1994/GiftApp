package de.domjos.gift_app.fragments

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import de.domjos.gift_app.R
import de.domjos.gift_app.adapters.TestAdapter
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.databinding.FragmentTestBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TestFragment : Fragment() {
    private lateinit var change: Question.OnChange
    private var _binding: FragmentTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbl: TabLayout? = _binding?.tblQuestions

        val viewPager = _binding?.viewPager

        var testAdapter: TestAdapter? = null

        this.change = object: Question.OnChange {
            override fun change() {
                if(testAdapter != null) {
                    var hasQuestions = false
                    for(i in 0 until tbl?.tabCount!!) {
                        val count = testAdapter?.getUnsetCount(i)!!
                        if(count != 0) {
                            val badge = tbl.getTabAt(i)?.orCreateBadge
                            badge?.number = count
                            hasQuestions = true
                        } else {
                            tbl.getTabAt(i)?.removeBadge()
                        }
                    }

                    binding.cmdReport.isEnabled = !hasQuestions
                }
            }
        }

       testAdapter = context?.applicationContext?.let { TestAdapter(childFragmentManager, it, this.change)}!!
        viewPager?.adapter = context?.let { testAdapter }
        viewPager?.offscreenPageLimit = 6
        tbl?.setupWithViewPager(viewPager)

        for(i in 0 until tbl?.tabCount!!) {
            val badge = tbl.getTabAt(i)?.orCreateBadge
            badge?.number = 15
        }

        val controller = findNavController()

        tbl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val count = tab?.position?.let { testAdapter.getUnsetCount(it) }!!
                if(count != 0) {
                    val badge = tbl.getTabAt(tab.position)?.orCreateBadge
                    badge?.number = count
                } else {
                    tbl.getTabAt(tab.position)?.removeBadge()
                }
            }
        })

        _binding?.cmdReport?.setOnClickListener {
            val list: LinkedList<Int> = testAdapter.getResults()
            val args = Bundle()
            for(i in 0 until list.size) {
                args.putInt(i.toString(), list[i])
            }
            controller.navigate(R.id.action_SecondFragment_to_ThirdFragment, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (_binding?.viewPager?.adapter as TestAdapter).save()
        _binding = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (this.binding.viewPager.adapter as TestAdapter).setChange(this.change)
    }
}