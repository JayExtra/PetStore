package com.example.mypetstore.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypetstore.R;
import com.example.mypetstore.Room.Pet;

public class PetRecyClerAdapter extends ListAdapter<Pet, PetRecyClerAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public PetRecyClerAdapter() {
        super(DIFF_CALLBACK);
    }


    public static final DiffUtil.ItemCallback<Pet> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pet>() {


        @Override
        public boolean areItemsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
            return oldItem.getId() == newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
            return oldItem.getPetId().equals(newItem.getPetId()) &&
                    oldItem.getPetBreed().equals(newItem.getPetBreed()) &&
                    oldItem.getPetType().equals(newItem.getPetType()) &&
                    oldItem.getPetColor().equals(newItem.getPetColor()) &&
                    oldItem.getPetname().equals(newItem.getPetname());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_pet_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Pet currentPet = getItem(position);
     holder.mNameTv.setText(currentPet.getPetName());
        holder.mIdTv.setText(currentPet.getPetId());
        holder.mTypeTv.setText(currentPet.getPetType());
        holder.mColorTv.setText(currentPet.getPetColor());

    }

    public Pet getPetAt(int position){
        return getItem(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mNameTv;
        public final TextView mIdTv;
        public final TextView mTypeTv;
        public final TextView mColorTv;
        public final CardView mCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv = (TextView) itemView.findViewById(R.id.pet_tv);
            mIdTv = (TextView) itemView.findViewById(R.id.pet_id_tv);
            mTypeTv = (TextView) itemView.findViewById(R.id.pet_type_tv);
            mColorTv = (TextView) itemView.findViewById(R.id.color_tv);
            mCardView = (CardView) itemView.findViewById(R.id.pet_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(mOnItemClickListener != null && position != RecyclerView.NO_POSITION){
                        mOnItemClickListener.onItemClick(getItem(position));
                    }
                }
            });



        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pet pet);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
