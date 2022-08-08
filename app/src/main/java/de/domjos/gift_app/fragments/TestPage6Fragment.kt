package de.domjos.gift_app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import de.domjos.gift_app.Settings
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.databinding.FragmentTestPage6Binding

class TestPage6Fragment : TestPageFragment() {
    private var _binding: FragmentTestPage6Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTestPage6Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.cl = _binding?.constraintLayout

        val settings: Settings = Settings(view.context)
        var i = 0
        super.cl?.forEach {
            val item: Question = it as Question
            item.setChoice(settings.getSetting("page6$i", -1))

            i++
        }
    }

    override fun save(context: Context) {
        val settings = Settings(context)
        var i = 0
        super.cl?.forEach {
            val item: Question = it as Question
            settings.saveSetting("page6$i", item.getChoice())

            i++
        }
    }
}