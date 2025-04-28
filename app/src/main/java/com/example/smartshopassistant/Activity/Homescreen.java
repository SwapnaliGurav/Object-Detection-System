package com.example.smartshopassistant.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.smartshopassistant.Activity.BaseActivity;
import com.example.smartshopassistant.Adapter.PopularAdapter;
import com.example.smartshopassistant.Adapter.SliderAdapter;
import com.example.smartshopassistant.ComparisonFlipkart;
import com.example.smartshopassistant.Domain.ItemsDomain;
import com.example.smartshopassistant.Domain.SliderItems;
import com.example.smartshopassistant.EcommerceActivity;
import com.example.smartshopassistant.MainActivity;
import com.example.smartshopassistant.PopularProducts;
import com.example.smartshopassistant.R;
import com.example.smartshopassistant.SettingActivity;
import com.example.smartshopassistant.databinding.ActivityHomescreenBinding;
import com.example.smartshopassistant.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class    Homescreen extends BaseActivity {
    ImageView amazon,flipkart, messo, myntra,shopsy;
    String str;
    private ActivityHomescreenBinding binding;
    private EditText editText;
    private MeowBottomNavigation meowBottomNavigation;
    private final int ID_PROFILE = 1;
    private final int ID_HOME = 2;
//    private final int ID_SETTING = 3;
    private final int ID_COMPARISON= 3;
    private static final long SLIDE_DELAY = 5000;
    private Handler sliderHandler = new Handler();
    private int currentPage = 0;
    private boolean isSliding = true;
    CardView lapcard, tvcard, headphonecard, watchcard, mobilecard, cameracard;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        binding = ActivityHomescreenBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());


        meowBottomNavigation =findViewById(R.id.nav);


        meowBottomNavigation.show(2,true);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.profile));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_COMPARISON,R.drawable.compare));

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model item) {
//              Toast.makeText(Homescreen.this, "clicked item"+item.getId(), Toast.LENGTH_SHORT).show();
//               Toast.makeText(Homescreen.this, "Clicked item " + item.getId(), Toast.LENGTH_SHORT).show();

                // You can perform additional actions based on the clicked item ID
                switch (item.getId()) {
                    case ID_PROFILE:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        Intent i = new Intent(Homescreen.this, MainActivity.class);
                        startActivity(i);
                       }
                        }, 600); // Adjust the delay time as needed

                        break;

                    case ID_HOME:
                        Intent intent=new Intent(Homescreen.this, Homescreen.class);
                        startActivity(intent);
                        finish();
                        // Handle the Message item click
                        break;
//                    case ID_SETTING:
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i = new Intent(Homescreen.this, SettingActivity.class);
//                                startActivity(i);
////                                finish(); // If you want to finish the current activity
//                            }
//                        }, 600); // Adjust the delay time as needed
//
//                        break;
////                        finish();

                    case ID_COMPARISON:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String str = String.valueOf(editText.getText());
                                if (!str.isEmpty()){
                                Intent k=new Intent(Homescreen.this, ComparisonFlipkart.class);
                                k.putExtra("editText" , str);
                                startActivity(k);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Please Enter A Product Name In Search Box Is you Want to Compare", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Homescreen.this, Homescreen.class);
                                    startActivity(i);
                                }
//                                finish();
                            }
                        }, 600); // Adjust the delay time as needed

                        break;


                }
                return Unit.INSTANCE;           }
        });

//        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(Homescreen.this, "clicked item"+item.getId(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(Homescreen.this, "Clicked item " + item.getId(), Toast.LENGTH_SHORT).show();
//
//                // You can perform additional actions based on the clicked item ID
//                switch (item.getId()) {
//                    case ID_PROFILE:
//
//                        // Handle the Home item click
//                        break;
//                    case ID_HOME:
//                        // Handle the Message item click
//                        break;
//                    case ID_SETTING:
//                        // Handle the Notification item click
//                        break;
//
//                }
//            }
//        });

