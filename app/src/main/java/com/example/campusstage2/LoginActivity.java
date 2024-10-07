package com.example.campusstage2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.campusstage2.model.Users;

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.loginButton);

        TextView registerlink = findViewById(R.id.register_link);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_password_input), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Users users = new Users(getBaseContext());
        SQLiteDatabase db = users.dbHelper.getReadableDatabase();
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String inputUsername = usernameInput.getText().toString();
                String inputPassword = passwordInput.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                        new String[]{inputUsername, HashUtil.hashPassword(inputPassword)});
                if (cursor.moveToFirst()) {

                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                    Auth auth = new Auth(getBaseContext());
                    auth.saveUsers(id, name, phone, email, username);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), "Invalid username/password", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        });

        registerlink.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        });
    }

}