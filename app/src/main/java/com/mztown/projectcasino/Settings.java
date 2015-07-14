package com.mztown.projectcasino;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
//        final User user = (User)getApplication();
        Button changeusername=(Button)findViewById(R.id.changeUserName);
        Button reset=(Button)findViewById(R.id.reset);
        Button newuser=(Button)findViewById(R.id.newUser);
        Button delete=(Button)findViewById(R.id.deleteUser);
        String pendingDeletion;
        final TextView newusername=(TextView)findViewById(R.id.newUserName);

        final DBOperation dbo=new DBOperation(this);
        newusername.setText(dbo.GetOnlineUser());

        UpdateSpinner(dbo);

        changeusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametwo = newusername.getText().toString();
                dbo.UserRename(nametwo);
                Intent intent = new Intent(Settings.this, mainhub.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbo.Reset();
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbo.NewUser();
                Intent intent = new Intent(Settings.this,mainhub.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner s=(Spinner)findViewById(R.id.userlist);
                TextView tv=(TextView)s.getSelectedView();
                dbo.DeleteUser(tv.getText().toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
    public void UpdateSpinner(DBOperation dbo){
        Spinner s=(Spinner)findViewById(R.id.userlist);
        final TextView text=(TextView)findViewById(R.id.textView);
        final Cursor cursor=dbo.GetUserList();
        if(cursor!=null) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, new String[]{"_id"}, new int[]{android.R.id.text1});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
        }
    }

}
