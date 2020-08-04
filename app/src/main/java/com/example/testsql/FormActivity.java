package com.example.testsql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView myDate;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private String date =null;
    private String status = null;
    private String IDno;
    Button update;
    EditText sno,note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        getSupportActionBar().hide();

        IDno = getIntent().getExtras().getString("IDno");
        myDate = (TextView) findViewById(R.id.tvDate);

        myDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FormActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListner,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = year+"-"+month+"-"+day;
                myDate.setText(date);
            }
        };

        Spinner spinner =findViewById(R.id.spinner);
        update = findViewById(R.id.upd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.status,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Click");
                setUpdate();

            }
        });
    }
    public void setUpdate()
    {
        sno =(EditText) findViewById(R.id.sno);
        note =(EditText)findViewById(R.id.note);

        String shipNo = sno.getText().toString();
        String Note = note.getText().toString();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if(shipNo.equals("") && Note.equals("") && date==null && status==null)
            {

            }else
                {
                    Connection con = DBConnection.getConnection();
                    Statement st = con.createStatement();
                    String sql ="UPDATE Track SET deldate='"+date+"',status='"+status+"',note='"+Note+"',delby='"+IDno+"' WHERE tid='"+shipNo+"'";

                    boolean result = st.executeUpdate(sql)> 0;

                    if(result)
                    {
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                        sno.setText("");
                        note.setText("");
                        myDate.setText("");
                    }else
                        {
                            Toast.makeText(getApplicationContext(),"Error,Cannot Update",Toast.LENGTH_SHORT).show();
                        }
                    System.out.println(result);

                }
        }catch (SQLNonTransientConnectionException e) {
            Toast.makeText(getApplicationContext(),"Connection time out",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          status =adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(),status,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
