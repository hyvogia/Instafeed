package hyvo.prog1415.instafeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Hy Vo Final Project

public class SearchActivity extends AppCompatActivity {
    private String[] businessList = {
            "Kully's Original Sports Bar",
            "The Merchant Ale House",
            "Pho Dau Bo",
            "HAMBURGER",
            "Incoho Restaurant",
            "The Feathery Pub",
            "Blue Mermaid Seafood & Steak House",
            "Wellington Court Restaurant + Catering",
            "Bistro Mirepoix",
            "Sahla Thai",
            "Golden Grill",
            "The Chopped Leaf",
            "Spicy Thai Restaurant",
            "OddBird"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView rv = this.findViewById(R.id.rvLocations);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ListAdapter(businessList));
    }
}
