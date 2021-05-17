package com.mercadolibretest.testmobile.views;

import com.mercadolibretest.testmobile.models.item.Item;

public interface GetItemView {

    void showProgress();

    void removeProgress();

    void onFailure(String errorMessage);

    void getItemSuccess(Item item);
}
