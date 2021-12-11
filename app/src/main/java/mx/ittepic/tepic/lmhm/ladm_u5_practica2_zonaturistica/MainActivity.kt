package mx.ittepic.tepic.lmhm.ladm_u5_practica2_zonaturistica

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.android.gms.maps.model.MarkerOptions




class MainActivity : AppCompatActivity(), OnMapReadyCallback, AdapterView.OnItemClickListener {

    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()
    var siPermiso = 1
    lateinit var locacion : LocationManager
    var c1 : Location = Location("")
    var c2 : Location = Location("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),siPermiso)

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),siPermiso)
        }
        locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var oyente = Oyente(this)
        locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, oyente)


        baseRemota.collection("LEY")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    findViewById<TextView>(R.id.lista).setText("ERROR: "+firebaseFirestoreException.message)
                    return@addSnapshotListener
                }

                var texto = java.util.ArrayList<String>()
                posicion.clear()
                for(document in querySnapshot!!){
                    var data = Data()
                    data.nombre = document.getString("nombre").toString()
                    data.cord1 = document.getGeoPoint("cord1")!!
                    data.cord2 = document.getGeoPoint("cord2")!!
                    texto.add(data.toString())
                    posicion.add(data)
                }
                findViewById<ListView>(R.id.lista).adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,texto)
            }
        findViewById<ListView>(R.id.lista).setOnItemClickListener(this)
        findViewById<Button>(R.id.btnBusqueda).setOnClickListener {
            if(findViewById<TextView>(R.id.busqueda).text.toString() == ""){
                findViewById<TextView>(R.id.ubicacion).setText("")
            }
            baseRemota.collection("LEY")
                .whereEqualTo("nombre", findViewById<TextView>(R.id.busqueda).getText().toString())
                .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                    if(firebaseFirestoreException != null){
                        findViewById<TextView>(R.id.ubicacion).setText("Imposible establecer conexión a FireBase")
                        return@addSnapshotListener
                    }
                    for(document in  querySnapshot!!){
                        c1.longitude = document.getGeoPoint("cord1")!!.longitude
                        c1.latitude = document.getGeoPoint("cord1")!!.latitude
                        c2.longitude = document.getGeoPoint("cord2")!!.longitude
                        c2.latitude = document.getGeoPoint("cord2")!!.latitude
                    }
                    var r = "(${(c1.latitude)}, ${c1.longitude}),(${c2.latitude}, ${c2.longitude})"
                    findViewById<TextView>(R.id.ubicacion).setText(r)
                }
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        var mMap = p0

        // Add a marker in ley and move the camera
        val ley = LatLng(21.493124, -104.884021)
        val HM = LatLng(21.492896, -104.883717)
        val TP = LatLng(21.493609, -104.882499)
        val Meg = LatLng(21.492842, -104.882929)
        val Ban = LatLng(21.492259, -104.883163)
        val PA = LatLng(21.493124, -104.884021)
        mMap.addMarker(MarkerOptions()
            .position(ley)
            .title("Ley"))
        mMap.addMarker(MarkerOptions()
            .position(PA)
            .title("Plaza alica"))
        mMap.addMarker(MarkerOptions()
            .position(Ban)
            .title("Banorte"))
        mMap.addMarker(MarkerOptions()
            .position(Meg)
            .title("Megacable"))
        mMap.addMarker(MarkerOptions()
            .position(TP)
            .title("Tower Pizza"))
        mMap.addMarker(MarkerOptions()
            .position(HM)
            .title("Hasi & Maki"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ley))
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onItemClick(p0: AdapterView<*>, p1: View, position: Int, p3: Long) {
        var item = findViewById<ListView>(R.id.lista).getItemAtPosition(position).toString()
        //Toast.makeText(this,"Click en ${item}",Toast.LENGTH_LONG).show()
        var nombre = item.split("\n")
        baseRemota.collection("LEY").document(nombre[0]).addSnapshotListener { value, error ->
            if(value!!.getString("nombre")!=null){
                val extra = Intent(this,MainActivity2::class.java).putExtra("nombre",value.getString("nombre"))
                extra.putExtra("descripcion",value.getString("Descripcion"))
                startActivity(extra)
            }

        }

        //Envia al MainActivity2 el nombre seleccionado en el item.

    }

}

class Oyente(puntero:MainActivity) : LocationListener {
    var p = puntero
    var baseRemota = FirebaseFirestore.getInstance()
    override fun onLocationChanged(location: Location) {
        p.findViewById<TextView>(R.id.coordenadasView).setText("Coordenadas:\n${location.latitude}, ${location.longitude}")
        var posicionActual = GeoPoint(location.latitude, location.longitude)
        for (item in p.posicion) {
            if (item.estoyEn(posicionActual)) {
                p.findViewById<TextView>(R.id.ubicado).setText("Usted se encuentra en:\n${item.nombre}")
                AlertDialog.Builder(p)
                    .setMessage("Quieres ver mas informacion de la locacion?")
                    .setPositiveButton("SI"){i,v->
                        baseRemota.collection("LEY").document(item.nombre).addSnapshotListener { value, error ->
                            if(value!!.getString("nombre")!=null){
                                val extra = Intent(p,MainActivity2::class.java).putExtra("nombre",value.getString("nombre"))
                                extra.putExtra("descripcion",value.getString("Descripcion"))
                                p.startActivity(extra)
                            }
                        }
                    }
                    .setNegativeButton("NO"){i,v->}
            }
        }
    }
}