package com.hch.hooney.tourtogether;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourAPI.Accomodation;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DiningAndAccomodationActivity extends AppCompatActivity {
    private final String TAG = "DiningAndAccomodationActivity";
    private ProgressDialog asyncDialog;

    //layout
    //share
    private ImageButton back;
    private TextView Field;
    private ImageButton bookmaking;
    private TextView modifyDate;
    private TextView title;
    private ImageView mainImage;
    private TextView addr;

    private LinearLayout homepageLayout;
    private TextView homepage;
    private LinearLayout telLayout;
    private TextView tel;
    private LinearLayout directionlayout;
    private TextView direction;
    private TextView overview;

    private LinearLayout smallLayout;
    private RecyclerView smallList;

    private MapFragment mapFragment;


    //d
    private LinearLayout d_layout;


    //a
    private LinearLayout a_layout;

    //variable
    private GoogleMap googleMap;


    private Accomodation accomodation;
    private String url_basic_info;
    private String url_intro_info;
    private String url_images_info;

    private String ContentID;
    private String ContentTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_and_accomodation);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            ContentTypeID = getIntent().getStringExtra("ContentTypeID");
            ContentID = getIntent().getStringExtra("ContentID");

            init();
            setURLS();

            if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
                asyncDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //loadDate
                        parse_xml_Accom_basic();
                        parse_xml_Accom_intro();
                        parse_xml_Accom_smallImages();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUI();
                                asyncDialog.dismiss();
                            }
                        });

                    }
                }).start();
            }else{

            }
        }

    }

    private void init(){
        asyncDialog = new ProgressDialog(getApplicationContext());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
            accomodation = new Accomodation();
        }else{

        }
    }

    private void setUI(){

    }

    private void setURLS(){
        String service="";
        if(DAO.Language.equals("ko")){
            service="KorService";
        }else{
            service="EngService";
        }

        if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
            url_basic_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailCommon?ServiceKey="+
                    DAO.ServiceKey+"&contentTypeId="+ContentTypeID+
                    "&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailIntro?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+
                    "&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
            url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                            "/detailImage?ServiceKey="+DAO.ServiceKey+
                            "&contentTypeId="+ContentTypeID+
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+ContentID+"&imageYN=Y";
        }else{

        }
    }

    private void parse_xml_Accom_smallImages(){
        ArrayList<String> tempList = new ArrayList<String>();
        try {
            URL url= new URL(url_images_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("smallimageurl")){
                            xpp.next();
                            tempList.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            accomodation.setSmallImageList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Accom_intro(){
        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(url_intro_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("checkintime")){
                            xpp.next();
                            accomodation.setIntro_checkInTime(xpp.getText());
                        }else if(tag.equals("checkouttime")){
                            xpp.next();
                            accomodation.setIntro_checkOutTime(xpp.getText());
                        }else if(tag.equals("chkcooking")){
                            xpp.next();
                            accomodation.setIntro_chkCooking(xpp.getText());
                        }else if(tag.equals("foodplace")){
                            xpp.next();
                            accomodation.setIntro_foodPlace(xpp.getText());
                        }else if(tag.equals("infocenterlodging")){
                            xpp.next();
                            accomodation.setIntro_infoCenter(xpp.getText());
                        }else if(tag.equals("parkinglodging")){
                            xpp.next();
                            accomodation.setIntro_parking(xpp.getText());
                        }else if(tag.equals("reservationlodging")){
                            xpp.next();
                            accomodation.setIntro_reservation(xpp.getText());
                        }else if(tag.equals("roomtype")){
                            xpp.next();
                            accomodation.setIntro_roomtype(xpp.getText());
                        }else if(tag.equals("subfacility")){
                            xpp.next();
                            accomodation.setIntro_subFacility(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }//getXmlData method...

    private void parse_xml_Accom_basic(){
        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(url_basic_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("addr1")){
                            xpp.next();
                            accomodation.setBasic_addr1(xpp.getText());
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            String temp = accomodation.getBasic_addr1();
                            accomodation.setBasic_addr1(temp+" "+xpp.getText());
                        }else if(tag.equals("contentid")){
                            xpp.next();
                            accomodation.setBasic_contentID(xpp.getText());
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            accomodation.setBasic_contentTypeID(xpp.getText());
                        }else if(tag.equals("directions")){
                            xpp.next();
                            accomodation.setBasic_directory(xpp.getText());
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            accomodation.setBasic_firstImage(xpp.getText());
                        }else if(tag.equals("homepage")){
                            xpp.next();
                            accomodation.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            accomodation.setBasic_mapX(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            accomodation.setBasic_mapY(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            accomodation.setBasic_modifyDate(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            accomodation.setBasic_overView(xpp.getText());
                        }else if(tag.equals("tel")){
                            xpp.next();
                            accomodation.setBasic_tel(xpp.getText());
                        }else if(tag.equals("title")){
                            xpp.next();
                            accomodation.setBasic_title(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
                        }
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

}
