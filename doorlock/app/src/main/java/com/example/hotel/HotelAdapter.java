package com.example.hotel;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.CustomViewHolder> {

    private ArrayList<HotelData> arrayList;
    static public String curName;
    static public String curAddress;
    static public String curNumber;
    static public String curCapacity;
    static public String curCheckIn;
    static public String curCheckOut;
    static public String curPrice;
    static public String curUserID;
    static public String curPwd;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private int num=0;

    public HotelAdapter(ArrayList<HotelData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HotelAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.CustomViewHolder holder, int position) {
        holder.hotelName.setText("호텔이름: "+arrayList.get(position).getHotelName());
        holder.hotelAddress.setText("호텔주소: "+arrayList.get(position).getHotelAddress());
        holder.hotelNumber.setText("호텔번호: "+arrayList.get(position).getHotelNumber());
        holder.hotelCapacity.setText("인원: "+arrayList.get(position).getHotelCapacity());
        holder.hotelCheckIn.setText("체크인: "+arrayList.get(position).getHotelCheckIn());
        holder.hotelCheckOut.setText("체크아웃: "+arrayList.get(position).getHotelCheckOut());
        holder.hotelPrice.setText("가격: "+arrayList.get(position).getHotelPrice());
        holder.hotelUserID.setText("작성자: "+arrayList.get(position).getHotelUserID());



        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curName=arrayList.get(position).getHotelName();
                curAddress=arrayList.get(position).getHotelAddress();
                curNumber=arrayList.get(position).getHotelNumber();
                curCapacity=arrayList.get(position).getHotelCapacity();
                curCheckIn=arrayList.get(position).getHotelCheckIn();
                curCheckOut=arrayList.get(position).getHotelCheckOut();
                curPrice=arrayList.get(position).getHotelPrice();
                curUserID=arrayList.get(position).getHotelUserID();
                curPwd=arrayList.get(position).getHotelPwd();


                if ( mSelectedItems.get(position, false) ){
                    mSelectedItems.put(position, false);
                    v.setBackgroundColor(Color.WHITE);
                    num--;
                }
                else {
                    if(num==0) {
                        mSelectedItems.put(position, true);
                        v.setBackgroundColor(Color.LTGRAY);
                        num++;
                    }
                    else
                    {
                        Toast.makeText(v.getContext(),"하나만 선택할 수 있습니다",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);

        }catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView hotelName;
        protected TextView hotelAddress;
        protected TextView hotelNumber;
        protected TextView hotelCapacity;
        protected TextView hotelCheckIn;
        protected TextView hotelCheckOut;
        protected TextView hotelPrice;
        protected TextView hotelUserID;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hotelName =itemView.findViewById(R.id.hotelName);
            this.hotelAddress =itemView.findViewById(R.id.hotelAddress);
            this.hotelNumber =itemView.findViewById(R.id.hotelNumber);
            this.hotelCapacity =itemView.findViewById(R.id.hotelCapacity);
            this.hotelCheckIn =itemView.findViewById(R.id.hotelCheckIn);
            this.hotelCheckOut =itemView.findViewById(R.id.hotelCheckOut);
            this.hotelPrice = itemView.findViewById(R.id.hotelPrice);
            this.hotelUserID =itemView.findViewById(R.id.hotelUserID);
        }
    }
}
