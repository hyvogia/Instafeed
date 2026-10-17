package hyvo.prog1415.instafeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

//Hy Vo Final Project

public class MainActivity extends AppCompatActivity {
    static final ArrayList<BusinessLogic> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.main_title);
        title.setText("Instafeed");

        try {
            FileInputStream f = MainActivity.this.openFileInput("data");
            ObjectInputStream in = new ObjectInputStream(f);
            Object obj = in.readObject();
            while(obj != null)
            {
                BusinessLogic data = (BusinessLogic) obj;
                list.add(data);
                obj = in.readObject();
            }
            in.close();
            f.close();

        } catch(Exception ex){
            //Toast.makeText(MainActivity.this,"Input Stream Error: " + ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

        ImageButton search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        ImageButton history = findViewById(R.id.btnHistory);
        history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ReviewHistory.class);
                MainActivity.this.startActivity(i);
            }
        });

        ImageButton map = findViewById(R.id.btnMap);
        map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
    }
}