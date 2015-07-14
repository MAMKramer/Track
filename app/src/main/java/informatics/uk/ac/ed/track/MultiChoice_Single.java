package informatics.uk.ac.ed.track;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import informatics.uk.ac.ed.track.Constants;
import informatics.uk.ac.ed.track.R;
import informatics.uk.ac.ed.track.Utils;
import informatics.uk.ac.ed.track.lib.BranchableAnswerOption;
import informatics.uk.ac.ed.track.lib.MultipleChoiceMultipleAnswer;
import informatics.uk.ac.ed.track.lib.MultipleChoiceSingleAnswer;
import informatics.uk.ac.ed.track.lib.TrackQuestion;
import informatics.uk.ac.ed.track.util.TrackQuestionActivity;

public class MultiChoice_Single extends TrackQuestionActivity {

    private MultipleChoiceSingleAnswer question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice__single);

        /* get question preferences using question ID */
        Intent intent = getIntent();
        SharedPreferences preferences =
                Utils.getQuestionPreferences(getApplicationContext(),
                        intent.getIntExtra(Constants.QUESTION_ID, Constants.DEF_VALUE_INT));

        /* display back/next (navigation) buttons */
        this.displayNavigationButtons(intent, R.id.btnPrevious, R.id.btnNext);

        /* deserialize question JSON string into object */
        Gson gson = new Gson();
        this.question = gson.fromJson(
                preferences.getString(Constants.QUESTION_JSON, Constants.DEF_VALUE_STR),
                MultipleChoiceSingleAnswer.class);

        /* display title, question and prefix, if available */
        this.displayTitleQuestionAndPrefix(this.question, R.id.toolbar, R.id.txtVwToolbarTitle,
                R.id.txtVwQuestionText, R.id.txtVwQuestionPrefix);


        /* display multiple choice options */
        RadioGroup rdGrp = (RadioGroup) findViewById(R.id.rdGrp);
        for (BranchableAnswerOption option : question.getAnswerOptions()) {
            RadioButton rdBtn = new RadioButton(this);
            rdBtn.setText(option.getOption());
            rdBtn.setId(option.getOptionId());
            rdGrp.addView(rdBtn);
            // TODO RadioButton styling for pre-Lollipop devices
        }

        /* add "Other" if necessary */
        if (this.question.getAddOther()) {
            // show "Other" radio button
            RadioButton rdBtnOther = (RadioButton) findViewById(R.id.rdBtnOther);
            rdBtnOther.setVisibility(View.VISIBLE);

            // hide / show "Other" textbox depending on whether option is selected
            rdGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                    EditText txtOther = (EditText) findViewById(R.id.txtOther);
                    if(checkedId == R.id.txtOther){
                        txtOther.setVisibility(View.VISIBLE);
                    } else {
                        txtOther.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_choice__single, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}