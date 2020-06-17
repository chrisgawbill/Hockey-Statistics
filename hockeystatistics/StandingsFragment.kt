package com.example.hockeystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.DivisionStandingsAdapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StandingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StandingsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
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
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runStandingsAPI("https://statsapi.web.nhl.com/api/v1/standings")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StandingsFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StandingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    /*Calls the API to get the division standings*/
    private fun runStandingsAPI(url:String) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                /*totalStandings will store division name and division string list that contains correct order of standing*/

                val totalStanding = ArrayList<StandingObject>()
                val metro = ArrayList<TeamObject>()
                val atlantic = ArrayList<TeamObject>()
                val central = ArrayList<TeamObject>()
                val pacific = ArrayList<TeamObject>()

                val message = response.body()?.string()
                val reader = JSONObject(message)
                var standings = reader.getJSONArray("records")
                var index:JSONObject
                var teams:JSONArray
                var name:JSONObject
                for(j in 0 until standings.length()) {
                    index = standings.getJSONObject(j)
                    teams = index.getJSONArray("teamRecords")
                    for (i in 0 until teams.length()) {
                        index = teams.getJSONObject(i)
                        name = index.getJSONObject("team")
                        when (j) {
                            0-> metro.add(TeamObject(name.getString("name"), index.getString("points").toInt()))
                            1->atlantic.add(TeamObject(name.getString("name"), index.getString("points").toInt()))
                            2->central.add(TeamObject(name.getString("name"),index.getString("points").toInt()))
                            else->pacific.add(TeamObject(name.getString("name"),index.getString("points").toInt()))
                        }
                    }
                }
                totalStanding.add(StandingObject("Metropolitan Division", metro))
                totalStanding.add(StandingObject("Atlantic Division", atlantic))
                totalStanding.add(StandingObject("Central Division", central))
                totalStanding.add(StandingObject("Pacific Division", pacific))
                activity?.runOnUiThread {
                    val recyclerView: RecyclerView = view?.findViewById(R.id.standing_recycler_view)!!
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.adapter = DivisionStandingsAdapter(totalStanding)
                    recyclerView.addItemDecoration(
                        DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    )
                    )
                }
            }
        })
    }
}