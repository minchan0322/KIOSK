package com.example.afinal;
//결제하기는 Other2에 구현

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Other extends Fragment {

    View view;
    Button hot, cold, snack, tissue, spoon, apron, wifi, toilet, pay;

    TextToSpeech tts;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.other, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        hot = view.findViewById(R.id.hot);
        cold = view.findViewById(R.id.cold);
        snack = view.findViewById(R.id.snack);
        tissue = view.findViewById(R.id.tissue);
        spoon = view.findViewById(R.id.spoon);
        apron = view.findViewById(R.id.apron);
        wifi = view.findViewById(R.id.wifi);
        toilet = view.findViewById(R.id.toilet);
        pay = view.findViewById(R.id.pay);

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("더워요",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("추워요",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("기본 안주",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        tissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("휴지",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        spoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("숟가락",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        apron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();tts.speak("앞지마",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 와이파이 비번
                tts.speak("와이파이",TextToSpeech.QUEUE_FLUSH,null);
                showWifi();
            }
        });

        toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 화장실 위치 및 비번
                tts.speak("와이파이",TextToSpeech.QUEUE_FLUSH,null);
                showToilet();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog() {
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.other2, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        ImageButton back = dialogView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showWifi() {
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.other2, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        ImageButton back = dialogView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView wifi = dialogView.findViewById(R.id.counter);
        wifi.setText("Wifi 비밀번호 \n 1234567890");
    }

    private void showToilet() {
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.other2, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        ImageButton back = dialogView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView toilet = dialogView.findViewById(R.id.counter);
        toilet.setText("화장실은 계산대 왼쪽에 있습니다. \n 비밀번호 : 1234*");
    }

}
