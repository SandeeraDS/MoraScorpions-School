package com.mlpj.www.morascorpions.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class TeacherProfileFragment extends Fragment {
    private UserLocalStore userLocalStore;
    private User currentUser;
    private CircleImageView imgProfilePic;
    private TextView tvProfileTeacherName, tvProfileTeacherRoleAndClass, tvProfileTeacherGrade, tvProfileTeacherTp, tvProfileTeacherEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        userLocalStore = new UserLocalStore(getContext());
        currentUser = userLocalStore.getUserDetails();

        imgProfilePic = view.findViewById(R.id.imgProfileTeacherPic);
        tvProfileTeacherName = view.findViewById(R.id.tvProfileTeacherName);
        tvProfileTeacherRoleAndClass = view.findViewById(R.id.tvProfileTeacherRoleAndClass);
        tvProfileTeacherGrade = view.findViewById(R.id.tvProfileTeacherGrade);
        tvProfileTeacherTp = view.findViewById(R.id.tvProfileTeacherTp);
        tvProfileTeacherEmail = view.findViewById(R.id.tvProfileTeacherEmail);

        if(!currentUser.getPicUrl().equals("")){
            Picasso.with(getContext()).load(currentUser.getPicUrl()).into(imgProfilePic);
        }

        tvProfileTeacherName.setText(currentUser.getName());
        tvProfileTeacherRoleAndClass.setText("Teacher of Class " + currentUser.getClassRoomName());
        tvProfileTeacherGrade.setText("Grade- " + currentUser.getTeacherGrade());
        tvProfileTeacherTp.setText("Telephone No- " + currentUser.getTpNumber());
        tvProfileTeacherEmail.setText("Email- " + currentUser.getEmail());

        return view;
    }


}
