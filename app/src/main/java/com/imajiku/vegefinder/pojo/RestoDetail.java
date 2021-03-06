package com.imajiku.vegefinder.pojo;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.imajiku.vegefinder.utility.Utility;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Alvin on 2016-11-13.
 */
public class RestoDetail{
    @SerializedName("id")
    private String id;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("province_id")
    private String provinceId;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("slug")
    private String slug;
    @SerializedName("meta_title")
    private String metaTitle;
    @SerializedName("meta_keywords")
    private String metaKeywords;
    @SerializedName("meta_description")
    private String metaDescription;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("address")
    private String address;
    @SerializedName("average_cost")
    private String averageCost;
    @SerializedName("price_rate")
    private String priceRate;
    @SerializedName("image")
    private String image;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("open_mon")
    private String openMon;
    @SerializedName("open_tue")
    private String openTue;
    @SerializedName("open_wed")
    private String openWed;
    @SerializedName("open_thu")
    private String openThu;
    @SerializedName("open_fri")
    private String openFri;
    @SerializedName("open_sat")
    private String openSat;
    @SerializedName("open_sun")
    private String openSun;
    @SerializedName("date_post")
    private String datePost;
    @SerializedName("telephone")
    private String phone;
    @SerializedName("restaurant_type")
    private int restoType;
    @SerializedName("name_restaurant_type")
    private String restoTypeName;
    @SerializedName("type")
    private int type;
    @SerializedName("name_type")
    private String typeName;
    @SerializedName("facilities")
    private ArrayList<RestoFacility> restoFacility;
    @SerializedName("menu")
    private ArrayList<RestoMenu> restoMenu;
    @SerializedName("tags")
    private ArrayList<RestoTag> restoTag;
    @SerializedName("all_image")
    private ArrayList<RestoImage> restoImg;
    @SerializedName("rates_review")
    private ArrayList<Review> ratesReview;

    @SerializedName("count_review")
    private int countReview;
    @SerializedName("count_bookmark")
    private int countBookmark;
    @SerializedName("count_beenhere")
    private int countBeenHere;
    @SerializedName("status_bookmark")
    private String statusBookmark;
    @SerializedName("status_beenhere")
    private String statusBeenHere;
    private String TAG = "exc";

    public boolean isOpenNow() {
        String[] open = {openMon, openTue, openWed, openThu, openFri, openSat, openSun};
        int currDay = new LocalDate().getDayOfWeek();
        String str = open[currDay - 1];
        return str.length() >= 5 &&
                !str.toLowerCase().startsWith("close") &&
                compareTime(getOpenCloseHour(open[currDay - 1]));
    }

    public String getOpenTime() {
        String[] open = {openMon, openTue, openWed, openThu, openFri, openSat, openSun};
        int currDay = new LocalDate().getDayOfWeek();
        if (!open[currDay - 1].isEmpty()) {
            return "  |  " + open[currDay - 1];
        }
        return open[currDay - 1];
    }

    public String[] getOpenCloseHour(String src) {
        String[] time = new String[2];
        String open = src.substring(0, 4);
        String close = src.substring(src.length() - 5, src.length() - 1);
        time[0] = open;
        if (open.equals(close)) {
            time[1] = null;
        } else {
            time[1] = close;
        }
        return time;
    }

    public boolean compareTime(String[] time) {
        String open = time[0];
        String close = time[1];
        boolean isEarly = false;
        boolean isLate = false;
        LocalTime now = LocalTime.now();
        LocalTime left = new LocalTime(open);
        isEarly = now.isBefore(left);
        if (close != null) {
            try {
                LocalTime right = new LocalTime(close);
                isLate = now.isAfter(right);
            }catch(Exception e){
                isLate = false;
            }
        }
        return !isEarly && !isLate;
    }

