package com.wlqq.huodi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.app.Preferences;
import com.wlqq.huodi.bean.LoginResponse;
import com.wlqq.huodi.bean.Session;
import com.wlqq.huodi.bean.UserProfile;
import com.wlqq.huodi.data.AuthenticationHolder;
import com.wlqq.huodi.data.Constants;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.handler.AccessDeniedHandler;
import com.wlqq.huodi.task.AutoSignInTask;
import com.wlqq.huodi.task.TaskListener;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.task.TaskResult;
import com.wlqq.huodi.task.UploadAllPictureTask;
import com.wlqq.huodi.utils.AlarmManagerUtil;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.ImageUtils;
import com.wlqq.huodi.utils.LogUtils;
import com.wlqq.huodi.utils.PictureUtil;
import com.wlqq.huodi.utils.SharedPreferencesUtil;
import com.wlqq.huodi.utils.SystemDefinedUploadFileType;
import com.wlqq.huodi.task.UploadPictureTask;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xlw
 * Date: 14-11-20
 * Email: xlwplm@qq.com
 */
public class CompleteProfileActivity extends BaseActivity {

	private static final int CUT_PHOTO_REQUEST_CODE = 100000;
	private static final String TAG = CompleteProfileActivity.class.getSimpleName();
	private boolean viewImage = false;

	private SystemDefinedUploadFileType currentSystemDefinedUploadFileType;

	private ImageView imageViewIdCard;
	private ImageView btnViewIdCard;
	private ImageView btnPhotoIdCard;

	private ImageView imageViewDriver;
	private ImageView btnViewDriver;
	private ImageView btnPhotoDriver;

	private ImageView imageViewVehicle;
	private ImageView btnViewVehicle;
	private ImageView btnPhotoVehicle;

	private ImageView imageViewVehicleBehind;
	private ImageView btnViewVehicleBack;
	private ImageView btnPhotoVehicleBehind;

	private Button mSubmitButton;
	private Activity activity;

	private static boolean idCardSuccess = false;
	private static boolean headerSuccess = false;
	private static boolean vehicleSideSuccess = false;
	private static boolean vehicleBehindSuccess = false;

