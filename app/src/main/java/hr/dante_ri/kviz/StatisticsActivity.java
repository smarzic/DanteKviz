package hr.dante_ri.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Button btnBack = findViewById(R.id.btnBackResult2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sp = getApplicationContext().getSharedPreferences("high-score", Context.MODE_PRIVATE);
        String results = sp.getString("results", "[]");

        int r[] = {R.id.txtR1, R.id.txtR2, R.id.txtR3, R.id.txtR4, R.id.txtR5};
        int d[] = {R.id.txtD1, R.id.txtD2, R.id.txtD3, R.id.txtD4, R.id.txtD5};

        for(int i = 0; i < r.length; i++) {
            TextView ri = findViewById(r[i]);
            TextView di = findViewById(d[i]);
            if(i == 0) {
                ri.setText("NEMA REZULTATA");
            } else {
                ri.setText("");
            }
            di.setText("");
        }

        try {
            JSONArray resultsArray = new JSONArray(results);
            String dateFormat = "d.M.yyyy. H:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            for(int i = 0; i < resultsArray.length() && i < 5; i++) {
                TextView ri = findViewById(r[i]);
                TextView di = findViewById(d[i]);
                JSONObject curr = resultsArray.getJSONObject(i);
                int points = curr.getInt("points"),
                        max = curr.getInt("maxPoints");
                long date = curr.getLong("date");
                Date resDate = new Date(date);

                ri.setText((i+1) + ". " + points + "/" + max);
                di.setText("(" + sdf.format(resDate) + ")");
            }
        } catch(Exception e) {}

    }
}