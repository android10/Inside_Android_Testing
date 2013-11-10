package com.pivotallabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NamesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_list_layout);

        List<String> names = new ArrayList<String>();
        names.add("Dick Cheney");             // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/chocolate-cake-icon.png
        names.add("Donald Rumsfeld");         // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/cookies-icon.png
        names.add("John Ashcroft");           // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/cream-cake-icon.png
        names.add("Karl Rove");               // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/chocolate-cake-icon.png
        names.add("Tom Ridge");               // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/chocolate-cake-icon.png
        names.add("Justin Bieber");           // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/tart-icon.png
        names.add("Lindsey Lohan");           // http://icons.iconarchive.com/icons/iloveicons.ru/so-sweet/128/tartlet-icon.png
        names.add("Sponge Bob Square Pants"); // http://icons.iconarchive.com/icons/mcdo-design/japan-summer/256/Cooribata-icon.png
        names.add("Kai Lan");                 // http://icons.iconarchive.com/icons/mcdo-design/japan-summer/256/Kingyobati-Empty-icon.png
        names.add("Sheriff Woody");           // http://icons.iconarchive.com/icons/mcdo-design/japan-summer/256/Matsriutiwa-icon.png
        names.add("Slinky Dog");              // http://icons.iconarchive.com/icons/mcdo-design/japan-summer/256/Kingyobati-Full-icon.png

        ((ListView) findViewById(R.id.names_list)).setAdapter(new NamesAdapter(names));
    }
}
