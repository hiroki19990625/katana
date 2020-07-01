package jp.katana.core.data

import net.minidev.json.JSONObject

interface ISkinImage {
    val width: Int
    val height: Int
    val data: String

    fun decode(token: JSONObject, name: String)
    fun encode(): String
}