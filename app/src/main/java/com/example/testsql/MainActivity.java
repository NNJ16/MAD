package com.example.testsql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText pwd,id;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        id= (EditText) findViewById(R.id.id);
        pwd= (EditText) findViewById(R.id.pwd);
        log = (Button) findViewById(R.id.log);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verify();
            }
        });
    }
    public void verify()
    {
        TextView tv = this.findViewById(R.id.textView);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Connection con = DBConnection.getConnection();
            String result ="Connection Success";
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery("Select * from DEmployee");
            String idc,pwdc,idm,pwdm;
            int x=0;
            while (rst.next())
            {
                idc = rst.getString(1);
                pwdc = rst.getString(4);
                idm = id.getText().toString();
                pwdm = pwd.getText().toString();

                if(idm.equals(idc) && pwdm.equals(pwdc))
                {
                    x++;
                    result = "User Found";
                    Intent intent = new Intent(MainActivity.this,FormActivity.class);
                    intent.putExtra("IDno",idm);
                    startActivity(intent);
                    finish();
                }else{
                    result = "User not Found";

                }

            }
            if(x==0)
            {
                Toast.makeText(getApplicationContext(),"Error, User not Found",Toast.LENGTH_SHORT).show();
            }
            tv.setText(result);

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No internet, Check your internet connection",Toast.LENGTH_SHORT).show();
        }
    }


}
