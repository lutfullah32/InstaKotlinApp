package com.lutfullahkabalak.instakotlinapp.Login


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_telefon_kodu_gir.*
import kotlinx.android.synthetic.main.fragment_telefon_kodu_gir.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class TelefonKoduGirFragment : Fragment() {

    var gelenTelNo = ""

    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var verificationID = ""

    var gelenKod = ""

    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_telefon_kodu_gir, container, false)

        progressBar = view.progressBarRegister

        view.tvKullaniciTelNo.setText(gelenTelNo)

        setupCallBack()

        view.btnTelKodIleri.setOnClickListener {
            if (gelenKod.equals(view.etOnayKodu.text.toString())){
                EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriGonder(gelenTelNo,null,verificationID,gelenKod,false))
                var transaction= activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,KayitFragment())
                transaction.addToBackStack("kayitFranmentEklendi")
                transaction.commit()
            }else{
                Toast.makeText(activity,"Kod yanlış",Toast.LENGTH_SHORT).show()
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            gelenTelNo, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this.activity!!, // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks





        return view
    }

    private fun setupCallBack() {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                gelenKod = credential.smsCode.toString()
                Log.println(Log.INFO,"SMSKOD","GELEN KOD : "+gelenKod)
                progressBar.visibility = View.INVISIBLE

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.println(Log.ERROR,"SMSHATA","HATA MESAJI : "+p0.message)
                Toast.makeText(activity,p0.message,Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                progressBar.visibility = View.VISIBLE
                verificationID = verificationId
                Log.println(Log.INFO,"VERIFICATION","VERIFICATION ID : "+verificationID)


            }
        }


    }

    @Subscribe (sticky = true)
    internal fun onTelefonEvent(kayitBilgileri:EventbusDataEvents.KayitBilgileriGonder){
        gelenTelNo = kayitBilgileri.telNo!!
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
