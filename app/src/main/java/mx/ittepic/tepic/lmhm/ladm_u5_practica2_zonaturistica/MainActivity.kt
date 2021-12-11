package mx.ittepic.tepic.lmhm.ladm_u5_practica2_zonaturistica

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class MainActivity : AppCompatActivity(), OnMapReadyCallback, AdapterView.OnItemClickListener {

    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()
    var siPermiso = 98
    lateinit var locacion : LocationManager
    var c1 : Location = Location("")
    var c2 : Location = Location("")
    val mMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),siPermiso)
        } else {
            locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var oyente = Oyente(this)
            locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, oyente)
        }
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ley))
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onItemClick(p0: AdapterView<*>, p1: View, position: Int, p3: Long) {
        var item = findViewById<ListView>(R.id.lista).getItemAtPosition(position).toString()
        Toast.makeText(this,"Click en ${item}",Toast.LENGTH_LONG).show()

    }
}

class Oyente(puntero:MainActivity) : LocationListener {
    var p = puntero
    override fun onLocationChanged(location: Location) {
        p.findViewById<TextView>(R.id.coordenadasView).setText("Coordenadas:\n${location.latitude}, ${location.longitude}")
        var posicionActual = GeoPoint(location.latitude, location.longitude)
        for (item in p.posicion) {
            if (item.estoyEn(posicionActual)) {
                p.findViewById<TextView>(R.id.ubicado).setText("Usted se encuentra en:\n${item.nombre}")
            }
        }
    }
}