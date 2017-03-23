package com.example.administrator.music.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.music.Song;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/22.
 */
public class SongFind {
    Context context;
   // ArrayList<Song>songArrayList;
    //ContentResolver contentResolver;
    public static ArrayList<Song> getSongInformation(Context context1){
      ArrayList<Song>arrayList=new ArrayList<>();
       Cursor cursor=context1.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
          while (cursor.moveToNext()){
              Song song=new Song();
              song.setSong_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
              song.setSong_author(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
              song.setSong_album(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
              String data=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
              song.setSong_location(Uri.parse(data));
              arrayList.add(song);

          }
        cursor.close();
return arrayList;
    }

    public static ArrayList<Song> songMum(Context context,String data){
        ArrayList<Song>arrayList=new ArrayList<>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,new String[]{"count(*)"},"artist=?",new String[]{data},null);
        //MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        while (cursor.moveToNext()){
            Song song=new Song();
            song.setSong_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            song.setSong_author(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setSong_album(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            String data3=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            song.setSong_location(Uri.parse(data3));
            arrayList.add(song);

        }
        cursor.close();
return arrayList;
    }
   public static ArrayList<Song>searchSong(String data,Context context1){
       ArrayList<Song>arrayList=new ArrayList<>();
       String contain = "%" + data + "%";
            //         "name like ？", new String[]{"%"+searcherFilter+"%"},
       Cursor cursor=context1.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,"TITLE like ?",new String[]{contain},null);
       while (cursor.moveToNext()) {
           Song song = new Song();
           song.setSong_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
           song.setSong_author(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
           song.setSong_album(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
           String data1 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
           song.setSong_location(Uri.parse(data1));
           //int num=getSongNum();
           arrayList.add(song);

       }
       cursor.close();
return arrayList;
       }
  public static  int getSongNum(ArrayList<Song>arrayList){
      int index=0;
      String name=arrayList.get(0).getSong_name();
      for (int i=0;i<arrayList.size();i++){
                if( name.equals(arrayList.get(i).getSong_name())) {
                    index++;
                }

      }
      return index;
  }


}
