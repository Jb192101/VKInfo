package org.jedi_bachelor.vkinfo;

import static org.jedi_bachelor.vkinfo.utils.NetworkUtils.generateURL;
import static org.jedi_bachelor.vkinfo.utils.NetworkUtils.getResponseFromURL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;
    private TextView errorMessage;
    private ProgressBar progressBar;

    private void showResultTextView() {
        result.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        result.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    class VKQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // Распарсиваем JSON
            String resultName = null;

            if(response == null || response.equals("")) {
                showErrorMessage();
            } else {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response"); // response - ключ, по которому находится этот массив

                    JSONObject human = jsonArray.getJSONObject(0);
                    resultName = human.getString("first_name") + "\n" + human.getString("last_name");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                result.setText(resultName);
                showResultTextView();
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        result = findViewById(R.id.tv_result);
        errorMessage = findViewById(R.id.tv_error_message);
        progressBar = findViewById(R.id.pb_loading_indicator);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = generateURL(searchField.getText().toString());
                new VKQueryTask().execute(url);
            }
        };

        searchButton.setOnClickListener(listener);
    }
}