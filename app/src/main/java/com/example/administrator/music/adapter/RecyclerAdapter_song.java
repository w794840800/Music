package com.example.administrator.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.music.R;
import com.example.administrator.music.Song;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/22.
 */
public class RecyclerAdapter_song extends RecyclerView.Adapter<RecyclerAdapter_song.MyViewHolder> {
    Context context;
    int cutrrent_position;
    ArrayList<Song> list;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    /*@Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(v,(Song) v.getTag(),cutrrent_position);
        }
    }*/


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Song data,int id);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public RecyclerAdapter_song(Context mContext, ArrayList<Song> songs) {
        list = songs;
        context = mContext;

    }

    public void setAdapterData(ArrayList<Song> data) {
        list = data;

    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myViewHolder = LayoutInflater.from(context).inflate(R.layout.recycler_song_item, parent, false);


        return new MyViewHolder(myViewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        cutrrent_position=position;
        holder.recyc_song_album.setText(list.get(position).getSong_album());
        holder.recyc_song_author.setText(list.get(position).getSong_author());
        holder.recyc_song_name.setText(list.get(position).getSong_name());
   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (mOnItemClickListener != null) {

               mOnItemClickListener.onItemClick(v,list.get(position),position);
           }
       }
   });
     //   holder.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recyc_song_name, recyc_song_author, recyc_song_album;

        public MyViewHolder(View itemView) {
            super(itemView);
            recyc_song_album = (TextView) itemView.findViewById(R.id.recyc_song_album);
            recyc_song_name = (TextView) itemView.findViewById(R.id.recyc_song_name);
            recyc_song_author = (TextView) itemView.findViewById(R.id.recyc_song_author);
        }
    }



}
