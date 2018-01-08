package com.platk.roomexample;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by ariefmaffrudin on 1/4/18.
 */

public class MemberViewModel extends ViewModel {

    private MutableLiveData<Members> members;
    public void setMember(MutableLiveData<Members> member){
        this.members = member;
    }

    public MutableLiveData<Members> getMembers(){
        return members;
    }
}
