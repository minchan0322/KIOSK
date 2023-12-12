package com.example.afinal;
//tts 적용 db적용 아님

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

public class Beer extends Fragment {

    private View view;
    private ImageButton back, cass, terra, kloud, asahi, hite, kelly;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.beer, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        cass = view.findViewById(R.id.cass);
        terra = view.findViewById(R.id.terra);
        kloud = view.findViewById(R.id.kloud);
        asahi = view.findViewById(R.id.asahi);
        hite = view.findViewById(R.id.hite);
        kelly = view.findViewById(R.id.kelly);

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

        cass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("카스", R.drawable.cass_size, 6500, "Cass", "보리");
                tts.speak("카스",TextToSpeech.QUEUE_FLUSH,null);
            }

        });

        terra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("테라", R.drawable.terra_size, 6500, "Terra", "보리");
                tts.speak("테라",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        kloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("클라우드", R.drawable.kloud_size, 6500,"Kloud", "보리");
                tts.speak("클라우드",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        asahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("아사히", R.drawable.asahi_size, 6500,"Asahi", "보리");
                tts.speak("아사히",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        hite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("하이트", R.drawable.hite_size, 6500, "Hite", "보리");
                tts.speak("하이트",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        kelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("켈리", R.drawable.kelly_size, 6500, "Kelly", "보리");
                tts.speak("켈리",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Beer");
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
        cass.setColorFilter(colorFilter);
        terra.setColorFilter(colorFilter);
        kloud.setColorFilter(colorFilter);
        asahi.setColorFilter(colorFilter);
        hite.setColorFilter(colorFilter);
        kelly.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        cass.clearColorFilter();
        terra.clearColorFilter();
        kloud.clearColorFilter();
        asahi.clearColorFilter();
        hite.clearColorFilter();
        kelly.clearColorFilter();
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
