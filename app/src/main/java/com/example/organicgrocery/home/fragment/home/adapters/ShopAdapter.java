package com.example.organicgrocery.home.fragment.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.response.Product;
import com.example.organicgrocery.singleProductScreen.SingleProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{
    List<Product> productDataList;
    LayoutInflater layoutInflater;
    Context context;//layout tasney kaam garcha

    public ShopAdapter(List<Product> productDataList, Context context) {
        this.productDataList = productDataList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(layoutInflater.inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.productNameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice()==0){
            holder.productPriceTV.setVisibility(View.GONE);
            holder.discountPriceTV.setText(productDataList.get(position).getPrice()+"");
        }
        else
            holder.discountPriceTV.setText(productDataList.get(position).getDiscountPrice()+"");
        holder.productPriceTV.setText(productDataList.get(position).getPrice()+"");

        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);
        holder.mainLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productPage = new Intent(context, SingleProductActivity.class);
                productPage.putExtra(SingleProductActivity.key, productDataList.get(holder.getAdapterPosition()));
                context.startActivity(productPage);

            }
        });



    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        TextView productNameTV, productPriceTV, discountPriceTV, discountPercentTV;
        LinearLayout mainLL;
        public ShopViewHolder( View itemView){
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.productPriceTV);
            discountPriceTV = itemView.findViewById(R.id.discountPriceTV);
            mainLL = itemView.findViewById(R.id.mainLL);

        }
    }
}
