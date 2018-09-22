package com.q8safemobile.fragments.ScanDevice.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.q8safemobile.R;
import com.q8safemobile.scan.DetectionsDisplay;

import java.util.ArrayList;

public class ErrorDetaileAdapter extends RecyclerView.Adapter<ErrorDetaileAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private ArrayList<DetectionsDisplay> productList;

    //getting the context and product list with constructor
    public ErrorDetaileAdapter(Context mCtx, ArrayList<DetectionsDisplay> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_details_items, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        DetectionsDisplay product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getFileName());
        holder.TxtViewAddress.setText(String.valueOf(product.getFileLoaction()));



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, TxtViewAddress, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            TxtViewAddress = itemView.findViewById(R.id.TVAddress);

        }
    }
}