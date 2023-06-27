package com.example.volleyrecyclerview

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.volleyrecyclerview.databinding.ProductsDesignBinding

class FoodAdapter(var activity: Activity, var arrayList: List<Product>) :
    RecyclerView.Adapter<FoodAdapter.Holder>() {

    class Holder(var binding: ProductsDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var root = ProductsDesignBinding.inflate(activity.layoutInflater, parent, false)
        return Holder(root)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.nameFood.text = arrayList[position].title
        holder.binding.priceFood.text = arrayList[position].price.toString()
        holder.binding.restaurantRateTvFood.rating =
            arrayList[position].rating.toString().toFloat()
        holder.binding.descFood.text = arrayList[position].description
        Glide.with(activity).load(arrayList[position].images[0])
            .into(holder.binding.imageFood)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}