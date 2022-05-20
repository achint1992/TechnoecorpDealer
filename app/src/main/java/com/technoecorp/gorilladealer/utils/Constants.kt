package com.technoecorp.gorilladealer.utils

class Constants {
    companion object {
        private const val baseUrl: String = "https://dealer.technoecorp.com/"
        const val appUrl: String = baseUrl + "api/v1/"

        const val dealerCard: String = baseUrl + "dealerCard/1/"
        const val certificate: String = baseUrl + "certificate/"

        const val policy:String="https://technoesoft.com/privacypolicy"
        const val terms:String ="https://technoesoft.com/termofuse"
    }
}