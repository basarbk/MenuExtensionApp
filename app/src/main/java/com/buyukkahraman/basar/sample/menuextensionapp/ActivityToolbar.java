package com.buyukkahraman.basar.sample.menuextensionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.buyukkahraman.basar.lib.view.menuextension.MenuExtension;

public class ActivityToolbar extends AppCompatActivity  implements MenuExtension.MenuExtensionListener {

    MenuExtension menuExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        menuExtension = (MenuExtension) findViewById(R.id.menuExtension);
        menuExtension.setActivity(this);
        menuExtension.setToolbar(toolbar, getResources().getColor(R.color.colorPrimary));

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
                if(menuExtension.getMenuId() == R.menu.extension_menu_first){
                    menuExtension.hide(true);
                } else {
                    menuExtension.setMenu(R.menu.extension_menu_first,getResources().getColor(R.color.extension_menu_first));
                    menuExtension.show();
                }
                break;
            case R.id.menu_second:
                if(menuExtension.getMenuId() == R.menu.extension_menu_second){
                    menuExtension.hide(true);
                } else {
                    menuExtension.setMenu(R.menu.extension_menu_second,getResources().getColor(R.color.extension_menu_second));
                    menuExtension.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuExtensionItemSelected(MenuItem menuItem) {

        if(menuItem.getItemId()==R.id.extension_menu_second_submenu){
            menuExtension.setMenu(R.menu.extension_menu_third,getResources().getColor(R.color.extension_menu_third));
            menuExtension.show();
        } else {
            Toast.makeText(this,"Clicked extension menu: "+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
        }

    }
}
