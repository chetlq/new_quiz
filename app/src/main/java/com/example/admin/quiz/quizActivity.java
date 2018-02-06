package com.example.admin.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class quizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEAT_INDEX = "cheat";
    private static final String ISRESULT_INDEX = "isresult";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;

    private Button mCheatButton;
    private Button mServiceButton;

    private TextView mQuestionTextView;
    private   Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true,false),
            new Question(R.string.question_mideast, false,false),
            new Question(R.string.question_africa, false,false),
            new Question(R.string.question_americas, true,false),
            new Question(R.string.question_asia, true,false),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private void updateQuestion() {
//        Log.i(TAG, "Updating question text for question #" + mCurrentIndex,
//                new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    };
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;



            if (mIsCheater) {
                messageResId = R.string.judgment_toast;
            } else {
                if (userPressedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast;
                } else {
                    messageResId = R.string.incorrect_toast;
                }

            }

        if (!mQuestionBank[mCurrentIndex].isResult()) {
            mQuestionBank[mCurrentIndex].setResult(true);
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();


        }else {
            if (mIsCheater) {
                Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.was_toast, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);



        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();

            }
        });
//        int question = mQuestionBank[mCurrentIndex].getTextResId();
//        mQuestionTextView.setText(question);


        //mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(quizActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);

            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(quizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);

            }
        });
        mPreviousButton = (Button)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater=false;
                if(mCurrentIndex!=0)
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
//                int question = mQuestionBank[mCurrentIndex].getTextResId();
//                mQuestionTextView.setText(question);
                updateQuestion();

            }

        });
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater=false;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                updateQuestion();

            }

        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Запуск CheatActivity
                //Intent i = new Intent(quizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].
                        isAnswerTrue();
                Intent i = CheatActivity.newIntent(quizActivity.this,
                        answerIsTrue);
                //startActivity(i);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        mServiceButton = (Button) findViewById( R.id.cheat_button );
        mServiceButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intentMyIntentService = new Intent(quizActivity.this, MyIntentService.class);
                startService(new Intent(getApplicationContext(), MyIntentService.class));
                startService(quizActivity.this.getIntent());
                startService(quizActivity.this.getIntent().putExtra("time", 3).putExtra("task",
                        "Погладить кота"));
//                startService(intentMyIntentService.putExtra("time", 1).putExtra("task",
//                        "Покормить кота"));
//                startService(intentMyIntentService.putExtra("time", 4).putExtra("task",
//                        "Поиграть с котом"));

            }
        } );


        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEAT_INDEX, false);
            mQuestionBank[mCurrentIndex].setResult(savedInstanceState.getBoolean(ISRESULT_INDEX, false)) ;
        }
        updateQuestion();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if (mIsCheater) mQuestionBank[mCurrentIndex].setResult(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CHEAT_INDEX, mIsCheater);

        //savedInstanceState.putBoolean(ISRESULT_INDEX, mQuestionBank[mCurrentIndex].isResult());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
    }
}
