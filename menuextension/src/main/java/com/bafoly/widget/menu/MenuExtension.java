package com.bafoly.widget.menu;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
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

    int previousColor;

    int menuId;

    static final int ANIM_DURATION = 300;
    static final int ANIM_HIDE = 200;

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
        this.backgroundColor = toolbarDefaultColor;
    }

    public void menuShowHideToggle(int menuId, int backgroundColor){
        if(this.menuId == menuId){
            dismiss();
        } else {
            PopupMenu popupMenu = new PopupMenu(context, null);
            Menu menu = popupMenu.getMenu();
            activity.getMenuInflater().inflate(menuId, menu);
            this.menu = menu;

            if(this.menuId!=-1){
                this.previousColor = this.backgroundColor;
            }
            this.backgroundColor = backgroundColor;
            this.menuId = menuId;

            if(menu!=null && menu.size()>0){
                this.elementCount = menu.size();
                show();
            }
        }
    }

    private void show(){
        if(getVisibility()==View.VISIBLE){
            setVisibility(View.GONE);
            getLayoutParams().height = minHeight;
            removeAllViewsInLayout();
            setTranslationY(0);
        }

        setBackgroundColor(toolbarDefaultColor);

        if(toolbar!=null){
            toolbar.setBackgroundColor(backgroundColor);
        }

        buttonPerRow = menu.size();
        buttonWidth = screenWidth/buttonPerRow;

        rowCount = 1;

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
                    dismiss();
                    listener.onMenuExtensionItemSelected(menu.getItem(idx));
                }
            });
        }

        setVisibility(View.VISIBLE);

        animateColorWithPosition(previousColor, backgroundColor);
    }

    public void dismiss(){
        menuId = -1;

        animateColor(toolbar, backgroundColor, toolbarDefaultColor);
        animateColor(this, backgroundColor, toolbarDefaultColor);

        animate().translationY(-getHeight()).setDuration(ANIM_HIDE).withEndAction(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
                getLayoutParams().height = minHeight;
                removeAllViewsInLayout();
                setTranslationY(0);
            }
        });

    }

    private void animateColorWithPosition(int fromColor, int toColor){

        animateColor(toolbar, fromColor, toColor);
        animateColor(this, fromColor, toColor);
        setTranslationY(-getLayoutParams().height);
        animate().translationY(0).setDuration(ANIM_DURATION).withEndAction(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    private void animateColor(View view, int fromColor, int toColor){
        ObjectAnimator anim = ObjectAnimator.ofObject(view,
                "backgroundColor",
                new ArgbEvaluator(),
                fromColor, toColor);

        anim.setDuration(ANIM_DURATION);
        anim.start();
    }
}
