package com.lutfullahkabalak.instakotlinapp.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lutfullahkabalak.instakotlinapp.R

class MessageFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_message,container,false)
        return view

    }
}