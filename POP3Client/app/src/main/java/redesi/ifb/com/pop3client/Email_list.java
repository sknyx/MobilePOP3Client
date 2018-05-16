package redesi.ifb.com.pop3client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import redesi.ifb.com.pop3client.R;

public class Email_list extends AppCompatActivity {

    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);

        Map<Integer, String> emails = new HashMap<Integer,String>();
//        emails = pop3.email

    }
}
