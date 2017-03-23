package com.example.administrator.music.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.music.R;
import com.example.administrator.music.Song;
import com.example.administrator.music.adapter.RecyclerAdapter_song;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicalFragment extends Fragment {
    public MusicalFragment() {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public MusicalFragment(ArrayList<Song> list, RecyclerAdapter_song recyclerAdapter_song) {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.musical_fragment, container, false);
    }
}
