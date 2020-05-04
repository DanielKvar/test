package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structure.Tekma;

import java.util.List;

public class TekmeAdapter extends RecyclerView.Adapter<TekmeAdapter.ViewHolder> {
    private MyApp App;
    private OnItemClickListener listener;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public TekmeAdapter(MyApp app) {
            App = app;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.rv_rowlayout, parent, false);
            TekmeAdapter.ViewHolder viewHolder = new TekmeAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Tekma trenutna = App.getAtPos(position);
            holder.txtHeader.setText(trenutna.getNaslov());
            holder.txtFooter.setText(trenutna.getStartDate().toString());
            if (position%2 == 1) {
                holder.ozadje.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.txtHeader.setTextColor(Color.BLACK);
            } else {
                holder.ozadje.setBackgroundColor(Color.WHITE);
                holder.txtHeader.setTextColor(Color.BLACK);

            }

        }

    @Override
    public int getItemCount() {
        return App.Count();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView iv;
        public View ozadje;
        public ViewHolder(@NonNull View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            ozadje = v.findViewById(R.id.mylayoutrow);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}