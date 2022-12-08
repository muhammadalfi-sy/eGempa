package com.kelompok1.egempa.views

interface MainPresenter {
    fun getDataGempaDirasakan()
    fun getDataGempaBerpotensi()
    fun onProses(proses: Boolean)
}