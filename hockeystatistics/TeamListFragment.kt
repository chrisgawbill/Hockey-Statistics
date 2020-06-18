package com.example.hockeystatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamListFragment : Fragment(), OnTeamClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private var teamList = ArrayList<TeamObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        createTeam()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_team_list, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = TeamListAdapter(this, teamList)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        activity?.setTitle("Team List")
        return rootView
    }
    override fun onTeamClick(item: TeamObject, position: Int) {
        val options = navOptions{
            anim{
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val action = TeamListFragmentDirections.actionTeamListFragmentToTeamFragment(item.name, item.id)
        findNavController().navigate(action, options)
    }
    private fun createTeam(){
        teamList.add(0, TeamObject("Anaheim Ducks",24))
        teamList.add(1, TeamObject("Arizona Coyotes",53))
        teamList.add(2, TeamObject("Boston Bruins",6))
        teamList.add(3, TeamObject("Buffalo Sabres",7))
        teamList.add(4, TeamObject("Calgary Flames",20))
        teamList.add(5, TeamObject( "Carolina Hurricanes",12))
        teamList.add(6, TeamObject( "Chicago Blackhawks",16))
        teamList.add(7, TeamObject("Colorado Avalanche",21))
        teamList.add(8, TeamObject("Columbus Blue Jackets",29))
        teamList.add(9, TeamObject("Dallas Stars",25))
        teamList.add(10, TeamObject("Detroit Red Wings",17))
        teamList.add(11, TeamObject("Edmonton Oilers",22))
        teamList.add(12, TeamObject("Florida Panthers",13))
        teamList.add(13, TeamObject("Los Angeles Kings",26))
        teamList.add(14, TeamObject("Minnesota Wild",30))
        teamList.add(15, TeamObject("Montreal Canadians",8))
        teamList.add(16, TeamObject("Nashville Predators",18))
        teamList.add(17, TeamObject("New Jersey Devils",1))
        teamList.add(18, TeamObject("New York Islanders",2))
        teamList.add(19, TeamObject("New York Rangers",3))
        teamList.add(20, TeamObject("Ottawa Senators",9))
        teamList.add(21, TeamObject("Philadelphia Flyers",4))
        teamList.add(22, TeamObject("Pittsburgh Penguins",5))
        teamList.add(23, TeamObject("San Jose Sharks",28))
        teamList.add(24, TeamObject("St. Louis Blues",19))
        teamList.add(25, TeamObject("Tampa Bay Lightning",14))
        teamList.add(26, TeamObject("Toronto Maple Leafs",10))
        teamList.add(27, TeamObject("Vancouver Canucks",23))
        teamList.add(28, TeamObject("Vegas Golden Knights",54))
        teamList.add(29, TeamObject("Washington Capitals",15))
        teamList.add(30, TeamObject("Winnipeg Jets",52))
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamListFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}