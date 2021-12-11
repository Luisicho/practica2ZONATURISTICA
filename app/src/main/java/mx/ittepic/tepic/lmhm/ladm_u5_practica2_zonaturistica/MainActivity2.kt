package mx.ittepic.tepic.lmhm.ladm_u5_practica2_zonaturistica

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File

class MainActivity2 : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Recuperados el dato enviado por el MainActivy1
        var nombre = intent.extras!!.getString("nombre").toString()
        var descripcion = intent.extras!!.getString("descripcion").toString()

        if(nombre == "Banorte"){
            val storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/banorte1.*")
            val localfile = File.createTempFile("tempImage","*")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }
            txtDescripcion.setText(descripcion)
        }
    }//onCreate
}//class