package com.mercadolibretest.testmobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mercadolibretest.testmobile.services.CurrencyService;
import com.mercadolibretest.testmobile.services.DescriptionService;
import com.mercadolibretest.testmobile.presenters.GetCurrencyPresenter;
import com.mercadolibretest.testmobile.views.GetCurrencyView;
import com.mercadolibretest.testmobile.presenters.GetDescriptionPresenter;
import com.mercadolibretest.testmobile.views.GetDescriptionView;
import com.mercadolibretest.testmobile.R;
import com.mercadolibretest.testmobile.network.RetrofitService;
import com.mercadolibretest.testmobile.adapters.AdapterPicture;
import com.mercadolibretest.testmobile.models.Currency;
import com.mercadolibretest.testmobile.models.item.Description;
import com.mercadolibretest.testmobile.models.item.Item;
import com.mercadolibretest.testmobile.models.item.Picture;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity implements GetDescriptionView, GetCurrencyView {

    String TAG = "ItemDetailActivity";
    RecyclerView recyclerViewPictures;
    TextView textViewTitleItem;
    TextView textViewDescriptionItem;
    TextView textViewPriceItem;
    TextView textViewBuyLinkItem;
    TextView textViewConditionItem;
    TextView textViewCurrencyPriceItem;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    AdapterPicture adapterPicture;
    Item item;
    Description description;
    List<Picture> pictures;
    ProgressBar progressBar;

    GetDescriptionPresenter getDescriptionPresenter;
    GetCurrencyPresenter getCurrencyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        recyclerViewPictures = findViewById(R.id.recyclerViewPictures);

        progressBar = findViewById(R.id.progress);

        textViewTitleItem = findViewById(R.id.textViewTitleItem);

        textViewDescriptionItem = findViewById(R.id.textViewDescriptionItem);

        textViewPriceItem = findViewById(R.id.textViewPriceItem);

        textViewBuyLinkItem = findViewById(R.id.textViewBuyLinkItem);

        textViewConditionItem = findViewById(R.id.textViewConditionItem);

        textViewCurrencyPriceItem = findViewById(R.id.textViewCurrencyPriceItem);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerViewPictures.setLayoutManager(RecyclerViewLayoutManager);

        item = getIntent().getParcelableExtra("item");
        description = getIntent().getParcelableExtra("description");

        String conditionText = item.getCondition();

        if ("new".equalsIgnoreCase(conditionText)) {
            conditionText = getString(R.string.new_text);
        } else if ("used".equalsIgnoreCase(conditionText)) {
            conditionText = getString(R.string.used_text);
        }
        String conditionItem = (conditionText != null ? conditionText : "")
                + (item.getSoldQuantity() != null && item.getSoldQuantity().longValue() != 0 ? (" - " + item.getSoldQuantity() +" "+ getString(R.string.sold)) : " ")
                + (item.getAvailableQuantity() != null && item.getAvailableQuantity().longValue() != 0 ? (" - " + item.getAvailableQuantity() +" "+ getString(R.string.available)) : "");

        textViewConditionItem.setText(conditionItem);

        textViewTitleItem.setText(item.getTitle());
        textViewCurrencyPriceItem.setText(item.getCurrencyId());
        textViewPriceItem.setText("" + item.getPrice());

        textViewBuyLinkItem.setClickable(true);
        textViewBuyLinkItem.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + item.getPermalink() + "'>" + getString(R.string.buy) + "</a>";
        textViewBuyLinkItem.setText(Html.fromHtml(text));

        pictures = new ArrayList<>();

        adapterPicture = new AdapterPicture(pictures, ItemDetailActivity.this);

        HorizontalLayout = new LinearLayoutManager(ItemDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPictures.setLayoutManager(HorizontalLayout);
        recyclerViewPictures.setHasFixedSize(true);


        recyclerViewPictures.setAdapter(adapterPicture);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewPictures);


        if (item != null) {
            if (item.getPictures() != null && !item.getPictures().isEmpty()) {

                pictures.addAll(item.getPictures());
                adapterPicture.notifyDataSetChanged();
            }

        }

        adapterPicture.setOnPictureClickListener(new AdapterPicture.OnPictureClickListener() {
            @Override
            public void onPictureClick(Picture picture) {
                Log.d(TAG, "Picture Size: " + picture.getSize());
            }
        });

        DescriptionService descriptionService = new DescriptionService(RetrofitService.getInstance());

        getDescriptionPresenter = new GetDescriptionPresenter(this, descriptionService);
        getDescriptionPresenter.getDescription(item.getId());

        CurrencyService currencyService = new CurrencyService(RetrofitService.getInstance());

        getCurrencyPresenter = new GetCurrencyPresenter(this,currencyService);
        getCurrencyPresenter.getCurrency(item.getCurrencyId());


    }

    public void changeCurrency(String currency) {
        textViewCurrencyPriceItem.setText(currency);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getCurrencySuccess(Currency currency) {
        changeCurrency(currency.getSymbol());
    }

    @Override
    public void getDescriptionSuccess(Description description) {

        textViewDescriptionItem.setText(description!=null?description.getPlainText():"");

    }
}
