package com.example.afinal;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton mic;

    Button main, side, drink, call, other, cart, mod, order;

    Intent intent;
    SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;

    TextToSpeech tts;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Home home = new Home();
        transaction.replace(R.id.kiosk_frame, home);
        transaction.commit();

        mic = findViewById(R.id.mic);
        call = findViewById(R.id.call);
        other = findViewById(R.id.other);
        main = findViewById(R.id.main);
        side = findViewById(R.id.side);
        drink = findViewById(R.id.drink);
        cart = findViewById(R.id.basket);
        mod = findViewById(R.id.mod);
        order = findViewById(R.id.order);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR"); // 언어 설정


        // 버튼 클릭 시 객체에 Context와 listener를 할당
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts.speak("음성인식", TextToSpeech.QUEUE_FLUSH, null);
                showDialog01();

//                //인텐트 선언 및 정의
//                Intent intent2 = new Intent(MainActivity.this, Confirm_Dialog.class);
//                //입력한 input값을 intent로 전달한다.
//                intent2.putExtra("confirm", "menu");
//                startActivity(intent2);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("직원 호출",TextToSpeech.QUEUE_FLUSH,null);
                staffCall();
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("기타",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Other other = new Other();
                transaction.replace(R.id.kiosk_frame, other);
                transaction.commit();
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("주 메뉴",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Home home = new Home();
                transaction.replace(R.id.kiosk_frame, home);
                transaction.commit();
            }
        });

        side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("곁들임 메뉴",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Side side = new Side();
                transaction.replace(R.id.kiosk_frame, side);
                transaction.commit();
            }
        });

        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("주류",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Drink drink = new Drink();
                transaction.replace(R.id.kiosk_frame, drink);
                transaction.commit();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("장바구니",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Cart cart = new Cart();
                transaction.replace(R.id.kiosk_frame, cart);
                transaction.commit();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("주문 내역",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Order order = new Order();
                transaction.replace(R.id.kiosk_frame, order);
                transaction.commit();
            }
        });

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("상태 변환",TextToSpeech.QUEUE_FLUSH,null);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Mod mod = new Mod();
                transaction.replace(R.id.kiosk_frame, mod);
                transaction.commit();
            }
        });

        // 하단 바 없애기 //
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        // 하단 바 없애기 //
    }


    public void startSTT(){
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
        mRecognizer.setRecognitionListener(listener); // 리스너 설정
        mRecognizer.startListening(intent); // 듣기 시작
    }

    public void showDialog01(){

        // ex
        View dlg = getLayoutInflater().inflate(R.layout.choice_dialog, null);

        AlertDialog.Builder dlg03 = new AlertDialog.Builder(MainActivity.this);
        dlg03.setView(dlg);

        final AlertDialog alertDialog = dlg03.create();
        alertDialog.show();

        Button noVoice = dlg.findViewById(R.id.disagree);
        noVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button yesVoice = dlg.findViewById(R.id.agree);
        yesVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSTT();

                alertDialog.dismiss();
            }
        });

    }

    public void staffCall(){

        // ex
        View dlg = getLayoutInflater().inflate(R.layout.other2, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dlg);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ImageButton back = dlg.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        TextView staff = dlg.findViewById(R.id.counter);
        staff.setText("직원이 가고 있습니다. \n 잠시만 기다려주세요~");

    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
            Log.d("tst5", "시작");
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
            Log.d("tst5", "onError: "+message);
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String result = "";

            for(int i = 0; i < matches.size() ; i++){
                result = matches.get(i);
            }

            Confirm_Dialog_F confirmDialogFragment = new Confirm_Dialog_F();
            Bundle bundle = new Bundle();
            bundle.putString("confirm", result);
            confirmDialogFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.kiosk_frame, confirmDialogFragment); // "kiosk_frame"을 사용할 프레임 레이아웃 ID로 변경
            transaction.addToBackStack(null); // 뒤로 가기 버튼 동작을 위해 백 스택에 추가
            transaction.commit();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };

}