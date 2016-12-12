package com.example.takuma.a3a;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final static String FILE_NAME = "text.txt";

    Button btnInput, btnOutput;
    EditText etInput;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInput = (Button)findViewById(R.id.btnInput);
        btnOutput = (Button)findViewById(R.id.btnOutput);
        etInput = (EditText)findViewById(R.id.txtInput);
        tvOutput = (TextView)findViewById(R.id.txtOutput);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveText(etInput.getText().toString());
            }
        });
        btnOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadText();
            }
        });
    }

    private void saveText(String text){
        try{
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            String stringOutput = new String(text + "\n");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeBytes(stringOutput);
            fos.close();
        }catch(FileNotFoundException FNFE){
            FNFE.printStackTrace();
        }catch(IOException IOE){
            IOE.printStackTrace();
        }
    }

    private void loadText() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            DataInputStream dis = new DataInputStream(fis);
            byte[] readed = new byte[1024];
            dis.read(readed);
            tvOutput.setText(new String(readed));
            fis.close();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }
}
