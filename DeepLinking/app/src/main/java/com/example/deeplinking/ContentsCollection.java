package com.example.deeplinking;

import java.util.ArrayList;

public class ContentsCollection  {

    public static ArrayList<Contents> getContents(){

        ArrayList<Contents> contentsArrayList = new ArrayList<>();
        Contents dataContents = new Contents();

        dataContents.setName("image name 1");
        dataContents.setUrl("IMG-20200413-WA0014.jpg");
        contentsArrayList.add(dataContents);
        System.out.println("1 : " + dataContents.getUrl());

        //dataContents = new Contents();
        dataContents = new Contents();
        dataContents.setName("image name 2");
        dataContents.setUrl("https://res.cloudinary.com/demo/image/upload/sample.jpg");
        contentsArrayList.add(dataContents);



        return contentsArrayList;
    }
}
