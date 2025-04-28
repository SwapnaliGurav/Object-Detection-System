package com.example.smartshopassistant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PopularProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_products)
        val imlist = ArrayList<String>()
        val tlist = ArrayList<String>()
        val dtexList = ArrayList<String>()
        val ftexList = ArrayList<String>()
        val priceList = ArrayList<String>()
        val price1List = ArrayList<String>()
        val hrefList = ArrayList<String>()
        val href1List = ArrayList<String>()



        // Initialize image views
        val imageViewIds = listOf(
            R.id.im1, R.id.im2, R.id.im3, R.id.im4, R.id.im5,
            R.id.im6, R.id.im7, R.id.im8, R.id.im9, R.id.im10
        )
        for (id in imageViewIds) {
            val imageView = findViewById<ImageView>(id)
            val layoutParams = imageView.layoutParams.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            imageView.layoutParams = layoutParams
            imageView.requestLayout()
        }

        // Initialize text views
        val dtexTextViews = listOf(
            R.id.dtex1, R.id.dtex2, R.id.dtex3, R.id.dtex4, R.id.dtex5,
            R.id.dtex6, R.id.dtex7, R.id.dtex8, R.id.dtex9, R.id.dtex10
        )

        // Initialize price text views
        val priceTextViews = listOf(
            R.id.price1, R.id.price2, R.id.price3, R.id.price4, R.id.price5,
            R.id.price6, R.id.price7, R.id.price8, R.id.price9, R.id.price10
        )

        // Clear text views initially
        for (id in dtexTextViews + priceTextViews) {
            findViewById<TextView>(id).text = ""
        }


        val intent = getIntent()
        var str = getIntent().getStringExtra("popularproducts")



        doAsync {
            // Fetch data from Flipkart
            val docFlipkart =
                Jsoup.connect("https://www.flipkart.com/search?q=" + str + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=off&as=off&as-pos=1&as-type=HISTORY")
                    .get()

            val class1Info = docFlipkart.getElementsByClass("slAVV4")
            val class2Info = docFlipkart.getElementsByClass("tUxRFH")
            val class3Info = docFlipkart.getElementsByClass("_1sdMkc LFEi7Z")

            if (class1Info.isNotEmpty()) {
                // Handle elements with class "class1"
                for (i in class1Info) {
                    val im = i.getElementsByTag("img").attr("src")
                    val dtex = i.getElementsByClass("wjcEIp").text()
                    val price = i.getElementsByClass("Nx9bqj")
                        .text() // Assuming this is the price class, adjust if needed
                    val anchor = i.select("a")
                    val href = anchor.attr("href")
                    Log.d("Nik1", "onCreate: " + href)
                    hrefList.add(href)
                    imlist.add(im)
                    dtexList.add(dtex)
                    priceList.add(price)
                }
            } else if (class2Info.isNotEmpty()) {
                // Handle elements with class "class2"
                for (i in class2Info) {
                    val im = i.getElementsByTag("img").attr("src")
                    val dtex = i.getElementsByClass("KzDlHZ").text()
                    val price = i.getElementsByClass("Nx9bqj _4b5DiR").text()
                    val anchor = i.select("a")
                    val href = anchor.attr("href")
                    Log.d("Nik", "onCreate: " + href)
                    hrefList.add(href)
                    imlist.add(im)
                    dtexList.add(dtex)
                    priceList.add(price)
                }
            } else if (class3Info.isNotEmpty()) {
                // Handle elements with class "class3"
                for (i in class3Info) {
                    val im = i.getElementsByTag("img").attr("src")
                    val dtex = i.getElementsByClass("WKTcLC").text()
                    val price = i.getElementsByClass("Nx9bqj").text()
                    val anchor = i.select("a")
                    val href = anchor.attr("href")
                    Log.d("Nik2", "onCreate: " + href)
                    hrefList.add(href)
                    imlist.add(im)
                    dtexList.add(dtex)
                    priceList.add(price)
                }
            }


            uiThread {
                // Display images using Glide library
                for ((index, imageUrl) in imlist.withIndex()) {
                    when (index) {
                        in 1 until imageViewIds.size -> {
                            when (index) {
                                1 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im1))

                                2 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im3))

                                3 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im5))

                                4 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im7))

                                5 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im9))
                            }
                        }
                    }
                }
                // Display headings and prices in TextViews
                for ((index, heading) in dtexList.withIndex()) {
                    when (index) {
                        in 1 until dtexTextViews.size -> {
                            when (index) {
                                1 -> findViewById<TextView>(R.id.dtex1).text = heading
                                2 -> findViewById<TextView>(R.id.dtex3).text = heading
                                3 -> findViewById<TextView>(R.id.dtex5).text = heading
                                4 -> findViewById<TextView>(R.id.dtex7).text = heading
                                5 -> findViewById<TextView>(R.id.dtex9).text = heading
                            }
                        }
                    }
                }
                // Display prices in TextViews
                for ((index, price) in priceList.withIndex()) {
                    when (index) {
                        in 1 until priceTextViews.size -> {
                            when (index) {
                                1 -> findViewById<TextView>(R.id.price1).text = price
                                2 -> findViewById<TextView>(R.id.price3).text = price
                                3 -> findViewById<TextView>(R.id.price5).text = price
                                4 -> findViewById<TextView>(R.id.price7).text = price
                                5 -> findViewById<TextView>(R.id.price9).text = price
                            }
                        }
                    }
                }
            }
        }

        doAsync {
            val doc =
                Jsoup.connect("https://www.amazon.in/s?k=" + str + "&crid=35VWVXGCRWCLC&sprefix=" + str + "%2Caps%2C1107&ref=nb_sb_noss_2")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .get()

            //Log.d("TAG", "onCreate: "+doc.toString());

            val class1Info =
                doc.getElementsByClass("sg-col-20-of-24 s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 AdHolder sg-col s-widget-spacing-small sg-col-12-of-16")
            val class2Info =
                doc.getElementsByClass("puis-card-container s-card-container s-overflow-hidden aok-relative puis-expand-height puis-include-content-margin puis puis-vx67qn71fe2k429m74ykw1e4z1 s-latency-cf-section puis-card-border")
            val class3Info =
                doc.getElementsByClass("sg-col-4-of-24 sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 AdHolder sg-col s-widget-spacing-small sg-col-4-of-20")
            val class4Info =
                doc.getElementsByClass("sg-col-20-of-24 s-result-item s-asin sg-col-0-of-12 sg-col-16-of-20 sg-col s-widget-spacing-small sg-col-12-of-16")
            val class5Info =
                doc.getElementsByClass("sg-col-4-of-24 sg-col-4-of-12 s-result-item s-asin sg-col-4-of-16 AdHolder sg-col s-widget-spacing-small sg-col-4-of-20")

            if (class1Info.isNotEmpty()) {
                // Handle elements with class "class1"
                for (i in class1Info) {
                    val t = i.getElementsByTag("img").attr("src")
                    Log.d("Kr", "onCreate: " + t)
                    val ftex =
                        i.getElementsByClass("a-size-mini a-spacing-none a-color-base s-line-clamp-2")
                            .text() // Assuming the class for heading is "s1Q9rs", you may need to adjust this
                    Log.d("Krunal", "onCreate: " + ftex)
                    val price1 = i.getElementsByClass("a-price-whole")
                        .text() // Assuming the class for price is "s-item__price", adjust if needed
                    val anchor = i.select("a")
                    val href1 = anchor.attr("href")
                    Log.d("Nika", "onCreate: " + href1)
                    href1List.add(href1)


////                     val pc = price1.toDouble()
////                    Log.d("Krun", "onCreate: "+pc)
//                    val demo = 50.0
//                    val convertprice = fetchExchangeRateAndConvert(price1)
//
//                    Log.d("Krun", "onCreate: "+convertprice)

//                    val priceDouble = price1.toDoubleOrNull()
//                    if (priceDouble != null) {
//                        val convertprice = fetchExchangeRateAndConvert(priceDouble)
//                        Log.d("Krun", "Converted Price: $convertprice")
//                    } else {
//                        Log.e("Krun", "Error converting price to double: $price1")
//                    }
                    tlist.add(t)
                    ftexList.add(ftex)
                    price1List.add(price1)

//                    price1List.add(convertprice.toString())
                }
            } else if (class2Info.isNotEmpty()) {
                // Handle elements with class "class2"
                for (i in class2Info) {
                    val t = i.getElementsByTag("img").attr("src")
                    Log.d("Krunall", "onCreate: " + t)
                    val ftex = i.getElementsByClass("a-size-base-plus a-color-base a-text-normal")
                        .text() // Assuming the class for heading is "s1Q9rs", you may need to adjust this
                    val price1 = i.getElementsByClass("a-offscreen").text()
                    val anchor = i.select("a")
                    val href1 = anchor.attr("href")
                    Log.d("Nika1", "onCreate: " + href1)
                    href1List.add(href1)
                    tlist.add(t)
                    ftexList.add(ftex)
                    price1List.add(price1)
                }
            } else if (class3Info.isNotEmpty()) {
                // Handle elements with class "class2"
                for (i in class3Info) {
                    val t = i.getElementsByTag("img").attr("src")
                    val ftex =
                        i.getElementsByClass("a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal")
                            .text() // Assuming the class for heading is "s1Q9rs", you may need to adjust this
                    val price1 = i.getElementsByClass("a-price-whole").text()
                    val anchor = i.select("a")
                    val href1 = anchor.attr("href")
                    Log.d("Nika2", "onCreate: " + href1)
                    href1List.add(href1)
                    tlist.add(t)
                    ftexList.add(ftex)
                    price1List.add(price1)
                }
            } else if (class4Info.isNotEmpty()) {
                // Handle elements with class "class4"
                for (i in class4Info) {
                    val t = i.getElementsByTag("img").attr("src")
                    //Log.d("Krunall", "onCreate: "+t)
                    val ftex =
                        i.getElementsByClass("a-size-mini a-spacing-none a-color-base s-line-clamp-2")
                            .text() // Assuming the class for heading is "s1Q9rs", you may need to adjust this
                    val price1 = i.getElementsByClass("a-price-whole").text()
                    val anchor = i.select("a")
                    val href1 = anchor.attr("href")
                    Log.d("Nika3", "onCreate: " + href1)
                    href1List.add(href1)
                    tlist.add(t)
                    ftexList.add(ftex)
                    price1List.add(price1)
                }
            } else if (class5Info.isNotEmpty()) {
                // Handle elements with class "class5"
                for (i in class5Info) {
                    val t = i.getElementsByTag("img").attr("src")
                    Log.d("Krunall", "onCreate: " + t)
                    val ftex = i.getElementsByClass("a-size-base-plus a-color-base a-text-normal")
                        .text() // Assuming the class for heading is "s1Q9rs", you may need to adjust this
                    val price1 = i.getElementsByClass("a-price-whole").text()
                    val anchor = i.select("a")
                    val href1 = anchor.attr("href")
                    Log.d("Nika4", "onCreate: " + href1)
                    href1List.add(href1)
                    tlist.add(t)
                    ftexList.add(ftex)
                    price1List.add(price1)
                }
            } else {
                // Handle other cases or fallback
                println("No elements with class 'class1' or 'class2' found.")
            }

            uiThread {
                // Display images using Glide library
                for ((index, imageUrl) in tlist.withIndex()) {
                    when (index) {
                        in 1 until imageViewIds.size -> {
                            when (index) {
                                1 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im2))

                                2 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im4))

                                3 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im6))

                                4 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im8))

                                5 -> Glide.with(this@PopularProducts).load(imageUrl)
                                    .into(findViewById(R.id.im10))
                            }
                        }
                    }
                }
                // Display headings and prices in TextViews
                for ((index, heading) in ftexList.withIndex()) {
                    when (index) {
                        in 1 until dtexTextViews.size -> {
                            when (index) {
                                1 -> findViewById<TextView>(R.id.dtex2).text = heading
                                2 -> findViewById<TextView>(R.id.dtex4).text = heading
                                3 -> findViewById<TextView>(R.id.dtex6).text = heading
                                4 -> findViewById<TextView>(R.id.dtex8).text = heading
                                5 -> findViewById<TextView>(R.id.dtex10).text = heading
                            }
                        }
                    }
                }
                // Display prices in TextViews
                for ((index, price1) in price1List.withIndex()) {
                    when (index) {
                        in 1 until priceTextViews.size -> {
                            when (index) {
                                1 -> findViewById<TextView>(R.id.price2).text = price1
                                2 -> findViewById<TextView>(R.id.price4).text = price1
                                3 -> findViewById<TextView>(R.id.price6).text = price1
                                4 -> findViewById<TextView>(R.id.price8).text = price1
                                5 -> findViewById<TextView>(R.id.price10).text = price1
                            }
                        }
                    }
                }
            }

        }

        val linearfbtn1 = findViewById<LinearLayout>(R.id.sec1)
        val linearfbtn2 =findViewById<LinearLayout>(R.id.sec3)
        val linearfbtn3 =findViewById<LinearLayout>(R.id.sec5)
        val linearfbtn4 =findViewById<LinearLayout>(R.id.sec7)
        val linearfbtn5 =findViewById<LinearLayout>(R.id.sec9)

        val linearabtn1 = findViewById<LinearLayout>(R.id.sec2)
        val linearabtn2 = findViewById<LinearLayout>(R.id.sec4)
        val linearabtn3 = findViewById<LinearLayout>(R.id.sec6)
        val linearabtn4 = findViewById<LinearLayout>(R.id.sec8)
        val linearabtn5 = findViewById<LinearLayout>(R.id.sec10)



        linearfbtn1.setOnClickListener {
            val baseUrl = "https://www.flipkart.com"
            val link = hrefList[1] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearfbtn2.setOnClickListener {
            val baseUrl = "https://www.flipkart.com"
            val link = hrefList[2] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearfbtn3.setOnClickListener {
            val baseUrl = "https://www.flipkart.com"
            val link = hrefList[3] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearfbtn4.setOnClickListener {
            val baseUrl = "https://www.flipkart.com"
            val link = hrefList[4] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearfbtn5.setOnClickListener {
            val baseUrl = "https://www.flipkart.com"
            val link = hrefList[5] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }


        linearabtn1.setOnClickListener {
            val baseUrl = "https://www.amazon.in"
            val link = href1List[1] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearabtn2.setOnClickListener {
            val baseUrl = "https://www.amazon.in"
            val link = href1List[2] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearabtn3.setOnClickListener {
            val baseUrl = "https://www.amazon.in"
            val link = href1List[3] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearabtn4.setOnClickListener {
            val baseUrl = "https://www.amazon.in"
            val link = href1List[4] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }

        linearabtn5.setOnClickListener {
            val baseUrl = "https://www.amazon.in"
            val link = href1List[5] // Get the item from ArrayList using index
            val url = baseUrl + link
            val intent = Intent(this@PopularProducts, EcommerceActivity::class.java)
            intent.putExtra("url2", url)
            startActivity(intent)
        }


    }

    // Start the home page activity
//    private fun  fetchExchangeRateAndConvert(priceUSD: Double): Double {
//        val apiUrl = "https://api.exchangerate-api.com/v4/latest/USD"
//        val url = URL(apiUrl)
//        var priceINR=0
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val connection = url.openConnection() as HttpURLConnection
//                val responseCode = connection.responseCode
//
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
//                    val response = StringBuilder()
//                    var inputLine: String?
//
//                    while (inputStream.readLine().also { inputLine = it } != null) {
//                        response.append(inputLine)
//                    }
//                    inputStream.close()
//
//                    val exchangeData = JSONObject(response.toString())
//                    val exchangeRate = exchangeData.getJSONObject("rates").getDouble("INR")
//
//                    priceINR = (priceUSD * exchangeRate).roundToInt()
//
//
//
//
//                    // Update UI on the main thread
////                    launch(Dispatchers.Main) {
////                        textViewPriceUSD.text = "Price in USD: $$priceUSD"
////                        textViewPriceINR.text = "Price in INR: ₹$priceINR"
////                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        return priceINR.toDouble()
//    }
//private fun fetchExchangeRateAndConvert(priceUSD: Double): Double {
//    val apiUrl = "https://v6.exchangerate-api.com/v6/19c5cdf93c213c74f42ba4fc/latest/USD"
//    val url = URL(apiUrl)
//
//    try {
//        val connection = url.openConnection() as HttpURLConnection
//        connection.requestMethod = "GET"
//
//        val responseCode = connection.responseCode
//
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            val reader = BufferedReader(InputStreamReader(connection.inputStream))
//            val response = StringBuilder()
//            var line: String?
//
//            while (reader.readLine().also { line = it } != null) {
//                response.append(line)
//            }
//
//            reader.close()
//
//            val exchangeData = JSONObject(response.toString())
//            val exchangeRate = exchangeData.getJSONObject("rates").getDouble("INR")
//
//            return priceUSD.toDouble() * exchangeRate
//        }
//    } catch (e: IOException) {
//        e.printStackTrace()
//    } catch (e: JSONException) {
//        e.printStackTrace()
//    }
//    return 0.0
//}

    private fun fetchExchangeRateAndConvert(priceUSD: String?): Double {
        val apiUrl = "https://v6.exchangerate-api.com/v6/19c5cdf93c213c74f42ba4fc/latest/USD"
        val url = URL(apiUrl)

        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()

                val exchangeData = JSONObject(response.toString())
                val ratesObject = exchangeData.optJSONObject("conversion_rates")
                if (ratesObject != null) {
                    val exchangeRate = ratesObject.optDouble("INR")
                    if (exchangeRate != 0.0 && priceUSD != null) {
                        // Remove commas from the price string and convert to Double
                        val priceWithoutCommas = priceUSD.replace(",", "")
                        val priceDouble = priceWithoutCommas.toDoubleOrNull()

                        if (priceDouble != null) {
                            return priceDouble * exchangeRate
                        } else {
                            println("Error: Invalid price format after removing commas.")
                        }
                    } else {
                        println("Error: Exchange rate not found for INR.")
                    }
                } else {
                    println("Error: Rates object not found in API response.")
                }
            } else {
                println("Error: HTTP response code is not OK.")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println("Error: IOException occurred.")
        } catch (e: JSONException) {
            e.printStackTrace()
            println("Error: JSONException occurred.")
        }
        return 0.0
    }

}

