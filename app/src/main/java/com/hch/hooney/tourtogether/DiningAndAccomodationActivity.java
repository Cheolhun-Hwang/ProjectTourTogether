package com.hch.hooney.tourtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DiningAndAccomodationActivity extends AppCompatActivity {

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
                parse_xml_Accom_basic();
            }else{

            }
        }

    }

    private void init(){

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

                        if(tag.equals("smallimageurl")){

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

    private void parse_xml_Accom_intro(){
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

                        if(tag.equals("checkintime")){

                        }else if(tag.equals("checkouttime")){

                        }else if(tag.equals("chkcooking")){

                        }else if(tag.equals("foodplace")){

                        }else if(tag.equals("infocenterlodging")){

                        }else if(tag.equals("parkinglodging")){

                        }else if(tag.equals("reservationlodging")){

                        }else if(tag.equals("roomtype")){

                        }else if(tag.equals("subfacility")){

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

                        }else if(tag.equals("contentid")){

                        }else if(tag.equals("contenttypeid")){

                        }else if(tag.equals("directions")){

                        }else if(tag.equals("firstimage")){

                        }else if(tag.equals("homepage")){

                        }else if(tag.equals("mapx")){

                        }else if(tag.equals("mapy")){

                        }else if(tag.equals("modifiedtime")){

                        }else if(tag.equals("overview")){

                        }else if(tag.equals("tel")){

                        }else if(tag.equals("title")){

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
