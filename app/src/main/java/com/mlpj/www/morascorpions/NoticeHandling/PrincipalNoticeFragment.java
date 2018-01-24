package com.mlpj.www.morascorpions.NoticeHandling;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrincipalNoticeFragment extends Fragment {

    private RecyclerView.Adapter mNoticeAdapter;
    private RecyclerView mRecyclerView;
    private List<NoticeItem> mNoticeItems;
    private FloatingActionButton mFloatingActionButtonAddNotice;
    private User mCurrentUser;
    private ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal_notice, container, false);


        mNoticeItems = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.noticeRecyclerViewPrincipal);
        mFloatingActionButtonAddNotice  = view.findViewById(R.id.floatingActionButtonNotice);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCurrentUser = new UserLocalStore(getContext()).getUserDetails();

        mProgressDialog = new ProgressDialog(getContext());
        loadNotices();

       mFloatingActionButtonAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNoticeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadNotices();

    }



    public void loadNotices(){
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait...!");
        mProgressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<NoticeItem>> call =  client.getNoticesForPrincipal();

        call.enqueue(new Callback<ArrayList<NoticeItem>>() {
            @Override
            public void onResponse(Call<ArrayList<NoticeItem>> call, Response<ArrayList<NoticeItem>> response) {
                mProgressDialog.dismiss();

                if(response.body()!=null && response.body().size()!=0){

                    mNoticeItems = response.body();
                    mNoticeAdapter = new NoticeAdapter(mNoticeItems);
                    mRecyclerView.setAdapter(mNoticeAdapter);
                }else{
                    Toast.makeText(getContext(),"Error Loading Data ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<NoticeItem>> call, Throwable t) {
                Toast.makeText(getContext(),"Error Loading Data " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
