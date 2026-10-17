package hyvo.prog1415.instafeed;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Hy Vo Final Project

public class ReviewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TextView tv = this.findViewById(R.id.reviewOutput);
        String reviews = "";
        tv.setText("");
        for(int i = 0; i < ReviewActivity.list.size(); i++)
            reviews += ReviewActivity.list.get(i).toString() + "\n";
        tv.setText(reviews);
    }
}
