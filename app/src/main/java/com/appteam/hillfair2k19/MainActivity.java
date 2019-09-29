package com.appteam.hillfair2k19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.VolleyError;
//import com.appteam.Wall.WallFragment;
import com.appteam.Wall.WallFragment;
import com.appteam.fragments.ClubsFragment;
import com.appteam.fragments.Coupons;
import com.appteam.fragments.SponsersFragment;

import com.appteam.hillfair2k19.ui.home.HomeFragment;
import com.appteam.hillfair2k19.ui.notifications.NotificationsFragment;

import com.cloudinary.android.MediaManager;
import com.schibsted.spain.parallaxlayerlayout.ParallaxLayerLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    IResult mResultCallback;
    IResult mResultCallbackAndroidNeworking;

    CircleImageView profile;
    ImageButton homeButton;
    ImageButton gamesButton;
    ImageButton scheduleButton;
    ImageButton couponButton;

    TextView homeTextView;
    TextView gamesTextView;
    TextView scheduleTextView;
    TextView couponTextView;

    LinearLayout homeLinearLayout;
    LinearLayout gameLinearLayout;
    LinearLayout scheduleLinearLayout;
    LinearLayout couponLinearLayout;
    ViewPager viewPager;
    ParallaxLayerLayout.TranslationUpdater sensorTranslationUpdater;

    public static int home;
    public static int games;
    public static int schedule;
    public static int coupon;
    public static String fireBaseID;
    public static int currentSelected;

    Button sponsors, club, coreteam, reward;


    private View nav;
    private LottieAnimationView navAnim;
    private TextView title, profileNav, aboutNav, settingNav, sponsorNav, callNav, notifNav, mapNav, hillffairNav, contributorNav, facesmash, leaderboard, coreTeamNav, clubsNav;
    private boolean check = true;
    private RelativeLayout navDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nav = findViewById(R.id.nav);
        navAnim = findViewById(R.id.navAnim);
        navDrawer = findViewById(R.id.navDrawer);
        profileNav = findViewById(R.id.profileNav);
        aboutNav = findViewById(R.id.aboutNav);
        settingNav = findViewById(R.id.settingNav);
        sponsorNav = findViewById(R.id.sponsorNav);
        callNav = findViewById(R.id.callNav);
        notifNav = findViewById(R.id.notifNav);
        hillffairNav = findViewById(R.id.hillffairNav);
        mapNav = findViewById(R.id.mapNav);
        contributorNav = findViewById(R.id.contributorNav);
        facesmash = findViewById(R.id.facesmash);
        leaderboard = findViewById(R.id.leaderboard);
        coreTeamNav = findViewById(R.id.coreTeamNav);
        clubsNav = findViewById(R.id.clubsNav);

        profileNav.setOnClickListener(this);
        aboutNav.setOnClickListener(this);
        callNav.setOnClickListener(this);
        notifNav.setOnClickListener(this);
        mapNav.setOnClickListener(this);
        settingNav.setOnClickListener(this);
        nav.setOnClickListener(this);
        hillffairNav.setOnClickListener(this);
        contributorNav.setOnClickListener(this);
        facesmash.setOnClickListener(this);
        leaderboard.setOnClickListener(this);
        coreTeamNav.setOnClickListener(this);
        clubsNav.setOnClickListener(this);


        final SharedPreferences sharedPreferences = getSharedPreferences("number", Context.MODE_PRIVATE);
        fireBaseID = sharedPreferences.getString("fireBaseId", null);

        FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // final WallFragment faceSmash=new WallFragment(this);

        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
// work here to change Activity fragments (add, remove, etc.).  Example here of adding.
        //  fragmentTransaction.add (R.id.fragmentHolder, faceSmash);
        // fragmentTransaction.commit();

        viewPager = findViewById(R.id.viewpager);
        final SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        linkViews();
        profile = findViewById(R.id.profile);

        SharedPreferences prefs = getSharedPreferences("number", Context.MODE_PRIVATE);
        String check2 = prefs.getString("Image", "https://www.fluigent.com/wp-content/uploads/2018/07/default-avatar-BW.png");
        if (!check2.equals("https://www.fluigent.com/wp-content/uploads/2018/07/default-avatar-BW.png")) {
            Bitmap img = setProfile(check2);
            profile.setImageBitmap(img);
        } else {
            Picasso.with(MainActivity.this).load(check2).resize(80, 80).centerCrop().into(profile);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileMain.class));
            }
        });

        home=homeLinearLayout.getMeasuredWidth();
        games=gameLinearLayout.getMeasuredWidth();
        schedule=scheduleLinearLayout.getMeasuredWidth();
        coupon=couponLinearLayout.getMeasuredWidth();
        animate(gameLinearLayout, gamesTextView);
        animate(scheduleLinearLayout, scheduleTextView);
        animate(couponLinearLayout, couponTextView);
        currentSelected=0;
    }

    //configuring cloudinary
