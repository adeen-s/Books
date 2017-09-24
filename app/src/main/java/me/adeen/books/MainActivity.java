package me.adeen.books;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import flipagram.assetcopylib.AssetCopier;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> myList;
    int sem;
    String books[] = new String[10];
    TextView textView;
    TextView displayText;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    int id;
    String course;
    BookResolver bookResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        displayText = (TextView) findViewById(R.id.displayText);
        myList = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayText.setVisibility(View.INVISIBLE);
        // Handle navigation view item clicks here.
        id = item.getItemId();

        if (id == R.id.nav_MCA) {
            sem = 12;
            course = "MCA";
        } else if (id == R.id.nav_BCom) {
            textView.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_MBA) {
            textView.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_MTech) {
            textView.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_share) {
            textView.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_send) {
            textView.setVisibility(View.VISIBLE);
        }

        for(int i = 1; i <= sem; i++) {
            myList.add("Semester " + i);
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                builderSingle.setTitle("Select One Subject:-");


                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                bookResolver = new BookResolver();
                ArrayList<String> bookList = bookResolver.getBookList(course, (i+1));
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, bookList);

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if(bookResolver.openBook(course, (i+1), strName, MainActivity.this)) {

                        } else {
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                            builderInner.setMessage(strName + " is not available yet");
                            builderInner.setTitle("Your Selected Item is");
                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builderInner.show();
                        }
                    }
                });
                builderSingle.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
