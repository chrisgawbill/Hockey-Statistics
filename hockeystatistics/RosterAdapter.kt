package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.roster_list_row.view.*

class RosterAdapter(private var clickListener: OnRosterClickListener, private var playerList: ArrayList<PlayerObject>): RecyclerView.Adapter<RosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.roster_list_row, parent, false)
        return RosterViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.initialize(playerList[position],clickListener)
    }
}
class RosterViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    private var playerName: TextView = view.roster_list_name
    private var playerPostion: TextView = view.roster_list_position
    private var playerNumber: TextView = view.roster_list_number
    fun initialize(item: PlayerObject ,action:OnRosterClickListener){
        playerName.text = item.name
        playerNumber.text = "#${item.number}"
        playerPostion.text = item.position
        view.setOnClickListener {
            action.onRosterClick(item, adapterPosition)
        }
    }
}
interface OnRosterClickListener{
    fun onRosterClick(item: PlayerObject, position: Int)
}
