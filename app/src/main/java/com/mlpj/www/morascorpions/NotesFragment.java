package com.mlpj.www.morascorpions;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private RecyclerView.Adapter mNotesAdapter;
    private RecyclerView mRecyclerView;
    private List<NoteItem> mNoteItems;
    private FloatingActionButton mFloatingActionButtonUploadNotes;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("NOTE"));

        Bundle args = getArguments();
        final int ternaryId = args.getInt("ternaryId");     //use this to get the set of notes along with ternaryId


        mNoteItems = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.RecyclerViewNotes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //code for getting the related note set from Database


        mNoteItems.add(new NoteItem(1,1,"2017/05/12","Vectors","hhv jhhgyg hgg", "Vectors.pdf",true));
        mNoteItems.add(new NoteItem(2,1,"2017/05/19","Matrices","hhv jhhgyg hgg", "Vectors.pdf",true));
        mNoteItems.add(new NoteItem(3,1,"2017/05/26","Algebra","hhv jhhgyg hgg", "Vectors.pdf",true));
        mNoteItems.add(new NoteItem(4,1,"2017/06/01","Derivatives","hhv jhhgyg hgg", "Vectors.pdf",true));

        FragmentManager fragmentManager = getFragmentManager();
        mNotesAdapter = new NotesAdapter(mNoteItems, getContext(), fragmentManager);
        mRecyclerView.setAdapter(mNotesAdapter);



        mFloatingActionButtonUploadNotes = view.findViewById(R.id.floatingActionButtonNotesUpload);
        mFloatingActionButtonUploadNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UploadNotesActivity.class);
                intent.putExtra("ternaryId",ternaryId);
                startActivity(intent);
            }
        });

        return view;
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getContext(),"recieved",Toast.LENGTH_LONG).show();
            NoteItem newNote = (NoteItem) intent.getSerializableExtra("NEW_NOTE");
            mNoteItems.add(newNote);
            mNotesAdapter.notifyDataSetChanged();
        }
    };

}