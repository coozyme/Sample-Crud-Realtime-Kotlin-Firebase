package tech.riseofdevelopers.realtimecrud

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.update.view.*

class Adapter (mCtx: Context, val layoutResId: Int, val list: List<Users>) :
    ArrayAdapter<Users>(mCtx,layoutResId,list){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflatter : LayoutInflater =  LayoutInflater.from(context)
        val view: View = layoutInflatter.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.tv_textNama)
        val textAlamat = view.findViewById<TextView>(R.id.tv_textAlamat)

        val  tblUpdate = view.findViewById<TextView>(R.id.btn_update)
        val tblDelete = view.findViewById<TextView>(R.id.btn_delete)

        val user = list[position]

        textName.text = user.nama
        textAlamat.text = user.alamat

        tblUpdate.setOnClickListener{
            showUpdateDialog(user)
//            Toast.makeText(context,"updatete" + user.id,Toast.LENGTH_SHORT).show()
        }

        tblDelete.setOnClickListener {
            Deleteinfo(user)



    }

        return view

}
    private fun Deleteinfo(user: Users) {
        val progressDialog = ProgressDialog(context,
            R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("USERS")
        mydatabase.child(user.id).removeValue()
        Toast.makeText(context,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ShowDataActivity::class.java)
        context.startActivity(intent)
    }



        private fun showUpdateDialog(user: Users) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Update")

            val inflater = LayoutInflater.from(context)

            val view = inflater.inflate(R.layout.update, null)

            val textNama = view.edt_updateNama
            val textAlamat = view.edt_updateAlamat

            textNama.setText(user.nama)
            textAlamat.setText(user.alamat)

            builder.setView(view)

            builder.setPositiveButton("Update") { dialog, which ->

                val dbUsers = FirebaseDatabase.getInstance().getReference("USERS")

                val nama = textNama.text.toString().trim()
                val alamat = textAlamat.text.toString().trim()

                if (nama.isEmpty()){
                    textNama.error = "please enter name"
                    textNama.requestFocus()
                    return@setPositiveButton
                }

                if (alamat.isEmpty()){
                    textAlamat.error = "Please Enter Alamat"
                    textAlamat.requestFocus()
                    return@setPositiveButton
                }

                val user = Users(user.id,nama,alamat)

                dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                    Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
                }

            }

            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(context, "TIDAK Jadi UPDATE", Toast.LENGTH_SHORT).show()
            }

            val alert = builder.create()
            alert.show()

        }


    }


