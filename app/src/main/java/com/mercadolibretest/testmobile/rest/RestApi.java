package com.mercadolibretest.testmobile.rest;


import com.mercadolibretest.testmobile.models.Currency;
import com.mercadolibretest.testmobile.models.item.Description;
import com.mercadolibretest.testmobile.models.item.Item;
import com.mercadolibretest.testmobile.models.search.SearchResult;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestApi {

    @GET("sites/MLA/search")
    Observable<SearchResult> search(@Query("q") String query, @Query("offset") Integer offset, @Query("limit") Integer limit);

    @GET("items/{item_id}")
    Observable<Item> getItem(@Path("item_id") String itemId);

    @GET("items/{item_id}/description")
    Observable<Description> getDescription(@Path("item_id") String itemId);

    @GET("currencies/{currency_id}")
    Observable<Currency> getCurrency(@Path("currency_id") String currencyId);


}
