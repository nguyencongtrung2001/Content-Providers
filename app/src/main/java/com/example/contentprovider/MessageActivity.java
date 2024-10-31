package com.example.contentprovider;

import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Lấy số điện thoại từ Intent
        phoneNumber = getIntent().getStringExtra("phone_number");

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        // Nếu có số điện thoại, lấy tin nhắn tương ứng
        if (phoneNumber != null) {
            List<String> messages = fetchMessages(phoneNumber);
            adapter = new MessageAdapter(messages);
            recyclerView.setAdapter(adapter);
        }

        // Button quay lại để trở về MainActivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 0, 0, 0); // Loại bỏ mọi khoảng cách giữa các item
            }
        });
    }

    // Hàm lấy tin nhắn từ số điện thoại cụ thể từ ContentProvider
    private List<String> fetchMessages(String phoneNumber) {
        List<String> messages = new ArrayList<>();
        Uri uri = Telephony.Sms.CONTENT_URI; // Sử dụng URI chính thức của SMS
        String[] projection = new String[]{"body"};
        String selection = "address = ?";
        String[] selectionArgs = new String[]{phoneNumber};

        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                messages.add(body);
            }
            cursor.close();
        }
        return messages;
    }
}
