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

public class Soup extends Fragment {

    private View view;
    private ImageButton back, fishsoup, bodea, braise, nagasaki, mushroom, pupa;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.soup, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        fishsoup = view.findViewById(R.id.fishsoup);
        bodea = view.findViewById(R.id.bodea);
        braise = view.findViewById(R.id.braisechicken);
        nagasaki = view.findViewById(R.id.nagasaki);
        mushroom = view.findViewById(R.id.mushroomstew);
        pupa = view.findViewById(R.id.pupasoup);

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

        fishsoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("어묵탕", R.drawable.fishsoup_size, 12000, "시원한 오뎅 국물", "오뎅뎅");
                tts.speak("어묵탕",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        bodea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("부대찌개", R.drawable.bodea_size, 18000, "햄이 가득", "햄 소시지 라면 등등");
                tts.speak("부대찌개",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        braise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("닭볶음탕", R.drawable.braise_size, 22000, "닭 한마리!", "닭 감자 등등");
                tts.speak("닭볶음탕",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        nagasaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("나가사키", R.drawable.nagasaki_size, 13000, "얼큰한 나가사키 짬뽕", "해물 채소 등등");
                tts.speak("나가사키",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        mushroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("버섯 전골", R.drawable.mushroom_size, 16000, "버섯보소", "버섯");
                tts.speak("버섯 전골",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        pupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("번데기탕", R.drawable.pupa_size, 9000, "웩", "번데기");
                tts.speak("번데기탕",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Soup");
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
        fishsoup.setColorFilter(colorFilter);
        bodea.setColorFilter(colorFilter);
        braise.setColorFilter(colorFilter);
        nagasaki.setColorFilter(colorFilter);
        mushroom.setColorFilter(colorFilter);
        pupa.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        fishsoup.clearColorFilter();
        bodea.clearColorFilter();
        braise.clearColorFilter();
        nagasaki.clearColorFilter();
        mushroom.clearColorFilter();
        pupa.clearColorFilter();
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