//        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShowItem(MeowBottomNavigation.Model item) {
//                String name;
//                switch (item.getId()){
//                    case ID_PROFILE: name = "profile";
//                    Intent i = new Intent(Homescreen.this,MainActivity.class);
//                        startActivity(i);
//                        finish();
//                       break;
//
//                    case ID_HOME: name = "home";
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i = new Intent(Homescreen.this, Homescreen.class);
//                                startActivity(i);
////                                finish(); // If you want to finish the current activity
//                            }
//                        }, 600); // Adjust the delay time as needed
//
//                        break;
//
//
//                    case ID_SETTING:name="setting";
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i = new Intent(Homescreen.this, LoginActivity.class);
//                                startActivity(i);
////                                finish(); // If you want to finish the current activity
//                            }
//                        }, 600); // Adjust the delay time as needed
//
//                        break;
//
//
//                }
//
//
//            }
//        });




        Intent intent = getIntent();


        editText = findViewById(R.id.editTextText2);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        startActivity(new Intent(Homescreen.this, MainActivity.class));
                        return true;
                    }
                }
                return false;
            }
        });


        str = getIntent().getStringExtra("result");
        editText.setText(intent.getStringExtra("result"));

Toast.makeText(getApplicationContext(),""+str,Toast.LENGTH_SHORT).show();
        amazon=findViewById(R.id.imageView14);
        flipkart=findViewById(R.id.imageView13);
        messo=findViewById(R.id.imageView15);
        shopsy=findViewById(R.id.imageView12);
        myntra=findViewById(R.id.imageView16);



        amazon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
           if(str==null)
               str=editText.getText().toString();
               startActivity(new Intent(Homescreen.this, EcommerceActivity.class).putExtra
                        ( "url2",  "https://www.amazon.in/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords="+ str));
           str=null;
            }
        });

        flipkart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str==null)
                    str=editText.getText().toString();
                startActivity(new Intent(Homescreen.this, EcommerceActivity.class).putExtra
                        ( "url2",  "https://www.flipkart.com/search?q="+ str +"=search&otracker1=search&marketplace=FLIPKART&as-show=off&as=off"));
                str=null;

            }
        });

        messo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str==null)
                    str=editText.getText().toString();
                startActivity(new Intent(Homescreen.this, EcommerceActivity.class).putExtra
                        ( "url2",  "https://www.meesho.com/" + str + "/pl/3kc"));
                str=null;
            }
        });

        shopsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str==null)
                    str=editText.getText().toString();
                startActivity(new Intent(Homescreen.this, EcommerceActivity.class).putExtra
                        ( "url2",  "https://www.ebay.com/sch/i.html?_from=R40&_trksid=p4432023.m570.l1313&_nkw=" + str ));
                str=null;
            }
        });

        myntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str==null)
                    str=editText.getText().toString();
                startActivity(new Intent(Homescreen.this, EcommerceActivity.class).putExtra
                        ( "url2",  "https://www.myntra.com/"+ str + "?rawQuery="+ str));
                str=null;
            }
        });

        lapcard = findViewById(R.id.lapcard);
        tvcard = findViewById(R.id.tvcard);
        watchcard = findViewById(R.id.watchcard);
        headphonecard = findViewById(R.id.headphonecard);
        mobilecard = findViewById(R.id.mobilecard);
        cameracard = findViewById(R.id.cameracard);


        lapcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "laptop" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });


        tvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "LedTV" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });

        headphonecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "Headphones" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });

        watchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "SmartWatch" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });

        mobilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "mobile" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });

        cameracard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "camera" ;
                Intent intent = new Intent(Homescreen.this, PopularProducts.class);
                intent.putExtra("popularproducts",str);
                startActivity(intent);
            }
        });




        initBanner();
    }


    private void initBanner() {
        DatabaseReference myref = database.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);

                    startSlider();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void banners(ArrayList<SliderItems> items) {
        SliderAdapter sliderAdapter = new SliderAdapter(items, binding.viewpageSlider);
        binding.viewpageSlider.setAdapter(sliderAdapter);
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(3);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewpageSlider.setPageTransformer(compositePageTransformer);

        binding.viewpageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
            }
        });
    }

    private void startSlider() {
        sliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isSliding) {
                    currentPage++;
                    if (currentPage >= binding.viewpageSlider.getAdapter().getItemCount()) {
                        currentPage = 0;
                    }
                    binding.viewpageSlider.setCurrentItem(currentPage, true);
                    sliderHandler.postDelayed(this, SLIDE_DELAY);
                }
            }
        }, SLIDE_DELAY);
    }

    private void stopSlider() {
        isSliding = false;
        sliderHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSlider();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Reset bottom navigation to default item when returning to MainActivity
        meowBottomNavigation.show(ID_HOME,true);

    }
}
