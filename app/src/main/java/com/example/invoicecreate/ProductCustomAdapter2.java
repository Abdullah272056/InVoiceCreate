package com.example.invoicecreate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invoicecreate.product.GetProductData;
import com.example.invoicecreate.retrofit.ApiInterface;

import java.util.List;

public class ProductCustomAdapter2 extends RecyclerView.Adapter<ProductCustomAdapter2.MyViewHolder> {

    TextView productNameTextView,productRegularPriceTextView,productSellingPriceTextView,
            productStockTextView,productUnitTextView,productDescriptionTextView,
            productAddDateTextView,productUpdateDateTextView;
    ImageView productIDetailsImageView;

    Button okButton;

    Context context;
    String token;
    List<GetProductData> productDataList;

    ApiInterface apiInterface;
    public ProductCustomAdapter2(Context context, String token, List<GetProductData> productDataList) {
        this.context = context;
        this.token = token;
        this.productDataList = productDataList;



    }
    @NonNull
    @Override
    public ProductCustomAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductCustomAdapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCustomAdapter2.MyViewHolder holder, final int position) {
        holder.productNameTextView.setText(String.valueOf(productDataList.get(position).getName()));
//        holder.productSellingPriceTextView.setText(String.valueOf(productDataList.get(position).getSellingPrice()));
//        holder.productStockTextView.setText(String.valueOf(productDataList.get(position).getStock()));
//
//
//        holder.productNameTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                productDataListNext=new ArrayList<>();
//                productDataListNext.add(productDataList.get(position));
//                Toast.makeText(context, String.valueOf(productDataListNext.size()), Toast.LENGTH_SHORT).show();
//
//               /// Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });


    }

    @Override
    public int getItemCount(){
        return productDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productNameTextView,productSellingPriceTextView,productStockTextView;
        ImageView productImageView;
        LinearLayout productRecyclerViewItem;
        ImageView editProductImageView,deleteProductImageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            productNameTextView=itemView.findViewById(R.id.productNameTextViewId);


//            productSellingPriceTextView=itemView.findViewById(R.id.productSellingPriceTextViewId);
//            productStockTextView=itemView.findViewById(R.id.productStockTextViewId);

        }


    }



}
