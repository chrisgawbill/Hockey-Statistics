package com.example.hockeystatistics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
        private val client = OkHttpClient()
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        run("https://statsapi.web.nhl.com/api/v1/teams/1/roster")
    }
    fun run(url:String){
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                val message = response.body()?.string()
                var reader = JSONObject(message)
                var division:JSONArray = reader.getJSONArray("roster")
                val person:JSONObject = division.getJSONObject(0)
                val name:JSONObject = person.getJSONObject("person")
                val names = name.getString("id")
                println(names)
            }
        })
    }
}