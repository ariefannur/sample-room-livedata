package com.platk.roomexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariefmaffrudin on 1/3/18.
 */

public class MainActivity extends AppCompatActivity {

    SampleDatabase sampleDatabase;

    private RecyclerView rvMain;
    private FloatingActionButton fabAdd;
    private Adapter adapter;
    private DaoMember daoMember;
    private MemberViewModel memberViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sampleDatabase = Room.databaseBuilder(this, SampleDatabase.class, "sample-db").build();

        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        adapter = new Adapter(new ArrayList<Members>());
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(adapter);


        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

       daoMember = sampleDatabase.daoMember();

        new AsyncTask<Void, Void, Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {

                Members members = new Members();
                //members.setId(1);
                members.setName("Nikolas");
                members.setAddress("Bantul");
                members.setPosition("Android dev");


                daoMember.insertMembers(members);

                LiveData<List<Members>> liveDataMembers = daoMember.getAllMembers();

                liveDataMembers.observe(MainActivity.this, new Observer<List<Members>>() {
                    @Override
                    public void onChanged(@Nullable List<Members> members) {
                        adapter.update(members);
                    }
                });

                return 1;
            }
        }.execute();


    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        private List<Members> lsMembers;

        public Adapter(ArrayList<Members> lsMembers){
            this.lsMembers = lsMembers;
        }

        public void update(List<Members> lsMembers){
            this.lsMembers = lsMembers;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.txtName.setText(lsMembers.get(position).getName());
            holder.txtPosition.setText(lsMembers.get(position).getPosition());

            holder.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.menu_delete){
                        new AsyncTask<Void, Void, Integer>(){

                            @Override
                            protected Integer doInBackground(Void... voids) {
                                daoMember.deleteRecord(lsMembers.get(position));
                                return 0;
                            }
                        }.execute();

                    }
                    return false;
                }
            });

            holder.layItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MutableLiveData<Members> mutableLiveDataMember = new MutableLiveData<>();
                    mutableLiveDataMember.setValue(lsMembers.get(position));
                    memberViewModel.setMember(mutableLiveDataMember);
                    startActivity(new Intent(MainActivity.this, DetilActivity.class));
                }
            });


        }

        @Override
        public int getItemCount() {
            return lsMembers!= null ? lsMembers.size() : 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName, txtPosition;
        private View imgMore;
        private PopupMenu popupMenu;
        private ViewGroup layItem;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtPosition = (TextView) itemView.findViewById(R.id.txt_position);
            imgMore = itemView.findViewById(R.id.img_more);
            layItem = (ViewGroup) itemView.findViewById(R.id.lay_item);

            popupMenu = new PopupMenu(imgMore.getContext(), imgMore);
            popupMenu.inflate(R.menu.menu_more);

            imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupMenu.show();
                }
            });

        }
    }

    
}
