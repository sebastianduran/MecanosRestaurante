package com.example.mecanos.ui;

import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.PlatoItemBinding;
import com.example.mecanos.model.Plato;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    List<? extends Plato> mPlatoList;

    @Nullable
    private final PlatoClickCallback mPlatoClickCallback;

    @Nullable
    private final PlatoLongClickCallback mPlatoLongClickCallback;

    public PlatoAdapter(@Nullable PlatoClickCallback clickCallback, @Nullable PlatoLongClickCallback longClickCallback) {

        mPlatoClickCallback = clickCallback;
        mPlatoLongClickCallback = longClickCallback;
        setHasStableIds(true);
    }

    public void setPlatoList(final List<? extends Plato> PlatoList) {

        if (mPlatoList == null) {
            mPlatoList = PlatoList;
            notifyItemRangeInserted(0, PlatoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPlatoList.size();
                }

                @Override
                public int getNewListSize() {
                    return PlatoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPlatoList.get(oldItemPosition).getId() ==
                            PlatoList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Plato newPlato = PlatoList.get(newItemPosition);
                    Plato oldPlato = mPlatoList.get(oldItemPosition);
                    return newPlato.getId() == oldPlato.getId()
                            && Objects.equals(newPlato.getDescription(), oldPlato.getDescription())
                            && Objects.equals(newPlato.getName(), oldPlato.getName())
                            && Float.compare(newPlato.getPrice(), oldPlato.getPrice()) == 0
                            && Float.compare(newPlato.getCosto(), oldPlato.getCosto()) == 0;
                }
            });
            mPlatoList = PlatoList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public PlatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlatoItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.plato_item,
                        parent, false);
        binding.setCallbackClick(mPlatoClickCallback);
        binding.setCallbackLongClick(mPlatoLongClickCallback);
        return new PlatoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PlatoViewHolder holder, int position) {
        holder.binding.setPlato(mPlatoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPlatoList == null ? 0 : mPlatoList.size();
    }

    @Override
    public long getItemId(int position) {
        return mPlatoList.get(position).getId();
    }

    static class PlatoViewHolder extends RecyclerView.ViewHolder {

        final PlatoItemBinding binding;

        public PlatoViewHolder(PlatoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
