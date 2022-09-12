package de.domjos.gift_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import de.domjos.gift_app.customControls.GiftItem
import de.domjos.gift_app.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var all = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateQuestions(false)

        _binding?.cmdResultAll?.setOnClickListener {
            all = !all

            updateQuestions(all)
        }
    }

    fun updateQuestions(all: Boolean) {
        val ll: LinearLayout = _binding!!.llMain

        if(all) {
            for(i in 0..14) {
                try {
                    this.arguments?.getInt(i.toString())
                        ?.let { (ll.getChildAt(i) as GiftItem).setPoints(it) }
                    ll.getChildAt(i).visibility = VISIBLE
                } catch (ex: Exception) {}
            }
        } else {
            for(i in 0..14) {
                try {
                    this.arguments?.getInt(i.toString())
                        ?.let { (ll.getChildAt(i) as GiftItem).setPoints(it) }
                    ll.getChildAt(i).visibility = GONE
                } catch (ex: Exception) {}
            }

            var item1 = -1
            var item2 = -1
            var item3 = -1
            var index1 = -1
            var index2 = -1
            var index3 = -1
            for(i in 0..14) {
                val item = this.arguments?.getInt(i.toString()) ?: continue

                if(item1 <= item) {
                    index3 = index2
                    item3 = item2
                    index2 = index1
                    item2 = item1
                    index1 = i
                    item1 = item
                } else if(item2 <= item) {
                    index3 = index2
                    item3 = item2
                    index2 = i
                    item2 = item
                } else if(item3 <= item) {
                    index3 = i
                    item3 = item
                }
            }

            if(index1 != -1) {
                ll.getChildAt(index1).visibility = VISIBLE
            }
            if(index2 !=-1) {
                ll.getChildAt(index2).visibility = VISIBLE
            }
            if(index3 != -1) {
                ll.getChildAt(index3).visibility = VISIBLE
            }
        }
    }
}