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

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int points = intent.getIntExtra("points",-1);
        int maxPoints = intent.getIntExtra("maxPoints",-1);
        float perc = (float)points / ((float)maxPoints+1) * 100;

        TextView res = findViewById(R.id.txtResult);
        res.setText(points + "/" + maxPoints + " (" + (Math.round(perc*10)/10.0) + "%)");

        Button back = findViewById(R.id.btnBackResult);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject newResult = new JSONObject();
                try {
                    newResult.put("points", points);
                    newResult.put("maxPoints", maxPoints);
                    newResult.put("date", System.currentTimeMillis());
                } catch(Exception e) {
                    back.performClick();
                    return;
                }
                SharedPreferences sp = getApplicationContext().getSharedPreferences("high-score", Context.MODE_PRIVATE);
                String results = sp.getString("results", "[]");
                JSONArray resultsArray = null;
                try {
                    resultsArray = new JSONArray(results);
                } catch(Exception e) {
                    resultsArray = new JSONArray();
                }

                boolean added = false;
                float newPerc = (float)points / maxPoints;
                for(int i = 0; i <  resultsArray.length(); i++) {
                    try {
                        JSONObject tmp = resultsArray.getJSONObject(i);
                        int tmpPoints = tmp.getInt("points"),
                                tmpMax = tmp.getInt("maxPoints");
                        float tmpPerc = (float) tmpPoints / tmpMax;
                        if(tmpPerc < newPerc) {
                            for(int j = resultsArray.length(); j > i; j--) {
                                resultsArray.put(j, resultsArray.get(j-1));
                            }
                            resultsArray.put(i, newResult);
                            added = true;
                            break;
                        }
                    } catch(Exception e) {
                        break;
                    }
                }
                if(!added) {
                    resultsArray.put(newResult);
                }

                while(resultsArray.length() > 5) {
                    resultsArray.remove(5);
                }

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", "value");
                editor.putString("results", resultsArray.toString());
                editor.apply();
                back.performClick();
            }
        });
    }
}