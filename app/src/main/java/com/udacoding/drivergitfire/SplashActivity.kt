package com.udacoding.drivergitfire

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.udacoding.drivergitfire.login.LoginActivity
import com.udacoding.drivergitfire.utama.HomeActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mAuth =  FirebaseAuth.getInstance()

        Handler().postDelayed(Runnable {

            if(mAuth?.currentUser?.email?.isEmpty() ?: true){
                startActivity<LoginActivity>()
            }
            else startActivity<HomeActivity>()
        },2000)
    }
}
