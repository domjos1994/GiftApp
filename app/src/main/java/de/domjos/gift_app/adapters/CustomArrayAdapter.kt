package de.domjos.gift_app.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import de.domjos.gift_app.R

class CustomArrayAdapter<T>(private val context: Context, private val items: List<T>, val action: (item: T, iv: ImageView, title: TextView, subTitle: TextView) -> Unit) : ArrayAdapter<T>(context, R.layout.array_item, items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        view = if (convertView == null) {
            val inflater = (context as Activity).layoutInflater
            inflater.inflate(R.layout.array_item, parent, false)
        } else {
            convertView
        }
        try {
            val iv = view?.findViewById<ImageView>(R.id.ivItem)!!
            val title = view.findViewById<TextView>(R.id.lblTitle)!!
            val subTitle = view.findViewById<TextView>(R.id.lblSubTitle)!!

            action(items[position], iv, title, subTitle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view!!
    }
}