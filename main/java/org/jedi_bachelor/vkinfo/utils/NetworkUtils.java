package org.jedi_bachelor.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Класс для работы с сетью
public class NetworkUtils {
    private static final String VK_HTTPS_RESPONSE = "https://api.vk.com";
    private static final String VK_METHOD_RESPONSE = "/method/users.get";
    private final static String PARAM_USER_ID = "user_ids";
    private final static String PARAM_VERSION = "v";

    public static URL generateURL(String _userID) {
        // Запрос
        Uri builtUri = Uri.parse(VK_HTTPS_RESPONSE + VK_METHOD_RESPONSE)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, _userID)
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

    public static String getResponseFromURL(URL _url) throws IOException {
        // Открываем connection
        HttpURLConnection urlConnection = (HttpURLConnection) _url.openConnection();

        try {
            // Вызываем метод для получения потока данных
            InputStream in = urlConnection.getInputStream();

            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A"); // используем разделитель (\\A - не разбивает строку на подстроки)

            boolean hasInput = scan.hasNext();

            if (hasInput) {
                return scan.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
