package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.speech.tts.TextToSpeech.ERROR;
import android.speech.tts.TextToSpeech;
import android.widget.ImageButton;

import java.util.Locale;

public class Home extends Fragment {

    private View view;

    private ImageButton soup, fried, stir, dry;
    private MenuDBHelper mMenuDBHelper;
    TextToSpeech tts;
    Context ct;
    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);
        ct = view.getContext();
        mMenuDBHelper = new MenuDBHelper(ct);


        insertDBData();

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        //탕류
        soup = view.findViewById(R.id.soup);

        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("탕류",TextToSpeech.QUEUE_FLUSH,null);

                Bundle bundle = new Bundle();
                bundle.putString("menu", "탕류");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Soup soup = new Soup();
                soup.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, soup);
                transaction.commit();
            }
        });

        //튀김류
        fried = view.findViewById(R.id.fried);

        fried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("튀김류",TextToSpeech.QUEUE_FLUSH,null);

                Bundle bundle = new Bundle();
                bundle.putString("menu", "튀김류");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fried fried = new Fried();
                fried.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, fried);
                transaction.commit();
            }
        });

        //볶음류
        stir = view.findViewById(R.id.stir);

        stir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("볶음류",TextToSpeech.QUEUE_FLUSH,null);

                Bundle bundle = new Bundle();
                bundle.putString("menu","볶음류");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Stir stir = new Stir();
                stir.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, stir);
                transaction.commit();
            }
        });

        //마른 안주

        dry = view.findViewById(R.id.dry);
        dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("마른안주",TextToSpeech.QUEUE_FLUSH,null);

                Bundle bundle = new Bundle();
                bundle.putString("menu","마른안주");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Dry dry = new Dry();
                dry.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, dry);
                transaction.commit();
            }
        });

        // 필터 상태 초기화
        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilterToButtons();
        }

        return view;
    }

    // 이미지 버튼에 필터를 적용하는 메서드
    private void applyFilterToButtons(ColorMatrixColorFilter colorFilter) {
        soup.setColorFilter(colorFilter);
        fried.setColorFilter(colorFilter);
        stir.setColorFilter(colorFilter);
        dry.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        soup.clearColorFilter();
        fried.clearColorFilter();
        stir.clearColorFilter();
        dry.clearColorFilter();
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

    private void insertDBData(){
        mMenuDBHelper.DeleteMenu();
        mMenuDBHelper.InsertMenu(R.drawable.fishsoup_size, "어묵탕", "Soup", 12000, "어묵탕 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.bodea_size, "부대찌개", "Soup", 18000, "부대찌개 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.braise_size, "닭볶음탕", "Soup", 22000, "닭볶음탕 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.nagasaki_size, "나가사키", "Soup", 13000, "나가사키 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.mushroom_size, "버섯전골", "Soup", 16000, "버섯전골 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.pupa_size, "번데기탕", "Soup", 9000, "번데기탕 상세설명" );

        mMenuDBHelper.InsertMenu(R.drawable.fry_1, "낙지 볶음", "Stir", 18000, "낙지 볶음 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_2, "소세지 볶음", "Stir", 9000, "소세지 볶음 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_3, "삼겹 볶음", "Stir", 16000, "삼겹 볶음 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_4, "오징어 볶음", "Stir", 12000, "오징어 볶음 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_5, "제육 볶음", "Stir", 12000, "제육 볶음 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_6, "감자 볶음", "Stir", 6000, "감자 볶음 상세설명" );

        mMenuDBHelper.InsertMenu(R.drawable.fry_1, "치킨", "Fried", 18000, "치킨 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_2, "감자튀김", "Fried", 7000, "감자튀김 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_3, "새우튀김", "Fried", 9000, "새우튀김 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_4, "오징어튀김", "Fried", 6000, "오징어튀김 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_5, "야채튀김", "Fried", 7000, "야채튀김 상세설명" );
        mMenuDBHelper.InsertMenu(R.drawable.fry_6, "고구마튀김", "Fried", 5000, "고구마튀김 상세설명" );


        mMenuDBHelper.InsertMenu(R.drawable.dry_1,"노가리","Dry",9000, "노가리 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.dry_2,	"먹태",	"Dry",	10000,"먹태 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.dry_3,	"마른오징어",	"Dry",	12000,"마른오징어 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.dry_4,	"쥐포",	"Dry",	8000,"쥐포 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.dry_5,	"땅콩",	"Dry",	5000,"땅콩 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.dry_6,	"마른안주세트",	"Dry",	18000,"마른안주 세트 상세설명");

        mMenuDBHelper.InsertMenu(R.drawable.	cheeseball_size,	"치즈볼",	"Side",	5000,	"치즈볼 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.	cheesestick_size,	"치즈스틱",	"Side",	4000,	"치즈스틱 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.	hwangdo_size,	"황도",	"Side",	8000,	"황도 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.	nacho_size,	"나초",	"Side",	7000,	"나초 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.	sotteok_size,	"소떡소떡",	"Side",	8000,	"소떡소떡 상세설명");
        mMenuDBHelper.InsertMenu(R.drawable.	yonggari_size,	"용가리",	"Side",	8000,	"용가리 상세설명");

        mMenuDBHelper.InsertMenu(R.drawable.	cheeseball_size,"	참이슬	","	Soju	",	5000	,"	참이슬	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	cheesestick_size,"	처음처럼	","	Soju	",	5000	,"	처음처럼	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	hwangdo_size,"	참이슬 오리지널	","	Soju	",	5000	,"	참이슬 오리지널	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	nacho_size,"	진로	","	Soju	",	5000	,"	진로	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	sotteok_size,"	새로	","	Soju	",	5000	,"	새로	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	yonggari_size,"	청하	","	Soju	",	6000	,"	청하	상세 설명	");

        mMenuDBHelper.InsertMenu(R.drawable.	cass_size,"	카스	","	Beer	",	6500	,"	카스	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	terra_size,"	테라	","	Beer	",	6500	,"	테라	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	kloud_size,"	클라우드	","	Beer	",	6500	,"	클라우드	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	asahi_size,"	아사히	","	Beer	",	6500	,"	아사히	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	hite_size,"	하이트	","	Beer	",	6500	,"	하이트	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	kelly_size,"	켈리	","	Beer	",	6500	,"	켈리	상세 설명	");

        mMenuDBHelper.InsertMenu(R.drawable.	jangsu_size,"	장수	","	Makgeolli	",	7000	,"	장수	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	jipyeong_size,"	지평	","	Makgeolli	",	7000	,"	지평	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	kuksundang_size,"	국순당	","	Makgeolli	",	7000	,"	국순당	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	chestnut_size,"	밤	","	Makgeolli	",	7000	,"	밤	상세 설명	");

        mMenuDBHelper.InsertMenu(R.drawable.	coke_size,"	콜라	","	Otherdrink	",	2000	,"	콜라	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	cidar_size,"	사이다	","	Otherdrink	",	2000	,"	사이다	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	fanta_size,"	환타	","	Otherdrink	",	2000	,"	환타	상세 설명	");
        mMenuDBHelper.InsertMenu(R.drawable.	tonic_size,"	토닉워터	","	Otherdrink	",	2000	,"	토닉워터	상세 설명	");
    }
}
