package com.lutfullahkabalak.instakotlinapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.*
import com.lutfullahkabalak.instakotlinapp.Models.Users
import com.lutfullahkabalak.instakotlinapp.R
import com.lutfullahkabalak.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class RegisterActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {


    lateinit var manager:FragmentManager
    lateinit var mRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AppCenter.start(getApplication(), "f03070df-4b83-4823-b765-cb020f1d9409",
                  Analytics::class.java)

        mRef = FirebaseDatabase.getInstance().reference

        manager = supportFragmentManager
        manager.addOnBackStackChangedListener(this)
        init()

    }


    private fun init() {
        tvEposta.setOnClickListener {
            viewTelefon.visibility = View.GONE
            viewEposta.visibility=View.VISIBLE
            etGirisYontemi.setText("")
            etGirisYontemi.setHint("e-Posta")
            etGirisYontemi.inputType= InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            btnIleri.isEnabled = false
            btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
            btnIleri.setBackgroundResource(R.drawable.register_button)

            tvEposta.text

        }

        tvTelefon.setOnClickListener {
            viewTelefon.visibility = View.VISIBLE
            viewEposta.visibility= View.GONE
            etGirisYontemi.setText("")
            etGirisYontemi.setHint("Telefon")
            etGirisYontemi.inputType= InputType.TYPE_CLASS_PHONE

            btnIleri.isEnabled = false
            btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
            btnIleri.setBackgroundResource(R.drawable.register_button)

        }

        etGirisYontemi.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                if (etGirisYontemi.hint.equals("Telefon")){
                    if (android.util.Patterns.PHONE.matcher(etGirisYontemi.text).matches()){
                        btnIleri.isEnabled = true
                        btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.beyaz))
                        btnIleri.setBackgroundResource(R.drawable.register_button_aktif)
                    }else{
                        btnIleri.isEnabled = false
                        btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
                        btnIleri.setBackgroundResource(R.drawable.register_button)
                    }
                }else if (etGirisYontemi.hint.equals("e-Posta")){
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(etGirisYontemi.text).matches()){
                        btnIleri.isEnabled = true
                        btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.beyaz))
                        btnIleri.setBackgroundResource(R.drawable.register_button_aktif)
                    }else{
                        btnIleri.isEnabled = false
                        btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
                        btnIleri.setBackgroundResource(R.drawable.register_button)
                    }
                }

            }

        })

        btnIleri.setOnClickListener {
            if (etGirisYontemi.hint.equals("Telefon")){

                mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        var telVarmi = false
                        if (p0!!.getValue()!=null){
                            for (user in p0!!.children){
                                var okunanKullanici = user.getValue(Users::class.java)
                                if (okunanKullanici!!.phone_number!!.equals(etGirisYontemi.text.toString())){
                                    Toast.makeText(this@RegisterActivity,"Bu Telefon Kullanılıyor",Toast.LENGTH_LONG).show()
                                    telVarmi = true
                                    break
                                }
                            }
                            Log.i("TELVARMI","DURUM : "+telVarmi.toString())
                            if (!telVarmi){
                                loginRoot.visibility=View.GONE
                                loginContainer.visibility=View.VISIBLE
                                var transaction=supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.loginContainer,TelefonKoduGirFragment())
                                transaction.addToBackStack("TelefonKoduGirFranmentEklendi")
                                transaction.commit()

                                EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriGonder(etGirisYontemi.text.toString(),null,null,null,false))
                            }
                        }

                    }

                })




            }else{

                var emailVarmi = false

                mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        Log.i("Okuma","Veri tababnından veriler okunuyor")
                        if (p0!!.getValue() !=null){
                            for (user in p0.children){
                                var okunanKullanici = user.getValue(Users::class.java)
                                if (okunanKullanici!!.email.toString().equals(etGirisYontemi.text.toString())){
                                    Toast.makeText(this@RegisterActivity,"Bu Email Kullanılıyor",Toast.LENGTH_LONG).show()
                                    emailVarmi = true
                                    break
                                }
                            }

                            if (!emailVarmi){

                                loginRoot.visibility=View.GONE
                                loginContainer.visibility=View.VISIBLE
                                var transaction=supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.loginContainer,KayitFragment())
                                transaction.addToBackStack("EmailGirisYontemiFranmentEklendi")
                                transaction.commit()

                                EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriGonder(null,etGirisYontemi.text.toString(),null,null,true))

                            }
                        }

                    }

                })

            }
        }

        tvGirisYap.setOnClickListener {
            var intent = Intent(this@RegisterActivity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

    }

    //override fun onBackPressed() {
    //    loginRoot.visibility=View.VISIBLE
    //    super.onBackPressed()
    //}

    override fun onBackStackChanged() {
        var elemanSayisi = manager.backStackEntryCount

        if (elemanSayisi==0){
            loginRoot.visibility=View.VISIBLE
        }
    }
}
