package in.menukart.menukart.api;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;

import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import in.menukart.menukart.R;
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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiClient {
    static ACProgressFlower dialog;

    private Context context;
    private Retrofit retrofit;
    private MenuKartServiceApi menuKartServiceApi;
    private String TAG = ApiClient.class.getSimpleName();
    private Gson gson;

    public ApiClient(Context context) {
        this.context = context;
        this.retrofit = MyApplication.getRetrofit();
        this.gson = new Gson();
    }

    public static boolean isConnectedToInternet(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void openAlertDialogWithPositive(Context context, String message, String positiveBtnText) {
        new iOSDialogBuilder(context)
//                .setTitle(context.getResources().getString(R.string.cricketer))
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setFont(Typeface.SANS_SERIF)
                .setCancelable(false)
                .setPositiveListener(context.getString(R.string.dialog_label_ok), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    public static void showProgressBar(Context context) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                // .text("Title is here")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }

    public static void hideProgressBar() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void restaurantList(final APIClientCallback<RestaurantList> apiClientCallback) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<RestaurantList> restaurantListCall = menuKartServiceApi.RESTAURANT_LIST();
            restaurantListCall.enqueue(new Callback<RestaurantList>() {
                @Override
                public void onResponse(Call<RestaurantList> call, Response<RestaurantList> response) {
                    RestaurantList restaurantList = response.body();
                    apiClientCallback.onSuccess(restaurantList);
                }

                @Override
                public void onFailure(Call<RestaurantList> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void menuList(final APIClientCallback<Menu> apiClientCallback, String menuListJson) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<Menu> restaurantListCall = menuKartServiceApi.MENU_LIST(menuListJson);
            restaurantListCall.enqueue(new Callback<Menu>() {
                @Override
                public void onResponse(Call<Menu> call, Response<Menu> response) {
                    Menu menu = response.body();
                    apiClientCallback.onSuccess(menu);
                }

                @Override
                public void onFailure(Call<Menu> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void privacyLinks(final APIClientCallback<Privacy> apiClientCallback) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<Privacy> restaurantListCall = menuKartServiceApi.PRIVACY_LINKS();
            restaurantListCall.enqueue(new Callback<Privacy>() {
                @Override
                public void onResponse(Call<Privacy> call, Response<Privacy> response) {
                    Privacy privacy = response.body();
                    apiClientCallback.onSuccess(privacy);
                }

                @Override
                public void onFailure(Call<Privacy> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void UserAddressList(final APIClientCallback<ManageAddress> apiClientCallback, String userAddressListJson) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ManageAddress> restaurantListCall = menuKartServiceApi.USER_ADDRESS_LIST(userAddressListJson);
            restaurantListCall.enqueue(new Callback<ManageAddress>() {
                @Override
                public void onResponse(Call<ManageAddress> call, Response<ManageAddress> response) {
                    ManageAddress manageAddress = response.body();
                    apiClientCallback.onSuccess(manageAddress);
                }

                @Override
                public void onFailure(Call<ManageAddress> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userAddAddressList(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> restaurantListCall = menuKartServiceApi.ADD_USER_ADDRESS(params);
            restaurantListCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userUpdateAddressList(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> restaurantListCall = menuKartServiceApi.UPDATE_USER_ADDRESS(params);
            restaurantListCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userUpdateProfile(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> restaurantListCall = menuKartServiceApi.UPDATE_USER_PROFILE(params);
            restaurantListCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void orderList(final APIClientCallback<OrderMenu> apiClientCallback, String orderListJson) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<OrderMenu> restaurantListCall = menuKartServiceApi.ORDER_LIST(orderListJson);
            restaurantListCall.enqueue(new Callback<OrderMenu>() {
                @Override
                public void onResponse(Call<OrderMenu> call, Response<OrderMenu> response) {
                    OrderMenu orderMenu = response.body();
                    apiClientCallback.onSuccess(orderMenu);
                }

                @Override
                public void onFailure(Call<OrderMenu> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userSendOtp(final APIClientCallback<SendOtp> apiClientCallback, Map<String, String> sendOtpParams) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<SendOtp> sendOtpCall = menuKartServiceApi.SEND_OTP(sendOtpParams);
            sendOtpCall.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                    SendOtp sendOtp = response.body();
                    apiClientCallback.onSuccess(sendOtp);
                }

                @Override
                public void onFailure(Call<SendOtp> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userResendOtp(final APIClientCallback<ResendOtp> apiClientCallback, Map<String, String> resendOtpParams) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResendOtp> sendOtpCall = menuKartServiceApi.RESEND_OTP(resendOtpParams);
            sendOtpCall.enqueue(new Callback<ResendOtp>() {
                @Override
                public void onResponse(Call<ResendOtp> call, Response<ResendOtp> response) {
                    ResendOtp resendOtp = response.body();
                    apiClientCallback.onSuccess(resendOtp);
                }

                @Override
                public void onFailure(Call<ResendOtp> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userVerifyOtp(final APIClientCallback<VerifyOtp> apiClientCallback, String orderListJson) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<VerifyOtp> verifyOtpCall = menuKartServiceApi.VERIFY_OTP(orderListJson);
            verifyOtpCall.enqueue(new Callback<VerifyOtp>() {
                @Override
                public void onResponse(Call<VerifyOtp> call, Response<VerifyOtp> response) {
                    VerifyOtp verifyOtp = response.body();
                    apiClientCallback.onSuccess(verifyOtp);
                }

                @Override
                public void onFailure(Call<VerifyOtp> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userSignUp(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> sendOtpParams) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> responseSuccessCall = menuKartServiceApi.USER_SIGN_UP(sendOtpParams);
            responseSuccessCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userCheckOrderStatus(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> responseSuccessCall = menuKartServiceApi.USER_CHECK_ORDER_STATUS(params);
            responseSuccessCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userUpdateOrderStatus(final APIClientCallback<ResponseSuccess> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<ResponseSuccess> responseSuccessCall = menuKartServiceApi.USER_UPDATE_ORDER_STATUS(params);
            responseSuccessCall.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess responseSuccess = response.body();
                    apiClientCallback.onSuccess(responseSuccess);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }

    public void userSaveOrder(final APIClientCallback<SaveOrder> apiClientCallback, Map<String, String> params) {
        if (!isConnectedToInternet(context)) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_check_network)));
        } else {
            menuKartServiceApi = retrofit.create(MenuKartServiceApi.class);
            Call<SaveOrder> saveOrderCall = menuKartServiceApi.USER_SAVE_ORDER(params);
            saveOrderCall.enqueue(new Callback<SaveOrder>() {
                @Override
                public void onResponse(Call<SaveOrder> call, Response<SaveOrder> response) {
                    SaveOrder saveOrder = response.body();
                    apiClientCallback.onSuccess(saveOrder);
                }

                @Override
                public void onFailure(Call<SaveOrder> call, Throwable t) {
                    apiClientCallback.onFailure(new NetworkException(context.getString(R.string.error_invalid_response)));
                }
            });
        }
    }
}