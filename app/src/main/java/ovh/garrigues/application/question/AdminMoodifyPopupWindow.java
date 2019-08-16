package ovh.garrigues.application.question;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import ovh.garrigues.application.R;
import ovh.garrigues.application.adapter.QuestionAdminModifyAdapter;
import ovh.garrigues.application.view.AdminActivity;

public class AdminMoodifyPopupWindow extends PopupWindow {
    private View mValidButton;
    private View mAboardButton;
    private ListView mListView;
    private TextView mtextQuestion;
    private Question question;
    private Context context;
    private QuestionAdminModifyAdapter adapter;
    private ImageView mImageButton;

    public AdminMoodifyPopupWindow(Context context) {
        super(context);
    }

    public AdminMoodifyPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdminMoodifyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AdminMoodifyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AdminMoodifyPopupWindow() {
    }

    public AdminMoodifyPopupWindow(View contentView) {
        super(contentView);
    }

    public AdminMoodifyPopupWindow(int width, int height) {
        super(width, height);
    }

    public AdminMoodifyPopupWindow(AdminActivity ac, View contentView, int width, int height, Question question) {
        super(contentView, width, height);
        this.context = ac.getApplicationContext();
        initPopupButton(contentView, ac);
        setQuestion(question);
    }

    public AdminMoodifyPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    private void initPopupButton(final View contentView, final AdminActivity ac) {
        mAboardButton = contentView.findViewById(R.id.ModifyAdminActivityBack);
        mValidButton = contentView.findViewById(R.id.ModifyAdminActivityValid);
        mListView = contentView.findViewById(R.id.ModifyAdminListAnswer);
        mtextQuestion = contentView.findViewById(R.id.ModifyAdminTextQuestion);
        mImageButton = contentView.findViewById(R.id.modifyAdminActivityAddAnswer);

        this.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mAboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminMoodifyPopupWindow.this.dismiss();
            }
        });
        mValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question newQuestion;

                int in = adapter.getCheckBoxGroup().getChekedPosition();
                boolean b1 = !question.getQuestion().equals(String.valueOf(mtextQuestion.getText()));
                boolean b2 = question.getNumberAnswer() != adapter.getCheckBoxGroup().getChekedPosition();

                if (b1 || b2) {
                    if (question.getAnswerStr().equals(adapter.getStringsAnswer())) {
                        newQuestion = new Question(String.valueOf(mtextQuestion.getText()), adapter.getCheckBoxGroup().getChekedPosition(), adapter.getStringsAnswer());
                        if (!newQuestion.isError()) {
                            Question[] questions = {question, newQuestion};
                            ac.modifyQuestion(questions);
                        }

                    }
                }
                AdminMoodifyPopupWindow.this.dismiss();
            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                adapter.add(str);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setQuestion(Question q) {

        this.question = q;
        if (q != null) {
            QuestionAdminModifyAdapter adapter = new QuestionAdminModifyAdapter(context, q);
            this.adapter = adapter;
            mListView.setAdapter(adapter);
            mtextQuestion.setText(q.getQuestion());
        }

    }

}