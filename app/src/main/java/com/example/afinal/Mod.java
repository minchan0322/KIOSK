package com.example.afinal;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Mod extends Fragment {
    Button eld, ord, color;

    TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mod, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        eld = view.findViewById(R.id.eld);
        ord = view.findViewById(R.id.ord);
        color = view.findViewById(R.id.color);

        eld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEld();tts.speak("노약자 모드",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        ord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOrd();tts.speak("일반 모드",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();tts.speak("색약 모드",TextToSpeech.QUEUE_FLUSH,null);
            }
        });


        return view;
    }

    private void changeEld() {
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mod2, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        TextView mod = dialogView.findViewById(R.id.changemod);
        mod.setText("노약자" + mod.getText().toString());

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        Button yes = dialogView.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button no = dialogView.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void changeOrd() {
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mod2, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        TextView mod = dialogView.findViewById(R.id.changemod);
        mod.setText("일반" + mod.getText().toString());

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        Button yes = dialogView.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button no = dialogView.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void changeColor() {
//        // 다이얼로그를 생성합니다.
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.color, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.
//
//        // 다이얼로그에 필요한 설정을 추가합니다.
//        builder.setView(dialogView);
//
//        // 다이얼로그를 띄웁니다.
//        AlertDialog dialog = builder.create();
//        dialog.show();

//        Intent intent = new Intent(getActivity(), Color.class);
//        startActivity(intent);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Color color1 = new Color();
        transaction.replace(R.id.kiosk_frame, color1);
        transaction.commit();


//        Button red = dialogView.findViewById(R.id.red);
//        red.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!RedBlindModeActive) {
//                    activateRedBlindMode();
//                } else {
//                    deactivateRedBlindMode();
//                }
//            }
//        });
//
//        Button green = dialogView.findViewById(R.id.green);
//        green.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//
//        Button blue = dialogView.findViewById(R.id.blue);
//        blue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//
//        Button general = dialogView.findViewById(R.id.general);
//        general.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

//        ImageButton back = dialogView.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

    }
}