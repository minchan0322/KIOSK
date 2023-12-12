package com.example.afinal;
//tts 선언 문 context 값을 null로 선언 오류 확인해야함

import static android.speech.tts.TextToSpeech.ERROR;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Confirm_Dialog extends AppCompatActivity {
    TextView cm, ct;
    Button yesCon, noCon;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog);

        // tts 사용 시 에러 발생
//        tts = new TextToSpeech(null, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != ERROR) {
//                    tts.setLanguage(Locale.KOREA);
//                }
//            }
//        });
//
//        tts.speak("해당 메뉴가 맞나요?",TextToSpeech.QUEUE_FLUSH,null);

        cm = findViewById(R.id.confirmMenu);
        ct = findViewById(R.id.confirmText);

        yesCon = findViewById(R.id.yesCon);
        noCon = findViewById(R.id.noCon);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        //text 키값으로 데이터를 받는다. String을 받아야 하므로 getStringExtra()를 사용함
        String confirm = intent.getStringExtra("confirm");

        cm.setText(confirm);

        yesCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트 선언 및 정의
//                Intent intent2 = new Intent(Confirm_Dialog.this, MainActivity.class);
//                //입력한 input값을 intent로 전달한다.
//                intent2.putExtra("result", cm.getText().toString());

                // 음성 인식으로 받은 결과값에 해당하는 메뉴로 이동하기.
                // Detail의 메뉴명을 조건으로. DB없이

                if(confirm.isEmpty()){
                    ct.setText("해당 메뉴는 없습니다.");
                } else {
                    // 주 메뉴/탕류
                    if(confirm.equals("어묵탕") | confirm.equals("오뎅탕")){
                        Detail("어묵탕", R.drawable.fishsoup_size, "Soup", 12000);
                    } else if(confirm.equals("부대찌개")){
                        Detail("부대찌개", R.drawable.bodea_size, "Soup", 18000);
                    } else if(confirm.equals("닭볶음탕")){
                        Detail("닭볶음탕", R.drawable.braise_size, "Soup", 22000);
                    } else if(confirm.equals("나가사키")){
                        Detail("나가사키", R.drawable.nagasaki_size, "Soup", 13000);
                    } else if(confirm.equals("버섯전골")){
                        Detail("버섯전골", R.drawable.mushroom_size, "Soup", 16000);
                    } else if(confirm.equals("번데기탕") | confirm.equals("번데기")){
                        Detail("번데기탕", R.drawable.pupa_size, "Soup", 9000);
                    }


                    // 주 메뉴/튀김류
                    else if(confirm.equals("치킨")){
                        Detail("치킨", R.drawable.fry_1, "Fried", 18000);
                    } else if(confirm.equals("감자튀김")){
                        Detail("감자튀김", R.drawable.fry_2, "Fried", 7000);
                    } else if(confirm.equals("새우튀김")){
                        Detail("새우튀김", R.drawable.fry_3, "Fried", 9000);
                    } else if(confirm.equals("오징어튀김")){
                        Detail("오징어튀김", R.drawable.fry_4, "Fried", 6000);
                    } else if(confirm.equals("야채튀김")){
                        Detail("야채튀김", R.drawable.fry_5, "Fried", 7000);
                    } else if(confirm.equals("고구마튀김")){
                        Detail("고구마튀김", R.drawable.fry_6, "Fried", 5000);
                    }


                    // 주 메뉴/볶음류
                    else if(confirm.equals("낙지볶음")){
                        Detail("낙지볶음", R.drawable.stir_1, "Stir", 123);
                    } else if(confirm.equals("소세지볶음")){
                        Detail("소세지볶음", R.drawable.stir_2, "Stir", 123);
                    } else if(confirm.equals("삼겹볶음") | confirm.equals("삼겹살볶음")){
                        Detail("삼겹볶음", R.drawable.stir_3, "Stir", 123);
                    } else if(confirm.equals("오징어볶음")){
                        Detail("오징어볶음", R.drawable.stir_4, "Stir", 123);
                    } else if(confirm.equals("제육볶음")){
                        Detail("제육볶음", R.drawable.stir_5, "Stir", 123);
                    } else if(confirm.equals("감자볶음")){
                        Detail("감자볶음", R.drawable.stir_6, "Stir", 123);
                    }


                    // 주 메뉴/마른안주
                    else if(confirm.equals("노가리")){
                        Detail("노가리", R.drawable.dry_1, "Dry", 9000);
                    } else if(confirm.equals("먹태")){
                        Detail("먹태", R.drawable.dry_2, "Dry", 10000);
                    } else if(confirm.equals("마른오징어")){
                        Detail("마른오징어", R.drawable.dry_3, "Dry", 12000);
                    } else if(confirm.equals("쥐포")){
                        Detail("쥐포", R.drawable.dry_4, "Dry", 8000);
                    } else if(confirm.equals("땅콩")){
                        Detail("땅콩", R.drawable.dry_5, "Dry", 5000);
                    } else if(confirm.equals("마른안주세트") | confirm.equals("안주세트")){
                        Detail("마른안주세트", R.drawable.dry_6, "Dry", 18000);
                    }


                    // 사이드
                    else if(confirm.equals("치즈볼")){
                        Detail("치즈볼", R.drawable.cheeseball_size, "Side", 5000);
                    } else if(confirm.equals("치즈스틱")){
                        Detail("치즈스틱", R.drawable.cheesestick_size, "Side", 4000);
                    } else if(confirm.equals("황도")){
                        Detail("황도", R.drawable.hwangdo_size, "Side", 8000);
                    } else if(confirm.equals("나초")){
                        Detail("나초", R.drawable.nacho_size, "Side", 7000);
                    } else if(confirm.equals("소떡소떡")){
                        Detail("소떡소떡", R.drawable.sotteok_size, "Side", 8000);
                    } else if(confirm.equals("용가리")){
                        Detail("용가리", R.drawable.yonggari_size, "Side", 8000);
                    }


                    // 주류/소주
                    else if(confirm.equals("참이슬")){
                        Detail("참이슬", R.drawable.fresh_size, "Soju", 5000);
                    } else if(confirm.equals("처음처럼")){
                        Detail("처음처럼", R.drawable.first_size, "Soju", 5000);
                    } else if(confirm.equals("참이슬오리지널") | confirm.equals("빨뚜")){
                        Detail("참이슬 오리지널", R.drawable.freshred_size, "Soju", 5000);
                    } else if(confirm.equals("진로")){
                        Detail("진로", R.drawable.jinro_size, "Soju", 5000);
                    } else if(confirm.equals("새로")){
                        Detail("새로", R.drawable.searo_size, "Soju", 5000);
                    } else if(confirm.equals("청하")){
                        Detail("청하", R.drawable.chungha_size, "Soju", 6000);
                    }


                    // 주류/맥주
                    else if(confirm.equals("카스")){
                        Detail("카스", R.drawable.cass_size, "Beer", 6500);
                    } else if(confirm.equals("테라")){
                        Detail("테라", R.drawable.terra_size, "Beer", 6500);
                    } else if(confirm.equals("클라우드")){
                        Detail("클라우드", R.drawable.kloud_size, "Beer", 6500);
                    } else if(confirm.equals("아사히")){
                        Detail("아사히", R.drawable.asahi_size, "Beer", 6500);
                    } else if(confirm.equals("하이트")){
                        Detail("하이트", R.drawable.hite_size, "Beer", 6500);
                    } else if(confirm.equals("켈리")){
                        Detail("켈리", R.drawable.kelly_size, "Beer", 6500);
                    }


                    // 주류/막걸리
                    else if(confirm.equals("장수") | confirm.equals("장수막걸리")){
                        Detail("장수", R.drawable.jangsu_size, "Makgeolli", 7000);
                    } else if(confirm.equals("지평") | confirm.equals("지평막걸리")){
                        Detail("지평", R.drawable.jipyeong_size, "Makgeolli", 7000);
                    } else if(confirm.equals("국순당") | confirm.equals("국순당막걸리")){
                        Detail("국순당", R.drawable.kuksundang_size, "Makgeolli", 7000);
                    } else if(confirm.equals("밤") | confirm.equals("밤막걸리")){
                        Detail("밤", R.drawable.chestnut_size, "Makgeolli", 7000);
                    }


                    // 주류/ 음료/기타
                    else if(confirm.equals("콜라") | confirm.equals("코카콜라") | confirm.equals("펩시")){
                        Detail("콜라", R.drawable.coke_size, "Otherdrink", 2000);
                    } else if(confirm.equals("사이다")){
                        Detail("사이다", R.drawable.cidar_size, "Otherdrink", 2000);
                    } else if(confirm.equals("환타")){
                        Detail("환타", R.drawable.fanta_size, "Otherdrink", 2000);
                    } else if(confirm.equals("토닉워터")){
                        Detail("토닉워터", R.drawable.tonic_size, "Otherdrink", 2000);
                    }

                    else {
                        ct.setText("해당 메뉴는 없습니다.");
                    }
                }

                finish();
            }
        });


        noCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void Detail(String name, Integer image, String kind, Integer price){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", kind);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Detail detail = new Detail();
        detail.setArguments(bundle);
        transaction.replace(R.id.kiosk_frame, detail);
        transaction.commit();
    }

}
