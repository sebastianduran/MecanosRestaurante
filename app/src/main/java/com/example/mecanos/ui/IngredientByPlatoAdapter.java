package com.example.mecanos.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientPlatoItemBinding;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.model.IngredientsByPlato;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientByPlatoAdapter extends RecyclerView.Adapter<IngredientByPlatoAdapter.IngredientByPlatoViewHolder> {

    private List<? extends IngredientsByPlato> mIngredientByPlatoList;

    @Nullable
    private final IngredientByPlatoClickCallback mIngredientByPlatoClickCallback;

    public IngredientByPlatoAdapter (@Nullable IngredientByPlatoClickCallback ingredientByPlatoClickCallback){
        mIngredientByPlatoClickCallback = ingredientByPlatoClickCallback;
    }

    public void setIngredientByPlatoList (final List<? extends IngredientsByPlato> IngredientsByPlato){
        if (mIngredientByPlatoList == null){
            mIngredientByPlatoList = IngredientsByPlato;
            notifyItemRangeChanged(0, IngredientsByPlato.size());
        }else{
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mIngredientByPlatoList.size();
                }

                @Override
                public int getNewListSize() {
                    return IngredientsByPlato.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    IngredientsByPlato oldItem = mIngredientByPlatoList.get(oldItemPosition);
                    IngredientsByPlato newItem = IngredientsByPlato.get(newItemPosition);
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    IngredientsByPlato oldItem = mIngredientByPlatoList.get(oldItemPosition);
                    IngredientsByPlato newItem = IngredientsByPlato.get(newItemPosition);
                    return oldItem.getId() == newItem.getId()
                            && Objects.equals(oldItem.getIngredientNombre(), newItem.getIngredientNombre())
                            && Objects.equals(oldItem.getPlatoNombre(), newItem.getPlatoNombre())
                            && Float.compare(oldItem.getCantidadIngredient(), newItem.getCantidadIngredient()) ==0
                            && Objects.equals(oldItem.getCompra(), newItem.getCompra());
                }
            });
            mIngredientByPlatoList = IngredientsByPlato;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public IngredientByPlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IngredientPlatoItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.ingredient_plato_item,
                        parent, false);
        binding.setCallback(mIngredientByPlatoClickCallback);
        return new IngredientByPlatoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(IngredientByPlatoViewHolder holder, int position) {
        holder.binding.setIngredientsByPlato(mIngredientByPlatoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mIngredientByPlatoList == null ? 0 : mIngredientByPlatoList.size();
    }

    static class IngredientByPlatoViewHolder extends RecyclerView.ViewHolder{

        final IngredientPlatoItemBinding binding;

        IngredientByPlatoViewHolder(IngredientPlatoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}