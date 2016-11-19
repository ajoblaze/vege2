package com.imajiku.vegefinder.fragment;

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

/**
 * Created by SRIN on 8/25/2015.
 */
public class PhotoDetailFragment extends Fragment {

    public static PhotoDetailFragment newInstance(String s) {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_photo_detail, container, false);
        Bundle args = getArguments();
        String imgPath = args.getString("path");
        Log.e("exc", "imgPath: "+imgPath);
        ImageView iv = (ImageView) v.findViewById(R.id.image_gallery);

        Picasso.with(getActivity())
                .load(imgPath)
                .noFade()
                .fit()
                .centerCrop()
                .into(iv);
//        iv.setImageBitmap(ImageDecoderHelper.decodeSampledBitmapFromFile(imgPath, 2240, 1400));
        return v;
    }
}
