# Menu Extension

Menu extension is for creating an expandable menu space for your Android toolbar. 

<img src="http://blog.bafoly.com/wp-content/uploads/2016/06/menuextension.gif"/>

Compatibility
-------------
This library is compatible with API 16 and later versions of Android.

Download
-------------
```gradle
dependencies {
    compile 'com.bafoly.menu:menu-extension:1.0.1'
}
```

Usage
-------------
The library is for increasing the size of existing Toolbar area, so in your activity layout XML, position the MenuExtension right below to the toolbar. An example layout would be as follows.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical">

    <android.support.v7.widget.Toolbar/>

    <RelativeLayout >
        <View ... your app content is here />

        <!-- Position the menu extension to the top of Relative Layout-->
        <com.bafoly.widget.menu.MenuExtension
            android:id="@+id/menuExtension"
            android:layout_width="fill_parent"
            android:layout_alignParentTop="true"
            android:orientation="vertical"/>
    </RelativeLayout>
</LinearLayout>
```

Create menu xml's for each extension menu.

Implementation of Activity is
```java
public class ActivityToolbar extends AppCompatActivity {

    MenuExtension menuExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        menuExtension = (MenuExtension) findViewById(R.id.menuExtension);
        
        // set the toolbar with current toolbar color
        menuExtension.setToolbar(toolbar, getResources().getColor(R.color.colorPrimary));
        
        // set the click callback
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
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_first:
                menuExtension.menuShowHideToggle(R.menu.extension_menu_first, Color.BLUE);
                break;
            case R.id.menu_second:
                menuExtension.menuShowHideToggle(R.menu.extension_menu_second, Color.RED);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
```
Demo Application
-------------
https://play.google.com/store/apps/details?id=com.bafoly.sample.menuextensionapp

License
-------------
http://www.apache.org/licenses/LICENSE-2.0