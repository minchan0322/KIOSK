package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CustomViewHolder> {

    private ArrayList<CartData> mCartDatas;
    private Context mContext;
    private CartDBHelper mCartDBHelper;

    private Button up, down, remove;
/////////////////////////////////////////////////////////////////////////////////////////////////////////



    public CartAdapter(ArrayList<CartData> mCartDatas, Context mContext) {
        this.mCartDatas = mCartDatas;
        this.mContext = mContext;
        mCartDBHelper = new CartDBHelper(mContext);
    }


    @NonNull
    @Override
    public CartAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        up = view.findViewById(R.id.upCount);
        down = view.findViewById(R.id.downCount);
        remove = view.findViewById(R.id.cart_remove);

        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CustomViewHolder holder, int position) {
        holder.cart_name.setText(mCartDatas.get(position).getCart_name());
        holder.cart_count.setText(String.valueOf(mCartDatas.get(position).getCart_count()));
        holder.cart_price.setText(String.valueOf(mCartDatas.get(position).getCart_price()));


        int curPos = position;      // 현재 클릭한 리스트 아이템 위치
        // 유효한 위치에서만 작업 수행
        CartData cartData = mCartDatas.get(curPos);
        int beforeid = cartData.getId();

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 리사이클러 클릭 시 해당 메뉴 이름 토스트메뉴로 출력. 다른 기능
                String curName = holder.cart_name.getText().toString();
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        if (curPos > 0 && curPos < mCartDatas.size()) {
            if (mCartDatas != null & mCartDatas.size() != 0){

                // 나머지 로직 계속 수행
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 삭제하기
                        mCartDBHelper.DeleteMenu(beforeid);

                        // UI 수정(삭제)
                        mCartDatas.remove(curPos);
                        notifyItemRemoved(curPos);
                        Toast.makeText(mContext, "제거 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            // 유효하지 않은 위치 처리
        }
        if (up != null){
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 현재 cart_count의 텍스트를 가져와서 정수로 변환
                    int currentCount = Integer.parseInt(holder.cart_count.getText().toString());
                    // 값을 1 증가시키고 다시 문자열로 변환하여 TextView에 설정
                    holder.cart_count.setText(String.valueOf(currentCount + 1));
                    mCartDBHelper.UpdateCount(currentCount+1, beforeid);

                    // 장바구니에서 갯수 변화에 따른 가격 변화시키기
                    int pPrice = Integer.parseInt(holder.cart_price.getText().toString());
                    holder.cart_price.setText(String.valueOf(pPrice));
                    mCartDBHelper.UpdatePrice(pPrice * currentCount, beforeid);
                }
            });
        }

        if (down != null){
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 현재 cart_count의 텍스트를 가져와서 정수로 변환
                    int currentCount = Integer.parseInt(holder.cart_count.getText().toString());
                    if (currentCount > 1){
                        // 값을 1 감소시키고 다시 문자열로 변환하여 TextView에 설정
                        holder.cart_count.setText(String.valueOf(currentCount - 1));
                        mCartDBHelper.UpdateCount(currentCount-1, beforeid);

                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != mCartDatas ? mCartDatas.size() : 0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView cart_image;
        protected TextView cart_name;
        protected TextView cart_option;
        protected TextView cart_count;
        protected TextView cart_price;
        protected Button cart_remove, up, down;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cart_name = itemView.findViewById(R.id.cart_name);
            this.cart_count = itemView.findViewById(R.id.cart_count);
            this.cart_price = itemView.findViewById(R.id.cart_price);
            this.cart_remove = itemView.findViewById(R.id.cart_remove);
            this.up = itemView.findViewById(R.id.up);
            this.down = itemView.findViewById(R.id.down);


        }
    }

    // 액티비티에서 호출되는 함수. 현재 어댑터에 새로운 아이템을 전달받아 추가하는 목적
    // 항상 최신의 데이터가 가장 위에 출력. index = 0
    public void addItem(CartData _item){
        mCartDatas.add(0, _item);
    }




}