//         Map config = new HashMap();
//         config.put("cloud_name", "dpxfdn3d8");
//         config.put("api_key", "172568498646598");
//         config.put("api_secret", "NNa_bFKyVxW0AB30wL8HVoFxeSs");
//         MediaManager.init(this, config);


//            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


//            WallFragment wallFragment = new WallFragment(this);
//        fragmentTransaction.replace(R.id.fragmentHolder,wallFragment );
//        fragmentTransaction.commit();
//

//        sponsors = findViewById(R.id.sponsors);
//        club = findViewById(R.id.club);
//        coreteam = findViewById(R.id.core);
//        reward = findViewById(R.id.reward);
//        sponsors.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SponsersFragment sponsersFragment = new SponsersFragment(MainActivity.this);
//                fragmentTransaction.add (R.id.fragmentHolder, sponsersFragment);
//                fragmentTransaction.commit();
//            }
//        });
//        club.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ClubsFragment clubsFragment = new ClubsFragment(MainActivity.this);
//                fragmentTransaction.add (R.id.fragmentHolder, clubsFragment);
//                fragmentTransaction.commit ();
//            }
//        });
//        coreteam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                coreteam coreteam = new coreteam(MainActivity.this);
//                fragmentTransaction.add (R.id.fragmentHolder, coreteam);
//                fragmentTransaction.commit ();
//            }
//        });
//        reward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Coupons coupons = new Coupons(MainActivity.this);
//                fragmentTransaction.add (R.id.fragmentHolder, coupons);
//                fragmentTransaction.commit ();
//            }
//        });


//        ParallaxLayerLayout parallaxLayout=findViewById(R.id.parallaxLayout);
//
//        sensorTranslationUpdater = new SensorTranslationUpdater(this);
//        parallaxLayout.setTranslationUpdater(sensorTranslationUpdater);


//        gameLinearLayout.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
//
//        gameLinearLayout.getBackground().setColorFilter(Color.parseColor("#00FFFFFF"), PorterDuff.Mode.SRC_ATOP);





//        Intent intent=new Intent(MainActivity.this,QuizCategories.class);
//        startActivity(intent);
//        MediaManager.init(this);


    //    FragmentManager fragmentManager = getSupportFragmentManager();
    //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


//        FaceSmash fragment = new FaceSmash();
//        com.appteam.hillfair2k19.fragments.SponsersFragment fragment = new com.appteam.hillfair2k19.fragments.SponsersFragment(this);
////        coreteam fragment = new coreteam(this);


//        fragmentTransaction.add(R.id.fragmentHolder, fragment);
//        fragmentTransaction.commit();

    // For Volley Api Users

    //Initialising
//    initVolleyCallback();
//
//    VolleyService mVolleyService = new VolleyService(mResultCallback, this);

    //GET REQUEST SINGLE JSON OBJECT
//        mVolleyService.getJsonObjectDataVolley("GETCALL","https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

//        GET REQUEST FOR JSON Array
//        mVolleyService.getJsonArrayDataVolley("GETJSONARRAYLIFESAVER","https://lifesaverapp.herokuapp.com/controlpolice");


    // POST REQUEST JSON BODY
//        JSONObject jsonObject = new JSONObject();
//        try {
//    jsonObject.put("latitude","98.0");
//    jsonObject.put("longitude","98.0");
//    jsonObject.put("station","Austrailia");
//    jsonObject.put("mobile","12345678910");
//
//} catch (JSONException e) {
//            e.printStackTrace();
//        }
//        mVolleyService.postJsonDataVolley("POSTJSONDATALIFESAVER","https://lifesaverapp.herokuapp.com/controlpolice",jsonObject);


    //For Android Networking Users

    //Initialising
//        initAndroidNetworkingCallback();
//        AndroidNetworking.initialize(MainActivity.this);
//        com.appteam.hillfair2k19.AndroidNetworking androidNetworking = new com.appteam.hillfair2k19.AndroidNetworking(mResultCallbackAndroidNeworking,this);

