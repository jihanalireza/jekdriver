package com.udacoding.drivergitfire.insertposition

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.udacoding.drivergitfire.network.myFirebaseDatabase
import java.sql.Driver

class TrackingService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()

        requestLocation()
    }

    private fun requestLocation() {
//        get koordinate terbaru
        var request = LocationRequest()
//        update location per detik
        request.interval = 1000

//        setting akurasi
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val client = LocationServices.getFusedLocationProviderClient(this@TrackingService)

        val permission = ContextCompat.checkSelfPermission(this@TrackingService,
            android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission == PackageManager.PERMISSION_GRANTED){
            client.requestLocationUpdates(request,object :  LocationCallback(){
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    val lat = p0?.lastLocation?.latitude
                    val lon = p0?.lastLocation?.longitude

                    val uid = FirebaseAuth.getInstance().currentUser?.uid

                    myFirebaseDatabase.driverRef().orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                          for (issue in p0.children){
                              val key = issue.key
                              p0.ref.child(key ?: "").child("lat").setValue(lat)
                              p0.ref.child(key ?: "").child("lon").setValue(lon)

                          }
                        }

                    })
                }
            },null)
        }
    }
}