package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.umeng.analytics.MobclickAgent;
import com.wlqq.huodi.R;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.task.CheckVehicleIsExistTask;
import com.wlqq.huodi.task.RegisterTask;
import com.wlqq.huodi.task.SMSSendTask;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.utils.CheckUtils;
import com.wlqq.huodi.utils.DeviceUtils;
import com.wlqq.huodi.utils.EditTextCheckUtils;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.LogUtils;
import com.wlqq.huodi.utils.SharedPreferencesUtil;
import com.wlqq.huodi.view.KeyBoard;
import com.wlqq.huodi.view.RegionSelector;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: xlw
 * Date: 14-11-17
 * Email: xlwplm@qq.com
 */
public class RegisterActivity extends BaseActivity {
    private String TAG = RegisterActivity.class.getSimpleName();

    private RegionSelector regionSelector;
    private EditText mPlateNumEditView;
    private GridView mGridView;
    private EditText driverNameEditText;//司机姓名
    private EditText idEditText;//身份证号码
    private ImageView truckType2Spinner;
    private String[] truckType2Array = null;
    private String[] truckLengthArray = null;
    private EditText truckType2EditText;
    private EditText lengthEditText;
    private EditText loadEditText;
    private EditText telEditText;
    private EditText verifyEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button btnSMS;
    private Button btnNext;
    private Button btnRegister;
    private Button btnUp;
    private LinearLayout bottomLayout;
    private LinearLayout userInfoLayout;
    private LinearLayout vehicleLayout;
    private TimeCount time;
    private Activity activity;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    String codeMsg = msg.getData().getString("messagecode");
                    verifyEditText.setText(codeMsg);
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        truckType2Array = getResources().getStringArray(R.array.truckType2Array);
        truckLengthArray = getResources().getStringArray(R.array.lengthArray);
        activity = this;
        time = new TimeCount(60000, 1000);

        registerSMSReceiver();