    public String getAvgRate() {
//        ratesReview = getDummyRatesReview();
        if (ratesReview == null || ratesReview.size() == 0) {
            return null;
        }
        double total = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        for (Review r : ratesReview) {
            total += r.getRate();
        }
        return df.format(total / ratesReview.size());
    }

    public String getStatistics() {
        int[] count = {countReview, countBookmark, countBeenHere};
        String[] label = {" Review", " Bookmark", " Been Here"};
        StringBuilder sb = new StringBuilder().append("");
        for (int i = 0; i < 3; i++) {
            sb.append(count[i]);
            sb.append(label[i]);
            if (i < 2) {
                if (count[i] > 1) {
                    sb.append("s");
                }
                sb.append(" - ");
            }
        }
        return sb.toString();
    }

    public String getWeekOpenTime() {
        String[] open = {openMon, openTue, openWed, openThu, openFri, openSat, openSun};
        StringBuilder sb = new StringBuilder().append("");
        LocalDate date = new LocalDate();
        for (int i = 0; i < 7; i++) {
            date = date.withDayOfWeek(i + 1);
            sb.append(date.dayOfWeek().getAsText());
            sb.append(" - ");
            sb.append(open[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

//    public ArrayList<Review> getDummyRatesReview() {
//        ArrayList<Review> dummy = new ArrayList<>();
//        dummy.add(new Review("Robby Radhika", null, 16, 32, 56, "This is Title Review",
//                        "2016-11-18", 4, "This is comment review, please ignore this one.", 0));
//        dummy.add(new Review("Administrator", "Default", 3, 32, 1, "wow", "2016-06-02", 4, "good", 0));
//        dummy.add(new Review("Supernam", "Mek", 3, 32, 40, "suge", "2016-12-05", 6, "mkey", 0));
//        dummy.add(new Review("Supernam2", "Mek", 3, 32, 40, "suge2", "2016-12-07", 1, "mkey2", 0));
//        dummy.add(new Review("Supernam3", "Mek", 3, 32, 40, "suge3", "2016-12-08", 2, "mkey3", 0));
//        return dummy;
//    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public int getCountryId() {
        return Integer.parseInt(countryId);
    }

    public int getProvinceId() {
        return Integer.parseInt(provinceId);
    }

    public int getCityId() {
        return Integer.parseInt(cityId);
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return metaTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getAverageCost() {
        return averageCost;
    }

    public String getPriceRate() {
        return priceRate;
    }

    public String getImage() {
        return Utility.attachImageUrl(image);
    }

    public String getImageUrl() {
        return Utility.attachImageUrl(imageUrl);
    }

    public String getDatePost() {
        return datePost;
    }

    public int getRestoType() {
        return restoType;
    }

    public int getType() {
        return type;
    }

    public String getRestoTypeName() {
        return restoTypeName;
    }

    public String getTypeName() {
        return typeName;
    }
    //    public int getFacilities() {
//        return facilities;
//    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<RestoFacility> getRestoFacility() {
        return restoFacility;
    }

    public ArrayList<RestoMenu> getRestoMenu() {
        return restoMenu;
    }

    public ArrayList<RestoTag> getRestoTag() {
        return restoTag;
    }

    public ArrayList<RestoImage> getRestoImg() {
        for(RestoImage ri : restoImg){
            ri.setImage(
                    Utility.attachImageUrl(ri.getImage())
            );
        }
        return restoImg;
    }

    public void setRestoImg(ArrayList<RestoImage> restoImg) {
        this.restoImg = restoImg;
    }

    public ArrayList<Review> getRatesReview() {
        if(ratesReview == null){
            return new ArrayList<>();
        }
        return ratesReview;
    }

    public int getCountReview() {
        return countReview;
    }

    public int getCountBookmark() {
        return countBookmark;
    }

    public int getCountBeenHere() {
        return countBeenHere;
    }

    public String getStatusBookmark() {
        return statusBookmark;
    }

    public String getStatusBeenHere() {
        return statusBeenHere;
    }
}
