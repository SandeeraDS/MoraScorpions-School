package com.mlpj.www.morascorpions.Notifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;


import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mlpj.www.morascorpions.Chat.ChatActivity;
import com.mlpj.www.morascorpions.Chat.MessageItem;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

public class NotificationService extends IntentService {

    private DatabaseReference mRootRef;
    private User mCurrentUser;
    private NotificationCompat.Builder notification;
    private int uniqueId = 4445;

    public NotificationService() {
        super("NotificationService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        loadMessages();
    }

    private void loadMessages() {
        mCurrentUser = new UserLocalStore(getBaseContext()).getUserDetails();
        mRootRef = FirebaseDatabase.getInstance().getReference().child("notifications");
        mRootRef.child("chat").child(mCurrentUser.getP_Id())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        if (!message.isSeen()) {
                            notification = new NotificationCompat.Builder(getBaseContext(), "chat");
                            notification.setAutoCancel(true);

                            notification.setSmallIcon(R.drawable.mainicon);
                            notification.setTicker("New Messages From " + message.getSentName());
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle("New messages In Mora Scrorpions From " + message.getSentName());
                            notification.setContentText(message.getBody());
                            notification.setVibrate(new long[]{0});
                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            notification.setSound(alarmSound);


                            Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                            intent.putExtra("id", message.getSentBy());
                            intent.putExtra("receiverName", message.getSentName());
                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);


                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);

                            mNotificationManager.notify((int) message.getTimestamp(), notification.build());

                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
