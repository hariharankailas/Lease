package com.deloitte.leaseclassification;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
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

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> listViewArray = new ArrayList<>();//arrayList to be passed to the adapter
    private ArrayList<Integer> dynamicButtonId = new ArrayList<Integer>();//ID of the dynamic buttons
    private ArrayList<String> buttonText = new ArrayList<String>();//Text content of the dynamic buttons
    private LeaseVO mLeaseVO = new LeaseVO();//Primary model
    private int noOfOptions;//No of dynamic button
    private String theJSON;//The JSON in string format
    private ListAdapter listAdapter;//List adapter
    private ListView chatList;//The listView
    private boolean isRefreshing;//If the application is undergoing refresh
    private View headerView;//Lease header
    private ImageView refreshIcon;//Refresh Icon
    private LinearLayout buttonLayout;//Button Layout

    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action);

        chatList = (ListView) findViewById(R.id.bubbleList);
        //CENTERING THE LEASE LOGO
        chatList.setPadding(0, 400, 0, 0);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        refreshIcon = (ImageView) findViewById(R.id.action_bar_forward);
        refreshIcon.setEnabled(false);
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRefreshing = false;
                refresh();
            }
        });


        populateModel();
        buttonLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        SetInitialList();
    }

    private StringBuffer dynamicParse() {
        String temp = null;
        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Dynamic copy.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            while ((temp = reader.readLine()) != null) {
                buffer.append(temp);
            }
        } catch (IOException e) {
            Log.e("IOException 1", "" + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("IOException 2", "" + e);
                }
            }
        }

        return buffer;

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
            //The complete chatList of Questions object is being added to the primary data model(mLeaseVO)
            mLeaseVO.setQuestionsVOs(questionsVOList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void SetInitialList() {
        //Initially the intro and the first question is loaded.
        chatList = (ListView) findViewById(R.id.bubbleList);
        listAdapter = new ListAdapter(getBaseContext(), R.layout.bubblelist, listViewArray);

        //Setting the header logo
        headerView = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.logo_header, null, false);
        chatList.addHeaderView(headerView);


        //Animation
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(listAdapter);
//        swingBottomInAnimationAdapter.setAbsListView(chatList);
//        swingBottomInAnimationAdapter.getViewAnimator().setAnimationDelayMillis(200);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(swingBottomInAnimationAdapter);
        scaleInAnimationAdapter.setAbsListView(chatList);
        chatList.setAdapter(scaleInAnimationAdapter);

        //Delay introduced for the start screen logo
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                setInitialParams();
            }
        }, 2500);
    }

    //Initially setting the listView with the intro
    private void setInitialParams() {
        new CountDownTimer(500, 1) {
            int chatPadding = chatList.getPaddingTop();

            public void onTick(long millisUntilFinished) {
                chatList.setPadding(0, (chatPadding - 15), 0, 0);
                chatPadding = chatPadding - 15;
            }

            public void onFinish() {

                Log.v("timer", "success");

            }
        }.start();

        new CountDownTimer(1200, 300) {
            int introCount = 0;

            public void onTick(long millisUntilFinished) {
                listViewArray.add(mLeaseVO.getIntro().get(introCount));
                listAdapter.notifyDataSetChanged();
                introCount++;
            }

            public void onFinish() {

                listViewArray.add(mLeaseVO.getQuestion().get(13).getQuestion());
                listAdapter.notifyDataSetChanged();
                chatList.smoothScrollToPosition(listAdapter.getCount(), 500);
                dynamicButtonId.clear();
                buttonText.clear();

                //To avoid multiple button generation on multiple tap on the refresh button

                buttonLayout.setBackgroundColor(getResources().getColor(R.color.buttonHolder));
                dynamicButtonId.add(15);
                dynamicButtonId.add(14);
                noOfOptions = dynamicButtonId.size();
                buttonText.add("Yes");
                buttonText.add("No, tell me more");
                isRefreshing = false;
                DynamicButtons(noOfOptions, dynamicButtonId, buttonText);
            }
        }.start();


    }


    //The buttons can be added after sleeping for a while.
