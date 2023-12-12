package com.example.afinal;
//tts 적용 안함

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart extends Fragment {

    private Button add, order;
    private ArrayList<CartData> mCartDatas;
    private ArrayList<OrderData> mOrderDatas;
    private CartAdapter mCartAdapter;
    private OrderAdapter mOrderAdapter;
    private RecyclerView mRv_cart;
    private LinearLayoutManager linearLayoutManager;

    private CartDBHelper mCartDBHelper;
    private OrderDBHelper mOrderDBHelper;

    private String name, option;
    private Integer image, count, price = 0;
    private Integer sum = 0;
    private TextView cart_total;





    Context ct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart, container, false);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();


        mRv_cart = view.findViewById(R.id.cart_rv);
        ct = view.getContext();
        linearLayoutManager = new LinearLayoutManager(ct);
        mRv_cart.setLayoutManager(linearLayoutManager);

        mCartDatas = new ArrayList<>();
        mCartDBHelper = new CartDBHelper(ct);
        mOrderDBHelper = new OrderDBHelper(ct);


        loadRecentDB();
        cart_total = view.findViewById(R.id.cart_total);
        sum = total();
        cart_total.setText(String.valueOf(sum));



        // 드래그하고 Ctrl + /. 장바구니 확인하려고 주석 처리 함
//        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//        mRootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//
//                    image = data.child("image").getValue(Integer.class);
//                    name = data.child("name").getValue(String.class);
//                    price = data.child("Price").getValue(Integer.class);
//                    count = data.child("count").getValue(Integer.class);
//                    option = "메뉴 옵션";
//                    Log.d("data ", data.toString());
//                    Log.d("image", image.toString());
//                    CartData cartData = new CartData(image, price, count, name, option);
//                    arrayList.add(cartData);
//                    cartAdapter.notifyDataSetChanged();
//                };
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        // detail에서 넘겨온 값 DB로 저장
        if(getArguments() != null){
            image = getArguments().getInt("image");
            name = getArguments().getString("name");
            price = getArguments().getInt("price");
            count = getArguments().getInt("count");
            option = "메뉴 옵션";

            CartData cartData = new CartData();
            cartData.setCart_image(image);
            cartData.setCart_name(name);
            cartData.setCart_option(option);
            cartData.setCart_count(count);
            cartData.setCart_price(price);


            ArrayList<CartData> list = mCartDBHelper.getMenuList();
            boolean check = false;
            for(int i = 0; i < list.size(); i++) {
                CartData data = list.get(i);
                if(name.equals(data.getCart_name())){
                    mCartDBHelper.UpdateCount(data.getCart_count()+1, data.getId());
                    check = true;
                    mCartDatas = mCartDBHelper.getMenuList();
                    mCartAdapter = new CartAdapter(mCartDatas, ct);
                    mRv_cart.setHasFixedSize(true);
                    mRv_cart.setAdapter(mCartAdapter);
                    sum = total();
                    cart_total.setText(String.valueOf(sum)+"원");
                }

            }
            if(!check){
                mCartDBHelper.InsertCart(image, name, option, count, price);
                mCartAdapter.addItem(cartData);
                sum = total();
                cart_total.setText(String.valueOf(sum)+"원");

            }



            // System.out.println("이름:"+ name+ "가격"+ price+ "개수: "+ count);

            //mCartDBHelper.InsertCart(image, name, option, count, price);

            //mCartAdapter.addItem(cartData);
            mRv_cart.smoothScrollToPosition(0);
            Toast.makeText(ct, "추가되었습니다.", Toast.LENGTH_SHORT).show();


        }



        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdd();
            }

        });

        order = view.findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                ArrayList<CartData> list = mCartDBHelper.getMenuList();
                for(int i = 0; i < list.size(); i++){
                    CartData data = list.get(i);
                    System.out.println("name:" + data.getCart_name());
                    System.out.println("count:" + data.getCart_count());
                    System.out.println("price:" + data.getCart_price());

                    mOrderDBHelper.InsertOrder(data.getCart_name(), data.getCart_count(), data.getCart_price());

                }
//                bundle.putInt("cartSize", list.size());
//                bundle.putString("name", name);
//                bundle.putInt("price", price);
//                bundle.putInt("count", count);

                mCartDBHelper.DeleteCart();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Finish_Order order = new Finish_Order();
                //order.setArguments(bundle);
                transaction.replace(R.id.kiosk_frame, order);
                transaction.commit();
            }
        });

        return view;
    }

    // 메뉴 당 갯수에 곱하고 곱한 값 끼리 더하기
    public Integer total(){
        sum = 0;
        for(int k = 0; k < mCartDatas.size(); k++){
            int kPrice = mCartDatas.get(k).getCart_price() * mCartDatas.get(k).getCart_count();
            sum += kPrice;
        }

        return sum;
    }

    public void loadRecentDB(){
        mCartDatas = mCartDBHelper.getMenuList();
        if(mCartAdapter == null){
            mCartAdapter = new CartAdapter(mCartDatas, ct);
            mRv_cart.setHasFixedSize(true);
            mRv_cart.setAdapter(mCartAdapter);
        }
    }

    public void showAdd(){
        // 다이얼로그를 생성합니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addcart, null); // 앞서 생성한 레이아웃 파일을 인플레이션하여 뷰로 가져옵니다.

        // 다이얼로그에 필요한 설정을 추가합니다.
        builder.setView(dialogView);

        // 다이얼로그를 띄웁니다.
        AlertDialog dialog = builder.create();
        dialog.show();

        Button main = dialogView.findViewById(R.id.main);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Home home = new Home();
                transaction.replace(R.id.kiosk_frame, home);
                transaction.commit();

                dialog.dismiss();
            }
        });

        Button side = dialogView.findViewById(R.id.side);
        side.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Side side = new Side();
                transaction.replace(R.id.kiosk_frame, side);
                transaction.commit();

                dialog.dismiss();
            }
        });

        Button drink = dialogView.findViewById(R.id.drink);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Drink drink = new Drink();
                transaction.replace(R.id.kiosk_frame, drink);
                transaction.commit();

                dialog.dismiss();
            }
        });

        ImageButton back = dialogView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
