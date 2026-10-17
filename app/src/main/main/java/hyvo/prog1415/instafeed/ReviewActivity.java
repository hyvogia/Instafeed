package hyvo.prog1415.instafeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//Hy Vo Final Project

public class ReviewActivity extends AppCompatActivity {
    static final ArrayList<BusinessLogic> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView title = findViewById(R.id.txtTitle);
        title.setText(getIntent().getStringExtra("title"));

        try {
            FileInputStream f = ReviewActivity.this.openFileInput("data");
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
            //Toast.makeText(ReviewActivity.this,"Input Stream Error: " + ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

        Button save = this.findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView newTitle = findViewById(R.id.txtTitle);
                final RatingBar rating = findViewById(R.id.rbrReview);
                final EditText comments = findViewById(R.id.txtComments);
                final java.sql.Date inputDate = new java.sql.Date(System.currentTimeMillis());
                BusinessLogic data = new BusinessLogic(
                        newTitle.getText().toString(),
                        rating.getRating(),
                        inputDate,
                        comments.getText().toString());
                list.add(data);
                newTitle.setText("");
                rating.setRating(0);
                comments.setText("");
                //Toast.makeText(ReviewActivity.this,"Added",Toast.LENGTH_SHORT).show();
                try {
                    FileOutputStream file = ReviewActivity.this.openFileOutput("data", Context.MODE_PRIVATE);
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    for(int x=0;x<list.size();x++)
                        out.writeObject(list.get(x));
                    out.close();
                    file.close();
                    Toast.makeText(ReviewActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                } catch(IOException ex){
                    Toast.makeText(ReviewActivity.this,"Not Saved" + ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(ReviewActivity.this, MainActivity.class);
                ReviewActivity.this.startActivity(i);
            }
        });

        /*Button save = this.findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {

            }
        });*/

        /*Button display = this.findViewById(R.id.btnDisplay);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewActivity.this, ReviewHistory.class);
                ReviewActivity.this.startActivity(i);
            }
        });*/
    }
}
