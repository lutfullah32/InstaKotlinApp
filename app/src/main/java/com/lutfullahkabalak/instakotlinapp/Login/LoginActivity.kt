package com.lutfullahkabalak.instakotlinapp.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.lutfullahkabalak.instakotlinapp.Models.Users
import com.lutfullahkabalak.instakotlinapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    lateinit var mRef : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference


        init()

    }

    fun init() {

        btnGirisYap.setOnClickListener {
            oturumAcacakKullaniciyiDenetle(etEmailTelefonUsername.text.toString(),etSifre.text.toString())
            Log.d("Buton","Butona Basıldı")
        }

    }

    fun oturumAcacakKullaniciyiDenetle(emailTelefonUsername: String, sifre: String) {
        mRef.child("users").orderByChild("email").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("HATA",p0.message )
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (ds in p0!!.children){

                    var okunanKullanici = ds.getValue(Users::class.java)
                    Log.d("Kullıcılar",okunanKullanici!!.user_name.toString() )
                    if (okunanKullanici!!.email!!.toString().equals(emailTelefonUsername)){
                        oturumAc(okunanKullanici,sifre,false)
                        break
                    }else if(okunanKullanici!!.user_name!!.toString().equals(emailTelefonUsername)){
                        oturumAc(okunanKullanici,sifre,false)
                        Log.d("KULLANICI","kullancı : "+okunanKullanici!!.user_name!!.toString() )
                        break
                    }else if(okunanKullanici!!.phone_number!!.toString().equals(emailTelefonUsername)){
                        oturumAc(okunanKullanici,sifre,true)
                        break
                    }
                }
            }

        })
    }

    fun oturumAc(user:Users,sifre:String,telefonlaGiris:Boolean){
        var girisYapacakEmail = ""

        if(telefonlaGiris){
            girisYapacakEmail = user.email_phone_number.toString()+"@kotlin.com"
        }else{
            girisYapacakEmail = user.email.toString()
        }

        mAuth.signInWithEmailAndPassword(girisYapacakEmail,sifre)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0!!.isSuccessful){
                        Toast.makeText(this@LoginActivity,"Oturum Açıldı : "+mAuth.currentUser!!.uid,Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@LoginActivity,"Kullanıcı Adı Şifre Hatalı",Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

    var watcher: TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}
