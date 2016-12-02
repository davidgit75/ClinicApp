package com.davidh.davidh.clinicapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by davidh on 1/12/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.SelfHolder> {

    private Context context;
    private ArrayList<HistoryTemplate> historyTemplates;
    private Integer currentPosition;

    public HistoryAdapter(Context context, ArrayList<HistoryTemplate> historyTemplates) {
        this.context = context;
        this.historyTemplates = historyTemplates;
    }

    @Override
    public HistoryAdapter.SelfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelfHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_history,null));
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.SelfHolder holder, int position) {
        holder.title.setText(historyTemplates.get(position).getTitle());
        holder.body.setText(historyTemplates.get(position).getBody());
        holder.date.setText(historyTemplates.get(position).getDate());
        holder.letter.setText(historyTemplates.get(position).getLetter());
    }

    @Override
    public int getItemCount() {
        return historyTemplates.size();
    }

    public int getPosition(){
        return currentPosition;
    }

    public class SelfHolder extends RecyclerView.ViewHolder {
        TextView title, body, letter, date;
        public SelfHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.history_reason_query);
            body = (TextView) itemView.findViewById(R.id.history_preview_recommendations);
            date = (TextView) itemView.findViewById(R.id.history_date);
            letter = (TextView) itemView.findViewById(R.id.history_init_letter);
        }
    }
}
