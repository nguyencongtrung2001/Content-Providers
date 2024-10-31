package com.example.contentprovider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhoneAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PhoneAdapter adapter;
    private List<String> phoneNumbers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        phoneNumbers = fetchPhoneNumbersFromSMS();
        adapter = new PhoneAdapter(phoneNumbers, this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 0, 0, 0); // Loại bỏ mọi khoảng cách giữa các item
            }
        });
    }

    private List<String> fetchPhoneNumbersFromSMS() {
        List<String> phoneNumbers = new ArrayList<>();
        Uri uri = Telephony.Sms.CONTENT_URI;
        String[] projection = new String[]{"address"};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                if (!phoneNumbers.contains(phoneNumber)) {
                    phoneNumbers.add(phoneNumber);
                }
            }
            cursor.close();
        }
        return phoneNumbers;
    }

    @Override
    public void onItemClick(String phoneNumber) {
        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }

}
