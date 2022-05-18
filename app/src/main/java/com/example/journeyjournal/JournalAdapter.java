package com.example.journeyjournal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    public ArrayList<JournalModel> data;
    public JournalAdapter(ArrayList<JournalModel> data, ItemClickListener itemClickListener) {
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    ItemClickListener mItemListener;

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_journal,parent,false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        int a = data.get(position).getId();
        holder.dId.setText(String.valueOf(a));
        holder.dTitle.setText(data.get(position).getTitle());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(JournalModel j);
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder {
        TextView dId, dTitle;
        public JournalViewHolder(View itemView) {
            super(itemView);
            dId = (TextView)itemView.findViewById(R.id.txtId);
            dTitle = (TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}
