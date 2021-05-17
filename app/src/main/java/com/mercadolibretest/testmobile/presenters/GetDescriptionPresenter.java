package com.mercadolibretest.testmobile.presenters;

import com.mercadolibretest.testmobile.services.DescriptionService;
import com.mercadolibretest.testmobile.views.GetDescriptionView;
import com.mercadolibretest.testmobile.network.NetworkError;
import com.mercadolibretest.testmobile.models.item.Description;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GetDescriptionPresenter {

    private final GetDescriptionView view;
    private final DescriptionService descriptionService;
    private CompositeSubscription subscriptions;

    public GetDescriptionPresenter(GetDescriptionView view, DescriptionService descriptionService) {
        this.view = view;
        this.descriptionService = descriptionService;
        this.subscriptions = new CompositeSubscription();
    }


    public void getDescription(String idItem) {
        view.showProgress();
        Subscription subscription = descriptionService.getDescription(idItem, new DescriptionService.GetDescriptionCallback() {
            @Override
            public void onSuccess(Description description) {
                view.removeProgress();
                view.getDescriptionSuccess(description);
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
