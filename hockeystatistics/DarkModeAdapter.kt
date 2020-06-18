package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dark_mode_row.view.*

class DarkModeAdapter(private var clickListener: OnDarkClickListener, private var settingList: ArrayList<String>): RecyclerView.Adapter<DarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DarkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.dark_mode_row, parent, false)
        return DarkViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return settingList.size
    }

    override fun onBindViewHolder(holder: DarkViewHolder, position: Int) {
        holder.initialize(settingList[position], clickListener)
    }
}
class DarkViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private var darkName: TextView = view.dark_mode_name
        fun initialize(item: String, action: OnDarkClickListener){
            darkName.text = item
            view.setOnClickListener {
                action.onDarkClick(item, adapterPosition)
            }
        }
    }
interface OnDarkClickListener{
    fun onDarkClick(item: String, position: Int)
}
