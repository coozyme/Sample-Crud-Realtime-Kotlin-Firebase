package tech.riseofdevelopers.realtimecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class ShowDataActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView :ListView
    lateinit var list : MutableList<Users>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        ref = FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        //panggi listview yg akan dipakai
        listView = findViewById(R.id.lv_showdata)

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){

                    list.clear()
                    for (h in snapshot.children) {
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                        val adapter = Adapter(this@ShowDataActivity, R.layout.users, list)
                        listView.adapter = adapter
                }
            }
        })


    }
}