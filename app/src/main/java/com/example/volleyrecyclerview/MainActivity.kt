package com.example.volleyrecyclerview

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val productArray = arrayListOf<Product>()
    val apiSample = "https://dummyjson.com/products"
    var recyclerViewAll: RecyclerView? = null
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //.. code
        val requestQueue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET, apiSample, null, { response ->
            // Parse the JSON response
            val products = parseProducts(response)
            // Pass the products to your RecyclerView adapter
            val adapter = FoodAdapter(this@MainActivity, products)
            recyclerViewAll!!.adapter = adapter
        }, { error ->
            // Handle error
        })
        // Add the request to the RequestQueue
        requestQueue.add(request)
    }

    private fun parseProducts(response: JSONObject): List<Product> {
        val products = mutableListOf<Product>()

        val productsArray = response.getJSONArray("products")
        for (i in 0 until productsArray.length()) {
            val productObject = productsArray.getJSONObject(i)

            val id = productObject.getInt("id")
            val title = productObject.getString("title")
            val description = productObject.getString("description")
            val price = productObject.getDouble("price")
            val discountPercentage = productObject.getDouble("discountPercentage")
            val rating = productObject.getDouble("rating")
            val stock = productObject.getInt("stock")
            val brand = productObject.getString("brand")
            val category = productObject.getString("category")
            val thumbnail = productObject.getString("thumbnail")

            val imagesArray = productObject.getJSONArray("images")
            val images = mutableListOf<String>()
            for (j in 0 until imagesArray.length()) {
                images.add(imagesArray.getString(j))
            }

            val product = Product(
                brand,
                category,
                description,
                discountPercentage,
                id,
                images,
                price,
                rating,
                stock,
                thumbnail,
                title
            )
            products.add(product)
        }

        return products
    }

    private fun getArrayRequest_category() {
        val queue = Volley.newRequestQueue(this)
        val URL = "https://dummyjson.com/products/categories"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, URL, null, { response ->
            // Ui Thread code
            Log.d("yazan", response.toString())
            val categories = response
            val categoryList = ArrayList<Category_model>()
            for (i in 0 until categories.length()) {
                val category = categories.getString(i)
                categoryList.add(Category_model(category))
            }
            listView = findViewById(R.id.listView)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList)
            listView.adapter = adapter
        }, { error ->
            Log.e("yazan", error.message.toString())
        })

        MySingleton.getInstance().addRequestQueue(jsonArrayRequest, "jsonObject")
    }

    private fun getProductsVolley() {
        val queue = Volley.newRequestQueue(this@MainActivity)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiSample, null, { response ->
            // Ui Thread code
            Log.d("yazan", response.toString())
            val jsonArray = response.getJSONArray("products")
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val arrayImages = jsonObject.getJSONArray("images")
                var images = ArrayList<String>()
                for (j in 0 until arrayImages.length()) {
                    images.add(arrayImages.getString(i))
                }
                val product = Product(
                    jsonObject.getString("brand"),
                    jsonObject.getString("category"),
                    jsonObject.getString("description"),
                    jsonObject.getDouble("discountPercentage"),
                    jsonObject.getInt("id"),
                    images,
                    jsonObject.getDouble("price"),
                    jsonObject.getDouble("rating"),
                    jsonObject.getInt("stock"),
                    jsonObject.getString("thumbnail"),
                    jsonObject.getString("title")
                )
                productArray.add(product)
                Log.d("yazan", product.toString())
            }
        }, { error ->
            Log.e("yazan", error.message.toString())
        })

        queue.add(jsonObjectRequest)
    }
}