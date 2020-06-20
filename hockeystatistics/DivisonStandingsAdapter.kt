package layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hockeystatistics.R
import com.example.hockeystatistics.StandingObject
import kotlinx.android.synthetic.main.standing_list_row.view.*

class DivisionStandingsAdapter(private var standingList: ArrayList<StandingObject>): RecyclerView.Adapter<StandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.standing_list_row, parent, false)
        return StandingViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return standingList.size
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.initialize(standingList[position])
    }
}
class StandingViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    private var teamName: TextView = view.division_name
    private var teamPlace:TextView = view.first_place
    fun initialize(item: StandingObject){
        var name: String
        var points:Int
        teamName.text = item.divisionName
        for(i in 0 until item.standings.size) {
            name = item.standings.get(i).name
            points = item.standings.get(i).id
            when(i){
                0->  teamPlace = view.first_place
                1-> teamPlace = view.second_place
                2-> teamPlace = view.third_place
                3-> teamPlace = view.fourth_place
                4-> teamPlace = view.fifth_place
                5-> teamPlace = view.sixth_place
                6-> teamPlace = view.seventh_place
                7-> teamPlace = view.eighth_place
            }
            teamPlace.text = "$name ($points)"
        }
        if(item.standings.size == 7){
            teamPlace = view.eighth
            teamPlace.visibility = View.INVISIBLE
        }
    }
}
