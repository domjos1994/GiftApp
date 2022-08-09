package de.domjos.gift_app.fragments.testPageImpl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.domjos.gift_app.customControls.Question
import de.domjos.gift_app.databinding.FragmentTestPage1Binding
import de.domjos.gift_app.fragments.TestPageFragment

class TestPage1Fragment(change: Question.OnChange) : TestPageFragment("page1", change) {
    private var _binding: FragmentTestPage1Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTestPage1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.cl = _binding?.constraintLayout
        super.onViewCreated(view, savedInstanceState)
    }
}