package com.example.clothesapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.clothesapp.R
import com.example.clothesapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {


    private lateinit var binding: FragmentSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_signup, container, false)

        binding.setLifecycleOwner(this)

        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumber)

        binding.button.setOnClickListener {
            getUsersNumber(it)
        }

        return binding.root
    }

    private fun getUsersNumber(view: View)
    {
        val phoneNumber = binding.countryCodePicker.fullNumberWithPlus
        if(phoneNumber != null)
        {
            val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            action.phoneNumber = phoneNumber
            view.findNavController().navigate(action)
        }
        else
        {
            binding.phoneNumber.error = "Kindly enter a valid phone number"
            binding.phoneNumber.requestFocus()
        }
    }


}