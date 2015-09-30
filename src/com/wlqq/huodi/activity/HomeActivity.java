package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.calculator.activity.VehicleBaseInfoActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.AddressComponent;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.handler.CloseApplicationKeyEventHandler;
import com.wlqq.huodi.locate.WRequestLocationTask;
import com.wlqq.huodi.task.*;
import com.wlqq.huodi.utils.AlarmManagerUtil;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends FinalActivity {

    private static final int OPTIONS_MENU_ID_FEEDBACK = 0;
    private static final int OPTIONS_MENU_ID_LOGOUT = 1;
    private static final int OPTIONS_MENU_ID_CHECK_UPDATE = 2;
    private static final int OPTIONS_MENU_ID_EXIT = 3;

    private static final int PUBLISH_VEHICLE_EMPTY = 0;  //  status=0 空车
    private static final int PUBLISH_VEHICLE_FULL = 1;   //  status=1 载货

    @ViewInject(id = R.id.rlMileage)
    RelativeLayout mMileageLayout;

    @ViewInject(id = R.id.rlNearby)
    RelativeLayout mNearbyLayout;

    @ViewInject(id = R.id.rlCalculate)
    RelativeLayout mCalculateLayout;

    @ViewInject(id = R.id.rlBooking)
    RelativeLayout mBookingLayout;

    @ViewInject(id = R.id.rlAbout)
    RelativeLayout mAboutLayout;

    @ViewInject(id = R.id.layout_published_vehicle)
    RelativeLayout publishedLayout;

    @ViewInject(id = R.id.layout_publish_vehicle)
    RelativeLayout publishLayout;

    private Activity activity;

    private String vehicleStatus = "";
    private String isAutiud = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        activity = this;
//		UmengUpdateAgent.setUpdateAutoPopup(true);
        UmengUpdateAgent.update(activity);
        registerListener();

        FeedbackAgent agent = new FeedbackAgent(activity);
        agent.sync();

        loadConfig();
    }

    private void loadConfig() {
        String startTime = MobclickAgent.getConfigParams(this, "startTime");
        if (!startTime.isEmpty()) {
            AuthenticationHolder.setLocationStartTime(Integer.parseInt(startTime));
        }

        String stopTime = MobclickAgent.getConfigParams(this, "stopTime");
        if (!stopTime.isEmpty()) {
            AuthenticationHolder.setLocationStopTime(Integer.parseInt(stopTime));
        }

        String locationEmptyInterval = MobclickAgent.getConfigParams(this, "locationEmptyInterval");
        if (!locationEmptyInterval.isEmpty()) {
            AuthenticationHolder.setLocationEmptyInterval(Long.parseLong(locationEmptyInterval));
        }

        String locationFullInterval = MobclickAgent.getConfigParams(this, "locationFullInterval");
        if (!locationFullInterval.isEmpty()) {
            AuthenticationHolder.setLocationFullInterval(Long.parseLong(locationFullInterval));
        }
    }

    private void registerListener() {

        mMileageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, MapRouteActivity.class));
            }
        });

        mNearbyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, NearbyActivity.class));
            }
        });

        mCalculateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, VehicleBaseInfoActivity.class));
            }
        });

        mBookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkLogin()) {
                    return;
                }

                if (isAutiud.equals("1")) {
                    if (vehicleStatus.equals("0")) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(R.string.tips);
                        builder.setMessage("您当前是空车配货状态，确定修改为预约货状态后，空车配货状态将被取消,是否确定修改？");
                        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton("确定修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                publishedLayout.setVisibility(View.GONE);
                                publishLayout.setVisibility(View.VISIBLE);
                                startActivity(new Intent(activity, BookingActivity.class));
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        try {
                            dialog.show();
                        } catch (WindowManager.BadTokenException e) {
                            LogUtils.e("", e.toString());
                        }
                    } else {
                        startActivity(new Intent(activity, BookingActivity.class));
                    }
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(R.string.tips);
                    builder.setMessage("您当前的账户状态为未审核状态，暂时不能使用该功能。是否进行完善资料？");
                    builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            startActivity(new Intent(activity, CompleteProfileActivity.class));
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    try {
                        dialog.show();
                    } catch (WindowManager.BadTokenException e) {
                        LogUtils.e("", e.toString());
                    }


                }
            }
        });

        mAboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, AboutActivity.class));
            }
        });

        publishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin()) {
                    publishVehicle(PUBLISH_VEHICLE_EMPTY);
                }

            }
        });

        publishedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin()) {
                    publishVehicle(PUBLISH_VEHICLE_FULL);
                }
            }
        });
    }

    private void publishVehicle(final int status) {

        if (isAutiud.equals("1")) {
            if (vehicleStatus.equals("2")) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.tips);
                builder.setMessage("您当前是预约状态，确定修改为就地配货状态后，之前预约的路线将被取消,是否确定修改？");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("确定修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        changeVehicleStatus(status);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                try {
                    dialog.show();
                } catch (WindowManager.BadTokenException e) {
                    LogUtils.e("", e.toString());
                }
            } else {
                changeVehicleStatus(status);
            }
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.tips);
            builder.setMessage("您当前的账户状态为未审核状态，暂时不能使用该功能。是否进行完善资料？");
            builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    startActivity(new Intent(activity, CompleteProfileActivity.class));
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            try {
                dialog.show();
            } catch (WindowManager.BadTokenException e) {
                LogUtils.e("", e.toString());
            }

        }
    }

    private void changeVehicleStatus(final int status) {
        HashMap<String, Object> httpParams = new HashMap<String, Object>();
        httpParams.put("status", status);
        AddressComponent location = LocationHolder.getLOCATION();
        if (location != null) {
            httpParams.put("lat", location.getLatitude());
            httpParams.put("lng", location.getLongitude());
            httpParams.put("address", location.getFormattedAddress());

            new UpdateVehicleStatusTask(HomeActivity.this) {
                @Override
                protected void onSucceed(Void aVoid) {
                    super.onSucceed(aVoid);

                    if (status == PUBLISH_VEHICLE_FULL) {
                        publishedLayout.setVisibility(View.GONE);
                        publishLayout.setVisibility(View.VISIBLE);
                        AuthenticationHolder.setEmpty(false);
                        vehicleStatus = "1";
                    } else {
                        publishLayout.setVisibility(View.GONE);
                        publishedLayout.setVisibility(View.VISIBLE);
                        AuthenticationHolder.setEmpty(true);
                        vehicleStatus = "0";
                    }

                    AlarmManagerUtil.sendLocationBroadcast(getApplicationContext());
                }
            }.execute(new TaskParams(httpParams));
        } else {
            location();
            Toast.makeText(activity, "正在重新定位，请稍后再试", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item;

        item = menu.add(0, OPTIONS_MENU_ID_FEEDBACK, 0, getString(R.string.options_menu_feedback));
        item.setIcon(android.R.drawable.ic_menu_preferences);
        if (AuthenticationHolder.isLogin()) {
            item = menu.add(0, OPTIONS_MENU_ID_LOGOUT, 0, getString(R.string.options_menu_logout));
            item.setIcon(android.R.drawable.ic_menu_revert);
        } else {
            item = menu.add(0, OPTIONS_MENU_ID_LOGOUT, 0, getString(R.string.options_menu_login));
            item.setIcon(android.R.drawable.ic_menu_revert);
        }

        item = menu.add(0, OPTIONS_MENU_ID_CHECK_UPDATE, 0, getString(R.string.options_menu_checkupdate));
        item.setIcon(android.R.drawable.ic_menu_info_details);

        item = menu.add(0, OPTIONS_MENU_ID_EXIT, 0, getString(R.string.options_menu_exit));
        item.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case OPTIONS_MENU_ID_FEEDBACK:
                FeedbackAgent agent = new FeedbackAgent(this);
                agent.startFeedbackActivity();

                UserInfo info = agent.getUserInfo();
                if (info == null)
                    info = new UserInfo();
                Map<String, String> contact = info.getContact();
                if (contact == null)
                    contact = new HashMap<String, String>();
                final UserProfile profile = AuthenticationHolder.getProfile();
                contact.put("电话", profile.getMobile());
                info.setContact(contact);
                agent.setUserInfo(info);

                return true;
            case OPTIONS_MENU_ID_LOGOUT:
                final Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Preferences.set(HuoDiConstants.PREF_ACTIVATED, false);
                AuthenticationHolder.setProfile(null);
                AuthenticationHolder.setSession(null);
                finish();
                return true;
            case OPTIONS_MENU_ID_CHECK_UPDATE:
                UmengUpdateAgent.setUpdateCheckConfig(false);
                // 如果想程序启动时自动检查是否需要更新， 把下面两行代码加在Activity 的onCreate()函数里。
                UmengUpdateAgent.setUpdateOnlyWifi(false); // 目前我们默认在Wi-Fi接入情况下才进行自动提醒。如需要在其他网络环境下进行更新自动提醒，则请添加该行代码
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.update(activity);
                UmengUpdateAgent.setUpdateListener(updateListener);


                return true;
            case OPTIONS_MENU_ID_EXIT:

                Intent back2HomeScreen = new Intent(Intent.ACTION_MAIN);
                back2HomeScreen.addCategory(Intent.CATEGORY_HOME);
                startActivity(back2HomeScreen);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    UmengUpdateListener updateListener = new UmengUpdateListener() {
        @Override
        public void onUpdateReturned(int updateStatus,
                                     UpdateResponse updateInfo) {
            switch (updateStatus) {
                case 0: // has update
                    UmengUpdateAgent.showUpdateDialog(activity, updateInfo);
                    break;
                case 1: // has no update
                    Toast.makeText(HuoDiApplication.getContext(), "没有更新", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 2: // none wifi
                    Toast.makeText(HuoDiApplication.getContext(), "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 3: // time out
                    Toast.makeText(HuoDiApplication.getContext(), "超时", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        final AddressComponent gps_located_address = LocationHolder.getLOCATION();
        if (gps_located_address == null || LocationHolder.getLOCATION() == null) {
            location();
        }

        if (AuthenticationHolder.isLogin()) {
            new CheckVehicleStatusTask(activity) {
                @Override
                protected void onSucceed(String s) {
                    super.onSucceed(s);
                    vehicleStatus = s;
                    if (s.equals("0")) {
                        AuthenticationHolder.setEmpty(true);
                        publishLayout.setVisibility(View.GONE);
                        publishedLayout.setVisibility(View.VISIBLE);
                    } else if (s.equals("1")) {
                        AuthenticationHolder.setEmpty(false);
                        publishedLayout.setVisibility(View.GONE);
                        publishLayout.setVisibility(View.VISIBLE);
                    }
                }
            }.execute(new TaskParams(new HashMap<String, Object>()));

            new CheckUserAuditedStatusTask(activity) {
                @Override
                protected void onSucceed(String s) {
                    super.onSucceed(s);
                    isAutiud = s;
                }
            }.execute(new TaskParams(new HashMap<String, Object>()));
        }
    }

    private void location() {
        new WRequestLocationTask(true) {
            @Override
            public void succeed() {
                super.succeed();
                AddressComponent location = LocationHolder.getLOCATION();
                if (location != null) {
                    HashMap<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("lat", location.getLatitude());
                    paramMap.put("lng", location.getLongitude());
                    paramMap.put("address", location.getFormattedAddress());

                    new SubmitLocationTask().execute(new TaskParams(paramMap));
                }
            }
        }.execute();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return new CloseApplicationKeyEventHandler(this).onKeyDown(event) || super.onKeyDown(keyCode, event);
    }

    private boolean checkLogin() {
        if (!AuthenticationHolder.isLogin()) {
            Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            return false;
        }
        return true;
    }

}
