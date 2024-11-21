package com.crrescita.employeetracker.activity.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.crrescita.employeetracker.activity.profile_menu.SupportUsActivity;
import com.crrescita.tel.R;
import com.crrescita.employeetracker.activity.LoginActivity;
import com.crrescita.employeetracker.activity.profile_menu.ChangePasswordProfileActivity;
import com.crrescita.employeetracker.activity.profile_menu.ContactUsActivity;
import com.crrescita.employeetracker.activity.profile_menu.UpdateProfileActivity;
import com.crrescita.employeetracker.activity.profile_menu.attendence_report.AttendenceReportActivity;
import com.bumptech.glide.Glide;
import com.securepreferences.SecurePreferences;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.AppConstant;
import utils.SingletonHelperGlobal;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView userName;
    private TextView userEmailId;
    private CircleImageView user_image;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    private CardView cardViewEditProfile;
    private CardView cardViewChangePassword;
    private CardView cardViewAttendenceReport;
    private CardView cardViewContactUs;
    private CardView cardViewLogout;
    private CardView cardViewSupportUs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        userName = view.findViewById(R.id.profile_name);
        userEmailId = view.findViewById(R.id.profile_email);
        user_image = view.findViewById(R.id.user_image);



        cardViewEditProfile = view.findViewById(R.id.cardViewEditProfile);
        cardViewEditProfile.setOnClickListener(this);

        cardViewChangePassword = view.findViewById(R.id.cardViewChangePassword);
        cardViewChangePassword.setOnClickListener(this);

        cardViewAttendenceReport = view.findViewById(R.id.cardViewAttendenceReport);
        cardViewAttendenceReport.setOnClickListener(this);

        cardViewContactUs = view.findViewById(R.id.cardViewContactUs);
        cardViewContactUs.setOnClickListener(this);

        cardViewLogout = view.findViewById(R.id.cardViewLogout);
        cardViewLogout.setOnClickListener(this);

        cardViewSupportUs = view.findViewById(R.id.cardViewSupportUs);
        cardViewSupportUs.setOnClickListener(this);




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(prefsMain.getString(AppConstant.USER_IMAGE,""))
                .into(user_image);

        userName.setText(prefsMain.getString(AppConstant.EMPLOYEE_NAME, "----------NA-------"));
        userEmailId.setText(prefsMain.getString(AppConstant.EMPLOYEE_EMAIL_ID, "-----NA----"));
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void logout() {
        prefsEditor.clear().apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardViewEditProfile:
                Intent intent3 = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(intent3);
                break;

            case R.id.cardViewChangePassword:
                Intent intent = new Intent(getActivity(), ChangePasswordProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.cardViewAttendenceReport:
                Intent intent4 = new Intent(getActivity(), AttendenceReportActivity.class);
                startActivity(intent4);
                break;

            case R.id.cardViewContactUs:
                Intent intent2 = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent2);
                break;


            case R.id.cardViewSupportUs:
                Intent intent6 = new Intent(getActivity(), SupportUsActivity.class);
                startActivity(intent6);
                break;

            case R.id.cardViewLogout:
                showLogoutConfirmationDialog();
                break;
            default:
                break;
        }
    }
}
