package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.team_list_row.view.*

class TeamListAdapter(var clickListener: onItemClickListener): RecyclerView.Adapter<CustomViewHolder>() {
    val teamNames = listOf("Anaheim Ducks", "Arizona Coyotes", "Boston Bruins", "Buffalo Sabres", "Calgary Flames", "Carolina Hurricanes",
        "Chicago Blackhawks", "Colorado Avalanche", "Columbus Blue Jackets", "Dallas Stars", "Detroit Red Wings", "Edmonton Oilers",
        "Florida Panthers", "Los Angeles Kings", "Minnesota WIld", "Montreal Canadians", "Nashville Predators", "New Jersey Devils",
        "New York Islanders", "New York Rangers", "Ottawa Senators", "Philadelphia Flyers", "Pittsburgh Penguins", "San Jose Sharks",
        "St. Louis Blues", "Tampa Bay Lightning", "Toronto Maple Leafs", "Vancouver Canucks", "Vegas Golden Knights", "Washington Capitals",
        "Winnipeg Jets")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_list_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamNames.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder?.view?.team_list_name.text = teamNames.get(position)
        holder.initialize(clickListener)
    }
}
class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun initialize(action:onItemClickListener){
        view.setOnClickListener {
            action.onItemClick(adapterPosition)
        }
    }
}
interface onItemClickListener{
    fun onItemClick(position: Int)
}
