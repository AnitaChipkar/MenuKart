package in.menukart.menukart.api;

import java.util.Map;

import in.menukart.menukart.entities.explore.RestaurantList;
import in.menukart.menukart.entities.foodcart.ResendOtp;
import in.menukart.menukart.entities.foodcart.SaveOrder;
import in.menukart.menukart.entities.foodcart.SendOtp;
import in.menukart.menukart.entities.foodcart.VerifyOtp;
import in.menukart.menukart.entities.order.Menu;
import in.menukart.menukart.entities.order.OrderMenu;
import in.menukart.menukart.entities.setting.ManageAddress;
import in.menukart.menukart.entities.setting.Privacy;
import in.menukart.menukart.entities.setting.ResponseSuccess;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MenuKartServiceApi {

    @Headers("Content-Type: application/json")
    @GET("/get_restaurant_list")
    Call<RestaurantList> RESTAURANT_LIST();

    @Headers("Content-Type: application/json")
    @GET("/mapped_restaurant_menus?")
    Call<Menu> MENU_LIST(@Query("restaurant_id") String restaurantId);

    @Headers("Content-Type: application/json")
    @GET("/weblinks")
    Call<Privacy> PRIVACY_LINKS();

    @Headers("Content-Type: application/json")
    @GET("/address?")
    Call<ManageAddress> USER_ADDRESS_LIST(@Query("user_id") String restaurantId);

    // @Headers("Content-Type: application/json")
    @POST("/add-address")
    @FormUrlEncoded
    Call<ResponseSuccess> ADD_USER_ADDRESS(@FieldMap Map<String, String> params);

    @POST("/update-address")
    @FormUrlEncoded
    Call<ResponseSuccess> UPDATE_USER_ADDRESS(@FieldMap Map<String, String> params);

    @POST("/update_profile")
    @FormUrlEncoded
    Call<ResponseSuccess> UPDATE_USER_PROFILE(@FieldMap Map<String, String> params);

    @Headers("Content-Type: application/json")
    @GET("/orders?")
    Call<OrderMenu> ORDER_LIST(@Query("user_id") String userId);

    @POST("/send_otp")
    @FormUrlEncoded
    Call<SendOtp> SEND_OTP(@FieldMap Map<String, String> params);

    @POST("/resend_otp")
    @FormUrlEncoded
    Call<ResendOtp> RESEND_OTP(@FieldMap Map<String, String> params);

    @Headers("Content-Type: application/json")
    @GET("/verify_otp?")
    Call<VerifyOtp> VERIFY_OTP(@Query("mobileno") String mobileNo);

    @POST("/sign-up")
    @FormUrlEncoded
    Call<ResponseSuccess> USER_SIGN_UP(@FieldMap Map<String, String> params);

    @POST("/check-order-status")
    @FormUrlEncoded
    Call<ResponseSuccess> USER_CHECK_ORDER_STATUS(@FieldMap Map<String, String> params);

    @POST("/update-order-status")
    @FormUrlEncoded
    Call<ResponseSuccess> USER_UPDATE_ORDER_STATUS(@FieldMap Map<String, String> params);


    @POST("/save-order")
    @FormUrlEncoded
    Call<SaveOrder> USER_SAVE_ORDER(@FieldMap Map<String, String> params);


}
