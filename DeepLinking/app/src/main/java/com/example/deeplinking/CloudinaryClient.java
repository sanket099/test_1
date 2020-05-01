package com.example.deeplinking;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;

public class CloudinaryClient {

    public static String getRoundCornerImage(String Imagename){

        Cloudinary cloudinary = new Cloudinary(MyConfiguration.getMyConfig());
        Transformation t  = new Transformation();
        t.radius(60);
        t.height(500);
        t.width(800);

        String url = cloudinary.url().transformation(t).generate(Imagename);
        return  url;

    }
}