	private static boolean idCardSelect = false;
	private static boolean headerSelect = false;
	private static boolean vehicleSideSelect = false;
	private static boolean vehicleBehindSelect = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
	}

	@Override
	protected int getTitleResourceId() {
		return R.string.completeProfile;
	}

	@Override
	protected int getContentViewLayout() {
		return R.layout.complete_profile;
	}

	@Override
	protected void setupView() {
		super.setupView();
		imageViewIdCard = (ImageView) findViewById(R.id.imageView1);
		btnViewIdCard = (ImageView) findViewById(R.id.btnView1);
		btnPhotoIdCard = (ImageView) findViewById(R.id.btnPhoto1);

		imageViewDriver = (ImageView) findViewById(R.id.imageView2);
		btnViewDriver = (ImageView) findViewById(R.id.btnView2);
		btnPhotoDriver = (ImageView) findViewById(R.id.btnPhoto2);

		imageViewVehicle = (ImageView) findViewById(R.id.imageView3);
		btnViewVehicle = (ImageView) findViewById(R.id.btnView3);
		btnPhotoVehicle = (ImageView) findViewById(R.id.btnPhoto3);
		mSubmitButton = (Button) findViewById(R.id.btnSubmit);

		imageViewVehicleBehind = (ImageView) findViewById(R.id.imageView4);
		btnViewVehicleBack = (ImageView) findViewById(R.id.btnView4);
		btnPhotoVehicleBehind = (ImageView) findViewById(R.id.btnPhoto4);

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == CUT_PHOTO_REQUEST_CODE) {

				setupImageView(currentSystemDefinedUploadFileType, new File(Constants.CAMERA_DIR.concat(currentSystemDefinedUploadFileType.getRemoteFilePath())));


				final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(R.string.tips);
				builder.setMessage("是否立即保存照片");
				builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
				builder.setNegativeButton("立即保存", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();


						List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
						SystemDefinedUploadFileType type = currentSystemDefinedUploadFileType;
						final Map<String, Object> params = new HashMap<String, Object>();
						params.put("type", type.name());
						final File file = new File(Constants.CAMERA_DIR + type.getRemoteFilePath());
						params.put("file", file);
						params.put("pln", SharedPreferencesUtil.getPln());
						paramsList.add(params);
						Map<String, Object>[] map = new HashMap[0];
						new UploadPictureTask(CompleteProfileActivity.this, createProgressDialog(activity)) {

							@Override
							protected void onSucceed(TaskResult voidTaskResult) {
								super.onSucceed(voidTaskResult);
								if (currentSystemDefinedUploadFileType.equals(SystemDefinedUploadFileType.IDENTITY_CARD)) {
									idCardSuccess = true;
								} else if (currentSystemDefinedUploadFileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE)) {
									vehicleSideSuccess = true;
								} else if (currentSystemDefinedUploadFileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND)) {
									vehicleBehindSuccess = true;
								} else if (currentSystemDefinedUploadFileType.equals(SystemDefinedUploadFileType.FIGURE)) {
									headerSuccess = true;
								}

//								try {
//									if (file.exists()) {
//										FileUtils.forceDelete(file);
//									}
//								} catch (Exception e) {
//									LogUtils.e(TAG, e.toString());
//								}

							}
						}.execute(paramsList.toArray(map));


					}
				});
				AlertDialog dialog = builder.create();

				try {
					dialog.show();
				} catch (WindowManager.BadTokenException e) {
					LogUtils.e("", e.toString());
				}


			} else {

				currentSystemDefinedUploadFileType = SystemDefinedUploadFileType.values()[requestCode];

				final File file = new File(Constants.CAMERA_DIR.concat(currentSystemDefinedUploadFileType.getRemoteFilePath()));
				LogUtils.i("SelectImageActivity", Constants.CAMERA_DIR.concat(currentSystemDefinedUploadFileType.getRemoteFilePath()));
				boolean fileFound = true;
				if (data != null) {
					Uri selectedImageUri = data.getData();
					if (selectedImageUri != null) { // browse
						try {
							File parentFile = file.getParentFile();
							if (!parentFile.exists()) {
								parentFile.mkdirs();
							}
							if (!file.exists()) {
								file.createNewFile();
							}
							IOUtils.copy(getContentResolver().openInputStream(selectedImageUri), new FileOutputStream(file));
						} catch (FileNotFoundException e) {
							fileFound = false;
							Log.e(TAG, "failed to : " + e.toString());
						} catch (IOException e) {
							Log.e(TAG, "failed to : " + e.toString());
						} finally {
						}
					}
				}
				final boolean finalFileFound = fileFound;
				new AsyncTask() {
					Dialog progressDialog;

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						progressDialog = new Dialog(activity, R.style.progress_dialog);
						View progressView = LayoutInflater.from(activity).inflate(R.layout.progressdialog, null);
						TextView msgTextView = (TextView) progressView.findViewById(R.id.message);
						msgTextView.setText("图片压缩中...");
						progressDialog.setContentView(progressView);
						progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            progressDialog.setCancelable(false);
						try {
							progressDialog.show();
						} catch (Exception e) {
						}

					}

					@Override
					protected Object doInBackground(Object[] objects) {
						FileOutputStream fos = null;
						Bitmap bm = PictureUtil.getSmallBitmap(file.getAbsolutePath());
						try {

							fos = new FileOutputStream(file);
							bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
						} catch (Exception e) {
							LogUtils.e(TAG, e.toString());
						}
						return null;
					}

					@Override
					protected void onPostExecute(Object o) {
						super.onPostExecute(o);
						try {

							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
						} catch (Exception e) {

						}
						if (finalFileFound && file.exists()) {
							cropImage(file);
						} else {
							Toast.makeText(CompleteProfileActivity.this, getResources().getString(R.string.select_picture_failed), Toast.LENGTH_SHORT).show();

						}
					}
				}.execute();

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setupImageView(SystemDefinedUploadFileType currentSystemDefinedUploadFileType, File file) {
		if (SystemDefinedUploadFileType.IDENTITY_CARD.equals(currentSystemDefinedUploadFileType)) {
			imageViewIdCard.setImageBitmap(ImageUtils.getBitmap(file, 5, ImageUtils.BitmapSizeType.ORIGINAL));
			idCardSelect = true;
		} else if (SystemDefinedUploadFileType.FIGURE.equals(currentSystemDefinedUploadFileType)) {
			imageViewDriver.setImageBitmap(ImageUtils.getBitmap(file, 5, ImageUtils.BitmapSizeType.ORIGINAL));
			headerSelect = true;
		} else if (SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE.equals(currentSystemDefinedUploadFileType)) {
			imageViewVehicle.setImageBitmap(ImageUtils.getBitmap(file, 5, ImageUtils.BitmapSizeType.ORIGINAL));
			vehicleSideSelect = true;
		} else if (SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND.equals(currentSystemDefinedUploadFileType)) {
			imageViewVehicleBehind.setImageBitmap(ImageUtils.getBitmap(file, 5, ImageUtils.BitmapSizeType.ORIGINAL));
			vehicleBehindSelect = true;
		}
	}

	@Override
	protected void registerListeners() {
		super.registerListeners();

		btnViewIdCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				browsePhoto(SystemDefinedUploadFileType.IDENTITY_CARD);
			}
		});

		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (idCardSuccess && vehicleSideSuccess && vehicleSideSuccess && headerSuccess) {
					autoLogin();
				} else {

					File destDir = new File(Constants.CAMERA_DIR);
					destDir = new File(Constants.CAMERA_DIR);

					final File[] files = destDir.listFiles();

					LogUtils.i(CompleteProfileActivity.class.getSimpleName(), String.format("files under %s are %s", destDir.getPath(), Arrays.toString(files)));
					HashMap<SystemDefinedUploadFileType, File> userProfilePictureMap = new HashMap<SystemDefinedUploadFileType, File>();
					;
					if (files != null) {
						for (File f : files) {
							final String name = f.getName();
							LogUtils.i(TAG, String.format("scan file[name=%s]", name));
							final SystemDefinedUploadFileType systemDefinedUploadFileType = SystemDefinedUploadFileType.fromFilePath(name);
							if (systemDefinedUploadFileType != null) {
								LogUtils.i(TAG, String.format("image[type=%s] found", systemDefinedUploadFileType));
								userProfilePictureMap.put(systemDefinedUploadFileType, f);
							}
						}
					}

					List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
					if (idCardSelect && vehicleSideSelect && vehicleBehindSelect && headerSelect) {
						if (userProfilePictureMap != null) {
							for (SystemDefinedUploadFileType type : userProfilePictureMap.keySet()) {
								if ((!idCardSuccess) && (type.equals(SystemDefinedUploadFileType.IDENTITY_CARD))) {
									final Map<String, Object> params = new HashMap<String, Object>();
									params.put("type", type.name());
									params.put("file", userProfilePictureMap.get(type));
									params.put("pln", SharedPreferencesUtil.getPln());
									paramsList.add(params);
								}
								if ((!vehicleBehindSuccess) && (type.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND))) {
									final Map<String, Object> params = new HashMap<String, Object>();
									params.put("type", type.name());
									params.put("file", userProfilePictureMap.get(type));
									params.put("pln", SharedPreferencesUtil.getPln());
									paramsList.add(params);
								}
								if ((!vehicleSideSuccess) && (type.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE))) {
									final Map<String, Object> params = new HashMap<String, Object>();
									params.put("type", type.name());
									params.put("file", userProfilePictureMap.get(type));
									params.put("pln", SharedPreferencesUtil.getPln());
									paramsList.add(params);
								}
								if ((!headerSuccess) && (type.equals(SystemDefinedUploadFileType.FIGURE))) {
									final Map<String, Object> params = new HashMap<String, Object>();
									params.put("type", type.name());
									params.put("file", userProfilePictureMap.get(type));
									params.put("pln", SharedPreferencesUtil.getPln());
									paramsList.add(params);
								}
							}
							Map<String, Object>[] map = new HashMap[0];
							new UploadAllPictureTask(CompleteProfileActivity.this, createProgressDialog(activity)) {
								@Override
								protected void onSucceed(TaskResult voidTaskResult) {
									super.onSucceed(voidTaskResult);
									autoLogin();
								}
							}.execute(paramsList.toArray(map));
						} else {
							Toast.makeText(activity, "请选择所有的照片后再进行提交", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(activity, "请选择所有的照片后再进行提交", Toast.LENGTH_LONG).show();
					}
				}
			}

			private void autoLogin() {
				new AutoSignInTask(activity) {
					@Override
					protected String getRemoteServiceAPIUrl() {
						return getServiceAPIUrl();
					}

					@Override
					protected void onSucceed(LoginResponse loginResponse) {
						AuthenticationHolder.setLogin(true);
						Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);
						final Session session = loginResponse.getSession();
						final UserProfile profile = loginResponse.getProfile();

						AuthenticationHolder.setSession(session);
						AuthenticationHolder.setProfile(profile);

						AlarmManagerUtil.sendLocationBroadcast(activity);

						final Context context = HuoDiApplication.getContext();

						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {


							}
						}, 1000);

						startActivity(new Intent(CompleteProfileActivity.this, HomeActivity.class));
						try {
							File directory = new File(Constants.CAMERA_DIR);
							if (directory.exists()) {
								FileUtils.deleteDirectory(directory);
								directory.deleteOnExit();
							}
						} catch (Exception e) {
							LogUtils.e(TAG, e.toString());
						}

						finish();
					}
				}.registerExceptionHandler(ErrorCode.ACCESS_DENIED, AccessDeniedHandler.getInstance()).

						setListener(new TaskListener.TaskListenerAdapter<LoginResponse>(activity) {
										@Override
										public void onSucceed(LoginResponse object) {

											Preferences.set(HuoDiConstants.PREF_ACTIVATED, true);

											final UserProfile profile = object.getProfile();
											final Session session = object.getSession();

											AuthenticationHolder.setProfile(profile);
											AuthenticationHolder.setSession(session);

										}

									}
						).

						execute(new TaskParams(new HashMap<String, Object>()

						));
			}
		});

		btnPhotoIdCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto(SystemDefinedUploadFileType.IDENTITY_CARD);
			}
		});

		btnViewDriver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
				File destDir = new File(Constants.CAMERA_DIR);
				destDir = new File(Constants.CAMERA_DIR);
				SystemDefinedUploadFileType type = SystemDefinedUploadFileType.FULL_SHOT_PHOTO;
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("type", type.name());
				params.put("file", new File(Constants.CAMERA_DIR + type.getRemoteFilePath()));
				params.put("pln", SharedPreferencesUtil.getPln());
				paramsList.add(params);
				Map<String, Object>[] map = new HashMap[0];
				new UploadPictureTask(CompleteProfileActivity.this, createProgressDialog(activity)).execute(paramsList.toArray(map));

			}
		});

		btnPhotoDriver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto(SystemDefinedUploadFileType.FIGURE);
			}
		});

		btnViewVehicle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				browsePhoto(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE);
			}
		});

		btnViewVehicleBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				browsePhoto(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND);
			}
		});

		btnViewDriver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				browsePhoto(SystemDefinedUploadFileType.FIGURE);
			}
		});

		btnPhotoVehicle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE);
			}
		});

		btnPhotoVehicleBehind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND);
			}
		});

	}

	private void takePhoto(SystemDefinedUploadFileType type) {
		viewImage = false;
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		File tmp = new File(Constants.CAMERA_DIR.concat(type.getRemoteFilePath()));
		File tmpDir = tmp.getParentFile();
		if (tmpDir != null && !tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmp));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, type.ordinal());
	}

	private void browsePhoto(SystemDefinedUploadFileType type) {
		viewImage = true;
		Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mIntent.addCategory(Intent.CATEGORY_OPENABLE);
		mIntent.setType("image/*");
		startActivityForResult(mIntent, type.ordinal());
	}

	private void cropImage(File file) {
		try {
			Uri uri = Uri.fromFile(file);
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 800);
			intent.putExtra("outputY", 800);
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			intent.putExtra("noFaceDetection", true);
			intent.putExtra("return-data", false);
			intent.putExtra("circleCrop", "true");
			startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
		} catch (ActivityNotFoundException e) {
		}
	}

	protected ProgressDialog createProgressDialog(final Activity activity) {
		ProgressDialog dialog = new ProgressDialog(activity);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle(getString(R.string.waiting));
		dialog.setMessage(getString(R.string.uploadingPictureTip));
		dialog.setIcon(android.R.drawable.ic_dialog_alert);
		dialog.setMax(100);
		dialog.setCancelable(true);
		return dialog;

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


	protected String getServiceAPIUrl() {
		return HuoDiConstants.API_DRIVER_URL_LOGIN;
	}

	@Override
	protected void onResume() {
		super.onResume();
		showImageView(imageViewIdCard, SystemDefinedUploadFileType.IDENTITY_CARD);
		showImageView(imageViewDriver, SystemDefinedUploadFileType.FIGURE);
		showImageView(imageViewVehicle, SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE);
		showImageView(imageViewVehicleBehind, SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND);


	}


	private void showImageView(ImageView imageView, SystemDefinedUploadFileType fileType) {
		String picturePath = Constants.CAMERA_DIR + fileType.getRemoteFilePath();
		final File file;
		file = new File(picturePath);
		if (file.exists()) {
			if (fileType.equals(SystemDefinedUploadFileType.IDENTITY_CARD)) {
				idCardSelect = true;
			}
			if (fileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE)) {
				vehicleSideSelect = true;
			}
			if (fileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND)) {
				vehicleBehindSelect = true;
			}
			if (fileType.equals(SystemDefinedUploadFileType.FIGURE)) {
				headerSelect = true;
			}
			imageView.setImageBitmap(ImageUtils.getBitmap(file, 5, ImageUtils.BitmapSizeType.ORIGINAL));
		} else {
			if (fileType.equals(SystemDefinedUploadFileType.IDENTITY_CARD)) {
				idCardSelect = false;
			}
			if (fileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_SIDE)) {
				vehicleSideSelect = false;
			}
			if (fileType.equals(SystemDefinedUploadFileType.VEHICLE_PHOTO_BEHIND)) {
				vehicleBehindSelect = false;
			}
			if (fileType.equals(SystemDefinedUploadFileType.FIGURE)) {
				headerSelect = false;
			}
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		try {
			FileUtils.deleteDirectory(new File(Constants.CAMERA_DIR));
		} catch (Exception e) {
			LogUtils.e(TAG, e.toString());
		}
	}
}
