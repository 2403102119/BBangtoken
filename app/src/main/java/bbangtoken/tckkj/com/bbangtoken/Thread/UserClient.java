package bbangtoken.tckkj.com.bbangtoken.Thread;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.Toast;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import bbangtoken.tckkj.com.bbangtoken.App;
import bbangtoken.tckkj.com.bbangtoken.R;
import bbangtoken.tckkj.com.bbangtoken.Util.DialogUtils;
import bbangtoken.tckkj.com.bbangtoken.Util.MUIToast;
import bbangtoken.tckkj.com.bbangtoken.Util.SPUtil;
import bbangtoken.tckkj.com.bbangtoken.Util.TestOkHttp;
import bbangtoken.tckkj.com.bbangtoken.Util.TokenErrorUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * kylin on 2017/6/26.
 */

public class UserClient {

    public static final int SHOW_LOADING = 0x10;
    public static final int DIMISS_LOADING = 0x11;
    private static final String TAG = "userclient";

    private Class<?> mClass;
    private HashMap<String, String> params;
    private HashMap<String, String> headers;
    private List<Pair<String, File>> filePairList;

    private String urlLink;

    private int responseCode;
    private String message;

    private String response = "";

    public String httpurl;

    //private Context context;


    final Handler mDeliver = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_LOADING:
                    if (loadingDialog != null) {
                        loadingDialog.show();
                    }
                    break;
                case DIMISS_LOADING:
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                    break;
            }
        }
    };
    private File file;
    private Response fileResponse;
    private LoadingDialog loadingDialog;
    private DialogUtils dialogUtils;

    public enum RequestMethod {
        POST, POSTFILE, GET, GETFILE, GETTASKFILE
    }

    public UserClient(String url, Class<?> mClass) {
        this.urlLink = url;
        params = new HashMap<>();
        headers = new HashMap<>();
        filePairList = new ArrayList<>();
        this.mClass = mClass;
    }

    public UserClient(String url) {
        this.urlLink = url;
        params = new HashMap<>();
        headers = new HashMap<>();
        filePairList = new ArrayList<>();
        this.mClass = mClass;
    }

    public void AddParam(String name, String value) {
        params.put(name, value);
    }

    public void AddHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addFiles(Pair<String, File> file) {
        filePairList.add(file);
    }

    /**
     * 同步调用，无结果回调。调用方不能再主线程中，需开子线程。
     *
     * @param method
     * @throws IOException
     */
    public void executeWithOkhttp(RequestMethod method) throws IOException,
            SocketTimeoutException {
        switch (method) {
            case GET:
                Response responseGet = OkHttpUtils.get().url(urlLink).headers(headers).params(params)
                        .build().execute();
                if (responseGet.isSuccessful()) {
                    this.response = getStringFromInputStream(responseGet.body()
                            .byteStream());
                }
                break;
            case POST:
                Response responsePost = OkHttpUtils.post().url(urlLink).headers(headers).params(params).build().execute();
                if (responsePost.isSuccessful()) {
                    this.response = getStringFromInputStream(responsePost.body()
                            .byteStream());
                }
                break;
            case POSTFILE:
                PostFormBuilder formBuilder = OkHttpUtils.post();
                if (filePairList.size() > 0) {
                    for (int i = 0; i < filePairList.size(); i++) {
                        Pair<String, File> filePair = filePairList.get(i);
                        formBuilder.addFile(filePair.first, filePair.second.getName(), filePair.second);
                    }
                }

                Response responsePostFile = formBuilder.url(urlLink).headers(headers).params(params).build().execute();
                if (responsePostFile.isSuccessful()) {
                    this.response = getStringFromInputStream(responsePostFile
                            .body().byteStream());
                }
            case GETFILE:
                Response responseGetFile = OkHttpUtils.post().url(urlLink).headers(headers).params(params).build().execute();

                if (responseGetFile.isSuccessful()) {
                    String fileOutPath = getFileFromInputStream(responseGetFile.body().byteStream());
                    if (fileOutPath != null) {
                        File file = new File(fileOutPath);
                        this.file = file;
                    }
                }
                break;
            case GETTASKFILE:
                Response responseGetTaskFile = OkHttpUtils.post().url(urlLink).headers(headers).params(params).build().execute();

                if (responseGetTaskFile.isSuccessful()) {
                    this.fileResponse = responseGetTaskFile;
                }
                break;
            default:
                break;
        }

    }


    private String getFileFromInputStream(InputStream byteStream) {
        File file = null;
        String fileName = urlLink.substring(urlLink.lastIndexOf("/") + 1);
        String outPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String dirPath = Environment.getExternalStorageDirectory() + "/zywx/";
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            try {
                String filePath = dirPath + fileName;
                file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream os = new FileOutputStream(file);
                int len = 0;
                byte[] bys = new byte[1024];
                while ((len = byteStream.read(bys)) != -1) {
                    os.write(bys, 0, len);
                }
                os.close();
                byteStream.close();
                outPath = ZipUtils.getInstance().unZipFiles(filePath, dirPath);
            } catch (IOException e) {
//                MLog.e("--RestClient--", "e:", e);
                e.printStackTrace();
            }
        }
        return outPath;

    }

    private boolean show = true;

    public void executePost(final MApiResultCallback callback, final LoadingDialog loadingDialog, final Context context) {

        params.put("token", App.token+"");
        params.put("user_id",App.user_id+"");
//        params.put("project_name","xiaoxiongmao");
        Log.e("userclient token",App.token+" "+urlLink);
        Log.e("userclient params",params+" ");
        show = true;
        if (loadingDialog != null) {
            mDeliver.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (show) {
                        loadingDialog.show();
                    }
                }
            }, 5000);
        }

        Log.e("开始访问:urlLink",""+urlLink);
        OkHttpUtils.post().url(urlLink).params(params).headers(headers).tag(urlLink)
                .build().connTimeOut(60 * 1000).readTimeOut(60 * 1000).writeTimeOut(60 * 1000)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response) {
                        show = false;
                        Log.e("访问结束:urlLink",""+urlLink);
                        Log.e("resposeData", response.toString());
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            mDeliver.post(new Runnable() {
                                @Override
                                public void run() {
                                    //mDeliver.removeMessages(SHOW_LOADING);
                                    loadingDialog.dismiss();
                                }
                            });
                        }

                        if (checkUserLoginState(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.has("flag")) {
                                    String flag = (String) jsonObject.optString("flag","");
                                    String error_code = (String) jsonObject.optString("error_code","");
                                    Log.e("flag",flag+"");
                                    if ("success".equals(flag)) {
                                        callback.onSuccess(response);
                                        callback.onFinish(response);
                                    } else if ("error".equals(flag)){
                                        if (App.islogin&&"1111".equals(error_code)){
                                            mDeliver.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    /*TODO token9 */
                                                    LayoutInflater inflater =
                                                            (LayoutInflater)context.getSystemService
                                                                    (Context.LAYOUT_INFLATER_SERVICE);
                                                    TokenErrorUtil tu = new TokenErrorUtil(inflater);
                                                    tu.tokenError(context);
                                                }
                                            });
                                        }
                                        callback.onFail(response);
                                        callback.onFinish(response);
                                    }else{
                                        callback.onFail(response);
                                        callback.onFinish(response);
                                    }
                                }else{
                                    callback.onFail(response);
                                    callback.onFinish(response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onFail(HandleResponseCode.RESPONSE_NOTLOGIN);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception exception) {
                        exception.printStackTrace();
                        show = false;
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            mDeliver.post(new Runnable() {
                                @Override
                                public void run() {
                                    //mDeliver.removeMessages(SHOW_LOADING);
                                    loadingDialog.dismiss();
                                }
                            });
                        }
                        mDeliver.post(new Runnable() {
                            @Override
                            public void run() {
//                                MUIToast.toast(context, R.string.connect_failed_toast);
                                Toast.makeText(context,R.string.connect_failed_toast,Toast.LENGTH_SHORT).show();
                            }
                        });
                        callback.onError(call, exception);
                    }
                });
    }

    /**
     * 需要根据执行结果继续下一步操作的执行本方法
     *
     * @param requestMethod
     * @param callback      结果回调
     */
    public void executeWithOkHttpCallback(RequestMethod requestMethod, final MApiResultCallback callback, final LoadingDialog loadingDialog) {

        //params.put("token", App.token + "");

        switch (requestMethod) {
            case GET:
                OkHttpUtils.get().url(urlLink).headers(headers).params(params)
                        .build().connTimeOut(1 * 15 * 1000).readTimeOut(1 * 15 * 1000).writeTimeOut(1 * 15 * 1000).execute(new StringCallback() {
                    @Override
                    public void onResponse(String response) {
                        if (checkUserLoginState(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("json", response.toString());
                                if (jsonObject.has("status")) {
                                    int status = jsonObject.optInt("status", -1);
                                    if (status == 1) {
                                        callback.onSuccess(response);
                                    } else if (status == 9) {
                                        callback.onFinish(response);


                                    } else {
                                        callback.onFail(response);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onFail(HandleResponseCode.RESPONSE_NOTLOGIN);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception exception) {
                        callback.onError(call, exception);
                    }
                });
                break;
            case POST:
                OkHttpUtils.post().url(urlLink).params(params).headers(headers)
                        .build().connTimeOut(15 * 1000).readTimeOut(20 * 1000).writeTimeOut(15 * 1000)
                        .execute(new StringCallback() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("resposeData", response.toString());

                                if (checkUserLoginState(response)) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(
                                                response);
                                        if (jsonObject.has("status")) {
                                            int status = (int) jsonObject.optInt("status", -1);
                                            Log.e("status", status + "");
                                            if (status == 1) {
                                                callback.onSuccess(response);
                                            } else if (status == 9) {
                                                callback.onFinish(response);
                                            } else {
                                                callback.onFail(response);
                                            }
                                        } else {
                                            callback.onFail(response);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    callback.onFail(HandleResponseCode.RESPONSE_NOTLOGIN);
                                }
                            }

                            @Override
                            public void onError(Call call, Exception exception) {
                                exception.printStackTrace();
                                callback.onError(call, exception);
                            }
                        });
                break;
            case POSTFILE:
                PostFormBuilder formBuilder = OkHttpUtils.post();
                if (filePairList.size() > 0) {
                    for (int i = 0; i < filePairList.size(); i++) {
                        Pair<String, File> filePair = filePairList.get(i);
                        formBuilder.addFile(filePair.first, filePair.second.getName(), filePair.second);
                    }
                }
                formBuilder
                        .url(urlLink)
                        .params(params)
                        .headers(headers)
                        .build().connTimeOut(15 * 1000).readTimeOut(15 * 1000).writeTimeOut(15 * 1000).execute(new StringCallback() {
                    @Override
                    public void onResponse(String response) {
//                        MLog.d("ResetClient", "Fileresponse:" + response.toString());
                        if (checkUserLoginState(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(
                                        response);
                                if (jsonObject.has("status")) {
                                    int status = jsonObject.optInt("status", -1);
                                    if (status == 1) {
                                        callback.onSuccess(response);
                                    } else if (status == 9) {
                                        callback.onFinish(response);
                                    } else {
                                        callback.onFail(response);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onFail(HandleResponseCode.RESPONSE_NOTLOGIN);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception exception) {
                        callback.onError(call, exception);
                    }

                    @Override
                    public void inProgress(float f) {
                        callback.inProgress(f);
                    }
                });

                break;
            default:
                break;
        }

    }

    public static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        is.close();
        String state = bos.toString();
        bos.close();
        return state;
    }


    public String getHttpurl() {
        return httpurl;
    }

    public void setHttpurl(String httpurl) {
        this.httpurl = httpurl;
    }

    public String getResponse() {
        if (checkUserLoginState(response)) {
            return response;
        }
        return "";
    }

    private boolean checkUserLoginState(String response) {
//        MLog.i("--response--", "" + response);
        if (!TextUtils.isEmpty(response) && response.indexOf("DOCTYPE") > -1) {
//			AppContext.getInstance().autoLogin();
            return false;
        }
        return true;
    }

    public String getErrorMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setUrl(String url) {
        this.urlLink = url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Response getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(Response fileResponse) {
        this.fileResponse = fileResponse;
    }


    /**
     * 其他设备登录提示
     */
    public void sendRedEnvelopes(final Activity ctx, String title, String content, String left, String right) {
        App.islogin = false;
        App.account="";
        App.token="";

        SPUtil.saveData(ctx, "islogin", false);
        SPUtil.saveData(ctx, "account", "");
        SPUtil.saveData(ctx, "token", "");
        SPUtil.saveData(ctx, "isBindAlias", false);
        dialogUtils = new DialogUtils(ctx, true, "账号异常", "您还未登录，请登录!", "取消", "去登录", true, true);
        dialogUtils.setOnOneBtnClickListener(new DialogUtils.OnOneBtnClickListener() {
            @Override
            public void onClick() {
                dialogUtils.hide();
                if (dialogUtils!=null){
                    dialogUtils=null;
                }
            }
        });
        dialogUtils.setOnTwoBtnClickListener(new DialogUtils.OnTwoBtnClickListener() {
            @Override
            public void onClick() {
                dialogUtils.hide();
                if (dialogUtils!=null){
                    dialogUtils=null;
                }
            }
        });
        dialogUtils.show();


    }


    /**
     * 第四步 上传文件
     */
    public void upLoadData(String fileKey, String data, final MApiResultCallback callBack, final LoadingDialog loadingDialog, final Context context) {
        params.put("token", App.token+"");
        params.put("user_id",App.user_id+"");

        Log.e("userclient token",App.token+" "+urlLink);
        Log.e("userclient params",params+" ");
        show = true;
        if (loadingDialog != null) {
            mDeliver.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (show) {
                        loadingDialog.show();
                    }
                }
            }, 500);
        }

        Log.e("开始访问:urlLink",""+urlLink);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES).build();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != new TestOkHttp(context).prepareData(data, urlLink)) {
            File file = new File(new TestOkHttp(context).prepareData(data, urlLink));
            RequestBody body = RequestBody.create(MediaType.parse("octet-stream"), file);
            requestBody.addFormDataPart(fileKey, file.getName(), body);

        }

        //TODO 添加参数
        Set set = params.keySet();
        for (Object key :set) {
            requestBody.addFormDataPart((String) key, params.get(key));
        }
        Request request = new Request.Builder().url(urlLink)
                .post(requestBody.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                show = false;
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    mDeliver.post(new Runnable() {
                        @Override
                        public void run() {
                            //mDeliver.removeMessages(SHOW_LOADING);
                            loadingDialog.dismiss();
                        }
                    });
                }
                mDeliver.post(new Runnable() {
                    @Override
                    public void run() {
                        MUIToast.toast(context, R.string.connect_failed_toast);
                    }
                });
                callBack.onError(call, e);
            }


            @Override
            public void onResponse(Call call, Response response2) throws IOException {
                show = false;
                Log.e("访问结束:urlLink",""+urlLink);
                Log.e("resposeData", response.toString());
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    mDeliver.post(new Runnable() {
                        @Override
                        public void run() {
                            //mDeliver.removeMessages(SHOW_LOADING);
                            loadingDialog.dismiss();
                        }
                    });
                }

                final String response = response2.body().string();
                if (checkUserLoginState(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("flag")) {
                            String flag = (String) jsonObject.optString("flag","");
                            String error_code = (String) jsonObject.optString("error_code","");
                            Log.e("flag",flag+"");
                            if ("success".equals(flag)) {
                                mDeliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onSuccess(response);
                                        callBack.onFinish(response);
                                    }
                                });
                            } else if ("error".equals(flag)){
                                if (App.islogin&&"1111".equals(error_code)){
                                    mDeliver.post(new Runnable() {
                                        @Override
                                        public void run() {
                                                    /*TODO token9 */
                                            LayoutInflater inflater =
                                                    (LayoutInflater)context.getSystemService
                                                            (Context.LAYOUT_INFLATER_SERVICE);
                                            TokenErrorUtil tu = new TokenErrorUtil(inflater);
                                            tu.tokenError(context);
                                        }
                                    });
                                }
                                callBack.onFail(response);
                                callBack.onFinish(response);
                            }else{
                                callBack.onFail(response);
                                callBack.onFinish(response);
                            }
                        }else{
                            callBack.onFail(response);
                            callBack.onFinish(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callBack.onFail(HandleResponseCode.RESPONSE_NOTLOGIN);
                }
            }
        });
    }

}