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

public class Fried extends Fragment {

    private View view;
    private ImageButton back, chicken, potato, shrimp, squid, yache, goguma;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fry, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        chicken = view.findViewById(R.id.chicken);
        potato = view.findViewById(R.id.potato);
        shrimp = view.findViewById(R.id.shrimp);
        squid = view.findViewById(R.id.squid);
        yache = view.findViewById(R.id.yache);
        goguma = view.findViewById(R.id.goguma);

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

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("치킨", R.drawable.fry_1, 18000, "겉바속촉", "토종닭");
                tts.speak("치킨",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("감자튀김", R.drawable.fry_2, 7000, "주문 즉시 튀겨드립니다", "왕감자");
                tts.speak("감자튀김",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        shrimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("새우튀김", R.drawable.fry_3, 9000, "탱글탱글 새우", "독도 새우");
                tts.speak("새우튀김",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        squid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("오징어튀김", R.drawable.fry_4, 6000, "쫄깃쫄깃 오징어", "울릉도 오징어");
                tts.speak("오징어튀김",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        yache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("야채튀김", R.drawable.fry_5, 7000, "야채도 튀기면 맛있다", "각종 야채");
                tts.speak("야채튀김",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        goguma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("고구마튀김", R.drawable.fry_6, 5000, "목메이는 그 맛", "고구마호박");
                tts.speak("고구마튀김",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Fried");
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
        chicken.setColorFilter(colorFilter);
        potato.setColorFilter(colorFilter);
        shrimp.setColorFilter(colorFilter);
        squid.setColorFilter(colorFilter);
        yache.setColorFilter(colorFilter);
        goguma.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        chicken.clearColorFilter();
        potato.clearColorFilter();
        shrimp.clearColorFilter();
        squid.clearColorFilter();
        yache.clearColorFilter();
        goguma.clearColorFilter();
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
