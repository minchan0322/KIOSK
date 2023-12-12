package com.example.afinal;
//tts 상세정보, 장바구니, 주문 버튼에만 구현

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Detail extends Fragment {




    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference forder = mRootRef.push();

    DatabaseReference fprice = forder.child("Price");
    DatabaseReference fname = forder.child("name");
    DatabaseReference fcount = forder.child("count");

    DatabaseReference fimage = forder.child("image");


    private View view;
    private ImageButton back;
    private ImageView imageView;
    private TextView menu, mprice, mcount;
    private String name, kind;
    private Integer image, price;
    private Button cart, order, detail, up, down;

    int count = 1;

    TextToSpeech tts;

    private SharedPreferences sharedPreferences;
    private static final String FILTER_PREFS = "FilterPrefs";
    private static final String FILTER_KEY = "FilterState";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail, container, false);

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        back = view.findViewById(R.id.back);
        menu = view.findViewById(R.id.menu);
        mprice = view.findViewById(R.id.mprice);
        cart = view.findViewById(R.id.cart);
        order = view.findViewById(R.id.order);
        imageView = view.findViewById(R.id.image);
        detail = view.findViewById(R.id.detail);

        mcount = view.findViewById(R.id.mcount);
        up = view.findViewById(R.id.up);
        down = view.findViewById(R.id.down);

        // 필터 상태 초기화
        sharedPreferences = getContext().getSharedPreferences(FILTER_PREFS, Context.MODE_PRIVATE);
        boolean isFilterApplied = sharedPreferences.getBoolean(FILTER_KEY, false);
        if (isFilterApplied) {
            applySavedFilterToButtons();
        }

        if(getArguments() != null){
            name = getArguments().getString("name");
            image = getArguments().getInt("image");
            kind = getArguments().getString("kind");
            price = getArguments().getInt("price");

            menu.setText(name);
            mprice.setText("가격 : " + String.valueOf(price) + "원");
            mcount.setText(String.valueOf(count));
            imageView.setImageResource(image);
        }

        if (kind.equals("Soup")){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Soup())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Dry")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Dry())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Fried")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Fried())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Stir")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Stir())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Side")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Side())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Soju")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Soju())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Beer")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Beer())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Makgeolli")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new Makgeolli())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } else if (kind.equals("Otherdrink")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.kiosk_frame, new OtherDrink())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }


        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetail();
                tts.speak("상세정보",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCart();
                tts.speak("장바구니",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askOrder();
                tts.speak("주문하기",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                mcount.setText(String.valueOf(count));
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1){
                    count--;
                }
                mcount.setText(String.valueOf(count));
            }
        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        fname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String text = dataSnapshot.getValue(String.class);
                // textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fprice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String text = dataSnapshot.getValue(String.class);
                // textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String text = dataSnapshot.getValue(String.class);
                // textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fcount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String text = dataSnapshot.getValue(String.class);
                // textView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void showDetail(){
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

        TextView detail = dialogView.findViewById(R.id.counter);
        detail.setText("메뉴 별 상세정보");        // 메뉴 명으로 if문 사용 가능. DB로
    }

    public void askCart(){
        // 해당 메뉴 장바구니 추가
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.choice_dialog, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        TextView textView = dialogView.findViewById(R.id.textView);
        textView.setText("장바구니에 추가하시겠습니까?");

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        Button disagree = dialogView.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button agree = dialogView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                fcount.setValue(count);
                fprice.setValue(price);
                fname.setValue(name);
                fimage.setValue(image);

                bundle.putInt("image", image);
                bundle.putString("name", name);
                bundle.putInt("price", price);
                bundle.putInt("count", count);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Cart cart = new Cart();
                cart.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, cart);
                transaction.commit();

                dialog.dismiss();
            }
        });
    }

    public void askOrder(){
        // 해당 메뉴 바로 주문
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.choice_dialog, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        TextView textView = dialogView.findViewById(R.id.textView);
        textView.setText("바로 주문 하시겠습니까?");

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        Button disagree = dialogView.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button agree = dialogView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("name", name);
                bundle.putInt("price", price);
                bundle.putInt("count", count);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Finish_Order order = new Finish_Order();
                order.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, order);
                transaction.commit();

                dialog.dismiss();
            }
        });
    }

    // 이미지 버튼에 필터를 적용하는 메서드
    private void applyFilterToButtons(ColorMatrixColorFilter colorFilter) {
        imageView.setColorFilter(colorFilter);
    }

    // 이미지 버튼의 필터를 제거하는 메서드
    private void clearFilterFromButtons() {
        imageView.clearColorFilter();
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
