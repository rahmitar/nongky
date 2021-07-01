package com.noobee.nongky.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.noobee.nongky.R
import com.noobee.nongky.databinding.FragmentHelpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelpFragment : Fragment() {

    private lateinit var helpFragmentBinding : FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        helpFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_help,container,false)
        return helpFragmentBinding.root
    }
}