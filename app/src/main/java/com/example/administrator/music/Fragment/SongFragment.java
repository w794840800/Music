package com.example.administrator.music.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.music.R;
import com.example.administrator.music.Song;
import com.example.administrator.music.Utils.DividerItemDecoration;
import com.example.administrator.music.adapter.RecyclerAdapter_song;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/21.
 */
public class SongFragment extends Fragment {

    ArrayList<Song> songArrayList;
    RecyclerView recyclerView;
    RecyclerAdapter_song adapter_song;

    // int recycler_id;
    public SongFragment() {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public SongFragment(ArrayList<Song> list, RecyclerAdapter_song recyclerAdapter_song) {
        songArrayList = list;
        adapter_song = recyclerAdapter_song;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler, container, false);

        return recyclerView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter_song);

    }
}
