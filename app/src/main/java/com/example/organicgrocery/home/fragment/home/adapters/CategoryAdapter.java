package com.example.organicgrocery.home.fragment.home.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.response.Category;
import com.example.organicgrocery.categoryPage.CategoryActivity;
import com.example.organicgrocery.utils.DataHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater inflater;
    Context context;
    Boolean showImage;
    Boolean select;
    Activity activity;

    public CategoryAdapter(List<Category> categories, Context context, Boolean showImage, Boolean select, Activity activity){
        this.categories = categories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.showImage = showImage;
        this.select = select;
        this.activity = activity;
    }

    @NonNull
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (showImage)
            return new CategoryViewHolder(inflater.inflate(R.layout.category_item, parent, false));
        else
            return new CategoryViewHolder(inflater.inflate(R.layout.item_category_noimage, parent, false));
    }





    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.cateNameTV.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.cateIv);
        holder.categoryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select) {
                    DataHolder.category = categories.get(holder.getAdapterPosition());
                    activity.finish();
                    ;
                } else {
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra(CategoryActivity.CATEGORY_DATA_KEY, categories.get(holder.getAdapterPosition()));
                    context.startActivity(intent);

                }
            }

        });
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

     class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView cateIv;
        TextView cateNameTV;
        LinearLayout categoryLL;

         public CategoryViewHolder(@NonNull View itemView) {
             super(itemView);
             cateIv = itemView.findViewById(R.id.cateIV);
             cateNameTV = itemView.findViewById(R.id.cateNameTV);
             categoryLL = itemView.findViewById(R.id.categoryLL);


         }
     }
}
