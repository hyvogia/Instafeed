package hyvo.prog1415.instafeed;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Hy Vo Final Project

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private String[] bsnList;

    public ListAdapter(String[] data){
        bsnList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.search_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String item = bsnList[position];
        holder.tv.setText(bsnList[position]);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Selected: "+ item,Toast.LENGTH_LONG).show();
                Intent i = new Intent(view.getContext(), ReviewActivity.class);
                i.putExtra("title", item);
                view.getContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bsnList.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        Button b;
        public ViewHolder(View v)
        {
            super(v);
            tv = v.findViewById(R.id.tvData);
            b = v.findViewById(R.id.btnDetails);
        }
    }
}
