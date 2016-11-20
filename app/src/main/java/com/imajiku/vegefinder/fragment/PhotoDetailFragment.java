package com.imajiku.vegefinder.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imajiku.vegefinder.R;
import com.imajiku.vegefinder.utility.ImageDecoderHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by SRIN on 8/25/2015.
 */
public class PhotoDetailFragment extends Fragment {

    private static ArrayList<ImageCache> cache;
    private String imgPath;
    private ImageView iv;
    private Bitmap currBitmap;

    public static PhotoDetailFragment newInstance(String s) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", s);
        fragment.setArguments(bundle);
        if(cache == null){
            cache = new ArrayList<>();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Saves downloaded images into cache
     * from http://stackoverflow.com/questions/24682217/get-bitmap-from-imageview-loaded-with-picasso
     * answer by jguerinet
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_photo_detail, container, false);
        Bundle args = getArguments();
        imgPath = args.getString("path");
        iv = (ImageView) v.findViewById(R.id.image_gallery);

        Picasso.with(getActivity())
                .load(imgPath)
                .noFade()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        boolean isFound = false;
                        for(ImageCache c : cache){
                            if(imgPath.equals(c.getUrl())){
                                isFound = true;
                                currBitmap = c.getBitmap();
                                break;
                            }
                        }
                        if(!isFound){
                            currBitmap = bitmap;
                            cache.add(new ImageCache(imgPath, bitmap));
                        }
                        iv.setImageBitmap(currBitmap);
                        Log.e("exc", "isbitmapnull: "+(currBitmap==null));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        return v;
    }

    public Bitmap getCurrentPhoto(){
        return currBitmap;
    }

    class ImageCache{
        private String url;
        private Bitmap bitmap;

        public ImageCache(String url, Bitmap bitmap) {
            this.url = url;
            this.bitmap = bitmap;
        }

        public String getUrl() {
            return url;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}
