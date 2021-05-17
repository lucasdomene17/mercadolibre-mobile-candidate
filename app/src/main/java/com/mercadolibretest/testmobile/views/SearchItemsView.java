package com.mercadolibretest.testmobile.views;

import com.mercadolibretest.testmobile.models.search.SearchResult;

public interface SearchItemsView {

    void showProgress();

    void removeProgress();

    void onFailure(String errorMessage);

    void searchSuccess(SearchResult searchResult);
}
