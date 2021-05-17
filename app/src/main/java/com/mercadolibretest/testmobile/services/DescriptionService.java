package com.mercadolibretest.testmobile.services;

import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.rest.RestApi;
import com.mercadolibretest.testmobile.models.item.Description;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DescriptionService {
    private final RestApi restApi;

    public DescriptionService(RestApi restApi) {
        this.restApi = restApi;
    }

    public Subscription getDescription(String idItem, final GetDescriptionCallback callback) {

        return restApi.getDescription(idItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Description>>() {
                    @Override
                    public Observable<? extends Description> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Description>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Description description) {
                        callback.onSuccess(description);

                    }
                });
    }

    public interface GetDescriptionCallback{
        void onSuccess(Description description);
        void onError(NetworkError networkError);
    }
}
