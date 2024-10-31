package com.example.contentprovider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String phoneNumber);
    }

    private List<String> phoneNumbers;
    private OnItemClickListener onClick;

    public PhoneAdapter(List<String> phoneNumbers, OnItemClickListener onClick) {
        this.phoneNumbers = phoneNumbers;
        this.onClick = onClick;

        // Gọi phương thức logPhoneNumbers() để kiểm tra danh sách số điện thoại
        logPhoneNumbers();
    }

    // Phương thức để ghi log danh sách số điện thoại
    private void logPhoneNumbers() {
        for (String phoneNumber : phoneNumbers) {
            Log.d("PhoneNumbers", "Phone number: " + phoneNumber);
        }
    }


    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_button, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        final String phoneNumber = phoneNumbers.get(position);
        holder.button.setText(phoneNumber);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder {
        Button button;

        PhoneViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.phoneButton);
        }
    }
}
