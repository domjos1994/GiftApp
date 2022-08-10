package de.domjos.gift_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.domjos.gift_app.services.DailyVerse
import de.domjos.gift_app.R
import de.domjos.gift_app.databinding.FragmentMainBinding
import de.domjos.gift_app.services.TaskRunner
import de.domjos.gift_app.services.TaskRunner.Callback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val taskRunner = TaskRunner()
            val impl = object : Callback<DailyVerse.JsonVerse?> {
                override fun onComplete(result: DailyVerse.JsonVerse?) {
                    if(result != null) {
                        binding.lblDailyVerseHeader.text = result.verse?.details?.reference
                        binding.lblDailyVerseContent.text = result.verse?.details?.text
                        binding.lblDailyVerseNotice.text = result.verse?.notice
                    } else {
                        binding.lblDailyVerseHeader.text = getString(R.string.main_header)
                        binding.lblDailyVerseContent.text = getString(R.string.main_content)
                    }
                }
            }
            taskRunner.executeAsync(DailyVerse(), impl)
        } catch (ex: Exception) {
            binding.lblDailyVerseHeader.text = ex.message
            binding.lblDailyVerseContent.text = ex.toString()
        }

        binding.cmdMainStart.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}