package redesi.ifb.com.pop3client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    EditText usuario_campo;
    EditText senha_campo;
    EditText host_campo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario_campo = (EditText) findViewById(R.id.usuario);
        senha_campo = (EditText) findViewById(R.id.senha);
        host_campo = (EditText) findViewById(R.id.host);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String erro = "";
                try {
                    POP3Client pop3 = new POP3Client(usuario_campo.getText().toString(),senha_campo.getText().toString(), host_campo.getText().toString());

                    if(pop3.login()){
                        Intent it = new Intent(MainActivity.this, Email_list.class);
                        it.putExtra("usuario", usuario_campo.getText().toString());
                        it.putExtra("email", usuario_campo.getText().toString());
                        startActivity(it);
                    }
                }catch (IOException e){
                    erro = e.getMessage();
                }

                Snackbar.make(view, "Replace with your own action " + erro, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
