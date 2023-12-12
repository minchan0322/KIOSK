package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static android.speech.tts.TextToSpeech.ERROR;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class First extends AppCompatActivity {

    Button eld, ord;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        eld = findViewById(R.id.eld);
        ord = findViewById(R.id.ord);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        eld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("노약자 메뉴를 선택하시겠습니까?",TextToSpeech.QUEUE_FLUSH,null);

                View dlg = getLayoutInflater().inflate(R.layout.eld, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                builder.setView(dlg);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button noEld = dlg.findViewById(R.id.noEld);
                noEld.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak("노약자 메뉴 선택을 취소하셨습니다.",TextToSpeech.QUEUE_FLUSH,null);
                        alertDialog.dismiss();
                    }
                });

                Button yesEld = dlg.findViewById(R.id.yesEld);
                yesEld.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak("노약자 메뉴를 선택하셨습니다.",TextToSpeech.QUEUE_FLUSH,null);

                        //인텐트 선언 및 정의
                        Intent intent = new Intent(First.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        ord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("일반 메뉴를 선택하시겠습니까?",TextToSpeech.QUEUE_FLUSH,null);

                View dlg = getLayoutInflater().inflate(R.layout.ord, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(First.this);
                builder.setView(dlg);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button noOrd = dlg.findViewById(R.id.noOrd);
                noOrd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak("일반 메뉴 선택을 취소하셨습니다.",TextToSpeech.QUEUE_FLUSH,null);
                        alertDialog.dismiss();
                    }
                });

                Button yesOrd = dlg.findViewById(R.id.yesOrd);
                yesOrd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak("일반 메뉴를 선택하시겠습니까?",TextToSpeech.QUEUE_FLUSH,null);
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 하단 바 없애기 //
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        // 하단 바 없애기 //

    }
}
