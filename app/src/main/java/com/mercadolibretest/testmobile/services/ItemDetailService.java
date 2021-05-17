package com.mercadolibretest.testmobile.services;

import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.rest.RestApi;
import com.mercadolibretest.testmobile.models.item.Item;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ItemDetailService {
    private final RestApi restApi;

    public ItemDetailService(RestApi restApi) {
        this.restApi = restApi;
    }

    public Subscription getItem(String idItem, final GetItemCallback callback) {

        return restApi.getItem(idItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Item>>() {
                    @Override
                    public Observable<? extends Item> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Item item) {
                        callback.onSuccess(item);

                    }
                });
    }

    public interface GetItemCallback{
        void onSuccess(Item item);
        void onError(NetworkError networkError);
    }
}
