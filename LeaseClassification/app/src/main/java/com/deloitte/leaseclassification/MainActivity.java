package com.deloitte.leaseclassification;

        import android.app.ActionBar;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ListView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.Timer;
        import java.util.TimerTask;
        import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> listViewArray = new ArrayList<>();
    //Primary data model instance
    private LeaseVO mLeaseVO = new LeaseVO();
    private int noOfOptions;
    private String theJSON;
    private ListAdapter adapter;

    ArrayList<Integer> dynamicButtonId = new ArrayList<Integer>();
    ArrayList<String> buttonText = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action);

        populateModel();

        SetInitialList();

    }

    private StringBuffer dynamicParse() {
        String mLine = null;
        StringBuffer nLine = new StringBuffer();


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Dynamic copy.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            while ((mLine = reader.readLine()) != null) {
                nLine.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return nLine;

    }

//The part where all the JSON data is processed and is used to populate the model.

    private void populateModel() {

        theJSON = dynamicParse().toString();

        try {
            //Getting Reference to the The primary json Object
            JSONObject jsonObject = new JSONObject(theJSON);

            //title object
            mLeaseVO.setTitle(jsonObject.getString("title"));


            JSONArray jsonIntro = jsonObject.getJSONArray("intro");

            //Intro content

            for (int i = 0; i < jsonIntro.length(); i++) {
                ArrayList<String> introList = new ArrayList<>();
                introList.add(jsonIntro.get(i).toString());
                mLeaseVO.setIntro(introList);
            }

            //Questions data model is being populated

            JSONArray jsonQuestions = jsonObject.getJSONArray("questionsVOs");
            ArrayList<QuestionsVO> questionsVOList = new ArrayList<>();

            //id and question is being set.
            for (int i = 0; i < jsonQuestions.length(); i++) {
                QuestionsVO localQuestionsVO = new QuestionsVO();
                JSONObject questions = jsonQuestions.getJSONObject(i);
                localQuestionsVO.setId(questions.getString("id"));
                localQuestionsVO.setQuestion(questions.getString("question"));

                //Options are being set
                JSONArray jsonOptions = questions.getJSONArray("options");
                ArrayList<String> optionList = new ArrayList<>();

                for (int j = 0; j < jsonOptions.length(); j++) {
                    optionList.add(jsonOptions.get(j).toString());
                }
                localQuestionsVO.setOptions(optionList);

                //QuestionsVO ID's are being set

                JSONArray jsonQId = questions.getJSONArray("question_ids");
                ArrayList<String> qIDList = new ArrayList<>();

                for (int k = 0; k < jsonQId.length(); k++) {

                    qIDList.add(jsonQId.get(k).toString());

                }
                localQuestionsVO.setQuestionIds(qIDList);

                //questionsVOList is being updated with the question object for this iteration of the QuestionsVO object
                questionsVOList.add(localQuestionsVO);

            }
            //The complete list of Questions object is being added to the primary data model(mLeaseVO)
            mLeaseVO.setQuestionsVOs(questionsVOList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//Initially setting the listView with the intro

    private void SetInitialList() {
        //Initially the intro and the first question is loaded.

        final ListView list = (ListView) findViewById(R.id.bubbleList);
        View headerView = ((LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.logo_header, null, false);
        list.addHeaderView(headerView);

        listViewArray.add(mLeaseVO.getTitle());

        {
            listViewArray.add(mLeaseVO.getIntro().get(0).toString());
        }
        listViewArray.add(mLeaseVO.getQuestion().get(0).getQuestion());


        adapter = new ListAdapter(getBaseContext(), R.layout.bubblelist, listViewArray);
        list.setAdapter(adapter);
        dynamicButtonId.add(2);
        dynamicButtonId.add(7);
        noOfOptions = dynamicButtonId.size();
        buttonText.add("yes");
        buttonText.add("no");

        DynamicButtons(noOfOptions, dynamicButtonId, buttonText);


    }

//The buttons can be added after sleeping for a while.
// with initial id values.
//Depending on the size of the dynamicButtonId list, the number of buttons will be decided.

    public void DynamicButtons(int noOfButtons, ArrayList<Integer> buttonId, ArrayList<String> buttonText) {

        //Linear layout where the buttons will appear,positioned just below the listView
        final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.notMain);

        //Creating the Buttons dynamically and adding the id and text.
        Animation buttonSLide = AnimationUtils.loadAnimation(this, R.anim.slide_button);
        for (int i = 0; i < noOfButtons; i++) {

            int buttonid = buttonId.get(i);
            String bText = buttonText.get(i);
            final Button mButton = new Button(MainActivity.this);
            mButton.setText(bText);
            mButton.setId(buttonid);
            mButton.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT));

            //Adding the button to the user interface
            mButton.clearAnimation();
            mButton.setAnimation(buttonSLide);
            mButton.startAnimation(buttonSLide);
            mLinearLayout.addView(mButton);





            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Remove the buttons of the screen on click
                    mLinearLayout.removeAllViews();

                    //Adds the next set of questions depending the button clicked.
                    addQuestion(mButton.getId(), (String) mButton.getText());
                    //clear the prevoius contents of the dynamicButtonId,as a new set of values are updated on click.
                    dynamicButtonId.clear();
                }
            });
        }
    }

    //Take the id from the parameter and update the Questions in the listview and
    // update the new set of buttons by calling the method dynamicButtons() with new parameters depending the new question added

    private void addQuestion(int id, String answer) {

        dynamicButtonId.clear();
        buttonText.clear();
        for (int i = 0; i < mLeaseVO.getQuestion().size(); i++) {
            //Adding the QuestionsVO corresponding to the button click
            if (Integer.parseInt(mLeaseVO.getQuestion().get(i).getId()) == id) {
                listViewArray.add(answer);

                listViewArray.add(mLeaseVO.getQuestion().get(i).getQuestion());
                adapter.notifyDataSetChanged();

                //Adding the value of the button chosen to the screen


                //Populating the dynamicButtonId with the options to create the next set of buttons dynamically
                for (int j = 0; j < mLeaseVO.getQuestion().get(i).getQuestionIds().size(); j++) {
                    dynamicButtonId.add(Integer.parseInt(mLeaseVO.getQuestion().get(i).getQuestionIds().get(j)));
                    buttonText.add(mLeaseVO.getQuestion().get(i).getOptions().get(j));
                }

                noOfOptions = dynamicButtonId.size();
                DynamicButtons(noOfOptions, dynamicButtonId, buttonText);
            }
        }

        //THE FINAL BUTTONS dummy dummy dummy
        int finalButtons = 99;
        if (finalButtons == 98) {
            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.notMain);
            mLinearLayout.removeAllViews();

        }
    }

}