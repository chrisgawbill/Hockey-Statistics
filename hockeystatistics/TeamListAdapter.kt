package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.team_list_row.view.*

class TeamListAdapter(var clickListener: onItemClickListener, var teamList: ArrayList<TeamObject>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_list_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.initialize(teamList.get(position),clickListener)
    }
}
class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    var teamName = view.team_list_name
    fun initialize(item: TeamObject ,action:onItemClickListener){
        teamName.text = item.name
        view.setOnClickListener {
            action.onItemClick(item, adapterPosition)
        }
    }
}
interface onItemClickListener{
    fun onItemClick(item: TeamObject, position: Int)
}
