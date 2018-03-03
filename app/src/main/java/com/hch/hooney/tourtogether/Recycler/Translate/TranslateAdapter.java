package com.hch.hooney.tourtogether.Recycler.Translate;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hch on 2018-03-01.
 */

public class TranslateAdapter extends RecyclerView.Adapter implements TextToSpeech.OnInitListener {
    private final String TAG = "TranslateAdapter";

    private ArrayList<TranslateItem> itemList;
    private RecyclerView itemListView;
    private Context mContext;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private int ttsTarget;
    private String context;
    private TextToSpeech tts;

    public TranslateAdapter(ArrayList<TranslateItem> itemList, RecyclerView itemListView, Context mContext) {
        this.itemList = itemList;
        this.itemListView = itemListView;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translate_list,parent,false);
        TranslateHolder holder = new TranslateHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TranslateHolder hold = (TranslateHolder)holder;

        final TranslateItem item = itemList.get(position);

        hold.t_from.setText(translateLanguge(item.getT_from()));

        hold.t_to.setText(translateLanguge(item.getT_to()));

        hold.t_origin.setText(item.getT_origin());
        hold.t_translate.setText(item.getT_translate());

        hold.t_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.handler.delete_translate(item.getT_id());
                itemListView.getAdapter().notifyDataSetChanged();
            }
        });

        hold.t_hearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = item.getT_translate();
                ttsTarget = item.getT_to();
                tts = new TextToSpeech(mContext, TranslateAdapter.this);
            }
        });

    }

    private String translateLanguge(int num){
        switch (num){
            case 0 :
                return mContext.getResources().getString(R.string.translate_country_0);
            case 1 :
                return mContext.getResources().getString(R.string.translate_country_1);
            case 2 :
                return mContext.getResources().getString(R.string.translate_country_2);
            case 3 :
                return mContext.getResources().getString(R.string.translate_country_3);
            case 4 :
                return mContext.getResources().getString(R.string.translate_country_4);
            default:
                return "None";
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // 새로 보여지는 뷰라면 애니메이션을 해줍니다
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_right_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onInit(int status) {
        int result = 0;
        if(status == TextToSpeech.SUCCESS){
            if(ttsTarget==0){
                result= tts.setLanguage(Locale.KOREA);
            }else if(ttsTarget==1){
                result= tts.setLanguage(Locale.ENGLISH);
            }else if(ttsTarget==2){
                result= tts.setLanguage(Locale.JAPANESE);
            }else if(ttsTarget==3){
                result= tts.setLanguage(Locale.SIMPLIFIED_CHINESE);
            }else if(ttsTarget==4){
                result= tts.setLanguage(Locale.TRADITIONAL_CHINESE);
            }

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e(TAG, "Language is not Available");
            }else{
                runTTS();
            }
        }else{
            Log.e(TAG, "TTS is Not init()");
        }
    }

    private void runTTS(){
        String target = context;
        Log.d(TAG, "Target : " + target);
        tts.speak(target, TextToSpeech.QUEUE_ADD, null);
    }
}
