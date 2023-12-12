package com.example.afinal;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Soju extends Fragment {

    private View view;
    private ImageButton back, fresh, first, freshred, jinro, searo, chungha;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.soju, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        fresh = view.findViewById(R.id.fresh);
        first = view.findViewById(R.id.first);
        freshred = view.findViewById(R.id.freshred);
        jinro = view.findViewById(R.id.jinro);
        searo = view.findViewById(R.id.searo);
        chungha = view.findViewById(R.id.chungha);

        // 필터 상태 초기화
        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilterToButtons();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Drink drink = new Drink();
                transaction.replace(R.id.kiosk_frame, drink);
                transaction.commit();
            }
        });

        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("참이슬", R.drawable.fresh_size, 5000, "16.5도", "에탄올");
                tts.speak("참이슬",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("처음처럼", R.drawable.first_size, 5000, "16.5도", "에탄올");
                tts.speak("처음처럼",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        freshred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("참이슬 오리지널", R.drawable.freshred_size, 5000, "20.1도", "에탄올");
                tts.speak("참이슬 오리지널",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        jinro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("진로", R.drawable.jinro_size, 5000, "16.5도", "에탄올");
                tts.speak("진로",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        searo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("새로", R.drawable.searo_size, 5000, "16도", "에탄올");
                tts.speak("새로",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        chungha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("청하", R.drawable.chungha_size, 6000, "13도", "에탄올");
                tts.speak("청하",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Soju");
        bundle.putString("explan", explan);
        bundle.putString("ingred", ingred);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Detail detail = new Detail();
        detail.setArguments(bundle);
        transaction.replace(R.id.kiosk_frame, detail);
        transaction.commit();
    }

    // 이미지 버튼에 필터를 적용하는 메서드
    private void applyFilterToButtons(ColorMatrixColorFilter colorFilter) {
        fresh.setColorFilter(colorFilter);
        first.setColorFilter(colorFilter);
        freshred.setColorFilter(colorFilter);
        jinro.setColorFilter(colorFilter);
        searo.setColorFilter(colorFilter);
        chungha.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        fresh.clearColorFilter();
        first.clearColorFilter();
        freshred.clearColorFilter();
        jinro.clearColorFilter();
        searo.clearColorFilter();
        chungha.clearColorFilter();
    }

    // 이미지 버튼의 필터 상태를 복원하는 메서드
    private void applySavedFilterToButtons() {
        // 이전에 저장한 필터 상태를 복원하고 필터를 적용
        int savedFilterType = sharedPreferences.getInt("ActiveFilter", 0);
        if (savedFilterType != 0) {
            ColorMatrixColorFilter colorFilter = getColorFilterForFilterType(savedFilterType);
            applyFilterToButtons(colorFilter);
        }
    }

    // 필터 유형에 따라 ColorMatrixColorFilter 반환
    private ColorMatrixColorFilter getColorFilterForFilterType(int filterType) {
        switch (filterType) {
            case 1:
                return getRedBlindFilter(); // 빨간색 필터
            case 2:
                return getGreenBlindFilter(); // 녹색 필터
            case 3:
                return getBlueBlindFilter(); // 청색 필터
            default:
                return null; // 필터 없음
        }
    }

    // 빨간색 필터를 반환하는 메서드 (activateRedBlindMode()와 유사)
    private ColorMatrixColorFilter getRedBlindFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] protanopiaMatrix = {
                0.567f, 0.433f, 0, 0, 0,
                0.558f, 0.442f, 0, 0, 0,
                0, 0.242f, 0.758f, 0, 0,
                0, 0, 0, 1, 0
        };
        colorMatrix.set(protanopiaMatrix);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    private ColorMatrixColorFilter getGreenBlindFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] deuteranopiaMatrix = {
                0.625f, 0.375f, 0, 0, 0,
                0.7f, 0.3f, 0, 0, 0,
                0, 0.3f, 0.7f, 0, 0,
                0, 0, 0, 1, 0
        };
        colorMatrix.set(deuteranopiaMatrix);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    private ColorMatrixColorFilter getBlueBlindFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] tritanopiaMatrix = {
                0.95f, 0.05f, 0, 0, 0,
                0, 0.433f, 0.567f, 0, 0,
                0, 0.475f, 0.525f, 0, 0,
                0, 0, 0, 1, 0
        };
        colorMatrix.set(tritanopiaMatrix);
        return new ColorMatrixColorFilter(colorMatrix);
    }
}
