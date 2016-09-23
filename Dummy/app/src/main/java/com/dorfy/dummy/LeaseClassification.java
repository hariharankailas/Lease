package com.dorfy.dummy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LeaseClassification {

    private String title;
    private List<String> intro = new ArrayList<String>();
    private List<Question> questions = new ArrayList<Question>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @param questions
     * The questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

 class Question {

    private String id;
    private String question;
    private List<String> options = new ArrayList<String>();
    private List<String> questionIds = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     * The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @return
     * The options
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /**
     *
     * @return
     * The questionIds
     */
    public List<String> getQuestionIds() {
        return questionIds;
    }

    /**
     *
     * @param questionIds
     * The question_ids
     */
    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
