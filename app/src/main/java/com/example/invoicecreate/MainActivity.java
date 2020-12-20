package com.example.invoicecreate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invoicecreate.customer.CustomerInformationData;
import com.example.invoicecreate.customer.CustomerInformationDataResponse;
import com.example.invoicecreate.invoice.SetInVoiceResponse;
import com.example.invoicecreate.invoice.SetProductData;
import com.example.invoicecreate.owner_all_information.OwnerDataWithResponse;
import com.example.invoicecreate.product.GetProductData;
import com.example.invoicecreate.product.GetProductDataResponse;
import com.example.invoicecreate.retrofit.ApiInterface;
import com.example.invoicecreate.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductCustomAdapter.OnContactClickListener , CustomerCustomAdapter.OnContactClickListener1 {
        TextView nameTextView,phoneTextView,addressTextView,dueTextView,customerIdTextView;
        String  name,phone,address,due,customerId;


        ApiInterface apiInterface;
        String token;
        List<SetProductData> setProductDataList;
        List<GetProductData> newList=new ArrayList<>();

        List<CustomerInformationData> customerInformationDataList;
        List<GetProductData> getProductDataList;

        SetInVoiceResponse setInVoiceResponse;
        Button inVoiceButton,product,selectCustomerButton;

    RecyclerView productRecyclerView;
    ListView listView;
    ProductCustomAdapter.OnContactClickListener onContactClickListener;
    CustomerCustomAdapter.OnContactClickListener1 onContactClickListener1;

    ProductCustomAdapter productCustomAdapter;
    ProductCustomAdapter2 productCustomAdapter2;
    CustomerCustomAdapter customerCustomAdapter;
    AlertDialog alertDialog;

    RecyclerView selectRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onContactClickListener=this;
        onContactClickListener1=this;

        inVoiceButton=findViewById(R.id.inVoiceButtonId);
        selectCustomerButton=findViewById(R.id.selectCustomerButtonId);
        product=findViewById(R.id.productId);
        listView=findViewById(R.id.listViewID);
        selectRecyclerView=findViewById(R.id.selectRecyclerView);
        customerIdTextView=findViewById(R.id.customerIdTextViewId);

        nameTextView=findViewById(R.id.nameTextViewId);
        phoneTextView=findViewById(R.id.phoneTextViewId);
        addressTextView=findViewById(R.id.addressTextViewId);
        dueTextView=findViewById(R.id.dueTextViewId);

        inVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInVoice();
            }
        });

        apiInterface = RetrofitClient.getRetrofit("http://mern-pos.herokuapp.com/").create(ApiInterface.class);
        token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmYTY2ODdiNzdkZmFjMDAxN2JkMzNkMSIsImlhdCI6MTYwODQ1OTk3NiwiZXhwIjoxNjA4NTQ2Mzc2fQ.oZvoDCfYWCk05Tnl08TpIl92ILTN7iu9Kdd7TymiqQo";


        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllProduct();
            }
        });


        selectCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCustomerInformation();
            }
        });
    }


    public void  setInVoice(){
        customerId= customerIdTextView.getText().toString().trim();
//        name= nameTextView.getText().toString().trim();
//        phone= phoneTextView.getText().toString().trim();


        if (TextUtils.isEmpty(customerId)){
            nameTextView.setError("Enter your email");
            nameTextView.requestFocus();
            return;
        }


        setProductDataList=new ArrayList<>();

        int sz=newList.size();
        for (int i=0;sz-1>=i;i++){
            setProductDataList.add(new SetProductData(newList.get(i).getId(),1));
        }
//        setProductDataList.add(new SetProductData("5fafea7e68aede001776971d",1));
//        setProductDataList.add(new SetProductData("5fd0d212a8fe9400174ab93d",1));
        setInVoiceResponse=new SetInVoiceResponse(customerId,600,
                2010,0, setProductDataList);
        apiInterface.getInvoiceResponse("Bearer "+token,setInVoiceResponse).enqueue(new Callback<OwnerDataWithResponse>() {
            @Override
            public void onResponse(Call<OwnerDataWithResponse> call, Response<OwnerDataWithResponse> response) {
                Toast.makeText(MainActivity.this, "sssssssss", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<OwnerDataWithResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                Log.e("getInvoice", String.valueOf(t.getMessage()));

            }
        });

    }


    public void getAllProduct() {
        apiInterface.getAllProduct("Bearer "+token).
                enqueue(new Callback<GetProductDataResponse>() {
            @Override
            public void onResponse(Call<GetProductDataResponse> call, Response<GetProductDataResponse> response) {

               if (response.isSuccessful()){
                   if (response.body().getSuccess()==true){
                       getProductDataList=new ArrayList<>();
                       getProductDataList.addAll(response.body().getProducts());
                       if (getProductDataList.size ()>0){
                           addProductInformation(getProductDataList);
                           // Toast.makeText(MainActivity.this, String.valueOf(getProductDataList.size()), Toast.LENGTH_SHORT).show();
                       }
                   }
               }else {
                   Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
               }
            }
            @Override
            public void onFailure(Call<GetProductDataResponse> call, Throwable t) {
            }
        });

    }

    private void addProductInformation( List<GetProductData> getProductDataList){
        AlertDialog.Builder builder     =new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(MainActivity.this);
        View view                       =layoutInflater.inflate(R.layout.product,null);
        builder.setView(view);
         alertDialog   = builder.create();

        productRecyclerView=view.findViewById(R.id.productRecyclerViewId);
        productCustomAdapter = new ProductCustomAdapter(MainActivity.this,token,getProductDataList,onContactClickListener);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        productRecyclerView.setAdapter(productCustomAdapter);

        alertDialog.show();
    }



    public  void getAllCustomerInformation(){
        apiInterface.getAllCustomerInformation("Bearer "+token).enqueue(new Callback<CustomerInformationDataResponse>() {
            @Override
            public void onResponse(Call<CustomerInformationDataResponse> call, Response<CustomerInformationDataResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==true){
                        customerInformationDataList=new ArrayList<>();
                        customerInformationDataList.addAll(response.body().getCustomerInformation());
                        if (customerInformationDataList.size ()>0){
                            addCustomerInformation(customerInformationDataList);
                            Toast.makeText(MainActivity.this, String.valueOf(customerInformationDataList.size()), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<CustomerInformationDataResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail Customer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCustomerInformation(List<CustomerInformationData> customerInformationDataList1){
        AlertDialog.Builder builder     =new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater   =LayoutInflater.from(MainActivity.this);
        View view                       =layoutInflater.inflate(R.layout.product,null);
        builder.setView(view);
        alertDialog   = builder.create();
        productRecyclerView=view.findViewById(R.id.productRecyclerViewId);
        customerCustomAdapter = new CustomerCustomAdapter(MainActivity.this,token,customerInformationDataList1,onContactClickListener1);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        productRecyclerView.setAdapter(customerCustomAdapter);
        alertDialog.show();
    }


    @Override
    public void onContactClick(int position) {
        //getAllProduct();
        Toast.makeText(this, String.valueOf(getProductDataList.get(position).getName()), Toast.LENGTH_SHORT).show();
            newList.add(getProductDataList.get(position));

        productCustomAdapter2 = new ProductCustomAdapter2(MainActivity.this,token,newList);
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        selectRecyclerView.setAdapter(productCustomAdapter2);
            Log.e("size",String.valueOf(newList.size()));
         alertDialog.dismiss();
    }
    @Override
    public void onContactClick1(int position) {
        nameTextView.setText(String.valueOf(customerInformationDataList.get(position).getName()));
        phoneTextView.setText(String.valueOf(customerInformationDataList.get(position).getPhone()));
        addressTextView.setText(String.valueOf(customerInformationDataList.get(position).getAddress()));
        dueTextView.setText(String.valueOf(customerInformationDataList.get(position).getDue()));
        customerIdTextView.setText(String.valueOf(customerInformationDataList.get(position).getId()));
        alertDialog.dismiss();
    }
}