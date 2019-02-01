package com.udacoding.drivergitfire.utama

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.insertposition.TrackingService
import com.udacoding.drivergitfire.utama.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity() {
    var driverFragment : Int? = 1
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(HomeDriverFragment(),1)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_dashboard -> {
//
//                setFragment(HistoryFragment())
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.navigation_profile -> {

                setFragment(ProfileFragment(),0)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
//        driverFragment = intent.getIntExtra("fragment",1)
//
//        when(driverFragment){
//            1->setFragment(HomeDriverFragment(),1)
//            2->setFragment(HomeDriverFragment(),2)
//            4->setFragment(HomeDriverFragment(),4)
//        }

        setFragment(HomeDriverFragment(),1)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
        }else{
            startService(Intent(this,TrackingService::class.java))
        }
    }

    fun setFragment(fragment : Fragment,fragmentActive:Int){
//        val bundle = Bundle()
//        bundle.putInt("fragment", fragmentActive)
//        fragment.arguments(bundle)
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1){
            startService(Intent(this,TrackingService::class.java))
        }
    }


}
