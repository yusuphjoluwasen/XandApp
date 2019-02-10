package guru.example.com.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import guru.example.com.myapplication.model.DefaultResponse;
import guru.example.com.myapplication.R;
import guru.example.com.myapplication.api.RetrofitClient;
import guru.example.com.myapplication.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextpassword, editTextName, editTextSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.emalogin);
        editTextpassword = findViewById(R.id.passlogin);
        editTextName = findViewById(R.id.name);
        editTextSchool= findViewById(R.id.school);

        findViewById(R.id.sign).setOnClickListener(this);
        findViewById(R.id.reg).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp(){
        String email = editTextEmail.getText().toString();
        String password = editTextpassword.getText().toString();
        final String name = editTextName.getText().toString();
        String school = editTextSchool.getText().toString();


        //validation
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("Password is required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Password should be at least 6 characters long");
            editTextpassword.requestFocus();
            return;
        }
        if(name.isEmpty()){
            editTextName.setError("Email is required");
            editTextName.requestFocus();
            return;
        }
        if(school.isEmpty()){
            editTextSchool.setError("Email is required");
            editTextSchool.requestFocus();
            return;
        }

        //Do user registration
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email,password,name,school);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                if(response.code() == 201) {
                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_SHORT).show();


                }else if(response.code() == 422){
                    Toast.makeText(MainActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();

                   }





            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign:
                userSignUp();
                break;
            case R.id.reg:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
