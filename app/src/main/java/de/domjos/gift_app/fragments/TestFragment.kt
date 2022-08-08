package de.domjos.gift_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import de.domjos.gift_app.R
import de.domjos.gift_app.adapters.TestAdapter
import de.domjos.gift_app.databinding.FragmentTestBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbl: TabLayout? = _binding?.tblQuestions

        val viewPager = _binding?.viewPager
        val testAdapter = context?.applicationContext?.let { TestAdapter(childFragmentManager, it) }
        viewPager?.adapter = context?.let { testAdapter }
        viewPager?.offscreenPageLimit = 6
        tbl?.setupWithViewPager(viewPager)

        for(i in 0 until tbl?.tabCount!!) {
            val badge = tbl.getTabAt(i)?.orCreateBadge
            badge?.number = 15
        }

        tbl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val badge = tab?.orCreateBadge
                badge?.number = tab?.position?.let { testAdapter?.getUnsetCount(it) }!!
            }
        })

        _binding?.cmdReport?.setOnClickListener {
            Toast.makeText(context, testAdapter?.getResults()?.joinToString(", "), Toast.LENGTH_LONG).show()

            val list: LinkedList<Int>? = testAdapter?.getResults()
            val args = Bundle()
            for(i in 0 until list!!.size) {
                args.putInt(i.toString(), list[i])
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}