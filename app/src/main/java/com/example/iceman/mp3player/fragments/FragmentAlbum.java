package com.example.iceman.mp3player.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.iceman.mp3player.R;
import com.example.iceman.mp3player.adapter.AlbumListAdapter;
import com.example.iceman.mp3player.models.Album;
import com.example.iceman.mp3player.utils.AppController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAlbum#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAlbum extends Fragment {

    ProgressBar mProgressBar;
    View mView;
    RecyclerView mRvAlbumList;
    AlbumListAdapter mAlbumAdapter;
    ArrayList<Album> mLstAlbum;
    LoadAlbumList loadAlbumList;


    public FragmentAlbum() {
        // Required empty public constructor
    }


    public static FragmentAlbum newInstance(String param1, String param2) {
        FragmentAlbum fragment = new FragmentAlbum();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_album, container, false);
        initControls();
        showAlbumList();
        return mView;
    }

    private void initControls() {
        mRvAlbumList = (RecyclerView) mView.findViewById(R.id.rv_album_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvAlbumList.setLayoutManager(layoutManager);

        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar_album_list);
    }

    private class LoadAlbumList extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mLstAlbum = AppController.getInstance().getListAlbum();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mAlbumAdapter = new AlbumListAdapter(getActivity(), mLstAlbum);
            mProgressBar.setVisibility(View.GONE);
            mRvAlbumList.setAdapter(mAlbumAdapter);
        }
    }

    private void showAlbumList() {
        loadAlbumList = new LoadAlbumList();
        loadAlbumList.execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(loadAlbumList != null && loadAlbumList.getStatus() != AsyncTask.Status.FINISHED){
            loadAlbumList.cancel(true);
        }
    }
}