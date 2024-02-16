package de.domjos.gift_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.domjos.gift_app.Helper
import de.domjos.gift_app.services.DailyVerse
import de.domjos.gift_app.R
import de.domjos.gift_app.databinding.FragmentMainBinding
import de.domjos.gift_app.services.Settings
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
    private lateinit var taskRunner: TaskRunner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            taskRunner = TaskRunner()
            val impl = object : Callback<DailyVerse.JsonVerse?> {
                override fun onComplete(result: DailyVerse.JsonVerse?) {
                    if(result != null) {
                        binding.lblDailyVerseHeader.text = result.reference
                        binding.lblDailyVerseContent.text = Helper.showHtml(result.text)
                        binding.lblDailyVerseNotice.text = ""
                    } else {
                        binding.lblDailyVerseHeader.text = getString(R.string.main_header)
                        binding.lblDailyVerseContent.text = getString(R.string.main_content)
                    }
                }
            }
            taskRunner.executeAsync(this.context?.let { DailyVerse(it) }, impl)
        } catch (ex: Exception) {
            binding.lblDailyVerseHeader.text = ex.message
            binding.lblDailyVerseContent.text = ex.toString()
        }

        showButtonText()
        binding.cmdMainStart.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.cmdMainBible.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourthFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        taskRunner.cancel()
    }

    fun showButtonText() {
        val settings = Settings(requireContext())
        var hasChanges = false
        for (i in 1..6) {
            for (j in 1..15) {
                val result = settings.getSetting("page$i$j", -1)
                if(result != -1) {
                    hasChanges = true
                    break
                }
            }
        }
        if(hasChanges) {
            binding.cmdMainStart.text = requireContext().getString(R.string.main_continue)
        } else {
            binding.cmdMainStart.text = requireContext().getString(R.string.main_start)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}