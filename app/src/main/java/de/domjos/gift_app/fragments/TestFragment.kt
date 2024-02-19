package de.domjos.gift_app.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.domjos.gift_app.Helper
import de.domjos.gift_app.R
import de.domjos.gift_app.adapters.TestAdapter
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.databinding.FragmentTestBinding
import de.domjos.gift_app.services.Settings
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TestFragment : Fragment() {
    private lateinit var change: Question.OnChange
    private var _binding: FragmentTestBinding? = null
    private lateinit var testAdapter: TestAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbl: TabLayout = _binding?.tblQuestions!!

        val viewPager = _binding?.viewPager!!

        this.change = object: Question.OnChange {
            override fun change() {
                var hasQuestions = false
                for(i in 0 until tbl.tabCount) {
                    val count = testAdapter.getUnsetCount(i)
                    if(count != 0) {
                        val badge = tbl.getTabAt(i)?.orCreateBadge
                        badge?.number = count
                        hasQuestions = true
                    } else {
                        tbl.getTabAt(i)?.removeBadge()
                    }
                }
                testAdapter.save()
                binding.cmdReport.isEnabled = !hasQuestions
            }
        }

        testAdapter = context?.applicationContext?.let { TestAdapter(childFragmentManager, lifecycle, it, this.change)}!!
        viewPager.adapter = context?.let { testAdapter }
        viewPager.offscreenPageLimit = 6


        for(i in 0 until tbl.tabCount) {
            val badge = tbl.getTabAt(i)?.orCreateBadge
            badge?.number = 15
        }

        TabLayoutMediator(tbl, viewPager) { tab, position ->
            val ctx = requireContext()
            tab.contentDescription = when(position) {
                0 -> ctx.getString(R.string.test_page1)
                1 -> ctx.getString(R.string.test_page2)
                2 -> ctx.getString(R.string.test_page3)
                3 -> ctx.getString(R.string.test_page4)
                4 -> ctx.getString(R.string.test_page5)
                5 -> ctx.getString(R.string.test_page6)
                else -> ""
            }
            tab.icon = when(position) {
                0 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_1)
                1 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_2)
                2 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_3)
                3 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_4)
                4 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_5)
                5 -> AppCompatResources.getDrawable(ctx, R.drawable.counter_6)
                else -> null
            }
        }.attach()

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

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
                menu.findItem(R.id.search).isVisible = false
                menu.findItem(R.id.action_impress).isVisible = false
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_reset -> {
                        Settings(requireContext()).reset()

                        Toast.makeText(requireContext(), R.string.reset_msg, Toast.LENGTH_LONG).show()

                        val childFragmentManager = childFragmentManager
                        val fragments = childFragmentManager.fragments
                        fragments.forEach { fItem ->
                            if(fItem is TestPageFragment) {
                                fItem.reset()
                            }
                            if(fItem is MainFragment) {
                                fItem.showButtonText()
                            }
                        }

                        return true
                    }
                    else -> {return false}
                }
            }
        }, viewLifecycleOwner)
    }

    fun reset() {
        testAdapter.reset()
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