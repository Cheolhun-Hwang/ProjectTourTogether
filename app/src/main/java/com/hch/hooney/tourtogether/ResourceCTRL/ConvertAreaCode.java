package com.hch.hooney.tourtogether.ResourceCTRL;

import android.content.Context;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.R;

/**
 * Created by hooney on 2018. 1. 27..
 */

public class ConvertAreaCode {
    private Context mContext;
    private String AreaCode;
    private String SigunguCode;

    public ConvertAreaCode(Context context) {
        this.mContext = context;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public String getSigunguCode() {
        return SigunguCode;
    }

    public void filteringToAuto(String AutoResult){

        if(DAO.Language.equals("ko")){
            String split[] = AutoResult.split(" ");

            getAreaAndSiCode(split[1], split[2]);

        }else{
            String split[] = AutoResult.split(", ");

            getAreaAndSiCode(split[3], split[2]);

        }
    }

    public void filteringToSelect(String area, String si){
        getAreaAndSiCode(area, si);
    }

    public void getAreaAndSiCode(String area, String si){
        getArea(area);
        getSi(si);
    }

    public void getArea(String area){
        if(area.contains("서울") || area.toLowerCase().contains("seoul")){
            this.AreaCode = "1";
        }else if(area.contains("인천")||area.toLowerCase().contains("incheon")){
            this.AreaCode = "2";
        }else if(area.contains("대전")||area.toLowerCase().contains("daejeon")){
            this.AreaCode = "3";
        }else if(area.contains("대구")||area.toLowerCase().contains("daegu")){
            this.AreaCode = "4";
        }else if(area.contains("광주")||area.toLowerCase().contains("gwangju")){
            this.AreaCode = "5";
        }else if(area.contains("부산")||area.toLowerCase().contains("busan")){
            this.AreaCode = "6";
        }else if(area.contains("울산")||area.toLowerCase().contains("ulsan")){
            this.AreaCode = "7";
        }else if(area.contains("세종")||area.toLowerCase().contains("sejong")){
            this.AreaCode = "8";
        }else if(area.contains("경기도")||area.toLowerCase().contains("gyeonggi")){
            this.AreaCode = "31";
        }else if(area.contains("강원도")||area.toLowerCase().contains("gangwon")){
            this.AreaCode = "32";
        }else if(area.contains("충청북")||area.toLowerCase().contains("chungcheongbuk")){
            this.AreaCode = "33";
        }else if(area.contains("충청남")||area.toLowerCase().contains("chungcheongnam")){
            this.AreaCode = "34";
        }else if(area.contains("경상북")||area.toLowerCase().contains("gyeongsangbuk")){
            this.AreaCode = "35";
        }else if(area.contains("경상남")||area.toLowerCase().contains("gyeongsangnam")){
            this.AreaCode = "36";
        }else if(area.contains("전라북")||area.toLowerCase().contains("jeollabuk")){
            this.AreaCode = "37";
        }else if(area.contains("전라남")||area.toLowerCase().contains("jeollanam")){
            this.AreaCode = "38";
        }else if(area.contains("제주")||area.toLowerCase().contains("jeju")) {
            this.AreaCode = "39";
        }else{
            Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
        }
    }

    public void getSi(String si){
        switch (this.AreaCode){
            case "1":
                if(si.contains("강남")||si.toLowerCase().contains("gangnam")){
                    this.SigunguCode = "1";
                }else if(si.contains("강동")||si.toLowerCase().contains("gangdong")){
                    this.SigunguCode = "2";
                }else if(si.contains("강북")||si.toLowerCase().contains("gangbuk")){
                    this.SigunguCode = "3";
                }else if(si.contains("강서")||si.toLowerCase().contains("gangseo")){
                    this.SigunguCode = "4";
                }else if(si.contains("관악")||si.toLowerCase().contains("gwanak")){
                    this.SigunguCode = "5";
                }else if(si.contains("광진")||si.toLowerCase().contains("gangjin")){
                    this.SigunguCode = "6";
                }else if(si.contains("구로")||si.toLowerCase().contains("guro")){
                    this.SigunguCode = "7";
                }else if(si.contains("금천")||si.toLowerCase().contains("geumcheon")){
                    this.SigunguCode = "8";
                }else if(si.contains("노원")||si.toLowerCase().contains("nowon")){
                    this.SigunguCode = "9";
                }else if(si.contains("도봉")||si.toLowerCase().contains("dobong")){
                    this.SigunguCode = "10";
                }else if(si.contains("동대문")||si.toLowerCase().contains("dongdaemun")){
                    this.SigunguCode = "11";
                }else if(si.contains("동작")||si.toLowerCase().contains("dongjak")){
                    this.SigunguCode = "12";
                }else if(si.contains("마포")||si.toLowerCase().contains("mapo")){
                    this.SigunguCode = "13";
                }else if(si.contains("서대문")||si.toLowerCase().contains("seodaemun")){
                    this.SigunguCode = "14";
                }else if(si.contains("서초")||si.toLowerCase().contains("seocho")){
                    this.SigunguCode = "15";
                }else if(si.contains("성동")||si.toLowerCase().contains("seongdong")){
                    this.SigunguCode = "16";
                }else if(si.contains("성북")||si.toLowerCase().contains("seongbuk")) {
                    this.SigunguCode = "17";
                }else if(si.contains("송파")||si.toLowerCase().contains("songpa")) {
                    this.SigunguCode = "18";
                }else if(si.contains("양천")||si.toLowerCase().contains("yangcheon")) {
                    this.SigunguCode = "19";
                }else if(si.contains("영등")||si.toLowerCase().contains("yeongdeungpo")) {
                    this.SigunguCode = "20";
                }else if(si.contains("용산")||si.toLowerCase().contains("yongsan")) {
                    this.SigunguCode = "21";
                }else if(si.contains("은평")||si.toLowerCase().contains("eunpyeong")) {
                    this.SigunguCode = "22";
                }else if(si.contains("종로")||si.toLowerCase().contains("jongno")) {
                    this.SigunguCode = "23";
                }else if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")) {
                    this.SigunguCode = "24";
                }else if(si.contains("중랑")||si.toLowerCase().contains("jungnang")) {
                    this.SigunguCode = "25";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "2":
                if(si.contains("강화")||si.toLowerCase().contains("ganghwa")){
                    this.SigunguCode = "1";
                }else if(si.contains("계양")||si.toLowerCase().contains("gyeyang")){
                    this.SigunguCode = "2";
                }else if(si.contains("남구")||si.toLowerCase().contains("nam-gu")||si.toLowerCase().contains("namgu")){
                    this.SigunguCode = "3";
                }else if(si.contains("남동")||si.toLowerCase().contains("namdong")){
                    this.SigunguCode = "4";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "5";
                }else if(si.contains("부평")||si.toLowerCase().contains("bupyeng")){
                    this.SigunguCode = "6";
                }else if(si.contains("서구")||si.toLowerCase().contains("seo-gu")||si.toLowerCase().contains("seogu")){
                    this.SigunguCode = "7";
                }else if(si.contains("연수")||si.toLowerCase().contains("yeonsu")){
                    this.SigunguCode = "8";
                }else if(si.contains("옹진")||si.toLowerCase().contains("ongjin")){
                    this.SigunguCode = "9";
                }else if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")){
                    this.SigunguCode = "10";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "3":
                if(si.contains("대덕")||si.toLowerCase().contains("deadeok")){
                    this.SigunguCode = "1";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "2";
                }else if(si.contains("서구")||si.toLowerCase().contains("seo-gu")||si.toLowerCase().contains("seogu")){
                    this.SigunguCode = "3";
                }else if(si.contains("유성")||si.toLowerCase().contains("yuseong")){
                    this.SigunguCode = "4";
                }else if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "4":
                if(si.contains("남구")||si.toLowerCase().contains("nam-gu")||si.toLowerCase().contains("namgu")){
                    this.SigunguCode = "1";
                }else if(si.contains("달서")||si.toLowerCase().contains("dalseo")){
                    this.SigunguCode = "2";
                }else if(si.contains("달성")||si.toLowerCase().contains("dalseong")){
                    this.SigunguCode = "3";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "4";
                }else if(si.contains("북구")||si.toLowerCase().contains("buk-gu")||si.toLowerCase().contains("bukgu")){
                    this.SigunguCode = "5";
                }else if(si.contains("서구")||si.toLowerCase().contains("seo-gu")||si.toLowerCase().contains("seogu")){
                    this.SigunguCode = "6";
                }else if(si.contains("수성")||si.toLowerCase().contains("suseong")){
                    this.SigunguCode = "7";
                }else if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")){
                    this.SigunguCode = "8";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "5":
                if(si.contains("광산")||si.toLowerCase().contains("gwangsan")){
                    this.SigunguCode = "1";
                }else if(si.contains("남구")||si.toLowerCase().contains("nam-gu")||si.toLowerCase().contains("namgu")){
                    this.SigunguCode = "2";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "3";
                }else if(si.contains("북구")||si.toLowerCase().contains("buk-gu")||si.toLowerCase().contains("bukgu")){
                    this.SigunguCode = "4";
                }else if(si.contains("서구")||si.toLowerCase().contains("seo-gu")||si.toLowerCase().contains("seogu")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "6":
                if(si.contains("강서")||si.toLowerCase().contains("gangseo")){
                    this.SigunguCode = "1";
                }else if(si.contains("금정")||si.toLowerCase().contains("geumjeong")){
                    this.SigunguCode = "2";
                }else if(si.contains("기장")||si.toLowerCase().contains("gijang")){
                    this.SigunguCode = "3";
                }else if(si.contains("남구")||si.toLowerCase().contains("nam-gu")||si.toLowerCase().contains("namgu")){
                    this.SigunguCode = "4";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "5";
                }else if(si.contains("동래")||si.toLowerCase().contains("dongnae")){
                    this.SigunguCode = "6";
                }else if(si.contains("부산진")||si.toLowerCase().contains("busanjin")){
                    this.SigunguCode = "7";
                }else if(si.contains("북구")||si.toLowerCase().contains("buk-gu")||si.toLowerCase().contains("bukgu")){
                    this.SigunguCode = "8";
                }else if(si.contains("사상")||si.toLowerCase().contains("sasang")){
                    this.SigunguCode = "9";
                }else if(si.contains("사하")||si.toLowerCase().contains("saha")){
                    this.SigunguCode = "10";
                }else if(si.contains("서구")||si.toLowerCase().contains("seo-gu")||si.toLowerCase().contains("seogu")){
                    this.SigunguCode = "11";
                }else if(si.contains("수영")||si.toLowerCase().contains("suyeong")){
                    this.SigunguCode = "12";
                }else if(si.contains("연제")||si.toLowerCase().contains("yeonje")){
                    this.SigunguCode = "13";
                }else if(si.contains("영도")||si.toLowerCase().contains("yeongdo")){
                    this.SigunguCode = "14";
                }else if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")){
                    this.SigunguCode = "15";
                }else if(si.contains("해운대")||si.toLowerCase().contains("haeundae")){
                    this.SigunguCode = "16";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "7":
                if(si.contains("중구")||si.toLowerCase().contains("jung-gu")||si.toLowerCase().contains("junggu")){
                    this.SigunguCode = "1";
                }else if(si.contains("남구")||si.toLowerCase().contains("nam-gu")||si.toLowerCase().contains("namgu")){
                    this.SigunguCode = "2";
                }else if(si.contains("동구")||si.toLowerCase().contains("dong-gu")||si.toLowerCase().contains("donggu")){
                    this.SigunguCode = "3";
                }else if(si.contains("북구")||si.toLowerCase().contains("buk-gu")||si.toLowerCase().contains("bukgu")){
                    this.SigunguCode = "4";
                }else if(si.contains("울주")||si.toLowerCase().contains("ulju")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "8":
                if(si.contains("세종")||si.toLowerCase().contains("sejong")){
                    this.SigunguCode = "1";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "31":
                if(si.contains("가평")||si.toLowerCase().contains("gapyeong")){
                    this.SigunguCode = "1";
                }else if(si.contains("고양")||si.toLowerCase().contains("goyang")){
                    this.SigunguCode = "2";
                }else if(si.contains("과천")||si.toLowerCase().contains("gwacheon")){
                    this.SigunguCode = "3";
                }else if(si.contains("광명")||si.toLowerCase().contains("gwangmyeong")){
                    this.SigunguCode = "4";
                }else if(si.contains("광주")||si.toLowerCase().contains("gwangju")){
                    this.SigunguCode = "5";
                }else if(si.contains("구리")||si.toLowerCase().contains("guri")){
                    this.SigunguCode = "6";
                }else if(si.contains("군포")||si.toLowerCase().contains("gunpo")){
                    this.SigunguCode = "7";
                }else if(si.contains("김포")||si.toLowerCase().contains("gimpo")){
                    this.SigunguCode = "8";
                }else if(si.contains("남양주")||si.toLowerCase().contains("namyangju")){
                    this.SigunguCode = "9";
                }else if(si.contains("동두천")||si.toLowerCase().contains("dongducheon")){
                    this.SigunguCode = "10";
                }else if(si.contains("부천")||si.toLowerCase().contains("bucheon")){
                    this.SigunguCode = "11";
                }else if(si.contains("성남")||si.toLowerCase().contains("seongnam")){
                    this.SigunguCode = "12";
                }else if(si.contains("수원")||si.toLowerCase().contains("suwon")){
                    this.SigunguCode = "13";
                }else if(si.contains("시흥")||si.toLowerCase().contains("siheung")){
                    this.SigunguCode = "14";
                }else if(si.contains("안산")||si.toLowerCase().contains("ansan")){
                    this.SigunguCode = "15";
                }else if(si.contains("안성")||si.toLowerCase().contains("anseong")){
                    this.SigunguCode = "16";
                }else if(si.contains("안양")||si.toLowerCase().contains("anyang")) {
                    this.SigunguCode = "17";
                }else if(si.contains("양주")||si.toLowerCase().contains("yangju")) {
                    this.SigunguCode = "18";
                }else if(si.contains("양평")||si.toLowerCase().contains("yangpyeong")) {
                    this.SigunguCode = "19";
                }else if(si.contains("여주")||si.toLowerCase().contains("yeoju")) {
                    this.SigunguCode = "20";
                }else if(si.contains("연천")||si.toLowerCase().contains("yeoncheon")) {
                    this.SigunguCode = "21";
                }else if(si.contains("오산")||si.toLowerCase().contains("osan")) {
                    this.SigunguCode = "22";
                }else if(si.contains("용인")||si.toLowerCase().contains("yongin")) {
                    this.SigunguCode = "23";
                }else if(si.contains("의왕")||si.toLowerCase().contains("uiwang")) {
                    this.SigunguCode = "24";
                }else if(si.contains("의정부")||si.toLowerCase().contains("uijeongbu")) {
                    this.SigunguCode = "25";
                }else if(si.contains("이천")||si.toLowerCase().contains("icheon")) {
                    this.SigunguCode = "26";
                }else if(si.contains("파주")||si.toLowerCase().contains("paju")) {
                    this.SigunguCode = "27";
                }else if(si.contains("평택")||si.toLowerCase().contains("pyeongtaek")) {
                    this.SigunguCode = "28";
                }else if(si.contains("포천")||si.toLowerCase().contains("pocheon")) {
                    this.SigunguCode = "29";
                }else if(si.contains("하남")||si.toLowerCase().contains("hanam")) {
                    this.SigunguCode = "30";
                }else if(si.contains("화성")||si.toLowerCase().contains("hwaseong")) {
                    this.SigunguCode = "31";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "32":
                if(si.contains("강릉")||si.toLowerCase().contains("gangneung")){
                    this.SigunguCode = "1";
                }else if(si.contains("고성")||si.toLowerCase().contains("goseong")){
                    this.SigunguCode = "2";
                }else if(si.contains("동해")||si.toLowerCase().contains("donghae")){
                    this.SigunguCode = "3";
                }else if(si.contains("삼척")||si.toLowerCase().contains("samcheok")){
                    this.SigunguCode = "4";
                }else if(si.contains("속초")||si.toLowerCase().contains("sokcho")){
                    this.SigunguCode = "5";
                }else if(si.contains("양구")||si.toLowerCase().contains("yanggu")){
                    this.SigunguCode = "6";
                }else if(si.contains("양양")||si.toLowerCase().contains("yangyang")){
                    this.SigunguCode = "7";
                }else if(si.contains("영월")||si.toLowerCase().contains("yeongwol")){
                    this.SigunguCode = "8";
                }else if(si.contains("원주")||si.toLowerCase().contains("wonju")){
                    this.SigunguCode = "9";
                }else if(si.contains("인제")||si.toLowerCase().contains("inje")){
                    this.SigunguCode = "10";
                }else if(si.contains("정선")||si.toLowerCase().contains("jeongseon")){
                    this.SigunguCode = "11";
                }else if(si.contains("철원")||si.toLowerCase().contains("cheorwon")){
                    this.SigunguCode = "12";
                }else if(si.contains("춘천")||si.toLowerCase().contains("chuncheon")){
                    this.SigunguCode = "13";
                }else if(si.contains("태백")||si.toLowerCase().contains("taebaek")){
                    this.SigunguCode = "14";
                }else if(si.contains("평창")||si.toLowerCase().contains("pyeongchang")){
                    this.SigunguCode = "15";
                }else if(si.contains("홍천")||si.toLowerCase().contains("hongcheon")){
                    this.SigunguCode = "16";
                }else if(si.contains("화천")||si.toLowerCase().contains("hwacheon")) {
                    this.SigunguCode = "17";
                }else if(si.contains("횡성")||si.toLowerCase().contains("hoengseong")) {
                    this.SigunguCode = "18";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "33":
                if(si.contains("괴산")||si.toLowerCase().contains("goesan")){
                    this.SigunguCode = "1";
                }else if(si.contains("단양")||si.toLowerCase().contains("danyang")){
                    this.SigunguCode = "2";
                }else if(si.contains("보은")||si.toLowerCase().contains("boeun")){
                    this.SigunguCode = "3";
                }else if(si.contains("영동")||si.toLowerCase().contains("yeongdong")){
                    this.SigunguCode = "4";
                }else if(si.contains("옥천")||si.toLowerCase().contains("okcheon")){
                    this.SigunguCode = "5";
                }else if(si.contains("음성")||si.toLowerCase().contains("eumseong")){
                    this.SigunguCode = "6";
                }else if(si.contains("제천")||si.toLowerCase().contains("jecheon")){
                    this.SigunguCode = "7";
                }else if(si.contains("진천")||si.toLowerCase().contains("jincheon")){
                    this.SigunguCode = "8";
                }else if(si.contains("청원")||si.toLowerCase().contains("cheongwon")){
                    this.SigunguCode = "9";
                }else if(si.contains("청주")||si.toLowerCase().contains("cheongju")){
                    this.SigunguCode = "10";
                }else if(si.contains("충주")||si.toLowerCase().contains("chungju")){
                    this.SigunguCode = "11";
                }else if(si.contains("증평")||si.toLowerCase().contains("jeungpyeong")){
                    this.SigunguCode = "12";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "34":
                if(si.contains("공주")||si.toLowerCase().contains("gongju")){
                    this.SigunguCode = "1";
                }else if(si.contains("금산")||si.toLowerCase().contains("geumsan")){
                    this.SigunguCode = "2";
                }else if(si.contains("논산")||si.toLowerCase().contains("nonsan")){
                    this.SigunguCode = "3";
                }else if(si.contains("당진")||si.toLowerCase().contains("dangjin")){
                    this.SigunguCode = "4";
                }else if(si.contains("보령")||si.toLowerCase().contains("boryeong")){
                    this.SigunguCode = "5";
                }else if(si.contains("부여")||si.toLowerCase().contains("buyeo")){
                    this.SigunguCode = "6";
                }else if(si.contains("서산")||si.toLowerCase().contains("seosan")){
                    this.SigunguCode = "7";
                }else if(si.contains("서천")||si.toLowerCase().contains("seocheon")){
                    this.SigunguCode = "8";
                }else if(si.contains("아산")||si.toLowerCase().contains("asan")){
                    this.SigunguCode = "9";
                }else if(si.contains("예산")||si.toLowerCase().contains("yesan")){
                    this.SigunguCode = "11";
                }else if(si.contains("천안")||si.toLowerCase().contains("cheonan")){
                    this.SigunguCode = "12";
                }else if(si.contains("청양")||si.toLowerCase().contains("cheongyang")){
                    this.SigunguCode = "13";
                }else if(si.contains("태안")||si.toLowerCase().contains("taean")){
                    this.SigunguCode = "14";
                }else if(si.contains("홍성")||si.toLowerCase().contains("hongseong")){
                    this.SigunguCode = "15";
                }else if(si.contains("계룡")){
                    this.SigunguCode = "16";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "35":
                if(si.contains("경산")||si.toLowerCase().contains("gyeongsan")){
                    this.SigunguCode = "1";
                }else if(si.contains("경주")||si.toLowerCase().contains("gyeongju")){
                    this.SigunguCode = "2";
                }else if(si.contains("고령")||si.toLowerCase().contains("goryeong")){
                    this.SigunguCode = "3";
                }else if(si.contains("구미")||si.toLowerCase().contains("gumi")){
                    this.SigunguCode = "4";
                }else if(si.contains("군위")||si.toLowerCase().contains("gunwi")){
                    this.SigunguCode = "5";
                }else if(si.contains("김천")||si.toLowerCase().contains("gimcheon")){
                    this.SigunguCode = "6";
                }else if(si.contains("문경")||si.toLowerCase().contains("mungyeong")){
                    this.SigunguCode = "7";
                }else if(si.contains("봉화")||si.toLowerCase().contains("bonghwa")){
                    this.SigunguCode = "8";
                }else if(si.contains("상주")||si.toLowerCase().contains("sangju")){
                    this.SigunguCode = "9";
                }else if(si.contains("성주")||si.toLowerCase().contains("seongju")){
                    this.SigunguCode = "10";
                }else if(si.contains("안동")||si.toLowerCase().contains("andong")){
                    this.SigunguCode = "11";
                }else if(si.contains("영덕")||si.toLowerCase().contains("yeongdeok")){
                    this.SigunguCode = "12";
                }else if(si.contains("영양")||si.toLowerCase().contains("yeongyang")){
                    this.SigunguCode = "13";
                }else if(si.contains("영주")||si.toLowerCase().contains("yeongju")){
                    this.SigunguCode = "14";
                }else if(si.contains("영천")||si.toLowerCase().contains("yeongcheon")){
                    this.SigunguCode = "15";
                }else if(si.contains("예천")||si.toLowerCase().contains("yecheon")){
                    this.SigunguCode = "16";
                }else if(si.contains("울릉")||si.toLowerCase().contains("ulleung")) {
                    this.SigunguCode = "17";
                }else if(si.contains("울진")||si.toLowerCase().contains("uljin")) {
                    this.SigunguCode = "18";
                }else if(si.contains("의성")||si.toLowerCase().contains("uiseong")) {
                    this.SigunguCode = "19";
                }else if(si.contains("청도")||si.toLowerCase().contains("cheongdo")) {
                    this.SigunguCode = "20";
                }else if(si.contains("청송")||si.toLowerCase().contains("cheongsong")) {
                    this.SigunguCode = "21";
                }else if(si.contains("칠곡")||si.toLowerCase().contains("chilgok")) {
                    this.SigunguCode = "22";
                }else if(si.contains("포항")||si.toLowerCase().contains("pohang")) {
                    this.SigunguCode = "23";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "36":
                if(si.contains("거제")||si.toLowerCase().contains("geoje")){
                    this.SigunguCode = "1";
                }else if(si.contains("거창")||si.toLowerCase().contains("geochang")){
                    this.SigunguCode = "2";
                }else if(si.contains("고성")||si.toLowerCase().contains("goseong")){
                    this.SigunguCode = "3";
                }else if(si.contains("김해")||si.toLowerCase().contains("gimhae")){
                    this.SigunguCode = "4";
                }else if(si.contains("남해")||si.toLowerCase().contains("namhae")){
                    this.SigunguCode = "5";
                }else if(si.contains("마산")||si.toLowerCase().contains("masan")){
                    this.SigunguCode = "6";
                }else if(si.contains("밀양")||si.toLowerCase().contains("miryang")){
                    this.SigunguCode = "7";
                }else if(si.contains("사천")||si.toLowerCase().contains("sacheon")){
                    this.SigunguCode = "8";
                }else if(si.contains("산청")||si.toLowerCase().contains("sancheong")){
                    this.SigunguCode = "9";
                }else if(si.contains("양산")||si.toLowerCase().contains("yangsan")){
                    this.SigunguCode = "10";
                }else if(si.contains("의령")||si.toLowerCase().contains("uiryeong")){
                    this.SigunguCode = "12";
                }else if(si.contains("진주")||si.toLowerCase().contains("jinju")){
                    this.SigunguCode = "13";
                }else if(si.contains("진해")||si.toLowerCase().contains("jinhae")){
                    this.SigunguCode = "14";
                }else if(si.contains("창녕")||si.toLowerCase().contains("changnyeong")){
                    this.SigunguCode = "15";
                }else if(si.contains("창원")||si.toLowerCase().contains("changwon")){
                    this.SigunguCode = "16";
                }else if(si.contains("통영")||si.toLowerCase().contains("tongyeong")){
                    this.SigunguCode = "17";
                }else if(si.contains("하동")||si.toLowerCase().contains("hadong")) {
                    this.SigunguCode = "18";
                }else if(si.contains("함안")||si.toLowerCase().contains("haman")) {
                    this.SigunguCode = "19";
                }else if(si.contains("함양")||si.toLowerCase().contains("hamyang")) {
                    this.SigunguCode = "20";
                }else if(si.contains("합천")||si.toLowerCase().contains("hapcheon")) {
                    this.SigunguCode = "21";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "37":
                if(si.contains("고창")||si.toLowerCase().contains("gochang")){
                    this.SigunguCode = "1";
                }else if(si.contains("군산")||si.toLowerCase().contains("gunsan")){
                    this.SigunguCode = "2";
                }else if(si.contains("김제")||si.toLowerCase().contains("gimje")){
                    this.SigunguCode = "3";
                }else if(si.contains("남원")||si.toLowerCase().contains("namwon")){
                    this.SigunguCode = "4";
                }else if(si.contains("무주")||si.toLowerCase().contains("muju")){
                    this.SigunguCode = "5";
                }else if(si.contains("부안")||si.toLowerCase().contains("buan")){
                    this.SigunguCode = "6";
                }else if(si.contains("순창")||si.toLowerCase().contains("sunchang")){
                    this.SigunguCode = "7";
                }else if(si.contains("완주")||si.toLowerCase().contains("wanju")){
                    this.SigunguCode = "8";
                }else if(si.contains("익산")||si.toLowerCase().contains("iksan")){
                    this.SigunguCode = "9";
                }else if(si.contains("임실")||si.toLowerCase().contains("imsil")){
                    this.SigunguCode = "10";
                }else if(si.contains("장수")||si.toLowerCase().contains("jangsu")){
                    this.SigunguCode = "11";
                }else if(si.contains("전주")||si.toLowerCase().contains("jeonju")){
                    this.SigunguCode = "12";
                }else if(si.contains("정읍")||si.toLowerCase().contains("jeongeup")){
                    this.SigunguCode = "13";
                }else if(si.contains("진안")||si.toLowerCase().contains("jinan")){
                    this.SigunguCode = "14";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "38":
                if(si.contains("강진")||si.toLowerCase().contains("gangjin")){
                    this.SigunguCode = "1";
                }else if(si.contains("고흥")||si.toLowerCase().contains("goheung")){
                    this.SigunguCode = "2";
                }else if(si.contains("곡성")||si.toLowerCase().contains("gokseong")){
                    this.SigunguCode = "3";
                }else if(si.contains("광양")||si.toLowerCase().contains("gwangyang")){
                    this.SigunguCode = "4";
                }else if(si.contains("구례")||si.toLowerCase().contains("gurye")){
                    this.SigunguCode = "5";
                }else if(si.contains("나주")||si.toLowerCase().contains("naju")){
                    this.SigunguCode = "6";
                }else if(si.contains("담양")||si.toLowerCase().contains("damyang")){
                    this.SigunguCode = "7";
                }else if(si.contains("목포")||si.toLowerCase().contains("mokpo")){
                    this.SigunguCode = "8";
                }else if(si.contains("무안")||si.toLowerCase().contains("muan")){
                    this.SigunguCode = "9";
                }else if(si.contains("보성")||si.toLowerCase().contains("boseong")){
                    this.SigunguCode = "10";
                }else if(si.contains("순천")||si.toLowerCase().contains("suncheon")){
                    this.SigunguCode = "11";
                }else if(si.contains("신안")||si.toLowerCase().contains("sinan")){
                    this.SigunguCode = "12";
                }else if(si.contains("여수")||si.toLowerCase().contains("yeosu")){
                    this.SigunguCode = "13";
                }else if(si.contains("영광")||si.toLowerCase().contains("yeonggwang")){
                    this.SigunguCode = "16";
                }else if(si.contains("영암")||si.toLowerCase().contains("yeongam")){
                    this.SigunguCode = "17";
                }else if(si.contains("완도")||si.toLowerCase().contains("wando")){
                    this.SigunguCode = "18";
                }else if(si.contains("장성")||si.toLowerCase().contains("jangseong")) {
                    this.SigunguCode = "19";
                }else if(si.contains("장흥")||si.toLowerCase().contains("jangheung")) {
                    this.SigunguCode = "20";
                }else if(si.contains("진도")||si.toLowerCase().contains("jindo")) {
                    this.SigunguCode = "21";
                }else if(si.contains("함평")||si.toLowerCase().contains("hampyeong")) {
                    this.SigunguCode = "22";
                }else if(si.contains("해남")||si.toLowerCase().contains("haenam")) {
                    this.SigunguCode = "23";
                }else if(si.contains("화순")||si.toLowerCase().contains("hwasun")) {
                    this.SigunguCode = "24";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "39":
                if(si.contains("남제주")||si.toLowerCase().contains("nam")){
                    this.SigunguCode = "1";
                }else if(si.contains("북제주")||si.toLowerCase().contains("buk")){
                    this.SigunguCode = "2";
                }else if(si.contains("서귀포")||si.toLowerCase().contains("seogwipo")){
                    this.SigunguCode = "3";
                }else if(si.contains("제주시")||si.toLowerCase().contains("jeju")){
                    this.SigunguCode = "4";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                break;
        }
    }

}
