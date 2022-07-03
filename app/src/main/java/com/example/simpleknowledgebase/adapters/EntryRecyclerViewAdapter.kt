package com.example.simpleknowledgebase.adapters


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.R


class EntryRecyclerViewAdapter(val context: Context, private val entryList: List<Entry>) : RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder>() {


    // Callback var,interface and function for OnClick events
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position :Int,entry: Entry)
    }
    fun setOnEntryItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_keyword_recycler_view, parent, false)

        return ViewHolder(view,mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entry = entryList[position]

        holder.keyword_rec_view_tv_Date.text = entry.date
        holder.keyword_rec_view_tv_title.text = entry.title
        holder.keyword_rec_view_tv_category.text = entry.category
        holder.keyword_rec_view_tv_description.text = entry.description
        holder.keyword_rec_view_tv_source.text = entry.source

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return entryList.size
    }

    // Holds the views for adding values to it in onBindViwHolder
    inner class ViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val keyword_rec_view_tv_Date: TextView = itemView.findViewById(R.id.keyword_rec_view_tv_Date)
        val keyword_rec_view_tv_title: TextView = itemView.findViewById(R.id.keyword_rec_view_tv_title)
        val keyword_rec_view_tv_category: TextView = itemView.findViewById(R.id.keyword_rec_view_tv_category)
        val keyword_rec_view_tv_description: TextView = itemView.findViewById(R.id.keyword_rec_view_tv_description)
        val keyword_rec_view_tv_source: TextView = itemView.findViewById(R.id.keyword_rec_view_tv_source)


        init {
            // Click Listener for updating/deleting an entry
            itemView.setOnClickListener {
                // entryList.get(bindingAdapterPosition): takes the current entry by its position from the entryList
                listener.onItemClick(bindingAdapterPosition,entryList.get(bindingAdapterPosition))
            }
            // Click listener for opening the entry source URL in the default browser
            itemView.findViewById<Button>(R.id.keyword_rec_view_btn_openSource).setOnClickListener(){
                try {
                    var openSourceURL: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(entryList.get(bindingAdapterPosition).source))
                    context?.startActivity(openSourceURL)
                }
                catch (e: Exception){
                    Toast.makeText(context,"The URL could not be opened. Please check its format!",
                        Toast.LENGTH_LONG).show();
                }
            }
        }


    }

}


