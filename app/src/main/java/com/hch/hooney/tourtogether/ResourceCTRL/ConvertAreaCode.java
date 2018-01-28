package com.hch.hooney.tourtogether.ResourceCTRL;

import android.content.Context;
import android.widget.Toast;

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
        String split[] = AutoResult.split(" ");

        getAreaAndSiCode(split[1], split[2]);
    }

    public void filteringToSelect(String area, String si){
        getAreaAndSiCode(area, si);
    }

    public void getAreaAndSiCode(String area, String si){
        getArea(area);
        getSi(si);
    }

    public void getArea(String area){
        if(area.contains("서울")){
            this.AreaCode = "1";
        }else if(area.contains("인천")){
            this.AreaCode = "2";
        }else if(area.contains("대전")){
            this.AreaCode = "3";
        }else if(area.contains("대구")){
            this.AreaCode = "4";
        }else if(area.contains("광주")){
            this.AreaCode = "5";
        }else if(area.contains("부산")){
            this.AreaCode = "6";
        }else if(area.contains("울산")){
            this.AreaCode = "7";
        }else if(area.contains("세종")){
            this.AreaCode = "8";
        }else if(area.contains("경기도")){
            this.AreaCode = "31";
        }else if(area.contains("강원도")){
            this.AreaCode = "32";
        }else if(area.contains("충청북")){
            this.AreaCode = "33";
        }else if(area.contains("충청남")){
            this.AreaCode = "34";
        }else if(area.contains("경상북")){
            this.AreaCode = "35";
        }else if(area.contains("경상남")){
            this.AreaCode = "36";
        }else if(area.contains("전라북")){
            this.AreaCode = "37";
        }else if(area.contains("전라남")){
            this.AreaCode = "38";
        }else if(area.contains("제주")) {
            this.AreaCode = "39";
        }else{
            Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
        }
    }

    public void getSi(String si){
        switch (this.AreaCode){
            case "1":
                if(si.contains("강남")){
                    this.SigunguCode = "1";
                }else if(si.contains("강동")){
                    this.SigunguCode = "2";
                }else if(si.contains("강북")){
                    this.SigunguCode = "3";
                }else if(si.contains("강서")){
                    this.SigunguCode = "4";
                }else if(si.contains("관악")){
                    this.SigunguCode = "5";
                }else if(si.contains("광진")){
                    this.SigunguCode = "6";
                }else if(si.contains("구로")){
                    this.SigunguCode = "7";
                }else if(si.contains("금천")){
                    this.SigunguCode = "8";
                }else if(si.contains("노원")){
                    this.SigunguCode = "9";
                }else if(si.contains("도봉")){
                    this.SigunguCode = "10";
                }else if(si.contains("동대문")){
                    this.SigunguCode = "11";
                }else if(si.contains("동작")){
                    this.SigunguCode = "12";
                }else if(si.contains("마포")){
                    this.SigunguCode = "13";
                }else if(si.contains("서대문")){
                    this.SigunguCode = "14";
                }else if(si.contains("서초")){
                    this.SigunguCode = "15";
                }else if(si.contains("성동")){
                    this.SigunguCode = "16";
                }else if(si.contains("성북")) {
                    this.SigunguCode = "17";
                }else if(si.contains("송파")) {
                    this.SigunguCode = "18";
                }else if(si.contains("양천")) {
                    this.SigunguCode = "19";
                }else if(si.contains("영등")) {
                    this.SigunguCode = "20";
                }else if(si.contains("용산")) {
                    this.SigunguCode = "21";
                }else if(si.contains("은평")) {
                    this.SigunguCode = "22";
                }else if(si.contains("종로")) {
                    this.SigunguCode = "23";
                }else if(si.contains("중구")) {
                    this.SigunguCode = "24";
                }else if(si.contains("중랑")) {
                    this.SigunguCode = "25";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "2":
                if(si.contains("강화")){
                    this.SigunguCode = "1";
                }else if(si.contains("계양")){
                    this.SigunguCode = "2";
                }else if(si.contains("남구")){
                    this.SigunguCode = "3";
                }else if(si.contains("남동")){
                    this.SigunguCode = "4";
                }else if(si.contains("동구")){
                    this.SigunguCode = "5";
                }else if(si.contains("부평")){
                    this.SigunguCode = "6";
                }else if(si.contains("서구")){
                    this.SigunguCode = "7";
                }else if(si.contains("연수")){
                    this.SigunguCode = "8";
                }else if(si.contains("옹진")){
                    this.SigunguCode = "9";
                }else if(si.contains("중구")){
                    this.SigunguCode = "10";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "3":
                if(si.contains("대덕")){
                    this.SigunguCode = "1";
                }else if(si.contains("동구")){
                    this.SigunguCode = "2";
                }else if(si.contains("서구")){
                    this.SigunguCode = "3";
                }else if(si.contains("유성")){
                    this.SigunguCode = "4";
                }else if(si.contains("중구")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "4":
                if(si.contains("남구")){
                    this.SigunguCode = "1";
                }else if(si.contains("달서")){
                    this.SigunguCode = "2";
                }else if(si.contains("달성")){
                    this.SigunguCode = "3";
                }else if(si.contains("동구")){
                    this.SigunguCode = "4";
                }else if(si.contains("북구")){
                    this.SigunguCode = "5";
                }else if(si.contains("서구")){
                    this.SigunguCode = "6";
                }else if(si.contains("수성")){
                    this.SigunguCode = "7";
                }else if(si.contains("중구")){
                    this.SigunguCode = "8";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "5":
                if(si.contains("광산")){
                    this.SigunguCode = "1";
                }else if(si.contains("남구")){
                    this.SigunguCode = "2";
                }else if(si.contains("동구")){
                    this.SigunguCode = "3";
                }else if(si.contains("북구")){
                    this.SigunguCode = "4";
                }else if(si.contains("서구")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "6":
                if(si.contains("강서")){
                    this.SigunguCode = "1";
                }else if(si.contains("금정")){
                    this.SigunguCode = "2";
                }else if(si.contains("기장")){
                    this.SigunguCode = "3";
                }else if(si.contains("남구")){
                    this.SigunguCode = "4";
                }else if(si.contains("동구")){
                    this.SigunguCode = "5";
                }else if(si.contains("동래")){
                    this.SigunguCode = "6";
                }else if(si.contains("부산진")){
                    this.SigunguCode = "7";
                }else if(si.contains("북구")){
                    this.SigunguCode = "8";
                }else if(si.contains("사상")){
                    this.SigunguCode = "9";
                }else if(si.contains("사하")){
                    this.SigunguCode = "10";
                }else if(si.contains("서구")){
                    this.SigunguCode = "11";
                }else if(si.contains("수영")){
                    this.SigunguCode = "12";
                }else if(si.contains("연제")){
                    this.SigunguCode = "13";
                }else if(si.contains("영도")){
                    this.SigunguCode = "14";
                }else if(si.contains("중구")){
                    this.SigunguCode = "15";
                }else if(si.contains("해운대")){
                    this.SigunguCode = "16";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "7":
                if(si.contains("중구")){
                    this.SigunguCode = "1";
                }else if(si.contains("남구")){
                    this.SigunguCode = "2";
                }else if(si.contains("동구")){
                    this.SigunguCode = "3";
                }else if(si.contains("북구")){
                    this.SigunguCode = "4";
                }else if(si.contains("울주")){
                    this.SigunguCode = "5";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "8":
                if(si.contains("세종")){
                    this.SigunguCode = "1";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "31":
                if(si.contains("가평")){
                    this.SigunguCode = "1";
                }else if(si.contains("고양")){
                    this.SigunguCode = "2";
                }else if(si.contains("과천")){
                    this.SigunguCode = "3";
                }else if(si.contains("광명")){
                    this.SigunguCode = "4";
                }else if(si.contains("광주")){
                    this.SigunguCode = "5";
                }else if(si.contains("구리")){
                    this.SigunguCode = "6";
                }else if(si.contains("군포")){
                    this.SigunguCode = "7";
                }else if(si.contains("김포")){
                    this.SigunguCode = "8";
                }else if(si.contains("남양주")){
                    this.SigunguCode = "9";
                }else if(si.contains("동두천")){
                    this.SigunguCode = "10";
                }else if(si.contains("부천")){
                    this.SigunguCode = "11";
                }else if(si.contains("성남")){
                    this.SigunguCode = "12";
                }else if(si.contains("수원")){
                    this.SigunguCode = "13";
                }else if(si.contains("시흥")){
                    this.SigunguCode = "14";
                }else if(si.contains("안산")){
                    this.SigunguCode = "15";
                }else if(si.contains("안성")){
                    this.SigunguCode = "16";
                }else if(si.contains("안양")) {
                    this.SigunguCode = "17";
                }else if(si.contains("양주")) {
                    this.SigunguCode = "18";
                }else if(si.contains("양평")) {
                    this.SigunguCode = "19";
                }else if(si.contains("여주")) {
                    this.SigunguCode = "20";
                }else if(si.contains("연천")) {
                    this.SigunguCode = "21";
                }else if(si.contains("오산")) {
                    this.SigunguCode = "22";
                }else if(si.contains("용인")) {
                    this.SigunguCode = "23";
                }else if(si.contains("의왕")) {
                    this.SigunguCode = "24";
                }else if(si.contains("의정부")) {
                    this.SigunguCode = "25";
                }else if(si.contains("이천")) {
                    this.SigunguCode = "26";
                }else if(si.contains("파주")) {
                    this.SigunguCode = "27";
                }else if(si.contains("평택")) {
                    this.SigunguCode = "28";
                }else if(si.contains("포천")) {
                    this.SigunguCode = "29";
                }else if(si.contains("하남")) {
                    this.SigunguCode = "30";
                }else if(si.contains("화성")) {
                    this.SigunguCode = "31";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "32":
                if(si.contains("강릉")){
                    this.SigunguCode = "1";
                }else if(si.contains("고성")){
                    this.SigunguCode = "2";
                }else if(si.contains("동해")){
                    this.SigunguCode = "3";
                }else if(si.contains("삼척")){
                    this.SigunguCode = "4";
                }else if(si.contains("속초")){
                    this.SigunguCode = "5";
                }else if(si.contains("양구")){
                    this.SigunguCode = "6";
                }else if(si.contains("양양")){
                    this.SigunguCode = "7";
                }else if(si.contains("영월")){
                    this.SigunguCode = "8";
                }else if(si.contains("원주")){
                    this.SigunguCode = "9";
                }else if(si.contains("인제")){
                    this.SigunguCode = "10";
                }else if(si.contains("정선")){
                    this.SigunguCode = "11";
                }else if(si.contains("철원")){
                    this.SigunguCode = "12";
                }else if(si.contains("춘천")){
                    this.SigunguCode = "13";
                }else if(si.contains("태백")){
                    this.SigunguCode = "14";
                }else if(si.contains("평창")){
                    this.SigunguCode = "15";
                }else if(si.contains("홍천")){
                    this.SigunguCode = "16";
                }else if(si.contains("화천")) {
                    this.SigunguCode = "17";
                }else if(si.contains("횡성")) {
                    this.SigunguCode = "18";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "33":
                if(si.contains("괴산")){
                    this.SigunguCode = "1";
                }else if(si.contains("단양")){
                    this.SigunguCode = "2";
                }else if(si.contains("보은")){
                    this.SigunguCode = "3";
                }else if(si.contains("영동")){
                    this.SigunguCode = "4";
                }else if(si.contains("옥천")){
                    this.SigunguCode = "5";
                }else if(si.contains("음성")){
                    this.SigunguCode = "6";
                }else if(si.contains("제천")){
                    this.SigunguCode = "7";
                }else if(si.contains("진천")){
                    this.SigunguCode = "8";
                }else if(si.contains("청원")){
                    this.SigunguCode = "9";
                }else if(si.contains("청주")){
                    this.SigunguCode = "10";
                }else if(si.contains("충주")){
                    this.SigunguCode = "11";
                }else if(si.contains("증평")){
                    this.SigunguCode = "12";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "34":
                if(si.contains("공주")){
                    this.SigunguCode = "1";
                }else if(si.contains("금산")){
                    this.SigunguCode = "2";
                }else if(si.contains("논산")){
                    this.SigunguCode = "3";
                }else if(si.contains("당진")){
                    this.SigunguCode = "4";
                }else if(si.contains("보령")){
                    this.SigunguCode = "5";
                }else if(si.contains("부여")){
                    this.SigunguCode = "6";
                }else if(si.contains("서산")){
                    this.SigunguCode = "7";
                }else if(si.contains("서천")){
                    this.SigunguCode = "8";
                }else if(si.contains("아산")){
                    this.SigunguCode = "9";
                }else if(si.contains("예산")){
                    this.SigunguCode = "11";
                }else if(si.contains("천안")){
                    this.SigunguCode = "12";
                }else if(si.contains("청양")){
                    this.SigunguCode = "13";
                }else if(si.contains("태안")){
                    this.SigunguCode = "14";
                }else if(si.contains("홍성")){
                    this.SigunguCode = "15";
                }else if(si.contains("계룡")){
                    this.SigunguCode = "16";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "35":
                if(si.contains("경산")){
                    this.SigunguCode = "1";
                }else if(si.contains("경주")){
                    this.SigunguCode = "2";
                }else if(si.contains("고령")){
                    this.SigunguCode = "3";
                }else if(si.contains("구미")){
                    this.SigunguCode = "4";
                }else if(si.contains("군위")){
                    this.SigunguCode = "5";
                }else if(si.contains("김천")){
                    this.SigunguCode = "6";
                }else if(si.contains("문경")){
                    this.SigunguCode = "7";
                }else if(si.contains("봉화")){
                    this.SigunguCode = "8";
                }else if(si.contains("상주")){
                    this.SigunguCode = "9";
                }else if(si.contains("성주")){
                    this.SigunguCode = "10";
                }else if(si.contains("안동")){
                    this.SigunguCode = "11";
                }else if(si.contains("영덕")){
                    this.SigunguCode = "12";
                }else if(si.contains("영양")){
                    this.SigunguCode = "13";
                }else if(si.contains("영주")){
                    this.SigunguCode = "14";
                }else if(si.contains("영천")){
                    this.SigunguCode = "15";
                }else if(si.contains("예천")){
                    this.SigunguCode = "16";
                }else if(si.contains("울릉")) {
                    this.SigunguCode = "17";
                }else if(si.contains("울진")) {
                    this.SigunguCode = "18";
                }else if(si.contains("의성")) {
                    this.SigunguCode = "19";
                }else if(si.contains("청도")) {
                    this.SigunguCode = "20";
                }else if(si.contains("청송")) {
                    this.SigunguCode = "21";
                }else if(si.contains("칠곡")) {
                    this.SigunguCode = "22";
                }else if(si.contains("포항")) {
                    this.SigunguCode = "23";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "36":
                if(si.contains("거제")){
                    this.SigunguCode = "1";
                }else if(si.contains("거창")){
                    this.SigunguCode = "2";
                }else if(si.contains("고성")){
                    this.SigunguCode = "3";
                }else if(si.contains("김해")){
                    this.SigunguCode = "4";
                }else if(si.contains("남해")){
                    this.SigunguCode = "5";
                }else if(si.contains("마산")){
                    this.SigunguCode = "6";
                }else if(si.contains("밀양")){
                    this.SigunguCode = "7";
                }else if(si.contains("사천")){
                    this.SigunguCode = "8";
                }else if(si.contains("산청")){
                    this.SigunguCode = "9";
                }else if(si.contains("양산")){
                    this.SigunguCode = "10";
                }else if(si.contains("의령")){
                    this.SigunguCode = "12";
                }else if(si.contains("진주")){
                    this.SigunguCode = "13";
                }else if(si.contains("진해")){
                    this.SigunguCode = "14";
                }else if(si.contains("창녕")){
                    this.SigunguCode = "15";
                }else if(si.contains("창원")){
                    this.SigunguCode = "16";
                }else if(si.contains("통영")){
                    this.SigunguCode = "17";
                }else if(si.contains("하동")) {
                    this.SigunguCode = "18";
                }else if(si.contains("함안")) {
                    this.SigunguCode = "19";
                }else if(si.contains("함양")) {
                    this.SigunguCode = "20";
                }else if(si.contains("합천")) {
                    this.SigunguCode = "21";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "37":
                if(si.contains("고창")){
                    this.SigunguCode = "1";
                }else if(si.contains("군산")){
                    this.SigunguCode = "2";
                }else if(si.contains("김제")){
                    this.SigunguCode = "3";
                }else if(si.contains("남원")){
                    this.SigunguCode = "4";
                }else if(si.contains("무주")){
                    this.SigunguCode = "5";
                }else if(si.contains("부안")){
                    this.SigunguCode = "6";
                }else if(si.contains("순창")){
                    this.SigunguCode = "7";
                }else if(si.contains("완주")){
                    this.SigunguCode = "8";
                }else if(si.contains("익산")){
                    this.SigunguCode = "9";
                }else if(si.contains("임실")){
                    this.SigunguCode = "10";
                }else if(si.contains("장수")){
                    this.SigunguCode = "11";
                }else if(si.contains("전주")){
                    this.SigunguCode = "12";
                }else if(si.contains("정읍")){
                    this.SigunguCode = "13";
                }else if(si.contains("진안")){
                    this.SigunguCode = "14";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "38":
                if(si.contains("강진")){
                    this.SigunguCode = "1";
                }else if(si.contains("고흥")){
                    this.SigunguCode = "2";
                }else if(si.contains("곡성")){
                    this.SigunguCode = "3";
                }else if(si.contains("광양")){
                    this.SigunguCode = "4";
                }else if(si.contains("구례")){
                    this.SigunguCode = "5";
                }else if(si.contains("나주")){
                    this.SigunguCode = "6";
                }else if(si.contains("담양")){
                    this.SigunguCode = "7";
                }else if(si.contains("목포")){
                    this.SigunguCode = "8";
                }else if(si.contains("무안")){
                    this.SigunguCode = "9";
                }else if(si.contains("보성")){
                    this.SigunguCode = "10";
                }else if(si.contains("순천")){
                    this.SigunguCode = "11";
                }else if(si.contains("신안")){
                    this.SigunguCode = "12";
                }else if(si.contains("여수")){
                    this.SigunguCode = "13";
                }else if(si.contains("영광")){
                    this.SigunguCode = "16";
                }else if(si.contains("영암")){
                    this.SigunguCode = "17";
                }else if(si.contains("완도")){
                    this.SigunguCode = "18";
                }else if(si.contains("장성")) {
                    this.SigunguCode = "19";
                }else if(si.contains("장흥")) {
                    this.SigunguCode = "20";
                }else if(si.contains("진도")) {
                    this.SigunguCode = "21";
                }else if(si.contains("함평")) {
                    this.SigunguCode = "22";
                }else if(si.contains("해남")) {
                    this.SigunguCode = "23";
                }else if(si.contains("화순")) {
                    this.SigunguCode = "24";
                }else{
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.error_location_set_areacode), Toast.LENGTH_LONG).show();
                }
                break;
            case "39":
                if(si.contains("남제주")){
                    this.SigunguCode = "1";
                }else if(si.contains("북제주")){
                    this.SigunguCode = "2";
                }else if(si.contains("서귀포")){
                    this.SigunguCode = "3";
                }else if(si.contains("제주시")){
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
