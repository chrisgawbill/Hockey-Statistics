package com.example.hockeystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [TeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamFragment : Fragment(), OnRosterClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var teamName:String = ""
    private var teamID:Int = 0
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
        activity?.setTitle("Team Page")
        return inflater.inflate(R.layout.fragment_team, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { TeamFragmentArgs.fromBundle(it) }
        teamName = args?.teamName!!
        teamID = args.teamId
        activity?.setTitle("Team Page")
        getData(view)

    }
    private fun getData(view:View){
        val name: TextView = view.findViewById(R.id.name)
        name.text = teamName
        runStatAPI("https://statsapi.web.nhl.com/api/v1/teams/$teamID/stats")
        runRosterAPI("https://statsapi.web.nhl.com/api/v1/teams/$teamID/roster")
    }
    private fun runStatAPI(url:String){
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                val message = response.body()?.string()
                val reader = JSONObject(message)
                val teamStat: JSONArray = reader.getJSONArray("stats")
                var index: JSONObject = teamStat.getJSONObject(0)
                var name: JSONArray = index.getJSONArray("splits")
                index = name.getJSONObject(0)
                var stat: JSONObject = index.getJSONObject("stat")
                val ot = stat.getString("ot")
                val win = stat.getString("wins")
                val loss = stat.getString("losses")
                val points = stat.getString("pts")
                val goalsPerDec = stat.getString("goalsPerGame")
                val goalsAgainstDec = stat.getString("goalsAgainstPerGame")
                val powerPlayPercentage = stat.getString("powerPlayPercentage")
                val penaltyKillPercentage = stat.getString("penaltyKillPercentage")
                val shots = stat.getString("shotsPerGame")
                val shotsAllowed = stat.getString("shotsAllowed")

                index = teamStat.getJSONObject(1)
                name = index.getJSONArray("splits")
                index = name.getJSONObject(0)
                stat = index.getJSONObject("stat")
                val goalsPerStanding = stat.getString("goalsPerGame")
                val goalsAgainstStanding = stat.getString("goalsAgainstPerGame")
                val powerPlayStanding = stat.getString("powerPlayPercentage")
                val penaltyKillStanding = stat.getString("penaltyKillPercentage")
                val shotsStanding = stat.getString("shotsPerGame")
                val shotsAllowedStanding = stat.getString("shotsAllowed")
                activity?.runOnUiThread {
                    view?.findViewById<TextView>(R.id.teamRecord)?.text = "$win - $loss - $ot ($points pts)"
                    view?.findViewById<TextView>(R.id.goalsPerGame)?.text = "$goalsPerDec  ($goalsPerStanding)"
                    view?.findViewById<TextView>(R.id.goalsAPerGame)?.text = "$goalsAgainstDec  ($goalsAgainstStanding)"
                    view?.findViewById<TextView>(R.id.powerPlay)?.text = "$powerPlayPercentage%  ($powerPlayStanding)"
                    view?.findViewById<TextView>(R.id.penaltyKill)?.text = "$penaltyKillPercentage% ($penaltyKillStanding)"
                    view?.findViewById<TextView>(R.id.teamShots)?.text = "$shots  ($shotsStanding)"
                    view?.findViewById<TextView>(R.id.teamShotsAllowed)?.text = "$shotsAllowed  ($shotsAllowedStanding)"
                }

            }
        })
    }
    private fun runRosterAPI(url:String){
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val message = response.body()?.string()
                val playerList = ArrayList<PlayerObject>()
                val reader = JSONObject(message)
                val roster = reader.getJSONArray("roster")
                for(i in 0 until roster.length()) {
                    var index = roster.getJSONObject(i)
                    var player = index.getJSONObject("person")
                    val name = player.getString("fullName")
                    val id = player.getString("id")

                    index = roster.getJSONObject(i)
                    val number = index.getString("jerseyNumber")

                    index = roster.getJSONObject(i)
                    player = index.getJSONObject("position")
                    val position = player.getString("name")

                    playerList.add(PlayerObject(id, name, position, number))
                }
                activity?.runOnUiThread {
                    val recyclerView: RecyclerView = view?.findViewById(R.id.roster_recyclcer_View)!!
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.adapter = RosterAdapter(this@TeamFragment, playerList)
                    recyclerView.addItemDecoration(DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    ))
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
         * @return A new instance of fragment TeamFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onRosterClick(item: PlayerObject, position: Int) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val action = TeamFragmentDirections.actionTeamFragmentToPlayerFragment(item.name, item.position, item.number, item.id)
        findNavController().navigate(action, options)
    }

}
