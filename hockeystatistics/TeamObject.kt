package com.example.hockeystatistics

class TeamObject(s: String, i:Int) {
    var name:String = s
    /*On initialization this is the id slot, but in the Standing fragment the id slot is used for points*/
    var id:Int = i
}