package com.mercadolibretest.testmobile.presenters;

import com.mercadolibretest.testmobile.views.GetItemView;
import com.mercadolibretest.testmobile.services.ItemDetailService;
import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.models.item.Item;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GetItemPresenter {

    private final GetItemView view;
    private final ItemDetailService itemDetailService;
    private CompositeSubscription subscriptions;

    public GetItemPresenter(GetItemView view, ItemDetailService itemDetailService) {
        this.view = view;
        this.itemDetailService = itemDetailService;
        this.subscriptions = new CompositeSubscription();
    }


    public void getItem(String idItem) {
        view.showProgress();
        Subscription subscription = itemDetailService.getItem(idItem, new ItemDetailService.GetItemCallback() {
            @Override
            public void onSuccess(Item item) {
                view.removeProgress();
                view.getItemSuccess(item);
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
