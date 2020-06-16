package com.example.hockeystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var playerName:String = ""
    private var playerID:String =""
    private var playerPosition:String = ""
    private var playerNumber:String = ""
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args =  arguments?.let { PlayerFragmentArgs.fromBundle(it) }
        playerName = args?.playerName!!
        playerID = args.playerID
        playerPosition = args.playerPosition
        playerNumber = args.playerNum
        getData()
    }
    private fun getData(){
        runBasicPlayerAPI("https://statsapi.web.nhl.com/api/v1/people/$playerID")
        runAdvancedPlayerAPI("https://statsapi.web.nhl.com/api/v1/people/$playerID/stats?stats=statsSingleSeason&season=20192020")
    }
    private fun runBasicPlayerAPI(url:String){
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val message = response.body()?.string()
                val reader = JSONObject(message)
                val stats = reader.getJSONArray("people")
                val index = stats.getJSONObject(0)
                val age = index.getString("currentAge")
                val city = index.getString("birthCity")
                val country = index.getString("birthCountry")
                val height = index.getString("height")
                val weight = index.getString("weight")
                activity?.runOnUiThread {
                    view?.findViewById<TextView>(R.id.playerName)?.text = playerName
                    view?.findViewById<TextView>(R.id.playerNumber)?.text = "#$playerNumber"
                    view?.findViewById<TextView>(R.id.playerPosition)?.text = playerPosition
                    view?.findViewById<TextView>(R.id.playerAge)?.text = "$age years old"
                    view?.findViewById<TextView>(R.id.playerOrigin)?.text = "From $city, $country"
                    view?.findViewById<TextView>(R.id.playerHeight)?.text = height
                    view?.findViewById<TextView>(R.id.playerWeight)?.text = "$weight lbs"
                }
            }
        })
    }
    private fun runAdvancedPlayerAPI(url:String){
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val statList = ArrayList<StatObject>()
                val message = response.body()?.string()
                val reader = JSONObject(message)
                val stats = reader.getJSONArray("stats")
                var index = stats.getJSONObject(0)
                val splits= index.getJSONArray("splits")
                index = splits.getJSONObject(0)
                val player = index.getJSONObject("stat")
                println(player)
                val games = player.getString("games")
                if(playerPosition == "Goalie"){
                    val shutOuts = player.getString("shutouts")
                    val wins = player.getString("wins")
                    val losses = player.getString("losses")
                    val ot = player.getString("ot")
                    val goalsAgainstAverage = player.getString("goalAgainstAverage")
                    val savePercentage = player.getString("savePercentage")
                    val gamesStarted = player.getString("gamesStarted")
                    val shotsAgainst = player.getString("shotsAgainst")
                    val goalsAgainst = player.getString("goalsAgainst")

                    statList.add(StatObject("Games", games))
                    statList.add(StatObject("Games Started", gamesStarted))
                    statList.add(StatObject("Wins", wins))
                    statList.add(StatObject("Losses", losses))
                    statList.add(StatObject("OT", ot))
                    statList.add(StatObject("Save Percentage", savePercentage))
                    statList.add(StatObject("GAA", goalsAgainstAverage))
                    statList.add(StatObject("ShutOuts", shutOuts))
                    statList.add(StatObject("Shots Against", shotsAgainst))
                    statList.add(StatObject("Goals Against", goalsAgainst))

                    activity?.runOnUiThread {
                        val recyclerView: RecyclerView = view?.findViewById(R.id.player_recycler_view)!!
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = PlayerAdapter(statList)
                        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                    }
                }else{
                    val timeOnIce = player.getString("timeOnIce")
                    val goals = player.getString("goals")
                    val assists = player.getString("assists")
                    val points = player.getString("points")
                    val penaltyMinutes = player.getString("pim")
                    val hits = player.getString("hits")
                    val blockedShots = player.getString("blocked")
                    val shortHandedGoals = player.getString("shortHandedGoals")
                    val powerPlayGoals = player.getString("powerPlayGoals")
                    val plusMinus = player.getString("plusMinus")
                    val faceOffPercentage = player.getString("faceOffPct")

                    statList.add(StatObject("Games", games))
                    statList.add(StatObject("TimeOnIce", timeOnIce))
                    statList.add(StatObject("Goals", goals))
                    statList.add(StatObject("Assists", assists))
                    statList.add(StatObject("Points", points))
                    statList.add(StatObject("Plus Minus", plusMinus))
                    statList.add(StatObject("PIM", penaltyMinutes))
                    statList.add(StatObject("Blocked Shots", blockedShots))
                    statList.add(StatObject("Power Play Goals", powerPlayGoals))
                    statList.add(StatObject("Short Handed Goals", shortHandedGoals))
                    statList.add(StatObject("Face Off Percentage", faceOffPercentage))
                    statList.add(StatObject("Hits", hits))

                    activity?.runOnUiThread {
                        val recyclerView: RecyclerView = view?.findViewById(R.id.player_recycler_view)!!
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = PlayerAdapter(statList)
                        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                    }
                }
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
