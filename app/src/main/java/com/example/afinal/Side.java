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

public class Side extends Fragment {

    private ImageButton cheeseball, cheesestick, hwangdo, nacho, sotteok, yonggari;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.side, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        cheeseball = view.findViewById(R.id.cheeseball);
        cheesestick = view.findViewById(R.id.cheesestick);
        hwangdo = view.findViewById(R.id.hwangdo);
        nacho = view.findViewById(R.id.nacho);
        sotteok = view.findViewById(R.id.sotteok);
        yonggari = view.findViewById(R.id.yonggari);

        // 필터 상태 초기화
        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilterToButtons();
        }

        // 메뉴 별 상세페이지로 이동
        cheeseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("치즈볼", R.drawable.cheeseball_size, 5000, "치즈 화상 주의", "체다취즈");
                tts.speak("치즈볼",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        cheesestick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("치즈스틱", R.drawable.cheesestick_size, 4000, "식기 전에 빨리 드세여", "스트링치즈");
                tts.speak("치즈스틱",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        hwangdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("황도", R.drawable.hwangdo_size, 8000, "와 싸다!", "통조림");
                tts.speak("황도",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        nacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("나초", R.drawable.nacho_size, 7000, "소스가 맛도리", "토르티야");
                tts.speak("나초",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        sotteok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("소떡소떡", R.drawable.sotteok_size, 8000, "왤케 비싸", "소세지 떡");
                tts.speak("소떡소떡",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        yonggari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("용가리", R.drawable.yonggari_size, 8000, "추억돋네요", "티라노");
                tts.speak("용가리",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }


    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Side");
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
        cheeseball.setColorFilter(colorFilter);
        cheesestick.setColorFilter(colorFilter);
        hwangdo.setColorFilter(colorFilter);
        nacho.setColorFilter(colorFilter);
        sotteok.setColorFilter(colorFilter);
        yonggari.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        cheeseball.clearColorFilter();
        cheesestick.clearColorFilter();
        hwangdo.clearColorFilter();
        nacho.clearColorFilter();
        sotteok.clearColorFilter();
        yonggari.clearColorFilter();
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
