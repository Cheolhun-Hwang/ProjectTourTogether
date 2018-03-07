package com.hch.hooney.tourtogether;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.hch.hooney.tourtogether.ResourceCTRL.ConvertAreaCode;
import com.hch.hooney.tourtogether.ResourceCTRL.Location;
import com.hch.hooney.tourtogether.Service.GPS;
import com.hch.hooney.tourtogether.TranslateNaver.PapagoSMT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostEditActivity extends AppCompatActivity {
    private final String TAG = "PostEditActivity";
    private final int SIGNAL_SELECTMAP = 6001;
    private final int SIGNAL_PERMISSON_GPS = 6002;
    private final int SIGNAL_PERMISSON_GALLERY = 6003;
    private final int SIGNAL_GALLERY = 6004;

    private ProgressDialog asyncDialog;

    private ImageButton back;
    private Button complete;

    private Spinner field;
    private TextView showLocation;
    private Button autoLocation;
    private Button selectLocation;
    private EditText detailLocation;    //text

    private ImageView showMainImage;
    private Button importMainImage;

    private TextView textCount;
    private EditText context;   //overview

    private ProgressBar progressBar;

    private FirebaseStorage storage;
    private StorageReference stref;
    private DatabaseReference mRootRef;
    private DatabaseReference postRef;
    private DatabaseReference userRef;

    //variable
    private mainPostItem item;
    private Handler handler;
    private Bitmap mainPostImage;
    private boolean isContextRight;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit);

        init();
        setEvent();
    }

    private void init(){
        //variable
        item = new mainPostItem();
        isContextRight = true;

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_sss");
        key =dateFormat2.format(date)+"__"+ DAO.user.getUID();
        String fileName = "i_"+dateFormat2.format(date)+".jpg";

        mRootRef = FirebaseDatabase.getInstance().getReference();
        postRef = mRootRef.child("post");
        userRef = mRootRef.child("ulog").child(DAO.user.getUID());
        storage = FirebaseStorage.getInstance();
        stref = storage.getReference("post/"+DAO.user.getUID()+"/"+fileName);

        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 1001:
                        checkDangerousPermissionsForGPS();
                        break;
                    case 1002:
                        checkDangerousPermissionsForGALLERY();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //layout
        back = (ImageButton) findViewById(R.id.create_post_back);
        complete = (Button) findViewById(R.id.create_post_complete);
        field = (Spinner) findViewById(R.id.create_post_spinner_field);
        showLocation = (TextView) findViewById(R.id.create_post_show_location);
        autoLocation = (Button) findViewById(R.id.create_post_find_auto_btn);
        selectLocation = (Button) findViewById(R.id.create_post_find_select_btn);
        detailLocation = (EditText) findViewById(R.id.create_post_detail_location_name);
        showMainImage = (ImageView) findViewById(R.id.create_post_show_photo);
        importMainImage = (Button) findViewById(R.id.create_post_select_photo_btn);
        textCount = (TextView) findViewById(R.id.create_post_textCount);
        context = (EditText) findViewById(R.id.create_post_context);
        progressBar = (ProgressBar) findViewById(R.id.create_post_progress_bar);
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.create_post_alert_cancel), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempField = field.getSelectedItem().toString();
                setCatOnItem(tempField);
                String tempAddress = showLocation.getText().toString();
                String tempDetail = detailLocation.getText().toString();
                if(tempAddress.contains("now") || tempDetail == null || tempDetail.equals("") ){
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.create_post_error_location), Toast.LENGTH_SHORT).show();
                }else{
                    String tempContext = context.getText().toString();
                    if(tempContext.length() < 1){
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.create_post_error_content), Toast.LENGTH_SHORT).show();
                    }else{
                        showPostOk();
                    }
                }
            }
        });

        autoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg_getLocation = Message.obtain();
                        msg_getLocation.what=1001;
                        handler.sendMessage(msg_getLocation);
                    }
                }).start();
            }
        });
        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectMapActivity.class);
                intent.putExtra("who", "edit");
                startActivityForResult(intent, SIGNAL_SELECTMAP);
            }
        });

        importMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg_getLocation = Message.obtain();
                        msg_getLocation.what=1002;
                        handler.sendMessage(msg_getLocation);
                    }
                }).start();
            }
        });

        context.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if(length > 150){
                    isContextRight = false;
                    textCount.setTextColor(getResources().getColor(R.color.red_400));
                }else{
                    isContextRight = true;
                    textCount.setTextColor(getResources().getColor(R.color.grey_600));
                }
                textCount.setText(length+" / 150");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setCatOnItem(String cat){
        String temp = cat.replaceAll("\n", " ");
        if(temp.equals(getResources().getString(R.string.search_tab1).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("12");
            }else{
                item.setContentTypeID("76");
            }
        }else if(temp.equals(getResources().getString(R.string.search_tab2).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("14");
            }else{
                item.setContentTypeID("78");
            }
        }else if(temp.equals(getResources().getString(R.string.search_tab3).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("28");
            }else{
                item.setContentTypeID("75");
            }
        }else if(temp.equals(getResources().getString(R.string.search_tab4).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("38");
            }else{
                item.setContentTypeID("79");
            }
        }else if(temp.equals(getResources().getString(R.string.search_tab5).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("39");
            }else{
                item.setContentTypeID("82");
            }
        }else if(temp.equals(getResources().getString(R.string.search_tab6).replaceAll("\n", " "))){
            if(DAO.Language.equals("ko")){
                item.setContentTypeID("32");
            }else{
                item.setContentTypeID("80");
            }
        }else{

        }
        //Log.d(TAG, "ContentTypeID : "+item.getContentTypeID());
    }

    /* 사용자 권한 확인 메서드
           - import android.Manifest; 를 시킬 것
         */
    private void checkDangerousPermissionsForGPS() {
        String[] permissions = {//import android.Manifest;
                android.Manifest.permission.ACCESS_FINE_LOCATION,   //GPS 이용권한
                android.Manifest.permission.ACCESS_COARSE_LOCATION, //네트워크/Wifi 이용 권한
                android.Manifest.permission.INTERNET                 //인터넷 사용 권한
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Log.d(TAG, "권한있음");
            runGPS();
        } else {
            //Log.d(TAG, "권한없음");

            ActivityCompat.requestPermissions(this, permissions, SIGNAL_PERMISSON_GPS);
        }
    }//end of checkDangerousPermissions

    /* 사용자 권한 확인 메서드
               - import android.Manifest; 를 시킬 것
             */
    private void checkDangerousPermissionsForGALLERY() {
        String[] permissions = {//import android.Manifest;
                Manifest.permission.WRITE_EXTERNAL_STORAGE,   //쓰기  권한
                Manifest.permission.READ_EXTERNAL_STORAGE,      //읽기 권한
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Log.d(TAG, "권한있음");
            runGallery();
        } else {
            //Log.d(TAG, "권한없음");

            ActivityCompat.requestPermissions(this, permissions, SIGNAL_PERMISSON_GALLERY);
        }
    }//end of checkDangerousPermissions

    // 사용자의 권한 확인 후 사용자의 권한에 대한 응답 결과를 확인하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == SIGNAL_PERMISSON_GPS) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Log.d(TAG, "권한 승인");
                    runGPS();
                } else {
                    //Log.d(TAG, "권한 승인되지 않음.");
                }
            }
        }else if(requestCode == SIGNAL_PERMISSON_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Log.d(TAG, "권한 승인");
                    runGallery();
                } else {
                    //Log.d(TAG, "권한 승인되지 않음.");
                }
            }
        }
    }//end of onRequestPermissionsResult



    private void runGPS(){
        asyncDialog.show();
        GPS gps = new GPS(this);
        if(gps.isGetLocation()){
            item.setMapy(gps.getLat());
            item.setMapx(gps.getLon());
            if(item.getMapy() == 0.0 && item.getMapx() == 0.0 ){
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_gps_loading), Toast.LENGTH_SHORT).show();
            }else{
                Location location = new Location(getApplicationContext(), item.getMapy(), item.getMapx());
                String areaResult = location.searchLocation();

                showLocation.setText(areaResult);

                ConvertAreaCode convertAreaCode = new ConvertAreaCode(getApplicationContext());
                convertAreaCode.filteringToAuto(areaResult);
                item.setAreaCode(convertAreaCode.getAreaCode());
                item.setSigunguCode(convertAreaCode.getSigunguCode());

            }
            asyncDialog.dismiss();
            gps.stopUsingGPS();
        }else{
            asyncDialog.dismiss();
            gps.showSettingAlert();
        }
    }

    private void runGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SIGNAL_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNAL_GALLERY){
            if(resultCode == Activity.RESULT_OK){
                try {
                    mainPostImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(data.getDataString()));
                    //Log.d("Bitmap", "before ==> width : " + mainPostImage.getWidth()+" / Height : " + mainPostImage.getHeight());

                    //리사이즈
                    mainPostImage = resizeBitmapImageFn(mainPostImage, 400);
                    //Log.d("Bitmap", "after ==> width : " + mainPostImage.getWidth()+" / Height : " + mainPostImage.getHeight());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                showMainImage.setImageBitmap(mainPostImage);
            }else{

            }
        }else if(requestCode == SIGNAL_SELECTMAP){
            if(resultCode == Activity.RESULT_OK){
                item.setMapy(data.getDoubleExtra("mapy", 0.0));
                item.setMapx(data.getDoubleExtra("mapx", 0.0));
                if(item.getMapy() == 0.0 && item.getMapx() == 0.0 ){
                }else{
                    try{
                        //Log.d("Selected map", "Mapy : " + item.getMapy()+" / Mapx : " + item.getMapx());

                        Location location = new Location(getApplicationContext(), item.getMapy(), item.getMapx());
                        String areaResult = location.searchLocation();

                        //Log.d("Location convert", "Location : " + location);
                        showLocation.setText(areaResult);

                        ConvertAreaCode convertAreaCode = new ConvertAreaCode(getApplicationContext());
                        convertAreaCode.filteringToAuto(areaResult);
                        item.setAreaCode(convertAreaCode.getAreaCode());
                        item.setSigunguCode(convertAreaCode.getSigunguCode());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void showPostOk(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.create_post_alert_title));
        alertDialog.setMessage(getResources().getString(R.string.create_post_alert_msg));

        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upload();
                    }
                });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void upload(){
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                item.setReadCount("0");
                item.setContentID(key);
                item.setAddr1(showLocation.getText().toString());

                item.setCommentCount("0");
                Date nowDate = new Date();
                SimpleDateFormat dateSet = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                item.setModifyDateTIme(dateSet.format(nowDate));
                item.setPost(1);

                item.setUCountry(DAO.Country);
                item.setUId(DAO.user.getUID());
                item.setUNAME(DAO.user.getUNAME());
                item.setUPROFILEIMAGE(DAO.user.getUPROFILEIMAGE());
                item.setuLanguage(DAO.Language);
                item.setBasic_overView(context.getText().toString());
                item.setTitle(detailLocation.getText().toString());

                String from = DAO.Language;
                String to = "";
                if(from.equals("ko")){
                    to = "en";
                    item.setKrContext(context.getText().toString());
                    String res = new PapagoSMT(PostEditActivity.this, from, to, context.getText().toString()).send();
                    String rest = new PapagoSMT(PostEditActivity.this, from, to, detailLocation.getText().toString()).send();
                    if(res.equals("none")){
                        item.setTrtitle(detailLocation.getText().toString());
                        item.setEnContext(context.getText().toString());
                    }else{
                        item.setTrtitle(rest);
                        item.setEnContext(res);
                    }
                }else if(from.equals("en")){
                    to="ko";
                    item.setEnContext(context.getText().toString());
                    String res = new PapagoSMT(PostEditActivity.this, from, to, context.getText().toString()).send();
                    String rest = new PapagoSMT(PostEditActivity.this, from, to, detailLocation.getText().toString()).send();
                    if(res.equals("none")){
                        item.setTrtitle(detailLocation.getText().toString());
                        item.setKrContext(context.getText().toString());
                    }else{
                        item.setTrtitle(rest);
                        item.setKrContext(res);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mainPostImage ==null){
                            item.setFirstImage("");
                            postRef.child(key).setValue(item);
                            userRef.child(key).setValue(item);

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), getResources().getText(R.string.create_post_alert_complete), Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            //Bitmap to byteArray
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mainPostImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            UploadTask uploadTask = stref.putBytes(byteArray);
                            uploadTask.addOnSuccessListener(PostEditActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    item.setFirstImage(taskSnapshot.getDownloadUrl().toString());
                                    postRef.child(key).setValue(item);
                                    userRef.child(key).setValue(item);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.create_post_alert_complete), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });

            }
        }).start();
    }

    private Bitmap resizeBitmapImageFn(Bitmap bmpSource, int maxResolution){
        //가로 세로 모두해서 최소 max 해상도로
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }
        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }
}
