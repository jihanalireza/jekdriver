package com.udacoding.drivergitfire.utama


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.udacoding.drivergitfire.R
import com.udacoding.drivergitfire.detail.DetailOrder
import com.udacoding.drivergitfire.network.myFirebaseDatabase
import com.udacoding.drivergitfire.utama.history.adapter.Historydapter
import com.udacoding.drivergitfire.utama.home.model.Booking
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHistory()

    }

    private fun getHistory() {
        val uid =  FirebaseAuth.getInstance().currentUser?.uid
        myFirebaseDatabase.bookingRef().orderByChild("driver").equalTo(uid).addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = ArrayList<Booking>()
                for (issue in p0.children){
                    val itemBooking = issue.getValue(Booking::class.java)
                    if(itemBooking?.status == 4){
                        data.add(itemBooking ?: Booking())
                    }
                }

                showDataHistory(data)
            }


        })
    }

    private fun showDataHistory(dataHistory: ArrayList<Booking>) {
        recycleHistory.adapter = Historydapter(dataHistory as List<Booking>,object : Historydapter.itemCLick{
            override fun ketikaKlik(item: Booking) {
                startActivity<DetailOrder>("booking" to item,
                    "status" to 4)
            }

        })
        recycleHistory.layoutManager = LinearLayoutManager(context)
    }


}
