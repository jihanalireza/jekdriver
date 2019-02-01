package com.udacoding.drivergitfire.utama

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.udacoding.drivergitfire.R

import kotlinx.android.synthetic.main.driver_home.*
import org.jetbrains.anko.support.v4.toast


class HomeDriverFragment() : Fragment() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemRequest -> {
                setFragment(RequestFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemHandle -> {

                setFragment(HandleFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemComplete-> {

                setFragment(CompleteFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.driver_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragment(RequestFragment())
//            val fragmentActive = arguments?.getInt("fragment")
//            when(fragmentActive){
//                1->setFragment(RequestFragment())
//                2->setFragment(HandleFragment())
//                4->setFragment(CompleteFragment())
//            }


        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setFragment(fragment: Fragment) {

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container2, fragment)?.commit()
    }
}