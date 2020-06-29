package com.cscodetech.freshfastfooddeliveryboy.retrofit;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {


    @POST(APIClient.APPEND_URL + "login.php")
    Call<JsonObject> getLogin(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "status.php")
    Call<JsonObject> getStatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "order_status.php")
    Call<JsonObject> orderStatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "olist.php")
    Call<JsonObject> getOlist(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "complete.php")
    Call<JsonObject> getComplete(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "noti.php")
    Call<JsonObject> getNoti(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "ostatus.php")
    Call<JsonObject> getOstatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "area.php")
    Call<JsonObject> getArea(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "profile.php")
    Call<JsonObject> UpdateProfile(@Body JsonObject object);

}
