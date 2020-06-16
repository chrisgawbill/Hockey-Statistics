package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_stat_list_row.view.*

class PlayerAdapter(private var statList: ArrayList<StatObject>): RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.player_stat_list_row, parent, false)
        return PlayerViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.initialize(statList[position])
    }
}
class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view){
    private var statType: TextView = view.player_stat_type
    private var statValue: TextView = view.player_stat_value
    fun initialize(item: StatObject){
        statType.text = item.type
        statValue.text = item.value
    }
}