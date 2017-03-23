package com.example.administrator.music;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.music.Fragment.MusicalFragment;
import com.example.administrator.music.Fragment.SongFragment;
import com.example.administrator.music.Utils.SongFind;
import com.example.administrator.music.adapter.FragmentPager;
import com.example.administrator.music.adapter.RecyclerAdapter_song;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection, View.OnClickListener {
    int mPosition = 0;
    int currentLevel = 1;
    // 显示当前播放的音乐
    LinearLayout linearLayout_play_ui;
    TextView play_song_name, play_song_author;
    ImageButton imageButton_previous, imageButton_play_pause, imageButton_next;
    TabLayout tabLayout;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerAdapter_song recyclerAdapter_song;
    ArrayList<Song> searchList = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Song> songArrayList = new ArrayList<>();
    ViewPager viewPager;
    SongFind songFind;
    MusicPlayerService musicPlayer;
    /**
     * 当前歌曲索引
     * —1:刚刚启动的时候，没有任何播放的歌曲
     */
    private int currentPosition = -1;
    private ArrayList<Song> checkSong = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getSongInformation();
        //　Fragment是一个独立的模块,紧紧地与activity绑定在一起。可以运行中动态地移除、加入、交换等。
        initFragment();
        //开启音乐播放服务
        initService();
        recyclerAdapter_song.setOnItemClickListener(new RecyclerAdapter_song.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Song data, int id) {
                if (currentPosition == id) {
                    // 停止
                    musicPlayer.pauseMusic();
                    setPlayButtonState();
                } else {
                    playCurrentMusic(id);
                    currentPosition = id;
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {

                //设置refresh刷新时间  在其中执行刷新内容 即重新获取内存中歌曲
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        songArrayList = songFind.getSongInformation(MainActivity.this);
                        checkSong = songArrayList;
                        //关闭刷新图标
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }, 5000);
            }
        });
    }

    //控制下面三个按钮的点击事件，播放，切歌功能
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_previous:
                // 声明上一首的索引
                int perIndex = 0;
                // 初次进应用
                if (currentPosition == -1) {
                    perIndex = 0;
                    // 如果，是第一首歌。那么就选择播放最后一首
                } else if (currentPosition == 0) {
                    perIndex = checkSong.size() - 1;
                    // 正常情况
                } else {
                    perIndex = currentPosition - 1;
                }

                // 播放
                playCurrentMusic(perIndex);
                // 注意：修改当前索引
                currentPosition = perIndex;

                break;

            case R.id.imageButton_play_pause:

                // 先设置播放器的状态
                musicPlayer.pauseMusic();
                // 再显示播放器的状态图标
                setPlayButtonState();

                break;

            case R.id.imageButton_next:

                int nextIndex = 0;
                //初次进入
                if (currentPosition == -1) {
                    nextIndex = 0;
                    // 最后一首歌
                } else if (currentPosition == checkSong.size() - 1) {
                    nextIndex = 0;
                } else {
                    nextIndex = currentPosition + 1;
                }
                // 播放
                playCurrentMusic(nextIndex);
                // 注意：更改当前索引
                currentPosition = nextIndex;

                break;

            default:
                break;
        }

    }

    private void getSongInformation() {
        songArrayList = songFind.getSongInformation(MainActivity.this);
        checkSong = songArrayList;

        recyclerAdapter_song = new RecyclerAdapter_song(MainActivity.this, songArrayList);

    }

    //创建及初始化音乐播放服务
    private void initService() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        startService(intent);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    //创建ViewPager中三个Fragment对象,
    private void initFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        SongFragment songFragment = new SongFragment(songArrayList, recyclerAdapter_song);
        fragmentList.add(songFragment);

        MusicalFragment MusicalFragment = new MusicalFragment();
        fragmentList.add(MusicalFragment);
        MusicalFragment AlbumFragment = new MusicalFragment();
        fragmentList.add(AlbumFragment);
        //创建完三个Fragment,加入到ViewPager的适配器(用来切换三个碎片)里

        FragmentPager fragmentPager = new FragmentPager(getSupportFragmentManager(), fragmentList, list);

        viewPager.setAdapter(fragmentPager);
        tabLayout.setupWithViewPager(viewPager);
        setCurrentSongText(0);
    }

    //初始化用到的布局，控件，按钮,以及RecyclerView的适配器
    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        linearLayout_play_ui = (LinearLayout) findViewById(R.id.contorl_player);
        play_song_name = (TextView) findViewById(R.id.play_song_name);
        play_song_author = (TextView) findViewById(R.id.play_song_author);
        imageButton_previous = (ImageButton) findViewById(R.id.imageButton_previous);
        imageButton_next = (ImageButton) findViewById(R.id.imageButton_next);
        imageButton_play_pause = (ImageButton) findViewById(R.id.imageButton_play_pause);

        imageButton_play_pause.setOnClickListener(this);
        imageButton_next.setOnClickListener(this);
        imageButton_previous.setOnClickListener(this);
        songFind = new SongFind();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        list.add("单曲");
        list.add("歌手");
        list.add("专辑");
        //去工具类SondFind中调用getSongInformation 获取内存中所有歌曲的信息

    }

    /**
     * 根据索引播放对应的方法
     *
     * @param position
     */
    public void playCurrentMusic(int position) {


        Song mSong = checkSong.get(position);
        musicPlayer.setMusic(mSong);
        musicPlayer.playMusic();
        // 设置当前播放音乐的数据
        setCurrentSongText(position);
        // 改变播放按钮状态
        setPlayButtonState();
    }

    /**
     * 设置当前播放歌曲名
     *
     * @param position
     */
    public void setCurrentSongText(int position) {
        // 获得对应position的歌曲
        mPosition = position;
        Song mSong = checkSong.get(position);
        // 显示歌曲名和歌手
        // tv_current_song.setText(mSong.getSongName() + "\n" + mSong.getSinger());
        play_song_name.setText(mSong.getSong_name());
        play_song_author.setText(mSong.getSong_author());
    }

    public void setPlayButtonState() {
        // 再显示播放器的状态图标
        boolean isPlaying = musicPlayer.isPlaying();
        if (isPlaying) {
            imageButton_play_pause.setImageResource(R.drawable.pause_music);
        } else {
            imageButton_play_pause.setImageResource(R.drawable.play_music);

        }

    }

    //把搜索框中获取的内容，带到工具类SondFind中调用getsearchSong 获取内存中符合要求的歌曲的信息
    private void queryData(String content) {
        searchList.clear();
        searchList = SongFind.searchSong(content.trim(), MainActivity.this);
        checkSong = searchList;

        recyclerAdapter_song.setAdapterData(searchList);
        recyclerAdapter_song.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);


        /***
         *    1.Creating a Searchable Configuration
         *    2.Declaring a searchable activity
         *   3.Configuring the search widget
         *   4.获取的SearchView对象，设置监听
         */
        //Configuring the search widget
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.serach).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //4.获取的SearchView对象，设置监听


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            //这里是要提交后才调用的
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            //这是随着输入变化调用的
            public boolean onQueryTextChange(String newText) {

                //linearLayout_play_ui.setVisibility(View.INVISIBLE);
                //数据每次变化每次都去调用queryData（)去内存中搜索对应条件歌曲信息
                queryData(newText);

                return true;
            }
        });


        return true;
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicPlayerService.MusicBinder mBinder = (MusicPlayerService.MusicBinder) service;
        musicPlayer = mBinder.getService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicPlayer = null;
    }

    @Override

    //这是控制上一曲，播放，暂停，下一曲 按钮的onClick


    //按返回时调用的方法
    public void onBackPressed() {
        // tabLayout.setVisibility(View.VISIBLE);
        if (currentLevel == 1) {
            checkSong = songArrayList;
            //linearLayout_play_ui.setVisibility(View.VISIBLE);
            setCurrentSongText(currentPosition);
        }
    }

}




