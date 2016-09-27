package com.example.hariramesh.dynamictest;

/**
 * Created by hariramesh on 9/25/16.
 */

import java.util.ArrayList;
import java.util.List;



public class LeaseVO {

    private String title;
    private List<String> intro = new ArrayList<String>();
    private List<QuestionsVO> mQuestionsVOs = new ArrayList<QuestionsVO>();

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The intro
     */
    public List<String> getIntro() {
        return intro;
    }

    /**
     *
     * @param intro
     * The intro
     */
    public void setIntro(List<String> intro) {
        this.intro = intro;
    }

    /**
     *
     * @return
     * The mQuestionsVOs
     */
    public List<QuestionsVO> getQuestion() {
        return mQuestionsVOs;
    }

    /**
     *
     * @param questionsVOs
     * The mQuestionsVOs
     */
    public void setQuestionsVOs(List<QuestionsVO> questionsVOs) {
        this.mQuestionsVOs = questionsVOs;
    }




}