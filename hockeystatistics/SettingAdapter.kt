package com.example.hockeystatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.setting_list_row.view.*

class SettingAdapter(private var clickListener: OnSettingClickListener, private var settingList: ArrayList<String>): RecyclerView.Adapter<SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.setting_list_row, parent, false)
        return SettingViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return settingList.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.initialize(settingList[position],clickListener)
    }
}
class SettingViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    private var teamName: TextView = view.setting_name
    fun initialize(item: String, action: OnSettingClickListener){
        teamName.text = item
        when(item){
            "Dark Mode"->view.setting_image.setImageResource(R.drawable.ic_settings_dark_mode)
            else->view.setting_image.setImageResource(R.drawable.ic_settings_about)
        }
        view.setting_image.contentDescription = item
        view.setOnClickListener {
            action.onSettingClick(item, adapterPosition)
        }
    }
}
interface OnSettingClickListener{
    fun onSettingClick(item: String, position: Int)
}
