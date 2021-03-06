package com.udacoding.drivergitfire.waiting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.network.myFirebaseDatabase
import com.udacoding.drivergitfire.utama.HomeActivity
import com.udacoding.drivergitfire.utama.home.model.Booking
import com.udacoding.drivergitfire.utils.Constan
import kotlinx.android.synthetic.main.activity_waiting_driver.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class WaitingDriverActivity : AppCompatActivity() {

    var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_driver)

        pulsator.start()

        key = intent.getStringExtra(Constan.Key)
        myFirebaseDatabase.bookingRef().child(key ?: "").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                    val booking = p0.getValue(Booking::class.java)
                    if(booking?.status == 2){
                        pulsator.stop()
                        toast("Taking Driver Berhasil")
                    }
            }

        })

        buttonCancelBooking.onClick {
            myFirebaseDatabase.bookingRef().child(key ?: "").child("status").setValue(3)
            toast("Cancel Success")
            startActivity<HomeActivity>()
        }

    }


}