//                 GET REQUEST SINGLE JSON OBJECT
//        androidNetworking.getJsonObjectAndroidNetworking("GETCALL","https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");


    //GET REQUEST FOR JSON ARRAY
//        androidNetworking.getJsonArrayAndroidNetworking("GETJSONARRAYFROMLIIFESAVER","https://lifesaverapp.herokuapp.com/controlpolice");


    //POST REQUEST A JSON OBJECT OR BODY
//        JSONObject jsonObject = new JSONObject();
//        try {
//    jsonObject.put("latitude","98.0");
//    jsonObject.put("longitude","98.0");
//    jsonObject.put("station","Hamirpur");
//    jsonObject.put("mobile","1234567890");
//
//} catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        androidNetworking.postJsondataAndroidNetworking("POSTINGJOSNBODYTOLIFESAVER","https://lifesaverapp.herokuapp.com/controlpolice",jsonObject);

    public Bitmap setProfile(String images) {
        byte[] decodedByte = Base64.decode(images, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response, JSONArray jsonArray) {
                if (response != null) {
                    //JsonObject
//                    Toast.makeText(MainActivity.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                } else {
                    //JsonArray
//                    Toast.makeText(MainActivity.this, String.valueOf(jsonArray), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
            }
        };

    }

    void initAndroidNetworkingCallback() {
        mResultCallbackAndroidNeworking = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response, JSONArray jsonArray) {
                if (response != null) {
                    //JsonObject
//                    Toast.makeText(MainActivity.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
                } else {
                    //JsonArray
//                    Toast.makeText(MainActivity.this, String.valueOf(jsonArray), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

    public void linkViews() {

        homeButton = findViewById(R.id.homeButton);
        gamesButton = findViewById(R.id.gamesButton);
        scheduleButton = findViewById(R.id.scheduleButton);
        couponButton = findViewById(R.id.couponButton);

        homeTextView = findViewById(R.id.homeTextView);
        gamesTextView = findViewById(R.id.gameTextView);
        scheduleTextView = findViewById(R.id.scheduleTextView);
        couponTextView = findViewById(R.id.couponTextView);

        homeLinearLayout = findViewById(R.id.homeLayout);
        gameLinearLayout = findViewById(R.id.gamesLayout);
        scheduleLinearLayout = findViewById(R.id.scheduleLayout);
        couponLinearLayout = findViewById(R.id.couponsLayout);


    }


    public void handleClick(View view) {
        int id = view.getId();
        LinearLayout l;
        FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.homeButton:
            case R.id.homeTextView:

                currentSelected = 0;
                l = findViewById(R.id.homeLayout);
                //TODO: load homepage
                WallFragment wallFragment = new WallFragment(this);
                fragmentTransaction.replace(R.id.fragmentHolder, wallFragment);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                animateReverse(l, homeTextView);

                animate(gameLinearLayout, gamesTextView);
                animate(scheduleLinearLayout, scheduleTextView);
                animate(couponLinearLayout, couponTextView);

                //  Toast.makeText(this,"" +currentSelected,Toast.LENGTH_SHORT).show();


                break;
            case R.id.gamesButton:
            case R.id.gameTextView:
                currentSelected = 1;

                l = findViewById(R.id.gamesLayout);
                //TODO: load gamepage

                QuizCategories quizCategories = new QuizCategories(this);
                fragmentTransaction.replace(R.id.fragmentHolder, quizCategories);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                animateReverse(l, gamesTextView);

                animate(homeLinearLayout, homeTextView);
                animate(scheduleLinearLayout, scheduleTextView);
                animate(couponLinearLayout, couponTextView);


                //   Toast.makeText(this,"" +currentSelected,Toast.LENGTH_SHORT).show();


                break;
            case R.id.scheduleButton:
            case R.id.scheduleTextView:
                currentSelected = 2;

                l = findViewById(R.id.scheduleLayout);
                //TODO: load schedulepage

                animate(gameLinearLayout, gamesTextView);
                animate(homeLinearLayout, homeTextView);
                animate(couponLinearLayout, couponTextView);
                animateReverse(l, scheduleTextView);
                //  Toast.makeText(this,"" +currentSelected,Toast.LENGTH_SHORT).show();


                break;
            case R.id.couponButton:
            case R.id.couponTextView:
                currentSelected = 3;

                l = findViewById(R.id.couponsLayout);
                //TODO: load couponpage

                Coupons coupons = new Coupons();
                fragmentTransaction.replace(R.id.fragmentHolder, coupons);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                animate(gameLinearLayout, gamesTextView);
                animate(scheduleLinearLayout, scheduleTextView);
                animate(homeLinearLayout, homeTextView);

                animateReverse(l, couponTextView);
                // Toast.makeText(this,"" +currentSelected,Toast.LENGTH_SHORT).show();


                break;

        }


    }


    public void animate(final LinearLayout l, final View txtv) {
        txtv.setVisibility(View.GONE);
        ValueAnimator anim = ValueAnimator.ofInt(l.getMeasuredWidth(), 56);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = l.getLayoutParams();
                layoutParams.width = val;
                l.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(200);
        anim.start();

        l.getBackground().setColorFilter(Color.parseColor("#00FFFFFF"), PorterDuff.Mode.SRC_ATOP);


    }

    public void animateReverse(final LinearLayout l, final View txtv) {
        txtv.setVisibility(View.VISIBLE);
        int x = l.getId();
        int w;
        if (x == R.id.homeLayout) w = home;
        else if (x == R.id.gamesLayout) w = games;
        else if (x == R.id.scheduleLayout) w = schedule;
        if (x == R.id.couponsLayout) w = coupon;
        else w = schedule;

        l.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        ValueAnimator anim = ValueAnimator.ofInt(56, 300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = l.getLayoutParams();
                layoutParams.width = val;
                l.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(150);
        anim.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.profileNav:
                startActivity(new Intent(this, ProfileMain.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.notifNav:
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, notificationsFragment);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;
            case R.id.contributorNav:
                startActivity(new Intent(MainActivity.this, ContributorsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.facesmash:
                FaceSmash faceSmash = new FaceSmash();
                fragmentTransaction.replace(R.id.fragmentHolder, faceSmash);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;
            case R.id.leaderboard:
                LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, leaderboardFragment);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;

            case R.id.coreTeamNav:
                coreteam coreteam = new coreteam();
                fragmentTransaction.replace(R.id.fragmentHolder, coreteam);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;
            case R.id.sponsorNav:
                SponsersFragment sponsersFragment = new SponsersFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, sponsersFragment);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;
            case R.id.clubsNav:
                ClubsFragment clubsFragment = new ClubsFragment();
                fragmentTransaction.replace(R.id.fragmentHolder, clubsFragment);
                fragmentTransaction.commit();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                navDrawer();
                break;


            case R.id.nav:
                navDrawer();
                break;

        }
    }


    private void navDrawer() {
        if (check) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator.setDuration(1000);
            valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedFraction() <= 0.7f)
                        navAnim.setProgress(animation.getAnimatedFraction());
                }
            });
            valueAnimator.start();
            check = false;
            navDrawer.setVisibility(View.VISIBLE);
            profileNav.setVisibility(View.VISIBLE);
            sponsorNav.setVisibility(View.GONE);
            notifNav.setVisibility(View.VISIBLE);
            contributorNav.setVisibility(View.VISIBLE);
            facesmash.setVisibility(View.VISIBLE);
            leaderboard.setVisibility(View.VISIBLE);
            coreTeamNav.setVisibility(View.VISIBLE);
            clubsNav.setVisibility(View.VISIBLE);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(navDrawer, "alpha", 0, 1);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(aboutNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(profileNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(settingNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(sponsorNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator1a = ObjectAnimator.ofFloat(mapNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator2a = ObjectAnimator.ofFloat(notifNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator3a = ObjectAnimator.ofFloat(callNav, "alpha", 0, 1);
            ObjectAnimator objectAnimator3aa = ObjectAnimator.ofFloat(hillffairNav, "alpha", 0, 1);

            ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(aboutNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(profileNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(settingNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(sponsorNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator4a = ObjectAnimator.ofFloat(callNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator5a = ObjectAnimator.ofFloat(mapNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator6a = ObjectAnimator.ofFloat(notifNav, "translationY", 15f, 0f);
            ObjectAnimator objectAnimator6aa = ObjectAnimator.ofFloat(hillffairNav, "translationY", 15f, 0f);

            objectAnimator5.setDuration(500);
            objectAnimator4.setDuration(500);
            objectAnimator3.setDuration(500);
            objectAnimator6.setDuration(500);
            objectAnimator7.setDuration(500);
            objectAnimator8.setDuration(500);
            objectAnimator9.setDuration(500);
            objectAnimator2.setDuration(500);
            objectAnimator.setDuration(500);

            objectAnimator1a.setDuration(500);
            objectAnimator2a.setDuration(500);
            objectAnimator3aa.setDuration(500);
            objectAnimator3a.setDuration(500);
            objectAnimator4a.setDuration(500);
            objectAnimator5a.setDuration(500);
            objectAnimator6a.setDuration(500);
            objectAnimator6aa.setDuration(500);

            objectAnimator2.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator4.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator5.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator7.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator8.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator9.setInterpolator(new AnticipateOvershootInterpolator());

            objectAnimator1a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator2a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3aa.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator4a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator5a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6aa.setInterpolator(new AnticipateOvershootInterpolator());

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8, objectAnimator9, objectAnimator1a, objectAnimator2a, objectAnimator3a, objectAnimator3aa, objectAnimator4a, objectAnimator5a, objectAnimator6a, objectAnimator6aa);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    navDrawer.setVisibility(View.VISIBLE);
                    profileNav.setVisibility(View.VISIBLE);
                    sponsorNav.setVisibility(View.GONE);
                    notifNav.setVisibility(View.VISIBLE);
                    contributorNav.setVisibility(View.VISIBLE);
                    facesmash.setVisibility(View.VISIBLE);
                    leaderboard.setVisibility(View.VISIBLE);
                    coreTeamNav.setVisibility(View.VISIBLE);
                    clubsNav.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        } else {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator.setDuration(750);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedFraction() <= 0.7f)
                        navAnim.setProgress(0.7f - animation.getAnimatedFraction());
                }
            });
            valueAnimator.start();
            check = true;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(navDrawer, "alpha", 1, 0);


            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(aboutNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(profileNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(settingNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(sponsorNav, "alpha", 1, 0);

            ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(aboutNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(profileNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(settingNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator9 = ObjectAnimator.ofFloat(sponsorNav, "translationY", 0f, 15f);

            ObjectAnimator objectAnimator4a = ObjectAnimator.ofFloat(callNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator5a = ObjectAnimator.ofFloat(mapNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator6a = ObjectAnimator.ofFloat(notifNav, "translationY", 0f, 15f);
            ObjectAnimator objectAnimator6aa = ObjectAnimator.ofFloat(hillffairNav, "translationY", 0f, 15f);

            ObjectAnimator objectAnimator1a = ObjectAnimator.ofFloat(mapNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator2a = ObjectAnimator.ofFloat(notifNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator3aa = ObjectAnimator.ofFloat(hillffairNav, "alpha", 1, 0);
            ObjectAnimator objectAnimator3a = ObjectAnimator.ofFloat(callNav, "alpha", 1, 0);

            objectAnimator.setDuration(250);
            objectAnimator5.setDuration(250);
            objectAnimator4.setDuration(250);
            objectAnimator3.setDuration(250);
            objectAnimator6.setDuration(250);
            objectAnimator7.setDuration(250);
            objectAnimator8.setDuration(250);
            objectAnimator9.setDuration(250);
            objectAnimator2.setDuration(250);

            objectAnimator1a.setDuration(250);
            objectAnimator2a.setDuration(250);
            objectAnimator3a.setDuration(250);
            objectAnimator3aa.setDuration(250);
            objectAnimator4a.setDuration(250);
            objectAnimator5a.setDuration(250);
            objectAnimator6a.setDuration(250);
            objectAnimator6aa.setDuration(250);

            objectAnimator1a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator2a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3aa.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator4a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator5a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6a.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6aa.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator2.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator3.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator4.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator5.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator6.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator7.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator8.setInterpolator(new AnticipateOvershootInterpolator());
            objectAnimator9.setInterpolator(new AnticipateOvershootInterpolator());

            objectAnimator.setStartDelay(0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8, objectAnimator9, objectAnimator1a, objectAnimator2a, objectAnimator3a, objectAnimator3aa, objectAnimator4a, objectAnimator5a, objectAnimator6a, objectAnimator6aa);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    navDrawer.setVisibility(View.GONE);
                    profileNav.setVisibility(View.GONE);
                    aboutNav.setVisibility(View.GONE);
                    settingNav.setVisibility(View.GONE);
                    sponsorNav.setVisibility(View.GONE);
                    callNav.setVisibility(View.GONE);
                    notifNav.setVisibility(View.GONE);
                    hillffairNav.setVisibility(View.GONE);
                    mapNav.setVisibility(View.GONE);
                    facesmash.setVisibility(View.GONE);
                    leaderboard.setVisibility(View.GONE);
                    coreTeamNav.setVisibility(View.GONE);
                    clubsNav.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