        String vehicleTpye = MobclickAgent.getConfigParams(this, "vehicleTpye");
        if (!vehicleTpye.isEmpty()) {
            truckType2Array = vehicleTpye.split(",");
        }
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.register;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.register;
    }

    @Override
    public void onBackPressed() {

        if (mGridView.getVisibility() == View.VISIBLE) {
            mGridView.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    private void registerSMSReceiver() {
        IntentFilter filter = new IntentFilter();

        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                for (Object obj : objs) {
                    byte[] pdu = (byte[]) obj;
                    SmsMessage sms = SmsMessage.createFromPdu(pdu);
                    String message = sms.getMessageBody();
                    String from = sms.getOriginatingAddress();
                    if (!TextUtils.isEmpty(from)) {
                        String code = patternCode(message);
                        if (!TextUtils.isEmpty(code)) {
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("messagecode", code);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                }
            }
        };
        registerReceiver(smsReceiver, filter);
    }

    @Override
    protected void setupView() {
        super.setupView();

        driverNameEditText = (EditText) findViewById(R.id.driverNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        mGridView = (GridView) findViewById(R.id.gvInput);
        mGridView.setVisibility(View.GONE);
        mPlateNumEditView = (EditText) findViewById(R.id.numberEditText);
        KeyBoard keyBoard = new KeyBoard();
        keyBoard.init(this, mPlateNumEditView, mGridView);
        idEditText = (EditText) findViewById(R.id.idEditText);
        truckType2Spinner = (ImageView) findViewById(R.id.truckType2Spinner);
        truckType2EditText = (EditText) findViewById(R.id.truckType2EditText);
        lengthEditText = (EditText) findViewById(R.id.truckLengthEditText);
        loadEditText = (EditText) findViewById(R.id.loadEditText);
        telEditText = (EditText) findViewById(R.id.telEditText);
        verifyEditText = (EditText) findViewById(R.id.codeEditText);
        regionSelector = (RegionSelector) findViewById(R.id.regionSelector);
        btnSMS = (Button) findViewById(R.id.btnSMS);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnUp = (Button) findViewById(R.id.btnUp);
        bottomLayout = (LinearLayout) findViewById(R.id.llBottom);
        vehicleLayout = (LinearLayout) findViewById(R.id.llVehicleInfo);
        userInfoLayout = (LinearLayout) findViewById(R.id.llUserInfo);
    }

    @Override
    protected void registerListeners() {
        super.registerListeners();

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = telEditText.getText().toString();
                String mobile = EditTextCheckUtils.replaceDBCWithSBC(text);
                if (mobile.length() == 11 && EditTextCheckUtils.isMobileNO(mobile)) {
                    getCodeMsg(mobile);
                } else {
                    Toast.makeText(RegisterActivity.this, "请正确填写手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoLayout.setVisibility(View.VISIBLE);
                vehicleLayout.setVisibility(View.GONE);
                bottomLayout.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInfo()) {
                    userInfoLayout.setVisibility(View.GONE);
                    vehicleLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                }
            }
        });
        idEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean b) {
                idEditText.setText(idEditText.getText().toString().toUpperCase());
            }
        });

        truckType2Spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog(R.string.truckLength, truckType2Array, truckType2EditText);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    register();
                }
            }
        });

        mPlateNumEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pln = mPlateNumEditView.getText().toString();
                if (StringUtils.isNotBlank(pln) && pln.length() == 7) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("pln", pln);

                    SharedPreferencesUtil.savePln(mPlateNumEditView.getText().toString());
                    new CheckVehicleIsExistTask(activity) {
                        @Override
                        protected void onSucceed(String aVoid) {
                            super.onSucceed(aVoid);
                            if (aVoid.equals("true")) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setTitle(R.string.tips);
                                builder.setMessage("该用车牌号已存在");
                                builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                                builder.setNegativeButton("修改车牌号", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
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
                    }.execute(new TaskParams(hashMap));
                }
            }
        });
    }

    private void register() {
        HashMap<String, Object> httpParams = new HashMap<String, Object>();
        httpParams.put("pln", mPlateNumEditView.getText().toString());
        httpParams.put("pwd", passwordEditText.getText().toString());
        httpParams.put("icn", idEditText.getText().toString());
        httpParams.put("boxStructure", truckType2EditText.getText().toString());
        httpParams.put("vl", lengthEditText.getText().toString().replace("米", ""));
        httpParams.put("vt", loadEditText.getText().toString());
        httpParams.put("m", telEditText.getText().toString());
        httpParams.put("pId", regionSelector.getPid());
        httpParams.put("cId", regionSelector.getCid());
        httpParams.put("countyId", regionSelector.getCntid());
        httpParams.put("contact", driverNameEditText.getText().toString());
        httpParams.put("code", verifyEditText.getText().toString());
        httpParams.put(HuoDiConstants.HTTP_PARAM_DEVICE_FINGERPRINT, DeviceUtils.getDeviceFingerprint());
        new RegisterTask(RegisterActivity.this) {
            @Override
            protected void onSucceed(Void aVoid) {
                super.onSucceed(aVoid);

                SavedCredential.getInstance().setAuthType(SavedCredential.AuthType.WULIUQQ);
                SavedCredential.getInstance().setPrincipal(mPlateNumEditView.getText().toString());
                SavedCredential.getInstance().setCredential(passwordEditText.getText().toString());


                startActivity(new Intent(activity, CompleteProfileActivity.class));
                finish();

            }
        }.execute(new TaskParams(httpParams));
    }


    private void getCodeMsg(String mobile) {
        Map<String, Object> httpParams = new HashMap<String, Object>();
        httpParams.put("mobile", mobile);
        new SMSSendTask(RegisterActivity.this) {
            @Override
            protected void onSucceed(Void aVoid) {
                super.onSucceed(aVoid);
                btnSMS.setClickable(false);
                time.start();
            }
        }.execute(new TaskParams(httpParams));
    }

    private void showChoiceDialog(int titleId, final String[] items, final EditText editText) {
        new AlertDialog.Builder(this)
                .setTitle(titleId)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText.setText(items[i]);
                    }
                })
                .show();
    }

    private boolean checkInfo() {


        if (StringUtils.isEmpty(mPlateNumEditView.getText().toString())) {
            Toast.makeText(this, R.string.truckNUmNull, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!EditTextCheckUtils.isTruckNO(mPlateNumEditView.getText().toString())) {
            Toast.makeText(this, R.string.truckNUmError, Toast.LENGTH_SHORT).show();
            return false;
        }

        final String mobileNum = EditTextCheckUtils.replaceDBCWithSBC(telEditText.getText().toString());
        if (StringUtils.isEmpty(mobileNum)) {
            Toast.makeText(this, R.string.mobileNumNull, Toast.LENGTH_LONG).show();
            return false;
        }
        if (!EditTextCheckUtils.isMobileNO(mobileNum)) {
            Toast.makeText(this, R.string.mobileNumError, Toast.LENGTH_LONG).show();
            return false;
        }


        if (verifyEditText.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return false;
        }

        String pwd = passwordEditText.getText().toString();
        if (pwd.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pwd.equals(confirmPasswordEditText.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "两次填写的密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (StringUtils.isEmpty(idEditText.getText().toString())) {
            Toast.makeText(this, R.string.IDNull, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CheckUtils.check(idEditText.getText().toString())) {
            Toast.makeText(this, R.string.IDNumError, Toast.LENGTH_SHORT).show();
            return false;
        }


        if (StringUtils.isEmpty(driverNameEditText.getText().toString())) {
            Toast.makeText(this, R.string.driverNameNull, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean check() {


        if (regionSelector.getCid() < 1000) {
            Toast.makeText(this, R.string.addressNull, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (StringUtils.isBlank(truckType2EditText.getText().toString())) {
            Toast.makeText(this, "请选择车型", Toast.LENGTH_LONG).show();
            return false;
        }

        if (StringUtils.isBlank(lengthEditText.getText().toString())) {
            Toast.makeText(this, "请填写车长", Toast.LENGTH_LONG).show();
            return false;
        }

        if (StringUtils.isBlank(loadEditText.getText().toString())) {
            Toast.makeText(this, "请填写吨位", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnSMS.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnSMS.setText("重新获取");
            btnSMS.setClickable(true);
        }

    }

    private String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent)) {
            return null;
        }
        String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


    protected String getServiceAPIUrl() {
        return HuoDiConstants.API_DRIVER_URL_LOGIN;
    }

}
