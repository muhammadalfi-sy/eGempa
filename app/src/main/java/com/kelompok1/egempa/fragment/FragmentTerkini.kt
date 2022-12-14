package com.kelompok1.egempa.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kelompok1.egempa.R
import com.kelompok1.egempa.networking.ApiEndpoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_terkini.*
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat

class FragmentTerkini : Fragment(), OnMapReadyCallback {

    var progressDialog: ProgressDialog? = null
    var strLat: String? = null
    var strLong: String? = null
    var strPotensi: String? = null
    var strTanggal: String? = null
    var strWaktu: String? = null
    var strSkala: String? = null
    var strKedalaman: String? = null
    var strWilayah: String? = null
    var strDirasakan: String? = null
    var latitude = 0.0
    var longitude = 0.0
    var googleMaps: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terkini, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setTitle("Mohon tunggu")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang mengambil data...")

        //show maps
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun getDataGempaTerkini() {
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_M5)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                @SuppressLint("SetTextI18n")
                override fun onResponse(response: JSONObject) {
                    try {
                        val jsonObj = response.getJSONObject("Infogempa")
                        val jsonObject = jsonObj.getJSONObject("gempa")
                        strPotensi = jsonObject.getString("Potensi")
                        strTanggal = jsonObject.getString("Tanggal")
                        strWaktu = jsonObject.getString("Jam")
                        strLat =
                            jsonObject.getString("Lintang").replace(" LU", "").replace(" LS", "")
                        strLong = jsonObject.getString("Bujur").replace(" BT", "")
                        strSkala = jsonObject.getString("Magnitude")
                        strKedalaman = jsonObject.getString("Kedalaman")
                        strWilayah = jsonObject.getString("Wilayah")
                        strDirasakan = jsonObject.getString("Dirasakan")

                        val formatDefault = SimpleDateFormat("dd-MMM-yy")
                        val formatTime = SimpleDateFormat("EEE, dd MMM yyyy")

                        try {
                            val timesFormatLast = formatDefault.parse(strTanggal)
                            strTanggal = formatTime.format(timesFormatLast)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }

                        latitude = "-$strLat".toDouble()
                        longitude = strLong!!.toDouble()

                        val latLng = LatLng(latitude, longitude)
                        googleMaps?.addMarker(MarkerOptions().position(latLng))
                        googleMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        googleMaps?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                        googleMaps?.uiSettings?.setAllGesturesEnabled(true)
                        googleMaps?.uiSettings?.isZoomGesturesEnabled = true
                        googleMaps?.isTrafficEnabled = true

                        tvWilayah.text = strWilayah
                        tvTanggal.text = "$strTanggal / $strWaktu"
                        tvSkala.text = "Skala : $strSkala" + " SR\n$strPotensi"
                        tvKedalaman.text = "Kedalaman : $strKedalaman"
                        tvDirasakan.text = strDirasakan
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            activity,
                            "Oops, ada yang tidak beres. Coba ulangi beberapa saat lagi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(anError: ANError) {
                    Toast.makeText(activity, "Tidak ada jaringan internet!", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMaps = googleMap
        getDataGempaTerkini()
    }

}
