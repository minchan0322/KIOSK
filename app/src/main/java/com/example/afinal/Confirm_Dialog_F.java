package com.example.afinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Confirm_Dialog_F extends Fragment {
    TextView cm, ct;
    Button yesCon, noCon;

    String name, kind;
    int image, price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_dialog, container, false);

        name = "";
        kind = "";
        image = 0;
        price = 0;

        cm = view.findViewById(R.id.confirmMenu);
        ct = view.findViewById(R.id.confirmText);

        yesCon = view.findViewById(R.id.yesCon);
        noCon = view.findViewById(R.id.noCon);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String confirm = arguments.getString("confirm");
            cm.setText(confirm);

            // 여기서 메뉴 데이터를 설정
            // 주 메뉴/탕류
            if(confirm.equals("어묵탕") || confirm.equals("오뎅탕")){
                name = "어묵탕";
                image = R.drawable.fishsoup_size;
                kind = "Soup";
                price = 12000;
            } else if(confirm.equals("부대찌개")){
                name = "부대찌개";
                image = R.drawable.bodea_size;
                kind = "Soup";
                price = 18000;
            } else if(confirm.equals("닭볶음탕")){
                name = "닭볶음탕";
                image = R.drawable.braise_size;
                kind = "Soup";
                price = 22000;
            } else if(confirm.equals("나가사키")){
                name = "나가사키";
                image = R.drawable.nagasaki_size;
                kind = "Soup";
                price = 13000;
            } else if(confirm.equals("버섯전골")){
                name = "버섯전골";
                image = R.drawable.mushroom_size;
                kind = "Soup";
                price = 16000;
            } else if(confirm.equals("번데기탕") || confirm.equals("번데기")){
                name = "번데기탕";
                image = R.drawable.pupa_size;
                kind = "Soup";
                price = 9000;
            }


            // 주 메뉴/튀김류
            else if(confirm.equals("치킨")){
                name = "치킨";
                image = R.drawable.fry_1;
                kind = "Fried";
                price = 18000;
            } else if(confirm.equals("감자튀김")){
                name = "감자튀김";
                image = R.drawable.fry_2;
                kind = "Fried";
                price = 7000;
            } else if(confirm.equals("새우튀김")){
                name = "새우튀김";
                image = R.drawable.fry_3;
                kind = "Fried";
                price = 9000;
            } else if(confirm.equals("오징어튀김")){
                name = "오징어튀김";
                image = R.drawable.fry_4;
                kind = "Fried";
                price = 6000;
            } else if(confirm.equals("야채튀김")){
                name = "야채튀김";
                image = R.drawable.fry_5;
                kind = "Fried";
                price = 7000;
            } else if(confirm.equals("고구마튀김")){
                name = "고구마튀김";
                image = R.drawable.fry_6;
                kind = "Fried";
                price = 5000;
            }


            // 주 메뉴/볶음류
            else if(confirm.equals("낙지볶음")){
                name = "낙지볶음";
                image = R.drawable.stir_1;
                kind = "Stir";
                price = 18000;
            } else if(confirm.equals("소세지볶음")){
                name = "소세지볶음";
                image = R.drawable.stir_2;
                kind = "Stir";
                price = 9000;
            } else if(confirm.equals("삼겹볶음") || confirm.equals("삼겹살볶음")){
                name = "삼겹볶음";
                image = R.drawable.stir_3;
                kind = "Stir";
                price = 16000;
            } else if(confirm.equals("오징어볶음")){
                name = "오징어볶음";
                image = R.drawable.stir_4;
                kind = "Stir";
                price = 12000;
            } else if(confirm.equals("제육볶음")){
                name = "제육볶음";
                image = R.drawable.stir_5;
                kind = "Stir";
                price = 12000;
            } else if(confirm.equals("감자볶음")){
                name = "감자볶음";
                image = R.drawable.stir_6;
                kind = "Stir";
                price = 6000;
            }


            // 주 메뉴/마른안주
            else if(confirm.equals("노가리")){
                name = "노가리";
                image = R.drawable.dry_1;
                kind = "Stir";
                price = 9000;
            } else if(confirm.equals("먹태")){
                name = "먹태";
                image = R.drawable.dry_2;
                kind = "Stir";
                price = 10000;
            } else if(confirm.equals("마른오징어")){
                name = "마른오징어";
                image = R.drawable.dry_3;
                kind = "Stir";
                price = 12000;
            } else if(confirm.equals("쥐포")){
                name = "쥐포";
                image = R.drawable.dry_4;
                kind = "Stir";
                price = 8000;
            } else if(confirm.equals("땅콩")){
                name = "땅콩";
                image = R.drawable.dry_5;
                kind = "Stir";
                price = 5000;
            } else if(confirm.equals("마른안주세트") || confirm.equals("안주세트")){
                name = "마른안주세트";
                image = R.drawable.dry_6;
                kind = "Stir";
                price = 18000;
            }


            // 사이드
            else if(confirm.equals("치즈볼")){
                name = "치즈볼";
                image = R.drawable.cheeseball_size;
                kind = "Side";
                price = 5000;
            } else if(confirm.equals("치즈스틱")){
                name = "치즈스틱";
                image = R.drawable.cheesestick_size;
                kind = "Side";
                price = 4000;
            } else if(confirm.equals("황도")){
                name = "황도";
                image = R.drawable.hwangdo_size;
                kind = "Side";
                price = 8000;
            } else if(confirm.equals("나초")){
                name = "나초";
                image = R.drawable.nacho_size;
                kind = "Side";
                price = 7000;
            } else if(confirm.equals("소떡소떡")){
                name = "소떡소떡";
                image = R.drawable.sotteok_size;
                kind = "Side";
                price = 8000;
            } else if(confirm.equals("용가리")){
                name = "용가리";
                image = R.drawable.yonggari_size;
                kind = "Side";
                price = 8000;
            }


            // 주류/소주
            else if(confirm.equals("참이슬")){
                name = "참이슬";
                image = R.drawable.fresh_size;
                kind = "Soju";
                price = 5000;
            } else if(confirm.equals("처음처럼")){
                name = "처음처럼";
                image = R.drawable.first_size;
                kind = "Soju";
                price = 5000;
            } else if(confirm.equals("참이슬오리지널") || confirm.equals("빨뚜")){
                name = "참이슬 오리지널";
                image = R.drawable.freshred_size;
                kind = "Soju";
                price = 5000;
            } else if(confirm.equals("진로")){
                name = "진로";
                image = R.drawable.jinro_size;
                kind = "Soju";
                price = 5000;
            } else if(confirm.equals("새로")){
                name = "새로";
                image = R.drawable.searo_size;
                kind = "Soju";
                price = 5000;
            } else if(confirm.equals("청하")){
                name = "청하";
                image = R.drawable.chungha_size;
                kind = "Soju";
                price = 6000;
            }


            // 주류/맥주
            else if(confirm.equals("카스")){
                name = "카스";
                image = R.drawable.cass_size;
                kind = "Beer";
                price = 6500;
            } else if(confirm.equals("테라")){
                name = "테라";
                image = R.drawable.terra_size;
                kind = "Beer";
                price = 6500;
            } else if(confirm.equals("클라우드")){
                name = "클라우드";
                image = R.drawable.kloud_size;
                kind = "Beer";
                price = 6500;
            } else if(confirm.equals("아사히")){
                name = "아사히";
                image = R.drawable.asahi_size;
                kind = "Beer";
                price = 6500;
            } else if(confirm.equals("하이트")){
                name = "하이트";
                image = R.drawable.hite_size;
                kind = "Beer";
                price = 6500;
            } else if(confirm.equals("켈리")){
                name = "켈리";
                image = R.drawable.kelly_size;
                kind = "Beer";
                price = 6500;
            }


            // 주류/막걸리
            else if(confirm.equals("장수") || confirm.equals("장수막걸리")){
                name = "장수 막걸리";
                image = R.drawable.jangsu_size;
                kind = "Makgeolli";
                price = 7000;
            } else if(confirm.equals("지평") || confirm.equals("지평막걸리")){
                name = "지평 막걸리";
                image = R.drawable.jipyeong_size;
                kind = "Makgeolli";
                price = 7000;
            } else if(confirm.equals("국순당") || confirm.equals("국순당막걸리")){
                name = "국순당 막걸리";
                image = R.drawable.kuksundang_size;
                kind = "Makgeolli";
                price = 7000;
            } else if(confirm.equals("밤") || confirm.equals("밤막걸리")){
                name = "밤 막걸리";
                image = R.drawable.chestnut_size;
                kind = "Makgeolli";
                price = 7000;
            }


            // 주류/ 음료/기타
            else if(confirm.equals("콜라") || confirm.equals("코카콜라") || confirm.equals("펩시")){
                name = "콜라";
                image = R.drawable.coke_size;
                kind = "Otherdrink";
                price = 2000;
            } else if(confirm.equals("사이다")){
                name = "사이다";
                image = R.drawable.cidar_size;
                kind = "Otherdrink";
                price = 2000;
            } else if(confirm.equals("환타")){
                name = "환타";
                image = R.drawable.fanta_size;
                kind = "Otherdrink";
                price = 2000;
            } else if(confirm.equals("토닉워터")){
                name = "토닉워터";
                image = R.drawable.tonic_size;
                kind = "Otherdrink";
                price = 2000;
            }

            else {
                ct.setText("해당 메뉴는 없습니다.");
            }

            yesCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!name.isEmpty()) {
                        // Detail 프래그먼트로 이동
                        Detail(name, image, kind, price);
                    }
                }
            });

            noCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 프래그먼트 닫기
                    getParentFragmentManager().popBackStack();
                }
            });
        }

        return view;
    }

    public void Detail(String name, Integer image, String kind, Integer price) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("image", image);
        bundle.putInt("price", price);
        bundle.putString("kind", kind);

        // Detail 프래그먼트 초기화 및 데이터 전달
        Detail detail = new Detail();
        detail.setArguments(bundle);

        // 프래그먼트 트랜잭션 시작
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.kiosk_frame, detail); // "kiosk_frame"를 사용할 프레임 레이아웃 ID로 변경
        transaction.addToBackStack(null); // 뒤로 가기 버튼 동작을 위해 백 스택에 추가
        transaction.commit();
    }
}