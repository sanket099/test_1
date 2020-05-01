package com.example.deeplinking;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PicassoClient{

    public static void downloadImage(Context context , String url , ImageView img){
        if(url!=null && url.length()>0){
            Picasso.with(context).load(url).placeholder(R.drawable.ic_add_a_photo_black_24dp).into(img);

        }
        else{
            Toast.makeText(context,"error img",Toast.LENGTH_LONG).show();
            //Picasso.with(context).load(R.drawable.ic_launcher_foreground);
        }
    }
}


