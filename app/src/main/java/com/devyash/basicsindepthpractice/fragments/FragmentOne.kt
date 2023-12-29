package com.devyash.basicsindepthpractice.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.devyash.basicsindepthpractice.R
import com.devyash.basicsindepthpractice.ThirdActivity
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

        (activity as ThirdActivity).sharedViewModel.sharedData.observe(viewLifecycleOwner, Observer {
            binding.tvInitialText.text = it.toString()
        })

        binding.btnChangeData.setOnClickListener {
            if(binding.etText.text.isEmpty()){
                Toast.makeText(requireContext(), "Please Write Something", Toast.LENGTH_SHORT).show()
            }else{
                (activity as ThirdActivity).sharedViewModel.sharedData.value = binding.etText.text.toString()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}