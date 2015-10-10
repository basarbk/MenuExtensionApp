package com.buyukkahraman.basar.lib.view.menuextension;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

public class MenuExtension extends LinearLayout {

    Context context;

    int screenWidth = -1;

    int screenHeight = -1;

    float density = 1;

    int minHeight = -1;

    LayoutInflater layoutInflater;

    Activity activity;

    MenuExtensionListener listener;

    Toolbar toolbar;

    int toolbarDefaultColor;

    int elementCount = 0;

    private static final int MINIMUM_BUTTON_WIDTH = 75;

    private int rowCount;

    private int buttonPerRow;

    private int buttonWidth;

    private int currentRowIdx;

    private LinearLayout[] rows;

    Menu menu;

    int backgroundColor;

    int menuId;

    public interface MenuExtensionListener{

        public void onMenuExtensionItemSelected(MenuItem menuItem);

    }


    public MenuExtension(Context context) {
        super(context);
        init(context);
    }

    public MenuExtension(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuExtension(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        setVisibility(View.GONE);
        TypedValue tv = new TypedValue();
        if(context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            minHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        density = metrics.density;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        try {
            this.listener = (MenuExtensionListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MenuExtensionListener");
        }
        this.layoutInflater = activity.getLayoutInflater();
    }

    public void setToolbar(Toolbar toolbar, int toolbarDefaultColor){
        this.toolbarDefaultColor = toolbarDefaultColor;
        this.toolbar = toolbar;
    }

    public void setMenu(int menuId, int backgroundColor){
        this.menuId = menuId;
        PopupMenu popupMenu = new PopupMenu(context, null);
        Menu menu = popupMenu.getMenu();
        activity.getMenuInflater().inflate(menuId, menu);
        this.menu = menu;
        this.backgroundColor = backgroundColor;
        if(menu!=null && menu.size()>0){
            this.elementCount = menu.size();
        }
    }

    public int getMenuId(){
        return menuId;
    }

    public void setMenuId(int menuId){
        this.menuId = menuId;
    }

    @SuppressLint("InflateParams")
    public void show(){
        // incase menu is displayed previously, clear all content and re-generate it
        hide(false);

        if(elementCount>0){
            setBackgroundColor(backgroundColor);

            if(toolbar!=null){
                toolbar.setBackgroundColor(backgroundColor);
            }

            buttonPerRow = menu.size();
            buttonWidth = screenWidth/buttonPerRow;

            rowCount = 1; // there must be at least one row.

            if(buttonWidth < (MINIMUM_BUTTON_WIDTH*density)){
                buttonWidth = (int)(MINIMUM_BUTTON_WIDTH*density);
                buttonPerRow = screenWidth / buttonWidth;
                rowCount = (int)Math.ceil((elementCount)/(float)buttonPerRow);
            }

            rows = new LinearLayout[rowCount];
            for(int i=0;i<rowCount;i++){
                rows[i] = new LinearLayout(getContext());
                rows[i].setLayoutParams(getLayoutParams());
                rows[i].setOrientation(LinearLayout.HORIZONTAL);
                rows[i].setGravity(Gravity.CENTER);
                addView(rows[i]);
            }

            getLayoutParams().height = rowCount*minHeight;

            currentRowIdx = 0;

            for (int i = 0; i<elementCount ; i++){
                currentRowIdx = i/buttonPerRow;
                final Button btn = new Button(context);
                btn.setGravity(Gravity.CENTER_VERTICAL);
                btn.setBackgroundResource(0);

                final int idx = i;

                btn.setWidth(buttonWidth);
                btn.setHeight(minHeight);
                btn.setText(menu.getItem(i).getTitle());
                btn.setGravity(Gravity.CENTER);
                btn.setTextSize(14);

                btn.setTextColor(Color.WHITE);

                rows[currentRowIdx].addView(btn);

                btn.setOnClickListener(new View.OnClickListener() {

                    @Override public void onClick(View v) {
                        hide(true);
                        listener.onMenuExtensionItemSelected(menu.getItem(idx));
                    }
                });
            }


            setVisibility(View.VISIBLE);
        }
    }

    public void hide(boolean reset){
        if (reset){
            menuId = -1;
        }
        if(getVisibility()==View.VISIBLE){
            if(toolbar!=null){
                toolbar.setBackgroundColor(toolbarDefaultColor);
            }
            setVisibility(View.GONE);
            getLayoutParams().height = minHeight;
            removeAllViewsInLayout();
        }
    }
}
