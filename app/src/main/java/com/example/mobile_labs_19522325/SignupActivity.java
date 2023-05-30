package com.example.mobile_labs_19522325;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText txtEditFullName, txtEditPhoneNumber, txtEditUsername, txtEditPassword;
    private MaterialButton btnSignUp;
    private TextInputLayout inputLayoutFullName, inputLayoutPassword, inputLayoutUsername, inputLayoutPhoneNumber;
    private TextView txtLogin;
    private RelativeLayout relativeLayout;
    DocumentReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ref = db.collection("users").document();

        txtEditFullName = findViewById(R.id.txtEditFullname);
        txtEditPhoneNumber = findViewById(R.id.txtEditPhone);
        txtEditUsername = findViewById(R.id.txtEditUsername);
        txtEditPassword = findViewById(R.id.txtEditPassword);
        txtLogin = findViewById(R.id.txtLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        relativeLayout = findViewById(R.id.relativeLayout);

        txtEditFullName.addTextChangedListener(new MyTextWatcher(txtEditFullName));
        txtEditPhoneNumber.addTextChangedListener(new MyTextWatcher(txtEditPhoneNumber));
        txtEditUsername.addTextChangedListener(new MyTextWatcher(txtEditUsername));
        txtEditPassword.addTextChangedListener(new MyTextWatcher(txtEditPassword));

        inputLayoutFullName = (TextInputLayout) findViewById(R.id.inputLayoutFullName);
        inputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.inputLayoutPhoneNumber);
        inputLayoutUsername = (TextInputLayout) findViewById(R.id.inputLayoutUsername);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = txtEditUsername.getText().toString();
                final String phone = txtEditPassword.getText().toString();
                final String username = txtEditUsername.getText().toString();
                final String password = txtEditPassword.getText().toString();
                CollectionReference usersRef = db.collection("users");
                Query query = usersRef.whereEqualTo("username", username);
                if (true) {
                    submitForm();
                }else{
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    String user = documentSnapshot.getString("username");

                                    if (user.equals(username)) {
                                        Log.d(TAG, "User Exists");
                                        Toast.makeText(SignupActivity.this, "Sorry, this user is already existed.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else if (task.getResult().size() == 0) {
//                            Toast.makeText(SignupActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                                addDataToFirestore(fullName, phone, username, password);
//                            Map<String, Object> reg_entry = new HashMap<>();
//                            reg_entry.put("fullName", fullName);
//                            reg_entry.put("password", encrypt(password));
//                            reg_entry.put("phone", phone);
//                            reg_entry.put("username", username);
//
//                            db.collection("users")
//                                    .add(reg_entry)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//                                            popUpMessage();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("Error", e.getMessage());
//                                        }
//                                    });
                            }
                        }

                    });
                }
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_view = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(register_view);
            }
        });

    }

    private void addDataToFirestore(String fullName, String phoneNumber, String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference dbUsers = db.collection("users");

        Users users = new Users(fullName, phoneNumber, username, password);

        dbUsers.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                popUpMessage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String encrypt(String value) {
        String key = "aesEncryptionKey";
        String initVector = "encryptionIntVec";
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void popUpMessage() {
        int gravity = Gravity.CENTER;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_success_message, null);
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

    private void submitForm() {
        if (!validateFullName()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }

        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
    }

    private boolean validateFullName() {
        String fullname = txtEditFullName.getText().toString();
        if (fullname.trim().isEmpty()) {
            inputLayoutFullName.setError(getString(R.string.err_msg_fullname));
            requestFocus(txtEditFullName);
            return false;
        } else {
            inputLayoutFullName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        String phone = txtEditPhoneNumber.getText().toString();

        if (phone.trim().isEmpty() ||
                !(phone.length() == 10) || !(phone.startsWith("0"))) {
            inputLayoutPhoneNumber.setError(getString(R.string.err_msg_phone));
            requestFocus(txtEditPhoneNumber);
            return false;
        } else {
            inputLayoutPhoneNumber.setErrorEnabled(false);
        }

        return true;
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
                case R.id.txtEditFullname:
                    validateFullName();
                    break;
                case R.id.txtEditPhone:
                    validatePhone();
                    break;
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
