import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.services.FlightCell

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class FlightsRecyclerAdapter(
    private val flightList: List<FlightModel>,
    private val onItemClickListener: OnItemClickListener,
    private var selectedPos: Int = RecyclerView.NO_POSITION
) :
    RecyclerView.Adapter<FlightsRecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    interface OnItemClickListener {
        fun onItemClicked(selectedFlight: FlightModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(FlightCell(parent.context))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(selectedPos==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#c0c0c0"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#e0e0e0"));

        val flightCell = holder.itemView as FlightCell
        flightCell.bindData(flightList[position])
        flightCell.setOnClickListener {
            selectedPos = position
            notifyDataSetChanged()
            onItemClickListener.onItemClicked(flightList[position])
        }
    }

    override fun getItemCount(): Int {
        return flightList.size
    }


}