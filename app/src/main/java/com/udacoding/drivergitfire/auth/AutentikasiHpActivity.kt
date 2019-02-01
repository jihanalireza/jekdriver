package com.udacoding.drivergitfire.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.network.myFirebaseDatabase
import com.udacoding.drivergitfire.utama.HomeActivity
import kotlinx.android.synthetic.main.activity_autentikasi_hp.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class AutentikasiHpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autentikasi_hp)
        var key = intent.getStringExtra("key")

        authentikasisubmit.onClick {
            val userRef = myFirebaseDatabase.userRef()
            userRef.child(key).child("hp").setValue(authentikasinomorhp.text.toString())
            startActivity<HomeActivity>()
        }


    }
}
