package com.example.afinal;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;  // 번들을 사용하기 위해 추가
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Color extends Fragment {

    Button red, green, blue, general;
    ImageView colortest;
    ImageButton back;
    TextToSpeech tts;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color, container, false);

        colortest = view.findViewById(R.id.colortest);
        red = view.findViewById(R.id.red);
        green = view.findViewById(R.id.green);
        blue = view.findViewById(R.id.blue);
        general = view.findViewById(R.id.general);
        back = view.findViewById(R.id.back);

        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilter();
        }


        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateRedBlindMode();
                tts.speak("적색약 선택", TextToSpeech.QUEUE_FLUSH, null);
                saveFilterState(1); // 1은 빨간색 필터에 해당
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGreenBlindMode();
                tts.speak("녹색약 선택", TextToSpeech.QUEUE_FLUSH, null);
                saveFilterState(2); // 2는 녹색 필터에 해당
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateBlueBlindMode();
                tts.speak("청색약 선택", TextToSpeech.QUEUE_FLUSH, null);
                saveFilterState(3); // 3은 청색 필터에 해당
            }
        });

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
                saveFilterState(0); // 0은 필터 없음을 나타냅니다.
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.kiosk_frame, new Mod())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void activateRedBlindMode() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] protanopiaMatrix = {
                0.567f, 0.433f, 0, 0, 0,
                0.558f, 0.442f, 0, 0, 0,
                0, 0.242f, 0.758f, 0, 0,
                0, 0, 0, 1, 0
        };

        colorMatrix.set(protanopiaMatrix);

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        colortest.setColorFilter(colorFilter);
    }

    private void activateGreenBlindMode() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] deuteranopiaMatrix = {
                0.625f, 0.375f, 0, 0, 0,
                0.7f, 0.3f, 0, 0, 0,
                0, 0.3f, 0.7f, 0, 0,
                0, 0, 0, 1, 0
        };

        colorMatrix.set(deuteranopiaMatrix);

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        colortest.setColorFilter(colorFilter);
    }

    private void activateBlueBlindMode() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] tritanopiaMatrix = {
                0.95f, 0.05f, 0, 0, 0,
                0, 0.433f, 0.567f, 0, 0,
                0, 0.475f, 0.525f, 0, 0,
                0, 0, 0, 1, 0
        };

        colorMatrix.set(tritanopiaMatrix);

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        colortest.setColorFilter(colorFilter);
    }

    private void clearFilter() {
        colortest.clearColorFilter();
    }

    private void saveFilterState(int filterType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FILTER_KEY, filterType != 0); // 필터가 있는지 여부를 저장
        editor.putInt("ActiveFilter", filterType); // 활성 필터 유형을 저장
        editor.apply();
    }

    private void applySavedFilter() {
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            int savedFilter = sharedPreferences.getInt("ActiveFilter", 0); // 기본값은 필터 없음
            switch (savedFilter) {
                case 1:
                    activateRedBlindMode();
                    break;
                case 2:
                    activateGreenBlindMode();
                    break;
                case 3:
                    activateBlueBlindMode();
                    break;
                default:
                    break;
            }
        }
    }
}
