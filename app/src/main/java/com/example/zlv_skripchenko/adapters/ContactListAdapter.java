package com.example.zlv_skripchenko.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zlv_skripchenko.R;
import com.example.zlv_skripchenko.utils.TestItem;

import java.util.List;

/**
 * Created by Yevgen on 15.06.2017.
 */

public class ContactListAdapter extends  RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {

private List<TestItem> moviesList;

  public void setItems(List<TestItem> moviesList){
      this.moviesList=moviesList;
      notifyDataSetChanged();
  }


public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    public TextView id_name;

    public MyViewHolder(View view) {
        super(view);
        id_name = (TextView) view.findViewById(R.id.id_name);
        view.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        if(onItemLongClickListener!=null){
            onItemLongClickListener.onLongClick(moviesList.get(getPosition()));
        }
        return false;
    }
}

   private OnItemLongClickListener onItemLongClickListener;
    public interface OnItemLongClickListener{
        void onLongClick(TestItem item);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public ContactListAdapter(List<TestItem> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TestItem movie = moviesList.get(position);
        holder.id_name.setText(movie.name);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
