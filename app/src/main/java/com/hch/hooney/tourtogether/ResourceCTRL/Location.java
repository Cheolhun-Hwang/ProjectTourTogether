package com.hch.hooney.tourtogether.ResourceCTRL;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by hooney on 2018. 1. 27..
 */

public class Location {
    private Context mContext;
    private double mapX; //lat 경도
    private double mapY; //lon 위도

    public Location(Context mContext, double mapX, double mapY) {
        this.mContext = mContext;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public String searchLocation(){
        String result_addr="";

        List<Address> addressList = null;

        try{
            Geocoder gc = null;
            //여기 번역되는지 확인해보기
            if(DAO.Language.equals("ko")){
                gc = new Geocoder(mContext, Locale.KOREA);
            }else{
                gc = new Geocoder(mContext, Locale.ENGLISH);
            }

            if(gc ==null){
                Toast.makeText(mContext, mContext.getText(R.string.error_location), Toast.LENGTH_SHORT).show();
            }else{
                addressList = gc.getFromLocation(mapX, mapY, 1);
                if(addressList !=null){
                    for(int i = 0; i<addressList.size();i++){
                        Address outAddress = addressList.get(i);
                        int addrCount = outAddress.getMaxAddressLineIndex() +1;

                        StringBuffer outAddrStr = new StringBuffer();
                        for(int j=0;j<addrCount;j++){
                            outAddrStr.append(outAddress.getAddressLine(j));
                        }
                        result_addr = outAddrStr.toString();
                    }
                    return result_addr;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return "Fail";
    }
}
