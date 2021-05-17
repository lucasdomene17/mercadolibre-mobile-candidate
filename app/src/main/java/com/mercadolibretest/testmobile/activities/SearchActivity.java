package com.mercadolibretest.testmobile.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mercadolibretest.testmobile.presenters.GetItemPresenter;
import com.mercadolibretest.testmobile.views.GetItemView;
import com.mercadolibretest.testmobile.services.ItemDetailService;
import com.mercadolibretest.testmobile.R;
import com.mercadolibretest.testmobile.network.RetrofitService;
import com.mercadolibretest.testmobile.views.SearchItemsView;
import com.mercadolibretest.testmobile.presenters.SearchPresenter;
import com.mercadolibretest.testmobile.services.SearchService;
import com.mercadolibretest.testmobile.adapters.AdapterItem;
import com.mercadolibretest.testmobile.models.item.Item;
import com.mercadolibretest.testmobile.models.search.Result;
import com.mercadolibretest.testmobile.models.search.SearchResult;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements SearchItemsView, GetItemView {

    String TAG = "SearchActivity";
    Toolbar toolbarSearch;
    RecyclerView recyclerView;
    AdapterItem adapterItem;
    List<Result> items;
    private LinearLayoutManager layoutManager;
    boolean isLoading;
    boolean fromGetItem;
    private int visibleThreshold = 5;
    int totalItemCount, lastVisibleItem, totalSearchItems;
    String query;
    ProgressBar progressBar;
    SearchPresenter searchPresenter;
    GetItemPresenter getItemPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        SearchService searchService = new SearchService(RetrofitService.getInstance());
        ItemDetailService itemDetailService = new ItemDetailService(RetrofitService.getInstance());
        searchPresenter = new SearchPresenter(this, searchService);
        getItemPresenter = new GetItemPresenter(this, itemDetailService);

    }

    private void initViews() {
        toolbarSearch = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarSearch);

        recyclerView = findViewById(R.id.recyclerViewItems);
        progressBar = findViewById(R.id.progress);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        adapterItem = new AdapterItem(items, this);

        adapterItem.setOnItemClickListener(new AdapterItem.OnItemClickListener() {
            @Override
            public void onItemClick(Result item) {
                fromGetItem = true;
                getItemPresenter.getItem(item.getId());

            }
        });

        recyclerView.setAdapter(adapterItem);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loadMore();
                    isLoading = true;
                } else {
                    Log.d(TAG, "isLoading: " + isLoading + " - totalItemCount: " + totalItemCount + " - lastVisibleItem: " + lastVisibleItem + " - visibleThreshold: " + visibleThreshold);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyboard(SearchActivity.this);
                    items.clear();
                    adapterItem.notifyDataSetChanged();
                    SearchActivity.this.query = query;
                    isLoading = true;
                    searchPresenter.search(query,0,10);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void updateListSearch(SearchResult searchResult) {
        if (searchResult.getPaging() != null && searchResult.getPaging().getTotal() != null) {
            totalSearchItems = searchResult.getPaging().getTotal().intValue();
        } else {
            totalSearchItems = 0;
        }

        items.clear();

        if (searchResult.getResults() != null && !searchResult.getResults().isEmpty()) {
            items.addAll(searchResult.getResults());
        }
        isLoading = false;
        adapterItem.notifyDataSetChanged();
    }

    public void addToListSearch(SearchResult searchResult) {
        if (searchResult.getPaging() != null && searchResult.getPaging().getTotal() != null) {
            totalSearchItems = searchResult.getPaging().getTotal().intValue();
        } else {
            totalSearchItems = 0;
        }
        items.remove(items.size() - 1);
        adapterItem.notifyItemRemoved(items.size());
        if (searchResult.getResults() != null && !searchResult.getResults().isEmpty()) {
            items.addAll(searchResult.getResults());
        }
        isLoading = false;
        adapterItem.notifyDataSetChanged();
    }

    private void loadMore() {
        if (items.size() < totalSearchItems) {
            items.add(null);
            adapterItem.notifyItemInserted(items.size() - 1);
            searchPresenter.search(query, items.size() - 1,10);
        }
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showProgress() {
        isLoading = true;
        if(items.isEmpty() || fromGetItem){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeProgress() {
        isLoading = false;
        if(items.isEmpty() || fromGetItem){
            progressBar.setVisibility(View.GONE);
        }
        fromGetItem = false;
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getItemSuccess(Item item) {

        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

    }

    @Override
    public void searchSuccess(SearchResult searchResult) {
        if(items.isEmpty()) {
            updateListSearch(searchResult);
        }else{
            addToListSearch(searchResult);
        }

    }
}
