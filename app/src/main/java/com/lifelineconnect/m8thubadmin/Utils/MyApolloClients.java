package com.lifelineconnect.m8thubadmin.Utils;

import android.content.Context;
import android.util.Log;

import com.apollographql.apollo.ApolloClient;

import java.security.KeyStore;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jaspreet on 2/14/18.
 */

public class MyApolloClients {
    private static final String BASE_URL = "https://channels-api.m8thub.com/api/graphiql";
    private static ApolloClient APOLLO_CLIENT;


    public static ApolloClient getApolloClient(Context context) {

        Log.d("token >> ", Utils.gettoken(context)+" <<");

        SSLSocketFactory socketFactory = new CompatibilitySSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault(), "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
        } catch (Exception e) {

        }
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization","Bearer "+ Utils.gettoken(context));
                    return chain.proceed(builder.build());
                })
                .sslSocketFactory(socketFactory, (X509TrustManager) trustManagers[0])
                .build();

        APOLLO_CLIENT = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(httpClient)

                .build();
        return APOLLO_CLIENT;
    }


}
