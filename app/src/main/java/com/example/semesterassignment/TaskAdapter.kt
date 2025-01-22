import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semesterassignment.R

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onDeleteTask: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)
        val buttonDelete: ImageView = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textViewTitle.text = task.title
        holder.checkBoxCompleted.isChecked = task.isCompleted

        holder.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
        }

        holder.buttonDelete.setOnClickListener {
            onDeleteTask(task)
        }
    }

    override fun getItemCount(): Int = tasks.size
}
