//package utils.service;
//
//import android.annotation.SuppressLint;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import com.abpal.employeetracker.BuildConfig;
//import com.abpal.employeetracker.R;
//import com.abpal.employeetracker.activity.AsyncDownloadMasterDataAndCheckField;
//import com.abpal.employeetracker.activity.DashboardActivity;
//import com.abpal.employeetracker.activity.withdrawn.WithdrawnCallBackLisnter;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.securepreferences.SecurePreferences;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.lang.annotation.Annotation;
//import java.util.List;
//import java.util.Map;
//
//import utils.SingletonHelperGlobal;
//import database.MySQLiteHelper;
//import modelResponse.ChecksDownloaded;
//import modelResponse.ModelError;
//import modelResponse.feassigned_check_response.CheckField;
//import modelResponse.feassigned_check_response.Datum;
//import modelResponse.feassigned_check_response.DisabledChecks;
//import modelResponse.feassigned_check_response.FEAssignedChecksResponse;
//import modelResponse.feassigned_check_response.StatedCheckField;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Converter;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
//import retrofitAPI.WebRequest;
//import utils.AppConstant;
//import utils.MasterDataCallbackListner;
//import utils.OpusConnectSingleTon;
//import utils.Utility;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService implements WithdrawnCallBackLisnter, MasterDataCallbackListner {
//
//    private static final String TAG = "MyFirebaseMsgService";
//    private WithdrawnCallBackLisnter withdrawnCallBackLisnter = this;
//    private MasterDataCallbackListner masterDataCallbackListner = this;
//    private MySQLiteHelper db;
//    private String currentLatLngStr = "";
//    private SecurePreferences prefsMain;
//    private SecurePreferences.Editor prefsEditor;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
//        prefsEditor = prefsMain.edit();
//
//        db = SingletonHelperGlobal.getInstance().mDBDbHelper;
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            sendNotification(remoteMessage.getData());
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//    }
//
//
//    @Override
//    public void onNewToken(@NonNull String token) {
//        //
//    }
//
//
//    private void sendNotification(Map<String, String> data) {
//        try {
//            String title = data.get("title");
//            String body = data.get("body");
//
//
//            int notificationType = 0;
//            int notificationId = 3838; //default
//            try {
//                notificationType = Integer.parseInt(data.get("notificationType"));
//                notificationId = Integer.parseInt(data.get("notificationId"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            switch (notificationType) {
//                case 1:
//                    downloadNewChecks();
//                    break;
//                case 2:
//                    // check withdrawn
//                    new AsyncDownloadMasterDataAndCheckField(db
//                            , this,
//                            withdrawnCallBackLisnter,prefsMain);
//                    break;
//                case 3:
//                    new AsyncDownloadMasterDataAndCheckField(db, false, this, masterDataCallbackListner,prefsMain);
//                    break;
//                case 4:
//                    // do nothing its just Reminder for Offline Sync
//                    //just show only notification
//                    break;
//            }
//
//
//            Intent intent = new Intent(this, DashboardActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            Bundle bundle = new Bundle();
//            bundle.putInt("redirect_flag", notificationType);
//            intent.putExtras(bundle);
//            intent.putExtra("redirect_flag", notificationType);
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationType /* Request code */, intent,
//                    PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
//
//            String channelId = "Opus Connect All";
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this, channelId)
//                            .setContentTitle(title)
//                            .setSmallIcon(R.drawable.updates_selected)
//                            .setContentText(body)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            // Since android Oreo notification channel is needed.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(channelId,
//                        "Opus Connect",
//                        NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//            }
//
//            notificationManager.notify(notificationId, notificationBuilder.build());
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    WebRequest mWebRequest;
//    boolean downloadBoth = false;
//    String fe_id = "";
//    String user_id = "";
//    String vendor_id = "";
//    String app_mode = "";
//    private JSONArray vendoridList = new JSONArray();
//    private JSONArray disabledList = new JSONArray();
//
//    private void downloadNewChecks() throws JSONException {
//
//
//        fe_id = prefsMain.getString("fe_id", "null");
//        user_id = prefsMain.getString("user_id", "null");
//        vendor_id = prefsMain.getString("vendor_id", "null");
//        app_mode = prefsMain.getString("app_mode", "");
//
//
//        if (app_mode.equalsIgnoreCase("1")) {
//            JSONObject inputJsonObj = makJsonObjectInput(fe_id, vendor_id, user_id, db.getCheckIDs("1,3"));
//            mWebRequest = new WebRequest(this, "bridge");
//            downloadFEAssignedChecks(user_id, vendor_id, inputJsonObj, mWebRequest);
//        } else if (app_mode.equalsIgnoreCase("2")) {
//            JSONObject inputJsonObj = makJsonObjectInput(fe_id, vendor_id, user_id, db.getCheckIDs("2"));
//            mWebRequest = new WebRequest(this, "fp");
//            downloadFEAssignedChecks(user_id, vendor_id, inputJsonObj, mWebRequest);
//        } else if (app_mode.equalsIgnoreCase("3")) {
//            JSONObject inputJsonObj = makJsonObjectInput(fe_id, vendor_id, user_id, db.getCheckIDs("1,3"));
//            downloadBoth = true;
//            mWebRequest = new WebRequest(this, "bridge");
//            downloadFEAssignedChecks(user_id, vendor_id, inputJsonObj, mWebRequest);
//        }
//    }
//
//
//    public void downloadFEAssignedChecks(final String user_id, final String vendor_id, final JSONObject jsonObject, final WebRequest modeRequest) {
//        //JSONObject temp = MyApplication.getInstance().makeAPIRequest(getActivity(),jsonObject.toString())
//        //Log.e("FEAssignedChecks Input",temp.toString())
//        Call<FEAssignedChecksResponse> user = modeRequest.m_ApiInterface.getAssignedChecksList(AppConstant.CONTENT_TYPE,
//                Utility.getInstance().getUserName(prefsMain), Utility.getInstance().getToken(prefsMain), user_id, vendor_id
//                , BuildConfig.VERSION_NAME, jsonObject.toString());
//
//        user.enqueue(new Callback<FEAssignedChecksResponse>() {
//            @SuppressLint("Range")
//            @Override
//            public void onResponse(Call<FEAssignedChecksResponse> call, Response<FEAssignedChecksResponse> response) {
//                vendoridList = new JSONArray();
//                try {
//                    if (response.errorBody() != null) {
//                        Retrofit retrofit = new Retrofit.Builder()
//                                .baseUrl(BuildConfig.BASE_URL)
//                                .addConverterFactory(ScalarsConverterFactory.create())
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//                        Converter<ResponseBody, ModelError> converter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);
//
//                        try {
//                            if (downloadBoth) {
//                                downloadBoth = false;
//                                mWebRequest = new WebRequest(MyFirebaseMessagingService.this, "fp");
//                                JSONObject inputJsonObj = makJsonObjectInput(fe_id, vendor_id, user_id, db.getCheckIDs("2"));
//                                downloadFEAssignedChecks(user_id, vendor_id, inputJsonObj, mWebRequest);
//                            }
//                        } catch (Exception e) {
//                            //This is Catch Block
//                            //MyApplication.getInstance().appendLog(TAG+": "+e.getLocalizedMessage())
//                        }
//                    } else {
//                        List<DisabledChecks> listDisabled = response.body().getDisabledChecks();
//                        try {
//                            if (listDisabled.size() > 0) {
//                                for (int j = 0; j < listDisabled.size(); j++) {
//                                    //Log.e("Disabled CheckID","Vendor request id: "+listDisabled.get(j).getVendor_request_ID()+" Case Check ID "+listDisabled.get(j).getCase_check_id()+" Source ID: "+listDisabled.get(j).getSource_id())
//                                    int row_id = db.getRowID(listDisabled.get(j).getVendor_request_ID(), listDisabled.get(j).getCase_check_id(), listDisabled.get(j).getSource_id(), "FEChecks");
//                                    //Log.e("Row ID",""+row_id)
//                                    if (row_id != -1) {
//                                        db.getcusorfromquery("DELETE FROM ImageData WHERE  fe_table_id = " + row_id);
//                                        db.getcusorfromquery("DELETE FROM CheckFieldsDetails WHERE  fe_table_id = " + row_id);
//                                        db.getcusorfromquery("DELETE FROM CheckFieldsStated WHERE  fe_table_id = " + row_id);
//                                        db.getcusorfromquery("DELETE FROM FEChecks WHERE  row_id = " + row_id);
//                                    }
//                                    //JSONObject jobDisabled = new JSONObject()
//                                    //jobDisabled.put("id",listDisabled.get(j).getVendor_request_ID())
//                                    //jobDisabled.put("source_id",listDisabled.get(j).getSource_id())
//                                    disabledList.put(listDisabled.get(j).getVendor_request_ID());
//                                }
//                            }
//                        } catch (Exception e) {
//                            //This is Catch Block
//                        }
//                        String msg = "";
//                        try {
//                            String code = response.body().getCode();
//                            msg = response.body().getMsg();
//                            if (msg.length() > 0) {
//                                //Log.e("MSG",response.body().getMsg())
//                                if (code.equalsIgnoreCase("200") && msg.equalsIgnoreCase("No New Check Assigned")) {
//                                    if (downloadBoth) {
//                                        downloadBoth = false;
//                                        mWebRequest = new WebRequest(MyFirebaseMessagingService.this, "fp");
//                                        JSONObject inputJsonObj = makJsonObjectInput(fe_id, vendor_id, user_id, db.getCheckIDs("2"));
//                                        downloadFEAssignedChecks(user_id, vendor_id, inputJsonObj, mWebRequest);
//                                    }
//                                }
//                            }
//                        } catch (Exception e) {
//                            //This is Catch Block
//                        }
//
//                        List<Datum> listDataResponse = response.body().getData();
//                        if (listDataResponse.size() > 0) {
//                            SQLiteDatabase database = db.getWritableDatabase();
//                            database.beginTransaction();
//                            try {
//                                ContentValues values = new ContentValues();
//                                for (Datum listData : listDataResponse) {
//
//                                    JSONObject documentsUrls = new JSONObject();
//                                    JSONArray jsonDocumentsURLs = new JSONArray();
//
//                                    if(listData.getEducationPhotoArrays()!=null){
//                                        for (int i = 0; i < listData.getEducationPhotoArrays().size(); i++) {
//                                            Log.i("photos", listData.getEducationPhotoArrays().get(i).getImageUrl());
//                                            jsonDocumentsURLs.put(listData.getEducationPhotoArrays().get(i).getImageUrl());
//                                            downloadImageRetroEduction(listData.getEducationPhotoArrays().get(i).getImageUrl(), listData.getCaseCheckId(),(i+1)+"");
//                                        }
//                                    }
//
//                                    String vendor_request_id_local = listData.getVendorRequestId();
//                                    String case_check_id_local = listData.getCaseCheckId();
//                                    String source_id_local = listData.getSourceId();
//                                    values.put("vendor_request_id", vendor_request_id_local);
//                                    values.put("case_check_id", case_check_id_local);
//
//
//                                    values.put("source_id", source_id_local);
//                                    values.put("check_id", listData.getCheckId());
//                                    values.put("vendor_sent_date", listData.getVendorSentDate());
//                                    values.put("vendor_request_due_date", listData.getVendorRequestDueDate());
//                                    values.put("vendor_request_due_date_for_sort", listData.getVendorRequestDueDateForSort());
//                                    values.put("fe_id", listData.getFeId());
//                                    values.put("check_type", listData.getCheckType());
//                                    values.put("process_id", listData.getProcessId());
//                                    values.put("check_name", listData.getCheckName());
//                                    values.put("client_Name", listData.getClientName());
//                                    values.put("client_id", listData.getClientId());
//                                    values.put("case_priority_flag", listData.getCasePriorityFlag());
//                                    values.put("appointment", listData.getAppointment());
//                                    values.put("Appointment_Slot", listData.getAppointmentSlot());
//                                    values.put("application_number", listData.getApplicationNumber());
//                                    values.put("fasTag_required", listData.getFastagRequired());
//                                    values.put("re_work_flag", listData.getReWorkFlag());
//                                    values.put("re_work_comment", listData.getReWorkComment());
//
//
//                                    values.put("invoice_details_one", listData.getInvoiceDetailsOne());
//                                    values.put("invoice_details_two", listData.getInvoiceDetailsTwo());
//                                    values.put("invoice_details_three", listData.getInvoiceDetailsThree());
//                                    values.put("candidate_gps_address", listData.getCandidateGpsAddress());
//
//                                    values.put("check_special_instruction", listData.getCheckSpecialInstruction());
//                                    values.put("check_gps", listData.getCheckGps());
//                                    values.put("candidate_Name", listData.getCandidateName());
//                                    values.put("candidate_number", listData.getCandidateNumber());
//                                    values.put("candidate_Father_Name", listData.getCandidateFatherName());
//                                    values.put("city", listData.getCity());
//                                    values.put("state", listData.getState());
//                                    values.put("country", listData.getCountry());
//                                    values.put("pin_code", listData.getPinCode());
//                                    values.put("address", listData.getAddress());
//                                    values.put("address", listData.getAddress());
//                                    values.put("documents_min_limit", listData.getDocuments_min_limit());
//                                    values.put("documents_max_limit", listData.getDocuments_max_limit());
//                                    values.put("family_id", listData.getFamily_id());
//                                    values.put("documents_urls", documentsUrls.toString());
//                                    values.put("fe_check_status", "1");
//                                    if (listData.getFastagRequired().equalsIgnoreCase("Yes")) {
//                                        values.put("list_order", "1");
//                                    } else if (listData.getCasePriorityFlag().equalsIgnoreCase("1")) {
//                                        values.put("list_order", "2");
//                                    } else if (listData.getReWorkFlag().equalsIgnoreCase("1")) {
//                                        values.put("list_order", "3");
//                                    } else if (listData.getAppointment().length() > 0) {
//                                        values.put("list_order", "4");
//                                    } else {
//                                        values.put("list_order", "");
//                                    }
//
//
//                                    values.put("route_main", "");
//                                    values.put("route_optimized", "NO");
//                                    values.put("isGPSCoordinateMissing", "NO");
//
//                                    values.put("is_auto_closure", listData.getIs_auto_closure());
//                                    values.put("is_candidate_signature_required", listData.getIs_candidate_signature_required());
//                                    values.put("candidate_photo", listData.getCandidate_photo());
//                                    values.put("nid_photo", listData.getNid_photo());
//                                    values.put("house_photo", listData.getHouse_photo());
//                                    values.put("address_proof", listData.getAddress_proof());
//                                    values.put("landmark_photo", listData.getLandmark_photo());
//
//
//                                    database.insert("FEChecks", null, values);
//                                    vendoridList.put(listData.getVendorRequestId());
//
//
//
//
//                                    int rowID = -1;
//                                    try {
//                                        String query = "Select row_id from FEChecks where vendor_request_id=" + vendor_request_id_local + " AND case_check_id =" + case_check_id_local + " AND source_id =" + source_id_local;
//                                        Cursor cursor = database.rawQuery(query, null);
//                                        cursor.moveToFirst();
//                                        if (cursor.getCount() > 0) {
//                                            rowID = cursor.getInt(cursor.getColumnIndex("row_id"));
//                                        }
//                                    } catch (Exception e) {
//                                        //This is Catch Block
//                                    }
//                                    //int row_id_local = db.getRowID(vendor_request_id_local, case_check_id_local, source_id_local, "FEChecks")
//                                    int row_id_local = rowID;
//
//                                    List<CheckField> checkFieldsListResponse = listData.getCheckFields();
//                                    if (null != checkFieldsListResponse) {
//                                        SQLiteDatabase CheckFieldDetailDatabase = db.getWritableDatabase();
//                                        CheckFieldDetailDatabase.beginTransaction();
//                                        try {
//                                            ContentValues valuesCheckFieldDetails = new ContentValues();
//                                            for (CheckField list : checkFieldsListResponse) {
//                                                valuesCheckFieldDetails.put("fe_table_id", row_id_local);
//                                                valuesCheckFieldDetails.put("vendor_request_id", vendor_request_id_local);
//                                                valuesCheckFieldDetails.put("case_check_id", case_check_id_local);
//                                                valuesCheckFieldDetails.put("field_id", list.getKey());
//                                                valuesCheckFieldDetails.put("stated_value", list.getValue());
//                                                valuesCheckFieldDetails.put("verified_value", "");
//                                                database.insert("CheckFieldsDetails", null, valuesCheckFieldDetails);
//                                            }
//                                            CheckFieldDetailDatabase.setTransactionSuccessful();
//                                        } finally {
//                                            CheckFieldDetailDatabase.endTransaction();
//                                        }
//                                    }
//
//
//                                    List<StatedCheckField> statedCheckFieldsResponse = listData.getStatedCheckFields();
//                                    if (null != statedCheckFieldsResponse) {
//                                        SQLiteDatabase CheckFieldsStatedDatabase = db.getWritableDatabase();
//                                        CheckFieldsStatedDatabase.beginTransaction();
//                                        try {
//                                            ContentValues CheckFieldsStatedValues = new ContentValues();
//                                            for (StatedCheckField list : statedCheckFieldsResponse) {
//                                                CheckFieldsStatedValues.put("fe_table_id", row_id_local);
//                                                CheckFieldsStatedValues.put("vendor_request_id", vendor_request_id_local);
//                                                CheckFieldsStatedValues.put("case_check_id", case_check_id_local);
//                                                CheckFieldsStatedValues.put("field_id", list.getKey());
//                                                CheckFieldsStatedValues.put("stated_value", list.getValue());
//                                                CheckFieldsStatedDatabase.insert("CheckFieldsStated", null, CheckFieldsStatedValues);
//                                            }
//                                            CheckFieldsStatedDatabase.setTransactionSuccessful();
//                                        } finally {
//                                            CheckFieldsStatedDatabase.endTransaction();
//                                        }
//                                    }
//
//                                }
//                                database.setTransactionSuccessful();
//                            } finally {
//                                database.endTransaction();
//                            }
//                        }
//
//
//                    }
//                } catch (Exception e) {
//                    Log.i("fd", e.getLocalizedMessage());
//                    //This is Catch Block
//                    //MyApplication.getInstance().appendLog(TAG+": "+e.getLocalizedMessage())
//                }
//                try {
//                    if (vendoridList.length() > 0 || disabledList.length() > 0) {
//                        JSONObject jsonObject1 = new JSONObject();
//                        jsonObject1.put("new_checks", vendoridList);
//                        jsonObject1.put("disabled_checks", disabledList);
//                        //Log.e("Input ChecksDownloaded",jsonObject1.toString())
//                        checksDownloaded(user_id, vendor_id, jsonObject1, modeRequest, jsonObject);
//                    } else {
//                        Cursor cursor_fe = db.getcusorfromquery(AppConstant.SELECT_ASSIGNED_CHECKS_LIST_ORDER_INTIAL);
//                        if (cursor_fe.getCount() == 0) {
//                            cursor_fe.close();
//                        } else {
//                            if (null != cursor_fe) {
//                                cursor_fe.close();
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    //This is Catch Block
//                    //MyApplication.getInstance().appendLog(TAG+": "+e.getLocalizedMessage())
//                }
//
//                OpusConnectSingleTon.getInstance().getUpdateFirebaseNotification().onNotificationUpdate();
//            }
//
//            @Override
//            public void onFailure(Call<FEAssignedChecksResponse> call, Throwable t) {
//            }
//        });
//    }
//
//    public void checksDownloaded(final String user_id, final String vendor_id, JSONObject jsonObject, final WebRequest moderequest, final JSONObject objCheckDownload) {
//        Call<ChecksDownloaded> user = moderequest.m_ApiInterface.checksdownloaded(AppConstant.CONTENT_TYPE,
//                Utility.getInstance().getUserName(prefsMain),
//                Utility.getInstance().getToken(prefsMain), user_id, vendor_id, BuildConfig.VERSION_NAME, jsonObject.toString());
//        user.enqueue(new Callback<ChecksDownloaded>() {
//            @Override
//            public void onResponse(Call<ChecksDownloaded> call, Response<ChecksDownloaded> response) {
//                try {
//                    if (response.errorBody() != null) {
//                        Retrofit retrofit = new Retrofit.Builder()
//                                .baseUrl(BuildConfig.BASE_URL)
//                                .addConverterFactory(ScalarsConverterFactory.create())
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//                        Converter<ResponseBody, ModelError> converter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);
//
//                    } else {
//                        if (downloadBoth) {
//                            //Log.e("Download Both","2nd Started")
//                            downloadBoth = false;
//                            mWebRequest = new WebRequest(MyFirebaseMessagingService.this, "fp");
//                            downloadFEAssignedChecks(user_id, vendor_id, objCheckDownload, mWebRequest);
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    //MyApplication.getInstance().appendLog(TAG+": "+e.getLocalizedMessage())
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ChecksDownloaded> call, Throwable t) {
//            }
//        });
//    }
//
//
//
//
//    private void downloadImageRetroEduction(String url, String caseCheckId, String id) {
//        //Log.e("Download Image called","true")
//        Call<ResponseBody> call = mWebRequest.m_ApiInterface.fetchImage(url);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    // display the image data in a ImageView or save it
//                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//                    try {
//
//                        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "OpusEducation");
//                        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
//                            Log.d(TAG, "failed to create directory");
//                        }
//
//                        File dir = new File(mediaStorageDir.getPath() + "/" + caseCheckId);
//                        try {
//                            if (dir.mkdir()) {
//                                System.out.println("Directory created");
//                            } else {
//                                System.out.println("Directory is not created");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        File file = new File(dir.getPath() + File.separator + id + ".jpg");
//
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
//                        byte[] bitmapdata = bos.toByteArray();
//
//                        FileOutputStream fos = new FileOutputStream(file);
//                        fos.write(bitmapdata);
//                        fos.flush();
//                        fos.close();
//
//                    } catch (Exception e) {
//                        Log.i("img_save", e.getLocalizedMessage());
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                // This is retrofit Failure
//            }
//        });
//
//    }
//
//    public JSONObject makJsonObjectInput(String fe_id, String vendor_id, String user_id, String downloaded_checks) throws JSONException {
//        JSONObject finalobject = new JSONObject();
//        JSONObject finalobjectK = new JSONObject();
//        finalobjectK.put("fe_id", fe_id);
//        finalobjectK.put("vendor_id", vendor_id);
//        finalobjectK.put("user_id", user_id);
//        if (!downloaded_checks.equalsIgnoreCase("Splash")) {
//            finalobjectK.put("downloaded_checks", downloaded_checks);
//        }
//        finalobject.put("data", finalobjectK);
//        //Log.e("Download Check JSON",finalobject.toString())
//        return finalobject;
//    }
//
//    @Override
//    public void onWithdrawnCheckDownloadCompleted(int count) {
//        OpusConnectSingleTon.getInstance().getUpdateFirebaseNotification().onNotificationUpdate();
//
//    }
//
//    @Override
//    public void onWithdrawnError(ModelError error) {
//
//    }
//
//    @Override
//    public void onMasterDataDownloadCompleted(String fe_id, String user_id, String vendor_id) {
//        OpusConnectSingleTon.getInstance().getUpdateFirebaseNotification().onNotificationUpdate();
//    }
//
//    @Override
//    public void onCheckFieldDownloadCompleted() {
//
//
//    }
//
//    @Override
//    public void onNotificationDownloadCompleted(int count) {
//
//    }
//
//    @Override
//    public void onError(ModelError error) {
//
//    }
//}