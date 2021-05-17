package com.mercadolibretest.testmobile.views;

import com.mercadolibretest.testmobile.models.item.Description;

public interface GetDescriptionView {

    void showProgress();

    void removeProgress();

    void onFailure(String errorMessage);

    void getDescriptionSuccess(Description description);
}
