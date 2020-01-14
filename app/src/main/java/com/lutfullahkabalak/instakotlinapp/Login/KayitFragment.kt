package com.lutfullahkabalak.instakotlinapp.Login


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.EventbusDataEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * A simple [Fragment] subclass.
 */
class KayitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kayit, container, false)
    }

    @Subscribe(sticky = true)
    internal fun onEmailEvent(emailAdres: EventbusDataEvents.EmailGonder){
        var gelenEmail=emailAdres.email
        Log.e("eventtest","gelen tel no : " + gelenEmail)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }


}
