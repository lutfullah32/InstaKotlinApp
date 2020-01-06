package com.lutfullahkabalak.instakotlinapp.Login

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.lutfullahkabalak.instakotlinapp.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
                    if (start+before >=10){
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

                loginRoot.visibility=View.GONE
                loginContainer.visibility=View.VISIBLE
                var transaction=supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,TelefonKoduGirFragment())
                transaction.addToBackStack("TelefonKoduGirFranmentEklendi")
                transaction.commit()


            }else{
                loginRoot.visibility=View.GONE
                loginContainer.visibility=View.VISIBLE
                var transaction=supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,EmailGirisYontemiFragment())
                transaction.addToBackStack("EmailGirisYontemiFranmentEklendi")
                transaction.commit()
            }
        }
    }

    override fun onBackPressed() {
        loginRoot.visibility=View.VISIBLE
        super.onBackPressed()
    }
}
