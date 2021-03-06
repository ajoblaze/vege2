package com.imajiku.vegefinder.activity;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.NewsListFragment;
import com.imajiku.vegefinder.model.model.NewsListModel;
import com.imajiku.vegefinder.model.presenter.NewsListPresenter;
import com.imajiku.vegefinder.model.view.NewsListView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;
import com.imajiku.vegefinder.utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;

public class NewsListActivity extends AppCompatActivity implements
        NewsListFragment.NewsListListener,View.OnClickListener, NewsListView {

    private static final int SORT_BUTTON_QTY = 2;
    private NewsListFragment newsListFragment;
    private TextView sort;
    private String TAG = "exc";
    private ExpandableRelativeLayout sortLayout;
    private TextView submitSort;
    private RadioGroup orderGroup;
    private boolean[] sortSelected;
    private LinearLayout[] sortButtonLayout;
    private int currSelectedSort = -1;
    private int pageType;
    private ArrayList<News> newsList;
    private NewsListPresenter presenter;
    private Typeface tf;
    private LinearLayout sortLinearLayout;
    private boolean isSortToggled;
    private ImageView sortArrow;
    private ProgressBar progressBar;
    private int apiCallCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        presenter = new NewsListPresenter(this);
        NewsListModel model = new NewsListModel(presenter);
        presenter.setModel(model);

        tf = Typeface.createFromAsset(getAssets(), Utility.regFont);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        addApiCounter(true);
        presenter.loadNews();
        initToolbar("News and Ideas");
        isSortToggled = false;

        newsListFragment = (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.news_list_fragment);
        sort = (TextView) findViewById(R.id.sort_btn);
        sortArrow = (ImageView) findViewById(R.id.sort_arrow);
        sortLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_sort);
        sortLinearLayout = (LinearLayout) findViewById(R.id.sort_layout);
        sortLinearLayout.setOnClickListener(this);
        sortButtonLayout = new LinearLayout[SORT_BUTTON_QTY];
        sortSelected = new boolean[]{false, false, false, false};
        sort.setTypeface(tf);

        //sort
        sortButtonLayout[0] = (LinearLayout) findViewById(R.id.sort_alpha_ll);
        sortButtonLayout[1] = (LinearLayout) findViewById(R.id.sort_date_ll);
        orderGroup = (RadioGroup) findViewById(R.id.sort_order);
        submitSort = (TextView) findViewById(R.id.submit_sort);
        for(int i=0;i<orderGroup.getChildCount();i++){
            RadioButton radio = (RadioButton) orderGroup.getChildAt(i);
            radio.setTypeface(tf);
             CompoundButtonCompat.setButtonTintList(radio, setColorStateList(
                    ContextCompat.getColor(this, R.color.accentGreenBtnDark)
            ));
        }
        submitSort.setTypeface(tf);

        //sort
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            sortButtonLayout[i].setOnClickListener(this);
            ((TextView) sortButtonLayout[i].getChildAt(1)).setTypeface(tf);
        }
        submitSort.setOnClickListener(this);
    }

    private ColorStateList setColorStateList(int color) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled} //enabled
                }, new int[]{color}
        );
    }

    public void addApiCounter(boolean isStart){
        if(isStart){
            apiCallCounter++;
        }else{
            if(apiCallCounter > 0) {
                apiCallCounter--;
            }
        }
        if(apiCallCounter == 1){
            progressBar.setVisibility(View.VISIBLE);
        }else if(apiCallCounter == 0){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void initToolbar(String title) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        TextView tv = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv.setText(title);
        tv.setTypeface(tf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_layout:
                sortLayout.toggle();
                isSortToggled = !isSortToggled;
                changeMenuButton();
                break;
            case R.id.sort_alpha_ll:
                changeSortButton(0);
                break;
            case R.id.sort_date_ll:
                changeSortButton(1);
                break;
            case R.id.submit_sort:
                if(currSelectedSort == -1) {
                    Toast.makeText(NewsListActivity.this, "Please choose sort type", Toast.LENGTH_SHORT).show();
                }else{
                    sortLayout.collapse();
                    newsListFragment.sort(newsList, getSortResult());
                }
                break;
        }
    }

    private int[] getSortResult() {
        int[] sortResult = new int[2];
        sortResult[0] = currSelectedSort;
        sortResult[1] =
                (orderGroup.getCheckedRadioButtonId() == R.id.asc) ? 0 : 1;
        return sortResult;
    }

    private void changeMenuButton() {
        if (isSortToggled) {
            sortArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp_m);
            sortLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        } else {
            sortArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp_m);
            sortLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.selectedGray));
        }
        sortArrow.setColorFilter(ContextCompat.getColor(this, R.color.accentGreenBtn));
    }

    private void changeSortButton(int index) {
        int color;
        // change selection
        if (currSelectedSort != index) {
            if (currSelectedSort != -1) {
                sortSelected[currSelectedSort] = false;
            }
            sortSelected[index] = true;
            currSelectedSort = index;
        } else {
            sortSelected[index] = false;
            currSelectedSort = -1;
        }
        // change color
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            if (sortSelected[i]) {
                color = ContextCompat.getColor(this, R.color.accentGreenBtn);
            } else {
                color = ContextCompat.getColor(this, R.color.black);
            }
            ((ImageView) sortButtonLayout[i].getChildAt(0)).setColorFilter(color);
        }
    }

    @Override
    public void successLoadNews(ArrayList<News> data) {
        sortLayout.setVisibility(View.VISIBLE);
        newsList = data;
        newsListFragment.sort(data, new int[]{1, 1});
        addApiCounter(false);
    }

    @Override
    public void failedLoadNews() {
        Toast.makeText(NewsListActivity.this, "Failed getting news data", Toast.LENGTH_SHORT).show();
        addApiCounter(false);
    }

    @Override
    public void onNewsList(Uri uri) {

    }

    @Override
    public void toggleSpinner(boolean b) {
        addApiCounter(b);
    }
}
