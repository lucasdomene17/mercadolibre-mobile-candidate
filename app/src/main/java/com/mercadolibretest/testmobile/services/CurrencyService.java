package com.mercadolibretest.testmobile.services;

import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.rest.RestApi;
import com.mercadolibretest.testmobile.models.Currency;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CurrencyService {
    private final RestApi restApi;

    public CurrencyService(RestApi restApi) {
        this.restApi = restApi;
    }

    public Subscription getCurrency(String idCurrency, final GetCurrencyCallback callback) {

        return restApi.getCurrency(idCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Currency>>() {
                    @Override
                    public Observable<? extends Currency> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Currency>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Currency currency) {
                        callback.onSuccess(currency);

                    }
                });
    }

    public interface GetCurrencyCallback{
        void onSuccess(Currency currency);
        void onError(NetworkError networkError);
    }
}
