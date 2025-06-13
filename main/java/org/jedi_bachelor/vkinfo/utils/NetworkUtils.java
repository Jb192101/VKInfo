package org.jedi_bachelor.vkinfo.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

// Класс для работы с сетью
public class NetworkUtils {
    private static final String VK_HTTPS_RESPONSE = "https://api.vk.com";
    private static final String VK_METHOD_RESPONSE = "/method/users.get";
    private final static String PARAM_USER_ID = "user_ids";
    private final static String PARAM_VERSION = "v";

    public static URL generateURL(String userID) {
        // Запрос
        Uri builtUri = Uri.parse(VK_HTTPS_RESPONSE + VK_METHOD_RESPONSE)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userID)
                .appendQueryParameter(PARAM_VERSION, "5.199")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
