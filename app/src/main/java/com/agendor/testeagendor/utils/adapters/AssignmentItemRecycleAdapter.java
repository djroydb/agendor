package com.agendor.testeagendor.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agendor.testeagendor.R;
import com.agendor.testeagendor.domain.model.Assignment;
import com.agendor.testeagendor.model.enums.AssignmentType;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * @author Robson Freitas
 */
public class AssignmentItemRecycleAdapter extends RecyclerView.Adapter<AssignmentItemRecycleAdapter.ViewHolder>{

    private List<Assignment> localDataSet;

    public AssignmentItemRecycleAdapter(List<Assignment> list){
        localDataSet = list;
    }

    @NonNull
    @Override
    public AssignmentItemRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_item_agendor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentItemRecycleAdapter.ViewHolder holder, int position) {
        Assignment assignment = localDataSet.get(position);
        DateTimeFormatter sdf = DateTimeFormat.forPattern("EE HH:mm");

        String dateFormat = assignment.getDate().toString(sdf);
        holder.getDate().setText(dateFormat.toUpperCase());
        holder.getIcon().setImageResource(getImageDrawable(assignment.getType()));
        holder.getDescription().setText(assignment.getDescription());
        holder.getClient().setText(assignment.getClient());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Recupera o drawable para o typo especificado
     * @param type AssignmentType
     * @return Drawable
     */
    private int getImageDrawable(AssignmentType type){
        switch (type){
            case EMAIL:
                return R.drawable.ic_baseline_mail_outline_24;
            case LIGACAO:
                return R.drawable.ic_baseline_call_24;
            case PROPOSTA:
                return R.drawable.ic_baseline_format_list_bulleted_24;
            case REUNIAO:
                return R.drawable.ic_baseline_business_center_24;
            case VISITA:
                return R.drawable.ic_baseline_place_24;
            default:
                return R.drawable.ic_baseline_more_horiz_24;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final ImageView icon;
        private final TextView description;
        private final TextView client;

        public ViewHolder(@NonNull View v) {
            super(v);
            date        = v.findViewById(R.id.date);
            icon        = v.findViewById(R.id.type_icon);
            description = v.findViewById(R.id.description_text);
            client      = v.findViewById(R.id.client_text);
        }

        public TextView getDate() {
            return date;
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getClient() {
            return client;
        }
    }
}
