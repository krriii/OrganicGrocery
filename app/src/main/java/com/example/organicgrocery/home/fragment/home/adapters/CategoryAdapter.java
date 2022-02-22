package com.example.organicgrocery.home.fragment.home.adapters;

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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater inflater;
    Context context;
    Boolean showImage;

    public CategoryAdapter(List<Category> categories, Context context, Boolean showImage){
        this.categories = categories;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.showImage = showImage;
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
        holder.catNameTV.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.catIv);
        holder.categoryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra(CategoryActivity.CATEGORY_DATA_KEY, categories.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }

        });
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

     class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView catIv;
        TextView catNameTV;
        LinearLayout categoryLL;

         public CategoryViewHolder(@NonNull View itemView) {
             super(itemView);
             catIv = itemView.findViewById(R.id.catIV);
             catNameTV = itemView.findViewById(R.id.catNameTV);
             categoryLL = itemView.findViewById(R.id.categoryLL);


         }
     }
}
