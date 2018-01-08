package com.platk.roomexample;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by ariefmaffrudin on 1/4/18.
 */

public class DetilActivity extends AppCompatActivity {

    MemberViewModel memberViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        MutableLiveData<Members> mutableLiveData = memberViewModel.getMembers();
        Log.d("AF", "liv data :: "+mutableLiveData == null ? "null" : "tdk null");

        mutableLiveData.observe(this, new Observer<Members>() {
            @Override
            public void onChanged(Members members) {
                if(members != null) {
                    Log.d("AF", " ini adalah detil member " + members.getName() + " : " + members.getPosition());
                }
            }
        });

    }
}
