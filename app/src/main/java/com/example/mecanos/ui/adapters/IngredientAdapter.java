package com.example.mecanos.ui.adapters;

import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientItemBinding;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.ui.clickcallback.IngredientClickCallback;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<? extends Ingredient> mIngredientList;

    @Nullable
    private final IngredientClickCallback mIngredientClickCallback;

    public IngredientAdapter(@Nullable IngredientClickCallback IngredientClickCallback) {
        mIngredientClickCallback = IngredientClickCallback;
    }

    public void setIngredientList(final List<? extends Ingredient> ingredients) {
        if (mIngredientList == null) {
            mIngredientList = ingredients;
            notifyItemRangeInserted(0, ingredients.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mIngredientList.size();
                }

                @Override
                public int getNewListSize() {
                    return ingredients.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Ingredient old = mIngredientList.get(oldItemPosition);
                    Ingredient Ingredient = ingredients.get(newItemPosition);
                    return old.getId() == Ingredient.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Ingredient old = mIngredientList.get(oldItemPosition);
                    Ingredient Ingredient = ingredients.get(newItemPosition);
                    return old.getId() == Ingredient.getId()
                            && Objects.equals(old.getNombre(), Ingredient.getNombre())
                            && Objects.equals(old.getProveedor(), Ingredient.getProveedor())
                            && Objects.equals(old.getProveedor(), Ingredient.getProveedor())
                            && Objects.equals(old.getDiaCompra(), Ingredient.getDiaCompra());
                }
            });
            mIngredientList = ingredients;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IngredientItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.ingredient_item,
                        parent, false);
        binding.setCallback(mIngredientClickCallback);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.binding.setIngredient(mIngredientList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mIngredientList == null ? 0 : mIngredientList.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        final IngredientItemBinding binding;

        IngredientViewHolder(IngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}