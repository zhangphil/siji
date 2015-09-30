package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.LocationHolder;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.handler.AccessDeniedHandler;
import com.wlqq.huodi.task.AutoSignInTask;
import com.wlqq.huodi.task.DownloadHostsTask;
import com.wlqq.huodi.task.TaskListener;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.task.TaskResult;
import com.wlqq.huodi.utils.HuoDiConstants;

import java.util.HashMap;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public class SplashActivity extends Activity {

    private AlphaAnimation startAnimation;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.splash, null);
        UmengUpdateAgent.setUpdateCheckConfig(false);
        MobclickAgent.updateOnlineConfig(this);
        setContentView(view);
        new DownloadHostsTask().execute(getString(R.string.host_file_url), getString(R.string.host_file_name));
        initView();
    }

    private void initView() {
        startAnimation = new AlphaAnimation(0.3f, 1.0f);
        startAnimation.setDuration(2000);
        view.startAnimation(startAnimation);
        startAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                autoSignIn();
            }
        });
    }

    private void redirectTo() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }


    protected String getServiceAPIUrl() {
        return HuoDiConstants.API_DRIVER_URL_LOGIN;
    }

    protected void autoSignIn() {

        // auto sign in if the user has already signed in before
        final SavedCredential savedCredential = SavedCredential.getInstance();
        if (savedCredential.isNotNull()) {

            new AutoSignInTask(this) {
                @Override
                protected String getRemoteServiceAPIUrl() {
                    return getServiceAPIUrl();
                }

                @Override
                protected void onSucceed(LoginResponse loginResponse) {
                    super.onSucceed(loginResponse);
                    AuthenticationHolder.setLogin(true);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    long locationId = LocationHolder.getUSER_SELECTED_REGION().getId();
                    long regionId;
                    if (locationId > 0) {
                        regionId = locationId;
                    } else {
                        regionId = AuthenticationHolder.getProfile().getCityId();
                    }


                }
            }.registerExceptionHandler(ErrorCode.ACCESS_DENIED, AccessDeniedHandler.getInstance()).

                    setListener(new TaskListener.TaskListenerAdapter<LoginResponse>(this) {
                        @Override
                        public void onSucceed(LoginResponse object) {
                            super.onSucceed(object);


                            Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
                            Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();

                            final UserProfile profile = object.getProfile();
                            final Session session = object.getSession();

                            AuthenticationHolder.setProfile(profile);
                            AuthenticationHolder.setSession(session);

//                                        goToHomeActivity();
                            finish();
                        }

                        @Override
                        public void onError(TaskResult.Status status) {
                            super.onError(status);

                            gotoLoginScreen();
                        }

                        @Override
                        public void onError(ErrorCode errorCode) {
                            super.onError(errorCode);
                            gotoLoginScreen();
                        }
                    }
                    ).

                    execute(new TaskParams(new HashMap<String, Object>()

                    ));
        } else {
            gotoLoginScreen();
        }
    }

    protected void gotoLoginScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

}
