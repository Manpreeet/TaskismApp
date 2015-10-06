/**
 * 
 */
package com.task.taskApplication;

import java.util.List;

import com.tarsem.bean.UserShiftBean;
import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.request.ViewScheduleAsyncTask;
import com.tarsem.responsecallback.ResponseCallback;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Manpreet
 * 
 */
public class ViewScheduleActivity extends ParentActivity {
	ParentActivity parentActivity;
	Context context;
	private TextView noRecordFound;
	private ProgressBar loadingProgress;
	private HorizontalScrollView horizontalScrollView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_view_schedule);
		findAttributesId();
	}

	/**
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * find attributes id's
	 */
	private void findAttributesId() {
		parentActivity = this;
		context = this;

		getSideMenu(ViewScheduleActivity.this);
		noRecordFound = (TextView) findViewById(R.id.noRecordFoundText);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scheduleUserParent);
		loadingProgress = (ProgressBar) findViewById(R.id.loadingProgress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (isConnectedToInternet()) {
			loadingProgress.setVisibility(View.VISIBLE);
			fetchScheduleList();

		}

	}

	/**
	 * developer:Manpreet date:04-Oct-2015 return:void description: method for
	 * fetch schedule list from server
	 */
	private void fetchScheduleList() {
		new ViewScheduleAsyncTask(ApplicationConstant.appurl
				+ "shiftbyemployee" + "&date=2015-09-04" + "&userid="
				+ Constant.getLoggedUserId(context), context,
				new ResponseCallback() {

					@Override
					public void onSuccessRecieve(Object object) {
						List<UserShiftBean> list = (List<UserShiftBean>) object;
						for (int i = 0; i < list.size(); i++) {
							LayoutInflater layoutInflater = LayoutInflater
									.from(context);
							View convertView = layoutInflater.inflate(
									R.layout.custom_view_schedule, null);
							if (i == 0) {

							} else {

							}
							horizontalScrollView.addView(convertView);
						}

						loadingProgress.setVisibility(View.GONE);
					}

					@Override
					public void onErrorRecieve(Object object) {
						loadingProgress.setVisibility(View.GONE);
						noRecordFound.setVisibility(View.VISIBLE);
						showToastMessage((String) object);
					}
				}).execute();
	}

	public void openLeftPanel(View view) {
		showMenu();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.task.taskApplication.ParentActivity#close()
	 */
	@Override
	public void close() {
		super.close();

		showContent();

	}

}
