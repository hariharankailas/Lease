package com.deloitte.leaseclassification;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> listViewArray = new ArrayList<>();
    ArrayList<Integer> dynamicButtonId = new ArrayList<Integer>();
    ArrayList<String> buttonText = new ArrayList<String>();
    //Primary data model instance
    private LeaseVO mLeaseVO = new LeaseVO();
    private int noOfOptions;
    private String theJSON;
    private ListAdapter adapter;
    private ListView list;
    private ArrayList<String> qId = new ArrayList<>();
    private int z;
    private  View headerView;
    private static int o =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        list= (ListView)findViewById(R.id.bubbleList);
        //CENTERING THE LEASE LOGO
        list.setPadding(0,400,0,0);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action);
        View view = getSupportActionBar().getCustomView();
             ImageView icon = (ImageView)findViewById(R.id.action_bar_forward);
        icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // your code here
                refresh();
            }
        });

        populateModel();

        SetInitialList(0);

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
            ArrayList<String> introList = new ArrayList<>();
            for (int i = 0; i < jsonIntro.length(); i++) {
                introList.add(jsonIntro.get(i).toString());
            }
            mLeaseVO.setIntro(introList);

            //Questions data model is being populated

            JSONArray jsonQuestions = jsonObject.getJSONArray("questionsVOs");
            ArrayList<QuestionsVO> questionsVOList = new ArrayList<>();

            //id and question is being set.
            for (int i = 0; i < jsonQuestions.length(); i++) {
                QuestionsVO localQuestionsVO = new QuestionsVO();
                JSONObject questions = jsonQuestions.getJSONObject(i);
                localQuestionsVO.setId(questions.getString("id"));
                qId.add(localQuestionsVO.getId().toString());

                Log.v("id",""+localQuestionsVO.getId());

                //SETTING THE LIST qId FOR THE ADAPTER

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


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void SetInitialList(int a) {
        //Initially the intro and the first question is loaded.
        list = (ListView) findViewById(R.id.bubbleList);

        adapter = new ListAdapter(getBaseContext(), R.layout.bubblelist,listViewArray,0);
        if (a == 1) {

            list.setPadding(0,500,0,0);
            headerView = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.logo_header, null, false);
            list.addHeaderView(headerView);
            adapter = new ListAdapter(getBaseContext(), R.layout.bubblelist, listViewArray,0);
            SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
            swingBottomInAnimationAdapter.setAbsListView(list);
            swingBottomInAnimationAdapter.getViewAnimator().setAnimationDelayMillis(200);
            list.setAdapter(swingBottomInAnimationAdapter);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    setInitialParams();
                }
            }, 2000);


        } else {


            headerView = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.logo_header, null, false);
            list.addHeaderView(headerView);
            adapter = new ListAdapter(getBaseContext(), R.layout.bubblelist, listViewArray,0);
            SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
            swingBottomInAnimationAdapter.setAbsListView(list);
            swingBottomInAnimationAdapter.getViewAnimator().setAnimationDelayMillis(200);
            list.setAdapter(swingBottomInAnimationAdapter);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    setInitialParams();
                }
            }, 2500);

        }
    }





    private void setInitialParams() {

//        list.setPaddingRelative(0,150,0,0);

        z=500;

        new CountDownTimer(500, 1) {

            public void onTick(long millisUntilFinished) {
                list.setPadding(0, (z - 18), 0, 0);

                z = z - 18;
            }

            public void onFinish() {

                Log.v("counters", "" + z);
//                listViewArray.add(mLeaseVO.getIntro().get(0));
//
//
//                adapter.notifyDataSetChanged();
//                listViewArray.add(mLeaseVO.getIntro().get(1));
//
////                listViewArray.add(mLeaseVO.getQuestion().get(0).getQuestion());
//                adapter.notifyDataSetChanged();
//
//              listViewArray.add(mLeaseVO.getIntro().get(2));
//                adapter.notifyDataSetChanged();

//                listViewArray.add(mLeaseVO.getQuestion().get(0).getQuestion());

//                adapter.notifyDataSetChanged();
                dynamicButtonId.clear();


                buttonText.clear();
                dynamicButtonId.add(2);
                dynamicButtonId.add(5);
                noOfOptions = dynamicButtonId.size();
                buttonText.add("yes");
                buttonText.add("no");
                DynamicButtons(noOfOptions, dynamicButtonId, buttonText);


            }
        }.start();

        new CountDownTimer(1200, 300) {

            public void onTick(long millisUntilFinished) {


                if(o<mLeaseVO.getIntro().size()){
                listViewArray.add(mLeaseVO.getIntro().get(o));
                Log.v("log",""+mLeaseVO.getIntro().get(o));
                adapter.notifyDataSetChanged();
                Log.v("ol",""+o);



            }o++;}

            public void onFinish() {

                listViewArray.add(mLeaseVO.getQuestion().get(0).getQuestion());
                adapter.notifyDataSetChanged();

            }
        }.start();

//


    }


//The buttons can be added after sleeping for a while.
// with initial id values.
//Depending on the size of the dynamicButtonId list, the number of buttons will be decided.

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void DynamicButtons(int noOfButtons, ArrayList<Integer> buttonId, ArrayList<String> buttonText) {

        //Linear layout where the buttons will appear,positioned just below the listView
        final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.notMain);

        //Creating the Buttons dynamically and adding the id and text.
        Animation buttonSLide = AnimationUtils.loadAnimation(this, R.anim.slide_button);

        final Animation buttonShake = AnimationUtils.loadAnimation(this, R.anim.shake_button);
        for (int i = 0; i < noOfButtons; i++) {

            int buttonid = buttonId.get(i);
            String bText = buttonText.get(i);
            final Button mButton = new Button(MainActivity.this);
            mButton.setText(bText);
            mButton.setId(buttonid);
            mButton.setTextColor(this.getResources().getColor(R.color.buttonHolder));
            mButton.setBackground(this.getResources().getDrawable(R.drawable.round_button));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(40, 0, 0, 0);
            mButton.setPadding(60,12,60,0);
            mButton.setLayoutParams(layoutParams);

            //Adding the button to the user interface
//            mButton.clearAnimation();
//            mButton.setAnimation(buttonSLide);
            mButton.startAnimation(buttonSLide);
            buttonSLide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                   mLinearLayout.startAnimation(buttonShake);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mLinearLayout.addView(mButton);
                }
            }, 800);


            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Remove the buttons of the screen on click
//                    mLinearLayout.removeAllViews();

                    Animation buttonSLideExit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_button_exit);
                    mLinearLayout.startAnimation(buttonSLideExit);
                    buttonSLideExit.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                                mLinearLayout.removeAllViews();

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });



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
    }

   public void refresh(){
        o=0;
        listViewArray.clear();
        list.removeHeaderView(headerView);
        adapter.notifyDataSetChanged();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.notMain);
        linearLayout.removeAllViews();
        SetInitialList(1);

    }
}