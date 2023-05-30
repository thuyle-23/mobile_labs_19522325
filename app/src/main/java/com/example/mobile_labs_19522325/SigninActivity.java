package com.example.mobile_labs_19522325;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private TextInputEditText txtEditUsername, txtEditPassword;
    private TextView txtSignUp;
    private MaterialButton btnLogin;
    private RelativeLayout relativeLayout;

    private TextInputLayout inputLayoutPassword, inputLayoutUsername;

    DocumentReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signin);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ref = db.collection("users").document();

        txtEditUsername = findViewById(R.id.txtEditUsername);
        txtEditPassword = findViewById(R.id.txtEditPassword);
        txtSignUp = findViewById(R.id.txtSignUp);

        btnLogin = findViewById(R.id.btnLogin);

        txtEditUsername.addTextChangedListener(new SigninActivity.MyTextWatcher(txtEditUsername));
        txtEditPassword.addTextChangedListener(new SigninActivity.MyTextWatcher(txtEditPassword));

        inputLayoutUsername = (TextInputLayout) findViewById(R.id.inputLayoutUsername);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);

        relativeLayout = findViewById(R.id.relativeLayout);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = txtEditUsername.getText().toString();
                final String password = txtEditPassword.getText().toString();

                CollectionReference usersRef = db.collection("users");

                if (true) {
                    submitForm();
                } else {
                    usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    String username = documentSnapshot.getString("username");
                                    String password = documentSnapshot.getString("password");
                                    String username1 = txtEditUsername.getText().toString().trim();
                                    String password1 = txtEditPassword.getText().toString().trim();

                                    if (username.equalsIgnoreCase(username1) & password.equalsIgnoreCase(password1)) {
                                        Intent home = new Intent(SigninActivity.this, HomePageActivity.class);
                                        startActivity(home);
                                        Toast.makeText(SigninActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
//                                        Toast.makeText(SigninActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                        int gravity = Gravity.CENTER;

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View popupView = inflater.inflate(R.layout.layout_error_message, null);
                                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                        boolean focusable = true;
                                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                                        popupView.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View v, MotionEvent event) {
                                                popupWindow.dismiss();
                                                return true;
                                            }
                                        });

                                        popupWindow.showAtLocation(relativeLayout, gravity, 0, 0);
                                    }
                                }

                            }
                        }
                    });

                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_view = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(register_view);
            }
        });

    }

    private void submitForm() {
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateUsername() {
        String username = txtEditUsername.getText().toString();
        if ((Pattern.matches("[a-zA-Z+]", username)) ||
                username.trim().isEmpty() ||
                !(username.length() > 5)) {
            inputLayoutUsername.setError(getString(R.string.err_msg_username));
            requestFocus(txtEditUsername);
            return false;
        } else {
            inputLayoutUsername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String password = txtEditPassword.getText().toString();
        if (password.trim().isEmpty() ||
                !(password.length() > 5)) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(txtEditPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.txtEditUsername:
                    validateUsername();
                    break;
                case R.id.txtEditPassword:
                    validatePassword();
                    break;
            }
        }

    }
}