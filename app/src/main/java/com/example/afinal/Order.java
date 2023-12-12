package com.example.afinal;
//tts로 정보를 어떻게 전달할지 생각해봐야하 할듯

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Order extends Fragment {
    // 장바구니처럼 어댑터, 데이터 클래스 만들어서 리사이클러뷰 동작

    private ArrayList<OrderData> mOrderDatas;
    private OrderAdapter mOrderAdapter;
    private RecyclerView mRv_order;
    private LinearLayoutManager linearLayoutManager;
    private OrderDBHelper mOrderDBHelper;

    private TextView order_total;
    private String name;
    private int sum;
    private int count, price = 0;

    Context ct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order, container, false);

        mRv_order = view.findViewById(R.id.order_rv);
        ct = view.getContext();
        linearLayoutManager = new LinearLayoutManager(ct);
        mRv_order.setLayoutManager(linearLayoutManager);

        mOrderDatas = new ArrayList<>();
        mOrderDBHelper = new OrderDBHelper(ct);

        loadRecentDB();

        order_total = view.findViewById(R.id.order_total);
        sum = total();
        order_total.setText(String.valueOf(sum)+"원");



        if(getArguments() != null){
            name = getArguments().getString("name");
            price = getArguments().getInt("price");
            count = getArguments().getInt("count");

            mOrderDBHelper.InsertOrder(name, count, price);

            OrderData orderData = new OrderData();
            orderData.setOrder_name(name);
            orderData.setOrder_count(count);
            orderData.setOrder_price(price);

            mOrderAdapter.addItem(orderData);
            mRv_order.smoothScrollToPosition(0);
            Toast.makeText(ct, "추가되었습니다.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void loadRecentDB(){
        mOrderDatas = mOrderDBHelper.getMenuList();
        for(int i = 0; i < mOrderDatas.size(); i++){
            System.out.println(mOrderDatas.get(i).getOrder_name());
        }
        if(mOrderAdapter == null){
            mOrderAdapter = new OrderAdapter(mOrderDatas, ct);
            mRv_order.setHasFixedSize(true);
            mRv_order.setAdapter(mOrderAdapter);
        }
    }

    private Integer total(){
        sum = 0;
        for(int k = 0; k < mOrderDatas.size(); k++){
            int kPrice = mOrderDatas.get(k).getOrder_price() * mOrderDatas.get(k).getOrder_count();
            sum += kPrice;
        }

        return sum;
    }

}
