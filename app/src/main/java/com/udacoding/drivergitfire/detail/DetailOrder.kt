package com.udacoding.drivergitfire.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.network.myFirebaseDatabase
import com.udacoding.drivergitfire.utama.HomeActivity
import com.udacoding.drivergitfire.utama.home.model.Booking
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class DetailOrder : AppCompatActivity(), OnMapReadyCallback {

    var mMap: GoogleMap? = null
    var booking: Booking? = null
    var key: String? = null
    var status: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        booking = intent.getSerializableExtra("booking") as Booking
        status = intent.getIntExtra("status",0)

        homeAwal.text = booking?.lokasiAwal
        homeTujuan.text = booking?.lokasiTujuan
        homeWaktudistance.text = booking?.jarak
        homeprice.text = booking?.harga

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        myFirebaseDatabase.bookingRef().orderByChild("tanggal").equalTo(booking?.tanggal)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (issue in p0.children) {
                        key = issue.key
                    }
                }

            })

        if(status == 2){
            homebuttonnext.text = "Complete Order"
        }

        homebuttonnext.onClick {
            if (booking?.status == 1) {
                takeOrder()
            } else if (booking?.status == 2) {
                completeOrder()
            }
        }

    }

    private fun completeOrder() {
        myFirebaseDatabase.bookingRef().child(key ?: "").child("status").setValue(4)
        startActivity<HomeActivity>("fragment" to 4)
    }

    private fun takeOrder() {
        myFirebaseDatabase.bookingRef().child(key ?: "").child("status").setValue(2)
        myFirebaseDatabase.bookingRef().child(key ?: "").child("driver")
            .setValue(FirebaseAuth.getInstance().currentUser?.uid)
        startActivity<HomeActivity>("fragment" to 2)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
//        create marker
        val lokasiAwal = LatLng(booking?.latAwal ?: 0.0, booking?.lonAwal ?: 0.0)
        val lokasiTujuan = LatLng(booking?.latTujuan ?: 0.0, booking?.lonTujuan ?: 0.0)

        mMap?.addMarker(MarkerOptions().position(lokasiAwal).title("awal").snippet(booking?.lokasiAwal))
        mMap?.addMarker(MarkerOptions().position(lokasiTujuan).title("Tujuan").snippet(booking?.lokasiTujuan))

        val mapBounds = LatLngBounds.builder()

        mapBounds.include(lokasiAwal)
        mapBounds.include(lokasiTujuan)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()

        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds.build(), width, height, padding))
    }
}
