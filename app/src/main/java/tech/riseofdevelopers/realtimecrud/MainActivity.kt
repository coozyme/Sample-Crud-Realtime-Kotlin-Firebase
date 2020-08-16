package tech.riseofdevelopers.realtimecrud

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        btn_kirim.setOnClickListener {
            savedata()

            val pindah = Intent (this, ShowDataActivity::class.java)
            startActivity(pindah)
        }

    }

    private fun savedata() {

        val nama = edt_nama.text.toString()
        val alamat = edt_alamat.text.toString()

        val userId = ref.push().key.toString()
        val user = Users(userId,nama, alamat)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Success",Toast.LENGTH_SHORT).show()
            edt_nama.setText("")
            edt_alamat.setText("")
        }


    }
}