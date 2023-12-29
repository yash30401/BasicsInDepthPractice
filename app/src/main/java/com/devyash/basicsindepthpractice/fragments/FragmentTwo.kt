package com.devyash.basicsindepthpractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devyash.basicsindepthpractice.R
import com.devyash.basicsindepthpractice.databinding.FragmentOneBinding
import com.devyash.basicsindepthpractice.databinding.FragmentTwoBinding

class FragmentTwo : Fragment(R.layout.fragment_two) {

    private var  _binding: FragmentTwoBinding?=null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentTwoBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}