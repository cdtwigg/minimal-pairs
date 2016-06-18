package gg.twi.minimalpairs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity {

    private String leftAnswer;
    private String rightAnswer;
    private boolean leftIsCorrect;

    public ValueAnimator flashColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftAnswer = "Pin";
        rightAnswer = "Bin";
        leftIsCorrect = false;

        Button leftButton = (Button) findViewById(R.id.leftButton);
        setupButton(leftButton, leftAnswer);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton(true, v);
            }
        });

        Button rightButton = (Button) findViewById(R.id.rightButton);
        setupButton(rightButton, rightAnswer);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton(false, v);
            }
        });
    }

    void setupButton(Button button, String targetText) {
        button.setText(targetText);
        button.setAllCaps(false);
        button.setTextSize(30);
    }

    void clickedButton(boolean isLeft, View v) {
        final float[] redColor = {255, 0, 0};
        final float[] greenColor = {34, 139, 34};

        Button b = (Button) v;

        final boolean isCorrect = (isLeft == leftIsCorrect);
        final float[] targetColor = isCorrect ? redColor : greenColor;
        final Drawable drawable = (Drawable) b.getBackground();
        drawable.mutate();

        drawable.clearColorFilter();

        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        animation.setDuration(200);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animVal = (Float) animation.getAnimatedValue();

                final float interp = animVal.floatValue();
                final float invInterp = (1.0f - interp);
                float[] colorMatrix =
                        {invInterp,         0,         0, 0, interp*targetColor[0],
                              0,    invInterp,         0, 0, interp*targetColor[1],
                              0,            0, invInterp, 0, interp*targetColor[2],
                              0,            0,         0, 1, 0};
                drawable.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            }
        });
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                drawable.clearColorFilter();
            }
        });
        animation.start();
    }

}
