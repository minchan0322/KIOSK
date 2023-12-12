package com.example.afinal;
//onCreateView에 구현 창 실행 즉시 tts 실행되도록

import static android.speech.tts.TextToSpeech.ERROR;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Finish_Order extends Fragment {

    private String name;
    private int price, count;

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finish_order, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        tts.speak("주문이 완료되었습니다! 나가실 때 카운터에서 한 번에 결제해주세요.",TextToSpeech.QUEUE_FLUSH,null);

        if(getArguments() != null){
            name = getArguments().getString("name");
            price = getArguments().getInt("price");
            count = getArguments().getInt("count");
        }

        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        // 손가락이 화면에 닿았을 때의 처리
                        Bundle bundle = new Bundle();

                        bundle.putString("name", name);
                        bundle.putInt("price", price);
                        bundle.putInt("count", count);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        Order order = new Order();
                        order.setArguments(bundle);
                        transaction.replace(R.id.kiosk_frame, order);
                        transaction.commit();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 손가락이 화면 위를 움직일 때의 처리
                        break;
                    case MotionEvent.ACTION_UP:
                        // 손가락이 화면에서 떼어졌을 때의 처리

                        break;
                }
                return true;
            }
        });

        return view;
    }
}
