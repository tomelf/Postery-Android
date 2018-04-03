package edu.purdue.posterymobile.postery;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import edu.purdue.posterymobile.postery.json.JSONParser;

public class HomeActivity extends Activity {
    private List<CardInfo> cardList;
    private RecyclerView recList;
    private SwipeRefreshLayout refreshLayout;
    private int currentCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupUI();
    }

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private void setupUI() {
        // setup category buttons
        resetCategoryButtons(R.id.bt_category_1);
        currentCategoryId = R.id.bt_category_1;

        // setup RefreshLayout
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupCardlist(currentCategoryId);
            }
        });
        final LinearLayoutManager layoutParams = new LinearLayoutManager(this);

        // setup RecycleView
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(layoutParams);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setOnScrollListener(new RecyclerView.OnScrollListener(){
            public void onScrollStateChanged(int newState) {
            }
            public void onScrolled(int dx, int dy) {
                refreshLayout.setEnabled(layoutParams.findFirstVisibleItemPosition() == 0);
            }
        });

        // load card list
        cardList = new ArrayList<CardInfo>();
        setupCardlist(currentCategoryId);
    }

    class DownloadCardsTask extends AsyncTask<String, String, List<CardInfo>> {

        @Override
        protected List<CardInfo> doInBackground(String... jsonUrl) {
            JSONParser parser = new JSONParser(jsonUrl[0]);
            List<CardInfo> cards = parser.loadCardsFromJson();
            return cards;
        }

        @Override
        protected void onPostExecute(List<CardInfo> list) {
            cardList.clear();
            cardList.addAll(list);
            recList.setAdapter(new CardAdapter(cardList));
            refreshLayout.setRefreshing(false);
        }
    }

    public void setupCardlist(int category_id) {
        currentCategoryId = category_id;
        // load card list
        String jsonUrl = "";
        switch(category_id) {
            case R.id.bt_category_1:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/academy.json";
                break;
            case R.id.bt_category_2:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/career.json";
                break;
            case R.id.bt_category_3:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/meetup.json";
                break;
            case R.id.bt_category_4:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/movie.json";
                break;
            case R.id.bt_category_5:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/music.json";
                break;
            case R.id.bt_category_6:
                jsonUrl = "http://web.ics.purdue.edu/~yang798/sport.json";
                break;
        }
//        new DownloadCardsTask().execute(jsonUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    private void resetCategoryButtons(int rid) {
        LinearLayout upperLayout = (LinearLayout)findViewById(R.id.UpperLayout);

        Button bt1 = (Button) findViewById(R.id.bt_category_1);
        Button bt2 = (Button) findViewById(R.id.bt_category_2);
        Button bt3 = (Button) findViewById(R.id.bt_category_3);
        Button bt4 = (Button) findViewById(R.id.bt_category_4);
        Button bt5 = (Button) findViewById(R.id.bt_category_5);
        Button bt6 = (Button) findViewById(R.id.bt_category_6);

        bt1.setClickable(true);
        bt2.setClickable(true);
        bt3.setClickable(true);
        bt4.setClickable(true);
        bt5.setClickable(true);
        bt6.setClickable(true);

        bt1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.academy_icon_dark), null, null);
        bt2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.career_icon_dark), null, null);
        bt3.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.meetup_icon_dark), null, null);
        bt4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.movie_icon_dark), null, null);
        bt5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.music_icon_dark), null, null);
        bt6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.sport_icon_dark), null, null);

        switch(rid) {
            case R.id.bt_category_1:
                bt1.setClickable(false);
                bt1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.academy_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.academy_banner));
                break;
            case R.id.bt_category_2:
                bt2.setClickable(false);
                bt2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.career_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.career_banner));
                break;
            case R.id.bt_category_3:
                bt3.setClickable(false);
                bt3.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.meetup_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.meetup_banner));
                break;
            case R.id.bt_category_4:
                bt4.setClickable(false);
                bt4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.movie_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.movie_banner));
                break;
            case R.id.bt_category_5:
                bt5.setClickable(false);
                bt5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.music_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.music_banner));
                break;
            case R.id.bt_category_6:
                bt6.setClickable(false);
                bt6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.sport_icon), null, null);
                upperLayout.setBackground(getResources().getDrawable(R.drawable.sport_banner));
                break;
        }
    }

    public void clickCategory(View v) {
        changeCategory(v.getId());
    }

    public void changeCategory(int vid) {
        resetCategoryButtons(vid);
        setupCardlist(vid);
    }
}
