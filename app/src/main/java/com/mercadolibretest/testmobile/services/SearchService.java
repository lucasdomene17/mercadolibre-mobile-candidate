package com.mercadolibretest.testmobile.services;

import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.rest.RestApi;
import com.mercadolibretest.testmobile.models.search.SearchResult;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchService {
    private final RestApi restApi;

    public SearchService(RestApi restApi) {
        this.restApi = restApi;
    }

    public Subscription search(String query, Integer offset, Integer limit, final SearchCallback callback) {

        return restApi.search(query, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SearchResult>>() {
                    @Override
                    public Observable<? extends SearchResult> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<SearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        callback.onSuccess(searchResult);

                    }
                });
    }

    public interface SearchCallback{
        void onSuccess(SearchResult searchResult);
        void onError(NetworkError networkError);
    }
}
