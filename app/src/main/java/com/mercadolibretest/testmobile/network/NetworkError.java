package com.mercadolibretest.testmobile.network;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import retrofit2.adapter.rxjava.HttpException;

public class NetworkError extends Throwable {
    public static final String DEFAULT_ERROR_MESSAGE = "Algo salió mal, por favor intente nuevamente";
    public static final String NETWORK_ERROR_MESSAGE = "Revise su conexión a internet";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public String getAppErrorMessage() {
        if (this.error instanceof IOException){
            return NETWORK_ERROR_MESSAGE;
        }
        if (!(this.error instanceof HttpException)) {
            return DEFAULT_ERROR_MESSAGE;
        }
        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            if (!TextUtils.isEmpty(status)) {
                return status;
            }
            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER)) {
                return headers.get(ERROR_MESSAGE_HEADER).get(0);
            }
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    protected String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            Response errorResponse = new Gson().fromJson(jsonString, Response.class);
            return errorResponse.status;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}