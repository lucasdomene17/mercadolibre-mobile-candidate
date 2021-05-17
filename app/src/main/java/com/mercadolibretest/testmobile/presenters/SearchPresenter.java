package com.mercadolibretest.testmobile.presenters;

import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.views.SearchItemsView;
import com.mercadolibretest.testmobile.services.SearchService;
import com.mercadolibretest.testmobile.models.search.SearchResult;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SearchPresenter {

    private final SearchItemsView view;
    private final SearchService searchService;
    private CompositeSubscription subscriptions;

    public SearchPresenter(SearchItemsView view, SearchService searchService) {
        this.view = view;
        this.searchService = searchService;
        this.subscriptions = new CompositeSubscription();
    }


    public void search(String query, Integer offset, Integer limit) {
        view.showProgress();
        Subscription subscription = searchService.search(query, offset, limit, new SearchService.SearchCallback() {
            @Override
            public void onSuccess(SearchResult searchResult) {
                view.removeProgress();
                view.searchSuccess(searchResult);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeProgress();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }


}
