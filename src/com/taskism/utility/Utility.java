package com.taskism.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.task.taskApplication.R;
import com.taskism.control.ActivityController;
import com.taskism.responsecallback.ResponseCallback;
import com.taskism.responsecallback.SettingResponseCallback;

public class Utility {
	// private static final Logger LOGGER = Logger.getLogger(Utility.class);

	private static SpannableString spnableStr = null;
	/* private static FuelSignalApplication application; */
	// private static Context context;
	private static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");;
	private static String formatDate = "";
	private static Calendar calendar = Calendar.getInstance();
	private static DecimalFormat decmlFormat = new DecimalFormat("#0.00");;

	private static Location lastLocation = new Location("PointA");
	private static Location curLocation = new Location("PointB");

	private static Context context;

	/**
	 * @author awdheshk
	 * @date Jan 30, 2014
	 * @argument
	 * @return Integer
	 * @description To convert string into Integer
	 */
	public static Integer convertString2Integer(String strVal) {
		Integer convrtVal = null;
		try {
			convrtVal = Integer.valueOf(strVal);
		} catch (Exception e) {
			return convrtVal;
		}
		return convrtVal;
	}

	/**
	 * @author Manpreet Singh
	 * @date Jan 30, 2014
	 * @argument
	 * @return Long
	 * @description To convert string into Long
	 */
	public static Long convertString2Long(String strVal) {
		Long convrtVal = null;
		try {
			convrtVal = Long.valueOf(strVal);
		} catch (Exception e) {
			return convrtVal;
		}
		return convrtVal;
	}

	/**
	 * @author Manpreet Singh
	 * @date Jan 30, 2014
	 * @argument
	 * @return Double
	 * @description To convert string into Double
	 */
	public static Double convertString2Double(String strVal) {
		Double convrtVal = null;
		try {
			convrtVal = Double.valueOf(strVal);
		} catch (Exception e) {
			return convrtVal;
		}
		return convrtVal;
	}

