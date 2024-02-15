package de.domjos.gift_app.fragments.testPageImpl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.domjos.gift_app.databinding.FragmentTestPage5Binding
import de.domjos.gift_app.fragments.TestPageFragment

class TestPage5Fragment : TestPageFragment("page5") {
    private var _binding: FragmentTestPage5Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTestPage5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.cl = _binding?.constraintLayout
        super.onViewCreated(view, savedInstanceState)
    }
}