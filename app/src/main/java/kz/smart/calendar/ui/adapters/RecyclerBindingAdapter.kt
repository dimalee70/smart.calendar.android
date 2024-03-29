package kz.smart.calendar.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_poll.view.*
import kz.smart.calendar.BR
import kz.smart.calendar.R
import kz.smart.calendar.models.objects.Poll
import kz.smart.calendar.models.objects.VoteOption
import kz.smart.calendar.modules.poll.domain.VoteOptionPresenter
import kz.smart.calendar.modules.poll.domain.VoteOptionView

import java.util.ArrayList

class RecyclerBindingAdapter<T>(
    private val holderLayout: Int,
    private val variableId: Int,
    private val context: Context
) : RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>(){
//    private var items: ArrayList<T> = ArrayList()
    private var items: ObservableArrayList<T> = ObservableArrayList()
    private var onItemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerBindingAdapter.BindingHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return BindingHolder(v)
    }

    init {
       // items.addOnListChangedCallback(ObservableListCallback())
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
        //items.addOnListChangedCallback(ObservableListCallback())
    }

    fun getItems(): ObservableArrayList<T>{
        return items
    }

    override fun onBindViewHolder(holder: RecyclerBindingAdapter.BindingHolder, position: Int) {
        val item = items[position]

        holder.binding!!.root.setOnClickListener { _ ->
            if (onItemClickListener != null)
                onItemClickListener!!.onItemClick(position, item)
        }
        holder.binding.setVariable(variableId, item)
//        holder.binding.executePendingBindings()
        if(holderLayout == R.layout.item_poll){
            val poll = item as Poll

            poll.presener = VoteOptionPresenter(poll, poll.vote_options)


//            holder.binding.root.finishButton.setOnClickListener {
//
//            }
            if (holderLayout == R.layout.item_categories)
                holder.setIsRecyclable(false)
            val votes = ObservableArrayList<VoteOption>()
            val recyclerVoteAdapter = RecyclerBindingAdapter<VoteOption>(R.layout.item_vote, BR.data, context)
            recyclerVoteAdapter.setOnItemClickListener(
                object: OnItemClickListener<VoteOption> {
                    override fun onItemClick(position: Int, item: VoteOption) {
                        val idx = votes.indexOfFirst {
                            it.isSlected
                        }
                        if(idx >= 0)
                            votes[idx].isSlected = false
                        if(idx != position)
                            item.isSlected = !item.isSlected
                    }
                }
            )
            votes.addAll((item as Poll).vote_options)
            recyclerVoteAdapter.setItems(votes)
            holder.binding.root.votesRv.adapter = recyclerVoteAdapter

        }
//        if(holderLayout == R.layout.item_custom || holderLayout == R.layout.item_custom_full){
//            holder.binding.root.closeIb.setOnClickListener{
//                removeAt(position)
//            }
//        }
    }

    fun removeAt(position: Int){
        items.removeAt(position)
//        notifyItemChanged(position)
//        notifyItemRangeChanged(position, items.size)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if(holderLayout == R.layout.item_poll){
            val votes = ObservableArrayList<VoteOption>()
            val recyclerVoteAdapter = RecyclerBindingAdapter<VoteOption>(R.layout.item_vote, BR.data, context)
            votes.addAll((item as Poll).vote_options)
            holderLayout

        }
        return holderLayout
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, item: T)
    }

    class BindingHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(v)

        fun bind(varId: Int, obj: Any) {
            binding?.setVariable(varId, obj)
        }
    }

    fun setItems(items: ObservableArrayList<T>?)
    {
        if (this.items.isEmpty() && items != null)
        {
            //val list = ArrayList<T>()
            //list.addAll(items.toMutableList())
            this.items = items

            notifyDataSetChanged()
        }
        else if (this.items.isNotEmpty() && (items == null || items.isEmpty()))
        {
            this.items.clear()
            notifyDataSetChanged()
        }
        else {
            if (items != null) {
                val diffResult = calculateDiff(this.items, items)
//                this.items.clear()
//                this.items.addAll(items)
                this.items = items
                diffResult.dispatchUpdatesTo(this)
//                notifyDataSetChanged() //под вопросом, может стоит удалить
            }
        }
        this.items.addOnListChangedCallback(ObservableListCallback())

    }

    fun calculateDiff(oldList: ArrayList<T>, newList: ArrayList<T>): DiffUtil.DiffResult {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                val old = oldList[oldItemPosition]
                val new = newList[newItemPosition]
                /*if (old is Product)
                {
                    return (old as? Product)?.productId == (new as? Product)?.productId
                }*/
                return old == new
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = oldList[oldItemPosition]
                val new = newList[newItemPosition]
                /*if (old is Product)
                {
                    return (old as? Product)?.productId == (new as? Product)?.productId
//                            && (old as? Product)?.name == (new as? Product)?.name
//                            && (old as? Product)?.description == (new as? Product)?.description
                }
                else
                {
                    return old == new
                }*/
                return old == new
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        return diff
    }


    private inner class ObservableListCallback :
        ObservableList.OnListChangedCallback<ObservableArrayList<T>>() {

        override fun onChanged(sender: ObservableArrayList<T>) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableArrayList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<T>,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableArrayList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }
    }
}
