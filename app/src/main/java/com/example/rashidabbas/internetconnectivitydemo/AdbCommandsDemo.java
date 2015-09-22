package com.example.rashidabbas.internetconnectivitydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbCommandsDemo extends AppCompatActivity
{

    ProgressDialog progressdailog;
    Activity context;
    Button btnExecute;
    TextView txtResult;
    String command;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adb_commands_demo);
        Init();
        btnExecute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                command = input.getText().toString();
                new ExecuteCommand().execute(command);
                // new getOutput().execute(command);
            }
        });
    }


    //****************** Init *************************
    private void Init()
    {
        context = AdbCommandsDemo.this;
        input = (EditText) findViewById(R.id.txt);
        btnExecute = (Button) findViewById(R.id.btn);
        txtResult = (TextView) findViewById(R.id.result);
    }

    class ExecuteCommand extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressdailog = ProgressDialog.show(context, "Executing", "Please Wait");
        }

        @Override
        protected String doInBackground(String... params)
        {
            Process p;
            StringBuffer output = new StringBuffer();
            try
            {
                p = Runtime.getRuntime().exec(params[0]);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    output.append(line + "\n");
                    p.waitFor();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(context, "Exp\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                Toast.makeText(context, "Exp\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            String response = output.toString();
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressdailog.dismiss();
            txtResult.setText(result); //
            Log.d("Output", result);
        }
    }
}