	/*	*//**
	 * @user: Manpreet Singh
	 * @date Feb 10, 2014
	 * @return void
	 * @exception
	 * @description This method will show pop up alert for opening GPS setting
	 *              activity.
	 */
	/*
	 * public static void showGpsSettinsPopUp(final Context context) { try {
	 * CustomDialogView customDialog = CustomDialogView.create(context);
	 * customDialog.setTitle(context.getString(R.string.dlg_gps_sttng_titl));
	 * customDialog.setCustomNegativeButton(context
	 * .getString(R.string.btn_cancel)); customDialog.setPositiveButton(
	 * context.getString(R.string.dlg_btn_settng), new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { Intent
	 * intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	 * context.startActivity(intent); dialog.dismiss(); } });
	 * customDialog.show(); } catch (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * @user: Manpreet Singh
	 * @date Feb 15, 2014
	 * @return void
	 * @exception
	 * @description This method will validate password containing at least one
	 *              character.
	 */
	public static boolean passwordPattern(String pswdStr) {
		try {

			String special = "/~`@#$%^&*()-_=+[]{}:;<>.,?'|\"\\";
			String pattern = ".*[" + Pattern.quote(special) + "].*";
			return pswdStr.matches(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 15, 2014
	 * @return void
	 * @exception
	 * @description This method will validate email address and return result as
	 *              per pattern
	 */
	public static boolean validateEmail(String emailStr) {
		try {
			if (emailStr == null || emailStr.length() == 0) {
				return false;
			}

			String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher mathcher = pattern.matcher(emailStr);
			return mathcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 19, 2014
	 * @return String
	 * @exception
	 * @description This utility method will convert date into MM/DD/YYYY format
	 */
	public static String getDateFormat_MM_DD_YYYY(Date dateStr) {
		try {
			// Date date = formatter.parse(dateStr);
			formatDate = formatter.format(dateStr);
			return formatDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @user: Manpreet Singh
	 * @date Mar 7, 2014
	 * @return String
	 * @exception
	 * @description This method will convert millisecond to Date string format
	 */
	public static String convertMiliSecToDate_MM_DD_YYYY(long miliSecond) {

		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		calendar.setTimeInMillis(miliSecond);
		return formatter.format(calendar.getTime());
	}

	/**
	 * @user: Manpreet Singh
	 * @date Mar 7, 2014
	 * @return String
	 * @exception
	 * @description
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());
		long date = System.currentTimeMillis();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		String formattedDate = df.format(date);
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd"); Calendar
		 * cal = Calendar.getInstance(); formatDate = dateFormat.format(cal);
		 */
		return formattedDate;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 20, 2014
	 * @return byte[]
	 * @exception IOException
	 * @description Converting objects to byte arrays
	 */
	public static byte[] object2Bytes(Object o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 20, 2014
	 * @return Object
	 * @exception IOException
	 *                , ClassNotFoundException
	 * @description Converting byte arrays to objects
	 */
	public static Object bytes2Object(byte raw[]) throws IOException,
			ClassNotFoundException {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(raw);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object o = ois.readObject();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 24, 2014
	 * @return void
	 * @exception
	 * @description This method will return the spannable underlined text value
	 */
	public static SpannableString getTextUnderline(String txtStr) {
		try {
			spnableStr = new SpannableString(txtStr);
			spnableStr.setSpan(new UnderlineSpan(), 0, spnableStr.length(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spnableStr;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Feb 26, 2014
	 * @return boolean
	 * @exception
	 * @description This metod will be used check available network
	 */
	public static boolean isConnectedToInternet(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Mar 7, 2014
	 * @return float
	 * @exception
	 * @description
	 */
	public static String get2Decimal(double val) {

		return decmlFormat.format(val);
	}

	/**
	 * @user: Manpreet Singh
	 * @date Mar 19, 2014
	 * @return Double
	 * @exception
	 * @description
	 */
	public static float get2DecimalVal(float val) {
		try {
			decmlFormat = new DecimalFormat("0.00");
			return Float.valueOf(decmlFormat.format(val));
		} catch (NumberFormatException e) {
			return val;
		}
	}

	public static String get2DecimalValue(float val) {
		try {
			decmlFormat = new DecimalFormat("0.00");
			return decmlFormat.format(val);
		} catch (Exception e) {
			return "";
		}

	}

	public static String get2DecimalVal(double val) {
		try {
			decmlFormat = new DecimalFormat("0.00");
			return decmlFormat.format(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Bitmap downloadImage(String url) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;

		try {
			stream = getHttpConnection(url);
			if (stream != null) {
				Log.e("test", "DOWNLOADING IMG IMG  " + url);

				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bitmap;
	}

	// Makes HttpURLConnection and returns InputStream
	private static InputStream getHttpConnection(String urlString)
			throws IOException {
		InputStream stream = null;
		urlString = urlString.replace(" ", "%20");
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		// if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
		// urlConnect.setRequestProperty("Connection", "close"); }
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = httpConnection.getInputStream();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stream;
	}

	/**
	 * @user: Manpreet Singh
	 * @date Apr 1, 2014
	 * @return String
	 * @exception
	 * @description Get xml data as string from input stream.
	 */
	public String getStringDataFromResponse(InputStream inputStream) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(inputStream, writer, "UTF-8");
		} catch (Exception e) {
			// LOGGER.error( "Eror to convert to String=== " + e.getMessage());
			e.printStackTrace();
		}

		return writer.toString();
	}

	/**
	 * @user: Manpreet Singh
	 * @date Apr 3, 2014
	 * @return Drawable
	 * @exception
	 * @description Method will return drable from bitmap
	 */
	public static Drawable getDrawable(Bitmap bitmap) {
		try {
			Drawable drawble = new BitmapDrawable(bitmap);
			return drawble;
		} catch (Exception e) {
			return null;
		} finally {
			bitmap = null;
		}
	}

	/**
	 * @user: Manpreet Singh
	 * @date Sep 24, 2015
	 * @return void
	 * @exception
	 * @description method for hide keyboard
	 */
	public static void hideKeyBoard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
				0);
	}

	/**
	 * 
	 * developer:Manpreet Singh
	 * date:03-Oct-2015
	 * return:void 
	 * description: method for save setting prefrence in sharedPrefrence
	 */
	public void saveSettings(String response,
			SettingResponseCallback settingResponseCallback, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"settingPrefrence", 1);
		try {
			JSONObject responseObject = new JSONObject(response);
			String userAdmin = responseObject.getString("useradmin");
			String roleAdmin = responseObject.getString("roleadmin");
			String scheduleAdmin = responseObject.getString("scheduleadmin");
			String taskAdmin = responseObject.getString("taskadmin");
			SharedPreferences.Editor editor = sharedPreferences.edit();
			if (userAdmin.equals("null")) {
				editor.putBoolean("userAdmin", false);
			} else {
				editor.putBoolean("userAdmin", true);
			}
			if (roleAdmin.equals("null")) {
				editor.putBoolean("roleAdmin", false);
			} else {
				editor.putBoolean("roleAdmin", true);

			}
			if (scheduleAdmin.equals("null")) {
				editor.putBoolean("scheduleAdmin", false);
			} else {
				editor.putBoolean("scheduleAdmin", true);

			}
			if (taskAdmin.equals("null")) {
				editor.putBoolean("taskAdmin", false);
			} else {
				editor.putBoolean("taskAdmin", true);

			}
			editor.commit();
			settingResponseCallback.onSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:boolean description:
	 */
	public boolean getSheduleAdminStatus(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"settingPrefrence", 1);
		return sharedPreferences.getBoolean("scheduleAdmin", false);

	}

	public boolean getTaskAdminStatus(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"settingPrefrence", 1);
		return sharedPreferences.getBoolean("taskAdmin", false);

	}

	public boolean getRoleAdminStatus(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"settingPrefrence", 1);
		return sharedPreferences.getBoolean("roleAdmin", false);

	}

	public boolean getUserAdminStatus(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"settingPrefrence", 1);
		return sharedPreferences.getBoolean("userAdmin", false);

	}

	// Method to on the wifi
	public static void setWifiOn(Context ctx) {
		WifiManager wifi;
		wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == false) {
			wifi.setWifiEnabled(true);
		}
	}

	// method to off the wifi
	public static boolean isWifiON(Context ctx) {
		WifiManager wifi;
		wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == false) {
			return false;
		} else {
			return true;
		}
	}

	// method to check if wifi is on
	public static void setWifiOff(Context ctx) {
		WifiManager wifi;
		wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == true) {
			wifi.setWifiEnabled(false);
		}
	}

	// Method to open the wifi settings
	public static void openWifiSettings(Context ctx) {
		WifiManager wifi;
		wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == false) {
			wifi.setWifiEnabled(true);
		}
		ctx.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
	}

	public static void setMobileDataEnabled(Context context, boolean enabled) {
		final ConnectivityManager conman = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		Class conmanClass = null;
		Method setMobileDataEnabledMethod = null;
		Object iConnectivityManager = null;
		Field iConnectivityManagerField = null;
		Class iConnectivityManagerClass = null;
		try {
			conmanClass = Class.forName(conman.getClass().getName());
		} catch (ClassNotFoundException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}

		try {
			iConnectivityManagerField = conmanClass
					.getDeclaredField("mService");
		} catch (NoSuchFieldException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}
		iConnectivityManagerField.setAccessible(true);

		try {
			iConnectivityManager = iConnectivityManagerField.get(conman);
		} catch (IllegalArgumentException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		} catch (IllegalAccessException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}

		try {
			iConnectivityManagerClass = Class.forName(iConnectivityManager
					.getClass().getName());
		} catch (ClassNotFoundException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}

		try {
			setMobileDataEnabledMethod = iConnectivityManagerClass
					.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		} catch (NoSuchMethodException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}
		setMobileDataEnabledMethod.setAccessible(true);

		try {
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} catch (IllegalArgumentException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		} catch (IllegalAccessException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		} catch (InvocationTargetException e) {
			// LOGGER.error("Error in setMobileDataEnabled "+e);
		}
	}

	public static String getTimeDiff(Date dateOne, Date dateTwo) {
		String diff = "";
		long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
		diff = String.format(
				"%d hour(s) %d min(s) %d sec(s)",
				TimeUnit.MILLISECONDS.toHours(timeDiff),
				TimeUnit.MILLISECONDS.toMinutes(timeDiff)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(timeDiff)),
				TimeUnit.MILLISECONDS.toSeconds(timeDiff)
						- TimeUnit.HOURS.toSeconds(TimeUnit.MILLISECONDS
								.toHours(timeDiff)));
		return diff;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

	public static String getExtractedPhoneNo(String s) {
		String upToNCharacters = s.substring(11,
				Math.min(s.length(), s.length()));

		return upToNCharacters;
	}

	/**
	 * 
	 * developer:Manpreet date:03-Oct-2015 return:void description: method for
	 * open dialog
	 */
	public void showCustomDialog(String submitBtnText, String title,
			String description, boolean status, final Activity activity,
			final Class class1, final ResponseCallback responseCallback) {
		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog);
		TextView dialogTitle, dialogMessage;
		Button submitButton, cancelButton;
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
		dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogMessage = (TextView) dialog.findViewById(R.id.userName);
		submitButton = (Button) dialog.findViewById(R.id.deleteButton);
		cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
		dialogTitle.setText(title);
		dialogMessage.setText(description);
		submitButton.setText(submitBtnText);
		// dialogTitle.setText(title)
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (responseCallback!=null) {
					responseCallback.onSuccessRecieve(true);
				}
				if (class1 != null) {
					ActivityController.startActivityController(6, null,
							activity, true);
				}
				dialog.dismiss();
			}
		});

	}
	
	/**
	 * 
	 * developer:manpreets2
	   date:Nov 3, 2015
	   return:String
	   description: method for encode bitmap to string
	 */
	public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        immagex.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("Taskism", imageEncoded);
        return imageEncoded;
    }
	/**
	 * 
	 * developer:manpreets2
	   date:Nov 3, 2015
	   return:String
	   description: method for encode bitmap to string
	 */
	public static byte[] encodeToByte(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("Taskism", imageEncoded);
        return b;
    }

}
