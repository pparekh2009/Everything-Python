package com.priyanshparekh.everythingpython;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    Activity activity;
    private Context context;
    private List<Program> programList;
    private List<Program> programListAll;

    public RecyclerViewAdapter(Activity activity, List<Program> programList) {
        this.activity = activity;
        this.programList = programList;
        this.programListAll = new ArrayList<Program>(programList);
    }

    public RecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Program program = programList.get(position);

//        holder.programId.setText(Integer.toString(program.getId()));
        holder.programId.setText(Integer.toString(position+1));
        holder.programQues.setText(program.getProgramQuestion());

    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Program> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(programListAll);
            }
            else {
                for (Program program : programListAll) {
                    if (program.getProgramQuestion().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(program);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            programList.clear();
            programList.addAll((Collection<? extends Program>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView programId;
        public TextView programQues;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            programId = itemView.findViewById(R.id.program_id);
            programQues = itemView.findViewById(R.id.program_ques);
        }

        @Override
        public void onClick(View v) {
            Program programObj = new Program();
            DBHelper db = new DBHelper(activity.getApplicationContext());
//            List<Program> list = new ArrayList<>();
            List<Program> list = db.getProgramList();
            Program data = list.get(getAdapterPosition());

            int position = this.getAdapterPosition();
            Intent intent = new Intent(activity.getApplicationContext(), CompilerActivity.class);
            intent.putExtra("PROGRAM", data.getProgram());
            activity.startActivity(intent);
            Toast.makeText(activity.getApplicationContext(), "Position: " + (position+1), Toast.LENGTH_SHORT).show();
        }
    }
}
