package mx.ittepic.tepic.lmhm.ladm_u5_practica2_zonaturistica

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val localfile = File.createTempFile("tempImage","jpeg")

        if(nombre == "Banorte"){

            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/banorte1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/banorte2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/banorte3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }//if Banorte

        //IF para obtener las imagenes de
        if (nombre == "Hasi & Maki"){
            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/imagen1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }

        //if Ley
        if(nombre == "Ley"){
            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/imagen1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }//if Ley

        if(nombre == "Megacable"){
            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/imagen1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }//if Megacable

        if(nombre == "Tower Pizza"){
            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/imagen1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }//if Tower Pizza

        if(nombre == "Plaza alica"){
            //imagen 1
            var storeRef  = FirebaseStorage.getInstance().reference.child(nombre + "/imagen1.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img1.setImageBitmap(bitmap)
            }

            //Imagen 2
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen2.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img2.setImageBitmap(bitmap)
            }

            //imagen 3
            storeRef = FirebaseStorage.getInstance().reference.child(nombre + "/imagen3.jpeg")
            storeRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                img3.setImageBitmap(bitmap)
            }
        }//if Plaza alica

        txtDescripcion.setText(descripcion)
    }//onCreate
}//class