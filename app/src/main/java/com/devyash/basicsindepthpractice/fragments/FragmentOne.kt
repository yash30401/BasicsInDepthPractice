package com.devyash.basicsindepthpractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devyash.basicsindepthpractice.R
import com.devyash.basicsindepthpractice.databinding.FragmentOneBinding


class FragmentOne : Fragment(R.layout.fragment_one) {

    private var  _binding:FragmentOneBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOneBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}