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
    compile 'com.bafoly.menu:menu-extension:1.0'
}
```

Usage
-------------
The library is aiming to expand the size of existing Toolbar, so in your activity layout XML, position the MenuExtension right below to the toolbar. An example layout would be as follows.

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
        // set this Activity for click callbacks
        menuExtension.setActivity(this);
        // set the toolbar with current toolbar color
        menuExtension.setToolbar(toolbar, getResources().getColor(R.color.colorPrimary));
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

    @Override
    public void onMenuExtensionItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.extension_menu_second_submenu){
            menuExtension.menuShowHideToggle(R.menu.extension_menu_third, Color.YELLOW);
        } else {
            textView.setText("Clicked : "+menuItem.getTitle());
        }
    }
```
Demo Application
-------------
https://play.google.com/store/apps/details?id=com.bafoly.sample.menuextensionapp

License
-------------
http://www.apache.org/licenses/LICENSE-2.0