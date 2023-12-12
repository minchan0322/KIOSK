package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CustomViewHolder> {

    private ArrayList<OrderData> mOrderDatas;
    private Context mContext;
    private OrderDBHelper mOrderDBHelper;

    public OrderAdapter(ArrayList<OrderData> mOrderDatas, Context mContext) {
        this.mOrderDatas = mOrderDatas;
        this.mContext = mContext;
        mOrderDBHelper = new OrderDBHelper(mContext);
    }

    @NonNull
    @Override
    public OrderAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.CustomViewHolder holder, int position) {
        holder.order_name.setText(mOrderDatas.get(position).getOrder_name());
        holder.order_count.setText(String.valueOf(mOrderDatas.get(position).getOrder_count()+"개"));
        holder.order_price.setText(String.valueOf(mOrderDatas.get(position).getOrder_price()+"원"));

        int curPos = position;      // 현재 클릭한 리스트 아이템 위치
        // 유효한 위치에서만 작업 수행
        OrderData orderData = mOrderDatas.get(curPos);
        int beforeid = orderData.getId();

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 리사이클러 클릭 시 해당 메뉴 이름 토스트메뉴로 출력. 다른 기능
//                String curName = holder.order_name.getText().toString();
//                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();

                // 클릭 시 해당 메뉴 삭제로 임시 변경
                if (curPos >= 0 && curPos < mOrderDatas.size()) {
                    if (mOrderDatas != null & mOrderDatas.size() != 0){
                            mOrderDBHelper.DeleteMenu(beforeid);

                            mOrderDatas.remove(curPos);
                            notifyItemRemoved(curPos);
                            Toast.makeText(mContext, "제거 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mOrderDatas ? mOrderDatas.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView order_name;
        protected TextView order_count;
        protected TextView order_price;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.order_name = itemView.findViewById(R.id.order_name);
            this.order_count = itemView.findViewById(R.id.order_count);
            this.order_price = itemView.findViewById(R.id.order_price);
        }
    }

    public void addItem(OrderData _item){
        mOrderDatas.add(0, _item);
    }
}
