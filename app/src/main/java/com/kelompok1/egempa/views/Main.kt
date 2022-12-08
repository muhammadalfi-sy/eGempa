package com.kelompok1.egempa.views

import android.annotation.SuppressLint
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kelompok1.egempa.networking.ApiEndpoint
import org.json.JSONObject

class Main(var mainView: MainView) : MainPresenter {

    override fun getDataGempaDirasakan() {
        mainView.onProses(true)
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_DIRASAKAN)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(response: JSONObject) {
                        mainView.onProses(false)
                        mainView.onGetDataJSON(response)
                    }

                    override fun onError(anError: ANError) {
                        mainView.onProses(false)
                        mainView.onNotice("Gagal mendapatkan data!")
                    }
                })
    }

    override fun getDataGempaBerpotensi() {
        mainView.onProses(true)
        AndroidNetworking.get(ApiEndpoint.URL_GEMPA_BERPOTENSI)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        mainView.onProses(false)
                        mainView.onGetDataJSON(response)
                    }

                    override fun onError(anError: ANError) {
                        mainView.onProses(false)
                        mainView.onNotice("Gagal mendapatkan data!")
                    }
                })
    }

    override fun onProses(proses: Boolean) {
        mainView.onProses(proses)
    }

}