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

public class Dry extends Fragment {

    private View view;
    private ImageButton back, nogari, muktea, oginga, gipo, ddangkong, modum;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dry, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        nogari = view.findViewById(R.id.nogari);
        muktea = view.findViewById(R.id.muktea);
        oginga = view.findViewById(R.id.oginga);
        gipo = view.findViewById(R.id.gipo);
        ddangkong = view.findViewById(R.id.ddangkong);
        modum = view.findViewById(R.id.modum);

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

        nogari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("노가리", R.drawable.dry_1, 9000, "술안주로 좋습니다.", "명태의 새끼");
                tts.speak("노가리",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        muktea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("먹태", R.drawable.dry_2, 10000, "청양마요 푹찍.", "명태 촉촉 말리기");
                tts.speak("먹태",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        oginga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("마른 오징어", R.drawable.dry_3, 12000, "오징어 한 마리 통쨰로 말렸습니다.", "울릉도 오징어");
                tts.speak("마른 오징어",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        gipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("쥐포", R.drawable.dry_4, 8000, "국내산 쥐치로 만든 쥐포!", "쥐치");
                tts.speak("쥐포",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        ddangkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("땅콩", R.drawable.dry_5, 5000, "고소하이", "우도 땅콩");
                tts.speak("땅콩",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        modum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPage("마른 안주 세트", R.drawable.dry_6, 18000, "안주 집합.", "기타 등등");
                tts.speak("마른 안주 세트",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return view;
    }

    public void viewPage(String name, Integer image, Integer price, String explan, String ingred){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", "Dry");
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
        nogari.setColorFilter(colorFilter);
        muktea.setColorFilter(colorFilter);
        oginga.setColorFilter(colorFilter);
        gipo.setColorFilter(colorFilter);
        ddangkong.setColorFilter(colorFilter);
        modum.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        nogari.clearColorFilter();
        muktea.clearColorFilter();
        oginga.clearColorFilter();
        gipo.clearColorFilter();
        ddangkong.clearColorFilter();
        modum.clearColorFilter();
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
