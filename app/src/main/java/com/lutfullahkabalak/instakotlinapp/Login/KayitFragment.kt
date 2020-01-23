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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.lutfullahkabalak.instakotlinapp.Models.Users

import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.fragment_kayit.*
import kotlinx.android.synthetic.main.fragment_kayit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * A simple [Fragment] subclass.
 */
class KayitFragment : Fragment() {

    var telNo=""
    var verificationID = ""
    var gelenKod = ""
    var gelenEmail = ""
    var emailIleKayitIslemi=true

    lateinit var mAuth:FirebaseAuth
    lateinit var mRef:DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater!!.inflate(R.layout.fragment_kayit, container, false)

        mAuth= FirebaseAuth.getInstance()
        if(mAuth.currentUser != null){
            mAuth.signOut()
        }
        mRef = FirebaseDatabase.getInstance().reference

        view.etAdSoyad.addTextChangedListener(watcher)
        view.etSifre.addTextChangedListener(watcher)
        view.etKullaniciAdi.addTextChangedListener(watcher)
        progressBar = view.pbKullaniciKayit

        view.btnGiris.setOnClickListener {

            var userNameVarmi = false

            mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0!!.getValue()!=null){
                        for (item in p0!!.children){
                            var user = item.getValue(Users::class.java)
                            if (user!!.user_name!!.equals(view.etKullaniciAdi.text.toString())){
                                Toast.makeText(activity,"Kullanıcı adı mevcut",Toast.LENGTH_LONG).show()
                                userNameVarmi = true
                                break
                            }
                        }
                        if (!userNameVarmi){

                            if (emailIleKayitIslemi){
                                var sifre=view.etSifre.text.toString()
                                var kullaniciAdi=view.etKullaniciAdi.text.toString()
                                var adSoyad = view.etAdSoyad.text.toString()

                                progressBar.visibility = View.VISIBLE

                                mAuth.createUserWithEmailAndPassword(gelenEmail, sifre)
                                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if (p0!!.isSuccessful){
                                                Toast.makeText(activity,"Oturum açıldı",Toast.LENGTH_SHORT).show()
                                                var userID = mAuth.currentUser!!.uid
                                                var kaydedilecekKullanici= Users(gelenEmail,sifre,kullaniciAdi,adSoyad,"","",userID)
                                                mRef.child("users").child(userID).setValue(kaydedilecekKullanici)
                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if (p0!!.isSuccessful){
                                                                Toast.makeText(activity,"Kullanıcı kaydedildi",Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility = View.INVISIBLE
                                                            }else{
                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                                        override fun onComplete(p0: Task<Void>) {
                                                                            if (p0.isSuccessful){
                                                                                Toast.makeText(activity,"Kullanıcı kaydedilmedi. Tekrar deneyin.",Toast.LENGTH_SHORT).show()
                                                                                progressBar.visibility = View.INVISIBLE
                                                                            }
                                                                        }

                                                                    })
                                                            }
                                                        }

                                                    })


                                            }else{
                                                Toast.makeText(activity,"Oturum açılamadı : "+p0.exception,Toast.LENGTH_SHORT).show()
                                                progressBar.visibility = View.INVISIBLE
                                            }
                                        }

                                    })
                            }else{
                                var sifre=view.etSifre.text.toString()
                                var sahteMail=telNo+"@kotlin.com"

                                var kullaniciAdi=view.etKullaniciAdi.text.toString()
                                var adSoyad = view.etAdSoyad.text.toString()
                                mAuth.createUserWithEmailAndPassword(sahteMail, sifre)
                                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if (p0!!.isSuccessful){
                                                Toast.makeText(activity,"Oturum telefon no ile oturum açıldı. UID : "+mAuth.currentUser!!.uid,Toast.LENGTH_SHORT).show()
                                                var userID = mAuth.currentUser!!.uid
                                                var kaydedilecekKullanici= Users("",sifre,kullaniciAdi,adSoyad,telNo,sahteMail,userID)
                                                mRef.child("users").child(userID).setValue(kaydedilecekKullanici)
                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if (p0!!.isSuccessful){
                                                                Toast.makeText(activity,"Kullanıcı kaydedildi",Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility = View.INVISIBLE
                                                            }else{

                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                                        override fun onComplete(p0: Task<Void>) {
                                                                            if (p0.isSuccessful){
                                                                                Toast.makeText(activity,"Kullanıcı kaydedilmedi. Tekrar deneyin.",Toast.LENGTH_SHORT).show()
                                                                                progressBar.visibility = View.INVISIBLE
                                                                            }
                                                                        }

                                                                    })
                                                            }
                                                        }

                                                    })

                                            }else{
                                                Toast.makeText(activity,"Oturum açılamadı : "+p0.exception,Toast.LENGTH_SHORT).show()
                                                progressBar.visibility = View.INVISIBLE
                                            }
                                        }

                                    })
                            }

                        }
                    }


                }

            })




        }

        return view
    }

    var watcher : TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.length>5){

                if (etAdSoyad.text.toString().length>5 && etSifre.text.toString().length>5 && etKullaniciAdi.text.toString().length>5){
                    btnGiris.isEnabled = true
                    btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.beyaz))
                    btnGiris.setBackgroundResource(R.drawable.register_button_aktif)
                }else{
                    btnGiris.isEnabled = false
                    btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.sonukmavi))
                    btnGiris.setBackgroundResource(R.drawable.register_button)
                }

            }else{
                btnGiris.isEnabled = false
                btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.sonukmavi))
                btnGiris.setBackgroundResource(R.drawable.register_button)
            }
        }

    }

    @Subscribe(sticky = true)
    internal fun onKayitEvent(kayitBilgileri: EventbusDataEvents.KayitBilgileriGonder){

        emailIleKayitIslemi = kayitBilgileri.emailKayit
        if (kayitBilgileri.emailKayit){

            gelenEmail = kayitBilgileri.email!!
        }else{
            telNo = kayitBilgileri.telNo!!
            verificationID= kayitBilgileri.verificationID!!
            gelenKod = kayitBilgileri.code!!

            Toast.makeText(activity,"Gelen Kod : "+gelenKod+"\n VerificationID : "+verificationID,Toast.LENGTH_SHORT).show()
        }

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
