package com.bag.animalfacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<AnimalFact> animalFacts;

    DataAdapter(Context context, ArrayList<AnimalFact> animalFacts) {
        this.animalFacts = animalFacts;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_fact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        final AnimalFact animalFact = animalFacts.get(position);

        holder.nameView.setText("Type: "+animalFact.getNameAnimal());

        holder.factView.setText("Fact: "+animalFact.getFact());

        if ( MainActivity.isRandomFragment ) {
            holder.dataView.setText("Data: "+animalFact.getData());
        } else {
            holder.dataView.setText("Upvotes: "+animalFact.getUpvotesString());
        }
    }

    @Override
    public int getItemCount() {
        return animalFacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, factView, dataView;
        ViewHolder(View view){
            super(view);
            nameView =  view.findViewById(R.id.nameAnimal);
            dataView =  view.findViewById(R.id.dataOrUpvotes);
            factView =  view.findViewById(R.id.fact);


        }
    }
}
