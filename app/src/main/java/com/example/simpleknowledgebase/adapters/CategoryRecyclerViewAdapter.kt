package com.example.simpleknowledgebase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleknowledgebase.R



class CategoryRecyclerViewAdapter(val context: Context, private val categoryList: List<String>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {


    // Callback var,interface and function for OnClick events
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position :Int,category: String)
    }
    fun setOnCategoryItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_category_overview_recycler_view, parent, false)

        return ViewHolder(view,mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = categoryList[position]

        holder.categoryOverview_tv_category.text = category


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return categoryList.size
    }


    // Holds the views for adding values to it in onBindViwHolder
    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val categoryOverview_tv_category: TextView = itemView.findViewById(R.id.categoryOverview_tv_category)


        init {
            // Click Listener for updating/deleting an entry
            itemView.setOnClickListener {
                // entryList.get(bindingAdapterPosition): takes the current entry by its position from the entryList
                listener.onItemClick(bindingAdapterPosition,categoryList.get(bindingAdapterPosition))
            }
        }


    }

}