package com.wlqq.huodi.view;


import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.wlqq.huodi.R;
import com.wlqq.huodi.app.HuoDiApplication;
import com.wlqq.huodi.utils.HuoDiConstants;

import org.apache.commons.lang.time.DateUtils;

/**
 * Created by caitiancai on 14/11/20.
 */
public class DateTimeButton extends Button {
	public Calendar time = Calendar.getInstance(Locale.CHINA);
	public static final DateFormat format = HuoDiConstants.DF_YYYY_MM_dd_HH_mm;
	private DatePicker datePicker;
	private TimePicker timePicker;

	private Button dataView;
	private AlertDialog dialog;

	//	private Activity activity;
	public DateTimeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DateTimeButton(Context context) {
		super(context);
		init();
	}

	//增加构造器
	public DateTimeButton(Context context, Button dataView) {
		super(context);
		this.dataView = dataView;
	}

	public AlertDialog dateTimePickerDialog() {
		LinearLayout dateTimeLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.date_time_layout, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.DatePicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.TimePicker);
		if (dataView == null)
			init();
		timePicker.setIs24HourView(true);


		OnTimeChangedListener timeListener = new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				time.set(Calendar.HOUR_OF_DAY, hourOfDay);
				time.set(Calendar.MINUTE, minute);

			}
		};

		timePicker.setOnTimeChangedListener(timeListener);

		OnDateChangedListener dateListener = new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
									  int dayOfMonth) {
				// TODO Auto-generated method stub
				time.set(Calendar.YEAR, year);
				time.set(Calendar.MONTH, monthOfYear);
				time.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			}
		};

		datePicker.init(time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH), dateListener);
		timePicker.setCurrentHour(time.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(time.get(Calendar.MINUTE));


		dialog = new AlertDialog.Builder(getContext()).setTitle("设置日期时间").setView(dateTimeLayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						time.set(Calendar.YEAR, datePicker.getYear());
						time.set(Calendar.MONTH, datePicker.getMonth());
						time.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
						time.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
						time.set(Calendar.MINUTE, timePicker.getCurrentMinute());

						updateLabel();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();
		return dialog;
	}

	/**
	 *
	 */
	private void init() {
		this.setBackgroundResource(R.drawable.bg_date_time);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setTextColor(R.color.black1);
		this.setTextSize(16);
		this.setPadding(20, 0, 100, 0);

		this.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
				dateTimePickerDialog();
			}
		});

		updateLabel();
	}

	/**
	 * 更新标签
	 */
	public void updateLabel() {

		final Date selectedTime = time.getTime();

		final Date date = DateUtils.addMinutes(new Date(), -1);
		if (date.before(selectedTime)) {
			if (dataView != null) {
				dataView.setText(format.format(time.getTime()));
			}
			this.setText(format.format(time.getTime()));
		} else {
			Toast.makeText(HuoDiApplication.getContext(), "您选择的时间不能早于当前时间", Toast.LENGTH_LONG).show();
			this.setText("");
		}


	}

	public void setDate(String datestr) {
		try {
			time.setTime(format.parse(datestr));
			updateLabel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
