package com.hch.hooney.tourtogether.DAO;

import java.util.ArrayList;

/**
 * Created by qewqs on 2018-01-23.
 */

public class DAO {
    public static USER user;
    public static Setting setting;
    public static ArrayList<mainPostItem> mainPostList;

    public static void setUser(USER user){
        DAO.user = user;
    }

    public static void init_mainPostList(){
        DAO.mainPostList = new ArrayList<mainPostItem>();
    }

    public static void loadData_mainPostList(){
        //temp
        mainPostItem mainPostItem1 = new mainPostItem();
        mainPostItem1.setPostID("post_1");
        mainPostItem1.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem1.setUNAME("Hooney");
        mainPostItem1.setPostImage("http://tong.visitkorea.or.kr/cms/resource/40/1568040_image2_1.jpg");
        mainPostItem1.setPostContext("Temp Post Comment...");
        mainPostItem1.setLocation("161, Sajik-ro, Jongno-gu, Seoul, Korea");
        mainPostItem1.setMapy(37.5801859611);
        mainPostItem1.setMapx(126.9767235747);
        mainPostItem1.setPushLike(false);
        mainPostItem1.setLikeCount("Like 0");
        mainPostItem1.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem1);

        mainPostItem mainPostItem2 = new mainPostItem();
        mainPostItem2.setPostID("post_2");
        mainPostItem2.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem2.setUNAME("Hooney");
        mainPostItem2.setPostImage("http://tong.visitkorea.or.kr/cms/resource/67/1567867_image2_1.jpg");
        mainPostItem2.setPostContext("Temp Post Comment...");
        mainPostItem2.setLocation("240, Olympic-ro, Songpa-gu, Seoul, Korea");
        mainPostItem2.setMapy(37.5113516917);
        mainPostItem2.setMapx(127.0979006014);
        mainPostItem2.setPushLike(true);
        mainPostItem2.setLikeCount("Like 1");
        mainPostItem2.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem2);

        mainPostItem mainPostItem3 = new mainPostItem();
        mainPostItem3.setPostID("post_2");
        mainPostItem3.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg");
        mainPostItem3.setUNAME("Hooney");
        mainPostItem3.setPostImage("http://tong.visitkorea.or.kr/cms/resource/36/1567936_image2_1.jpg");
        mainPostItem3.setPostContext("Temp Post Comment...");
        mainPostItem3.setLocation("50, 63-ro, Yeongdeungpo-gu, Seoul, Korea");
        mainPostItem3.setMapy(37.5197701048);
        mainPostItem3.setMapx(126.9401702800);
        mainPostItem3.setPushLike(false);
        mainPostItem3.setLikeCount("Like 0");
        mainPostItem3.setCommentCount("Comment 0");

        DAO.mainPostList.add(mainPostItem3);
    }

}
