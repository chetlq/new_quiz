package com.example.admin.quiz;

/**
 * Created by admin on 01.12.2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mResult;

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isResult() {
        return mResult;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setResult(boolean Result) {
        mResult = Result;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public Question(int textResId, boolean AnswerTrue, boolean Result) {
        mTextResId = textResId;

        mAnswerTrue = AnswerTrue;

        mResult = Result;
    }
}
