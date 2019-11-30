package br.com.si.ufrrj.carta

class singleCard(var nome: String) {
    var id:Int = 0
    val defaltValue = "10"
    var figura:String = ""

    var forca:String? = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }
    var velocidade:String? = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }
    var inteligencia:String? = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }
    var poder:String = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }
    var vigor:String = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }
    var combate:String = "0"
        get() {
            if(field == "null") return defaltValue
            return field
        }

}