package com.bafoly.sample.menuextensionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bafoly.widget.menu.MenuExtension;

public class ActivityToolbar extends AppCompatActivity {

    MenuExtension menuExtension;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        menuExtension = (MenuExtension) findViewById(R.id.menuExtension);
        menuExtension.setToolbar(toolbar, getResources().getColor(R.color.colorPrimary));
        menuExtension.setMenuExtensionListener(new MenuExtension.MenuExtensionListener() {
            @Override
            public void onMenuExtensionItemSelected(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.extension_menu_second_submenu){
                    menuExtension.menuShowHideToggle(R.menu.extension_menu_third,getResources().getColor(R.color.extension_menu_third));
                } else {
                    textView.setText("Clicked : "+menuItem.getTitle());
                }
            }
        });

        textView = (TextView) findViewById(R.id.text);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_first:
                menuExtension.menuShowHideToggle(R.menu.extension_menu_first,getResources().getColor(R.color.extension_menu_first));
                break;
            case R.id.menu_second:
                menuExtension.menuShowHideToggle(R.menu.extension_menu_second,getResources().getColor(R.color.extension_menu_second));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
