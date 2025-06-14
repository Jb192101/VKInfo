package org.jedi_bachelor.vkinfo;

import static org.jedi_bachelor.vkinfo.utils.NetworkUtils.generateURL;
import static org.jedi_bachelor.vkinfo.utils.NetworkUtils.getResponseFromURL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jedi_bachelor.vkinfo.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private Button searchButton;
    private TextView result;

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

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Тест
                //URL url = generateURL(searchField.getText().toString());
                //result.setText(url.toString());

                // Тест 2
                URL url = generateURL(searchField.getText().toString());
                String response = null;
                try {
                    response = getResponseFromURL(url);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                result.setText(response);
            }
        };

        searchButton.setOnClickListener(listener);
    }
}