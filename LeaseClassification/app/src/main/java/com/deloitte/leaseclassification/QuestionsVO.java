package com.deloitte.leaseclassification;

/**
 * Created by dorao on 9/27/16.
 */

        import java.util.ArrayList;
        import java.util.List;


public class QuestionsVO {

    private String id;
    private String question;
    private List<String> options = new ArrayList<String>();
    private List<String> questionIds = new ArrayList<String>();


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




}