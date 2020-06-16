package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.team_list_row.view.*

class TeamListAdapter(private var clickListener: OnTeamClickListener, private var teamList: ArrayList<TeamObject>): RecyclerView.Adapter<TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_list_row, parent, false)
        return TeamListViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.initialize(teamList[position],clickListener)
    }
}
class TeamListViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    private var teamName: TextView = view.team_list_name
    fun initialize(item: TeamObject ,action:OnTeamClickListener){
        teamName.text = item.name
        view.setOnClickListener {
            action.onTeamClick(item, adapterPosition)
        }
    }
}
interface OnTeamClickListener{
    fun onTeamClick(item: TeamObject, position: Int)
}
