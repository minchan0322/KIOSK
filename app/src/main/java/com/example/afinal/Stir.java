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
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Stir extends Fragment {

    private View view;
    private ImageButton back, nakgi, sosegi, pigbok, oging, jeyuk, gamja;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stir, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        nakgi = view.findViewById(R.id.nakgi);
        sosegi = view.findViewById(R.id.sosegi);
        pigbok = view.findViewById(R.id.pigbok);
        oging = view.findViewById(R.id.oging);
        jeyuk = view.findViewById(R.id.jeyuk);
        gamja = view.findViewById(R.id.gamja);

        // 필터 상태 초기화
        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilterToButtons();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Home home = new Home();
                home.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, home);
                transaction.commit();
            }
        });

        nakgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("낙지 볶음", R.drawable.stir_1, 18000, "매콤해요", "낙지 채소 등등");
                tts.speak("낙지 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        sosegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("소세지 볶음", R.drawable.stir_2, 9000, "쏘야는 못참지", "소시지 야채 등등");
                tts.speak("소세지 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pigbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("삼겹 볶음", R.drawable.stir_3, 16000, "삼겹살 먹고싶다", "삼겹살 야채 등등");
                tts.speak("삼겹 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        oging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("오징어 볶음", R.drawable.stir_4, 12000, "오징어덮밥", "오징어 야채 등등");
                tts.speak("오징어 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        jeyuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("제육 볶음", R.drawable.stir_5, 12000, "제육 하나요", "돼지고기 야채 등등");
                tts.speak("제육 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        gamja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("감자 볶음", R.drawable.stir_6, 6000, "밥도둑", "감자 당근 등등");
                tts.speak("감자 볶음",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Stir");
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
        nakgi.setColorFilter(colorFilter);
        sosegi.setColorFilter(colorFilter);
        pigbok.setColorFilter(colorFilter);
        oging.setColorFilter(colorFilter);
        jeyuk.setColorFilter(colorFilter);
        gamja.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        nakgi.clearColorFilter();
        sosegi.clearColorFilter();
        pigbok.clearColorFilter();
        oging.clearColorFilter();
        jeyuk.clearColorFilter();
        gamja.clearColorFilter();
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