// with initial id values.
//Depending on the size of the dynamicButtonId chatList, the number of buttons will be decided.
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void DynamicButtons(int noOfButtons, ArrayList<Integer> buttonId, ArrayList<String> buttonText) {
        Animation buttonSLide = AnimationUtils.loadAnimation(this, R.anim.slide_button);

        final Animation buttonShake = AnimationUtils.loadAnimation(this, R.anim.shake_button);
        for (int i = 0; i < noOfButtons; i++) {


            int bId = buttonId.get(i);
            String bText = buttonText.get(i);
            final Button dButton = new Button(MainActivity.this);

            dButton.setText(bText);
            dButton.setId(bId);
            dButton.setClickable(false);
            dButton.setTextColor(this.getResources().getColor(R.color.buttonHolder));
            dButton.setBackground(this.getResources().getDrawable(R.drawable.round_button));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50, 0, 50, 0);
            dButton.setPadding(70, 12, 70, 0);
            dButton.setLayoutParams(layoutParams);
            dButton.setEnabled(true);

            dButton.startAnimation(buttonSLide);
            buttonSLide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    buttonLayout.startAnimation(buttonShake);
//                    dButton.setClickable(true);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            buttonLayout.addView(dButton);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setClickListener(dButton);
                }
            },500);

        }

    }

    private void setClickListener(final Button dButton){
        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshIcon.setEnabled(false);
                dButton.setEnabled(false);
                for (int i = 0; i < buttonLayout.getChildCount(); i++) {
                    View child = buttonLayout.getChildAt(i);

                    child.setEnabled(false);

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshIcon.setEnabled(true);
                    }
                }, 2100);

                buttonLayout.removeAllViews();
                addQuestion(dButton.getId(), (String) dButton.getText());
//                            dynamicButtonId.clear();
                Vibrator vibration = (Vibrator) getSystemService(MainActivity.this.VIBRATOR_SERVICE);
                vibration.vibrate(100);

                Animation buttonSLideExit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_button_exit);
                buttonLayout.startAnimation(buttonSLideExit);
                buttonSLideExit.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        buttonLayout.removeAllViews();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
    }



    //Take the id from the parameter and update the Questions in the listview and
    // update the new set of buttons by calling the method dynamicButtons() with new parameters depending the new question added
    private void addQuestion(int id, String answer) {
        dynamicButtonId.clear();
        buttonText.clear();
        //when the dynamic button to restart is clicked
        refreshIcon.setEnabled(false);
        if (id == 40) {

            isRefreshing = false;

            refresh();
        } else {

            for (int i = 0; i < mLeaseVO.getQuestion().size(); i++) {
                //Adding the QuestionsVO corresponding to the button click
                if (Integer.parseInt(mLeaseVO.getQuestion().get(i).getId()) == id) {

                    listViewArray.add(answer);
                    listAdapter.notifyDataSetChanged();
                    chatList.smoothScrollToPosition(listAdapter.getCount(), 500);

                    Handler handler = new Handler();
                    final int finalI = i;
                    final int finalI1 = i;
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            listViewArray.add(mLeaseVO.getQuestion().get(finalI).getQuestion());
                            listAdapter.notifyDataSetChanged();
                            chatList.smoothScrollToPosition(listAdapter.getCount(), 500);

                            //Adding the value of the button chosen to the screen
                            //Populating the dynamicButtonId with the options to create the next set of buttons dynamically
                            for (int j = 0; j < mLeaseVO.getQuestion().get(finalI1).getQuestionIds().size(); j++) {
                                dynamicButtonId.add(Integer.parseInt(mLeaseVO.getQuestion().get(finalI1).getQuestionIds().get(j)));
                                buttonText.add(mLeaseVO.getQuestion().get(finalI1).getOptions().get(j));
                            }

                            noOfOptions = dynamicButtonId.size();
                            DynamicButtons(noOfOptions, dynamicButtonId, buttonText);
                        }

                    }, 800);


                }
            }
        }
    }

    public void refresh() {

        if (!isRefreshing) {

            buttonLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            chatList.setPadding(0, 400, 0, 0);
            listViewArray.clear();
            chatList.removeHeaderView(headerView);
            listAdapter.notifyDataSetChanged();
            buttonLayout.removeAllViews();
            SetInitialList();
            isRefreshing = true;
            refreshIcon.setEnabled(false);
        }
    }
}