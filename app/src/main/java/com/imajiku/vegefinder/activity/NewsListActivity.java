package com.imajiku.vegefinder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.fragment.NewsListFragment;
import com.imajiku.vegefinder.fragment.RestoListFragment;
import com.imajiku.vegefinder.model.model.NewsListModel;
import com.imajiku.vegefinder.model.presenter.NewsListPresenter;
import com.imajiku.vegefinder.model.view.NewsListView;
import com.imajiku.vegefinder.pojo.News;
import com.imajiku.vegefinder.pojo.Resto;

import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity implements
        NewsListFragment.NewsListListener,View.OnClickListener, NewsListView {

    private static final int FILTER_BOX_QTY = 6;
    private static final int SORT_BUTTON_QTY = 4;
    private NewsListFragment newsListFragment;
    private Button filter, sort;
    private String TAG = "exc";
    private ExpandableRelativeLayout filterLayout, sortLayout;
    private Button selectAll, clear, submitFilter, submitSort;
    private CheckBox[] filterBox;
    private RadioGroup orderGroup;
    private RadioButton desc;
    private boolean[] sortSelected;
    private Button[] sortButton;
    private int currSelectedSort = -1;
    private int pageType;
    private ArrayList<Resto> restoList;
    private NewsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        presenter = new NewsListPresenter(this);
        NewsListModel model = new NewsListModel(presenter);
        presenter.setModel(model);

        presenter.loadNews();

        newsListFragment = (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.review_list_fragment);
        filter = (Button) findViewById(R.id.filter_btn);
        sort = (Button) findViewById(R.id.sort_btn);
        filterLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_filter);
        sortLayout = (ExpandableRelativeLayout) findViewById(R.id.layout_sort);
        filter.setOnClickListener(this);
        sort.setOnClickListener(this);
        filterBox = new CheckBox[FILTER_BOX_QTY];
        sortButton = new Button[SORT_BUTTON_QTY];
        sortSelected = new boolean[]{false, false, false, false};

        // filter
        selectAll = (Button) findViewById(R.id.select_all);
        clear = (Button) findViewById(R.id.clear);
        filterBox[0] = (CheckBox) findViewById(R.id.open_now);
        filterBox[1] = (CheckBox) findViewById(R.id.rate_8);
        filterBox[2] = (CheckBox) findViewById(R.id.bookmarked);
        filterBox[3] = (CheckBox) findViewById(R.id.been_here);
        filterBox[4] = (CheckBox) findViewById(R.id.vegan);
        filterBox[5] = (CheckBox) findViewById(R.id.vege);
        submitFilter = (Button) findViewById(R.id.submit_filter);

        //sort
        sortButton[0] = (Button) findViewById(R.id.alpha);
        sortButton[1] = (Button) findViewById(R.id.distance);
        sortButton[2] = (Button) findViewById(R.id.date);
        sortButton[3] = (Button) findViewById(R.id.price);
        orderGroup = (RadioGroup) findViewById(R.id.sort_order);
        submitSort = (Button) findViewById(R.id.submit_sort);

        // filter
        selectAll.setOnClickListener(this);
        clear.setOnClickListener(this);
        submitFilter.setOnClickListener(this);

        //sort
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            sortButton[i].setOnClickListener(this);
        }
        submitSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_btn:
                sortLayout.collapse();
                filterLayout.toggle();
                break;
            case R.id.sort_btn:
                filterLayout.collapse();
                sortLayout.toggle();
                break;
            case R.id.select_all:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setSelected(true);
                }
                break;
            case R.id.clear:
                for (int i = 0; i < FILTER_BOX_QTY; i++) {
                    filterBox[i].setSelected(false);
                }
                break;
            case R.id.alpha:
                changeSortButton(0);
                break;
            case R.id.distance:
                changeSortButton(1);
                break;
            case R.id.date:
                changeSortButton(2);
                break;
            case R.id.price:
                changeSortButton(3);
                break;
            case R.id.submit_filter:
                filterLayout.collapse();
//                newsListFragment.filter(getFilterResult(), pageType == PAGE_SAVED);
                break;
            case R.id.submit_sort:
                sortLayout.collapse();
//                newsListFragment.sort(restoList, getSortResult(), pageType == PAGE_SAVED);
                break;
        }
    }

    private String getFilter() {
        if(filterBox[4].isSelected() && !filterBox[5].isSelected()) {
            return "vegan";
        }
        if(!filterBox[4].isSelected() && filterBox[5].isSelected()){
            return "vegetarian";
        }
        return "";
    }

    private int[] getSortResult() {
        int[] sortResult = new int[2];
        for (int i = 0; i < SORT_BUTTON_QTY; i++) {
            if(filterBox[i].isSelected()) {
                sortResult[0] = i;
                break;
            }
        }
        sortResult[1] =
                (orderGroup.getCheckedRadioButtonId() == R.id.asc) ? 0 : 1;
        return sortResult;
    }

    private boolean[] getFilterResult() {
        boolean[] filterResult = new boolean[FILTER_BOX_QTY];
        for (int i = 0; i < FILTER_BOX_QTY; i++) {
            filterResult[i] = filterBox[i].isSelected();
        }
        return filterResult;
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
                color = ContextCompat.getColor(this, R.color.translucentGreen75);
            } else {
                color = ContextCompat.getColor(this, R.color.translucentRed75);
            }
            sortButton[i].setBackgroundColor(color);
        }
    }

    @Override
    public void successLoadNews(ArrayList<News> data) {
        filterLayout.setVisibility(View.VISIBLE);
        sortLayout.setVisibility(View.VISIBLE);
        newsListFragment.sort(data, new int[]{2, 1});
    }

    @Override
    public void failedLoadNews() {
        Log.e(TAG, "failedBrowseNearby: ");
    }

    @Override
    public void onNewsList(Uri uri) {

    }
}
