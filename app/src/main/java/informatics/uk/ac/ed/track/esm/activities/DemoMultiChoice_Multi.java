package informatics.uk.ac.ed.track.esm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import informatics.uk.ac.ed.track.R;


public class DemoMultiChoice_Multi extends AppCompatActivity {

    private Button btnNext, btnPrevious;
    private ArrayList<CheckBox> checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_multi_choice__multi);

        // initialise UI controls
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);

        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx1));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx2));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx3));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx4));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx5));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx6));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx7));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx8));
        checkBoxes.add((CheckBox) findViewById(R.id.chkBx9));

        // set onClick listeners
        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    Intent intent = new Intent(DemoMultiChoice_Multi.this, DemoScaleVertical.class);
                    startActivity(intent);
                }
            }
        });

        btnPrevious.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoMultiChoice_Multi.super.onBackPressed();
            }
        });
    }

    public boolean isValid() {
        boolean hasErrors = false;

        boolean atLeastOneSelected = false;
        int checked = 0;

        while ((!atLeastOneSelected) && (checked < this.checkBoxes.size())) {
            if (this.checkBoxes.get(checked).isChecked()) {
                atLeastOneSelected = true;
            }
            checked++;
        }

        if (!atLeastOneSelected) {
            Toast toast = Toast.makeText(this,
                    getResources().getString(R.string.error_answerToProceed), Toast.LENGTH_SHORT);
            toast.show();
            hasErrors = true;
        }

        return !hasErrors;
    }
}
