package com.mercadolibretest.testmobile.presenters;

import com.mercadolibretest.testmobile.services.CurrencyService;
import com.mercadolibretest.testmobile.views.GetCurrencyView;
import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.models.Currency;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GetCurrencyPresenter {

    private final GetCurrencyView view;
    private final CurrencyService currencyService;
    private CompositeSubscription subscriptions;

    public GetCurrencyPresenter(GetCurrencyView view, CurrencyService currencyService) {
        this.view = view;
        this.currencyService = currencyService;
        this.subscriptions = new CompositeSubscription();
    }


    public void getCurrency(String idCurrency) {
        Subscription subscription = currencyService.getCurrency(idCurrency, new CurrencyService.GetCurrencyCallback() {
            @Override
            public void onSuccess(Currency currency) {
                view.getCurrencySuccess(currency);
            }

            @Override
            public void onError(NetworkError networkError) {
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }


}
