package com.kelompok1.egempa.views

import org.json.JSONObject

interface MainView {
    fun onGetDataJSON(response: JSONObject?)
    fun onNotice(pesanNotice: String?)
    fun onProses(proses: Boolean)
}