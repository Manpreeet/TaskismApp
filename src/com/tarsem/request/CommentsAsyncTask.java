/**
 * 
 */
package com.tarsem.request;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.tarsem.constant.ApplicationConstant;
import com.tarsem.constant.Constant;
import com.tarsem.parser.CommonParser;
import com.tarsem.responsecallback.ResponseCallback;

/**
 * @author Manpreet
 * 
 */
public class CommentsAsyncTask extends AsyncTask<Void, Void, Void> {
	/**
		 * 
		 */

	private String url;
	private String response = null, type;
	ResponseCallback responseCallback;
	Context context;

	public CommentsAsyncTask(String url, Context context,
			ResponseCallback responseCallback, String type) {
		this.url = url;
		this.context = context;
		this.responseCallback = responseCallback;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {

		try {
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");

			// read the response
			System.out.println("Response Code: " + conn.getResponseCode());
			InputStream in = new BufferedInputStream(conn.getInputStream());
			response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			System.out.println(response);
			// responseObject = new HttpRequest().getJSONResponse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		try {
			if (response != null&&!response.equals("")) {

				new CommonParser()
						.fetchUserComments(response, responseCallback);
			} else {
				responseCallback.onErrorRecieve("no comments");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}