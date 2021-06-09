package in.menukart.menukart.view.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;

import in.menukart.menukart.R;
import in.menukart.menukart.entities.foodcart.UserDetails;
import in.menukart.menukart.util.AppConstants;
import in.menukart.menukart.view.other.MainActivity;
import in.menukart.menukart.view.setting.manageaddress.ManageAddressActivity;
import in.menukart.menukart.view.setting.privacypolicy.PrivacyActivity;
import in.menukart.menukart.view.setting.userprofile.UpdateProfileActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlManageAddress, rlTermsConditions,
            rlPrivacyPolicy, rlCancellationPolicy,
            rlHelpSupport, rlLogOut;
    AppCompatImageView imgUpdateProfile;
    AppCompatTextView textProfileName, textProfileEmail, textProfileContact, textLoginFirst;
    View root;
    UserDetails userDetails;
    Context context;
    CardView cardViewProfile, cardViewSettings;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        initSettingViews();
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void initSettingViews() {
        textLoginFirst = root.findViewById(R.id.tv_login_first);
        cardViewProfile = root.findViewById(R.id.card_view_profile);
        cardViewSettings = root.findViewById(R.id.card_view_settings);
        textProfileName = root.findViewById(R.id.tv_profile_name);
        textProfileEmail = root.findViewById(R.id.tv_profile_email);
        textProfileContact = root.findViewById(R.id.tv_profile_contact);

        rlManageAddress = root.findViewById(R.id.rl_manage_address);
        rlTermsConditions = root.findViewById(R.id.rl_terms_conditions);
        rlPrivacyPolicy = root.findViewById(R.id.rl_privacy_policy);
        rlCancellationPolicy = root.findViewById(R.id.rl_cancellation_policy);
        rlHelpSupport = root.findViewById(R.id.rl_help_support);
        rlLogOut = root.findViewById(R.id.rl_logout);
        imgUpdateProfile = root.findViewById(R.id.iv_update_user_profile);

        rlManageAddress.setOnClickListener(this);
        rlTermsConditions.setOnClickListener(this);
        rlPrivacyPolicy.setOnClickListener(this);
        rlCancellationPolicy.setOnClickListener(this);
        rlHelpSupport.setOnClickListener(this);
        rlLogOut.setOnClickListener(this);
        imgUpdateProfile.setOnClickListener(this);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(AppConstants.USER_DETAILS, null);
        userDetails = gson.fromJson(json, UserDetails.class);

        if (userDetails != null) {
            textLoginFirst.setVisibility(View.GONE);
            cardViewProfile.setVisibility(View.VISIBLE);
            cardViewSettings.setVisibility(View.VISIBLE);
            textProfileName.setText(userDetails.getFname() + " " + userDetails.getLname());
            textProfileEmail.setText("Email id : " + userDetails.getEmail());
            textProfileContact.setText("Mobile No : " + userDetails.getMobileno());

        } else {
            textLoginFirst.setVisibility(View.VISIBLE);
            cardViewProfile.setVisibility(View.GONE);
            cardViewSettings.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_manage_address:
                Intent intentManage = new Intent(getActivity(), ManageAddressActivity.class);
                startActivity(intentManage);
                break;
            case R.id.rl_terms_conditions:
                Intent intentTerms = new Intent(getActivity(), PrivacyActivity.class);
                intentTerms.putExtra(AppConstants.TERMS_CONDITIONS, "terms_conditions");
                startActivity(intentTerms);
                break;
            case R.id.rl_privacy_policy:
                Intent intentPrivacy = new Intent(getActivity(), PrivacyActivity.class);
                intentPrivacy.putExtra(AppConstants.PRIVACY_POLICY, "privacy_policy");
                startActivity(intentPrivacy);
                break;
            case R.id.rl_cancellation_policy:
                Intent intentCancellation = new Intent(getActivity(), PrivacyActivity.class);
                intentCancellation.putExtra(AppConstants.CANCELLATION_POLICY, "cancellation_policy");
                startActivity(intentCancellation);
                break;
            case R.id.rl_help_support:
                Intent intentSupport = new Intent(getActivity(), SupportActivity.class);
                startActivity(intentSupport);
                break;
            case R.id.rl_logout:
                openDialogLogOut();
                break;
            case R.id.iv_update_user_profile:
                Intent intentUpdateProfile = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(intentUpdateProfile);
                break;

        }
    }

    private void openDialogLogOut() {
        new iOSDialogBuilder(context)
                .setTitle(context.getResources().getString(R.string.text_log_out))
                .setSubtitle(context.getResources().getString(R.string.text_ask_for_log_out))
                .setBoldPositiveLabel(true)
                .setFont(Typeface.SANS_SERIF)
                .setCancelable(false)
                .setPositiveListener(context.getString(R.string.text_log_out_yes), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeListener(context.getString(R.string.text_log_out_no), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }
}