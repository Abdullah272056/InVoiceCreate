package com.example.invoicecreate.retrofit;
import com.example.invoicecreate.customer.CustomerInformationDataResponse;
import com.example.invoicecreate.invoice.SetInVoiceResponse;
import com.example.invoicecreate.owner_all_information.OwnerDataWithResponse;
import com.example.invoicecreate.product.GetProductDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @GET("api/user/")
//    Call<AddresResponse> getAddress();


/////Authorization////






  //Get Me
  @GET("api/auth/me")
  Call<OwnerDataWithResponse> getUserAllInformation(@Header("Authorization") String authorization);


  //Get Me
  @POST("/api/invoice")
  Call<OwnerDataWithResponse> getInvoiceResponse(@Header("Authorization") String authorization, @Body SetInVoiceResponse setInVoiceResponse);

  ////// product//////
  // get all product
  @GET("api/product")
  Call<GetProductDataResponse> getAllProduct(@Header("Authorization") String authorization);


  // get all customer
  @GET("api/customer")
  Call<CustomerInformationDataResponse> getAllCustomerInformation(@Header("Authorization") String authorization);










}
