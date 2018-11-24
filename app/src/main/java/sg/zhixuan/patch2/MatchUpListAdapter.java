package sg.zhixuan.patch2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MatchUpListAdapter extends RecyclerView.Adapter<MatchUpListAdapter.MatchUpViewHolder>{

    Context context;
    List<MatchUpUser> matchedUpUserList;
    DatabaseReference matchupRef = FirebaseDatabase.getInstance().getReference().child("matchup");
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("requests");

    public MatchUpListAdapter(Context c, List<MatchUpUser> l) {
        this.context = c;
        this.matchedUpUserList = l;
    }

    @NonNull
    @Override
    public MatchUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.matchuplistitem,
                parent,
                false
        );

        return new MatchUpViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchUpViewHolder holder, final int position) {
        final MatchUpUser matchUpUser = matchedUpUserList.get(position);

        if (matchUpUser.type.equals("foundByUser")) {
            holder.txtMatchedUpUser.setTextColor(Color.parseColor("#3b3561"));
        } else if (matchUpUser.type.equals("userFoundByOthers")) {
            holder.txtMatchedUpUser.setTextColor(Color.parseColor("#dd1c1a"));
        }

        holder.txtMatchedUpUser.setText(matchUpUser.name);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence options[] = new CharSequence[]{"Delete", "User Details"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Options")
                    .setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                                deleteDialog.setTitle("Delete")
                                        .setMessage("Would you like to delete this match up?\nNote that you will not be able to contact this user anymore if you accept.")
                                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                matchupRef.child(MainActivity.uid).child(matchedUpUserList.get(position).uid).removeValue();
                                                matchupRef.child(matchedUpUserList.get(position).uid).child(MainActivity.uid).removeValue();
                                                requestRef.child(matchedUpUserList.get(position).uid).child(MainActivity.uid).removeValue();
                                                requestRef.child(MainActivity.uid).child(matchedUpUserList.get(position).uid).removeValue();
                                                matchedUpUserList.remove(position);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .show();
                            }
                            else if (which == 1) {
                                MatchUpUserInformation.selectedUID = matchUpUser.uid;
                                context.startActivity(new Intent(context, MatchUpUserInformation.class));
                            }
                        }
                    }).show();

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MatchupUserConversationActivity.MatchUpUserHobbies = dataSnapshot.child(matchUpUser.uid).child("hobby").getValue(String.class);
                        MatchupUserConversationActivity.MatchUpUserName = matchUpUser.name;
                        MatchupUserConversationActivity.MatchUpUserUID = matchUpUser.uid;
                        context.startActivity(new Intent(context, MatchupUserConversationActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.pressedbottomborder);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(R.drawable.bottomborder);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.bottomborder);
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchedUpUserList.size();
    }

    public class MatchUpViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMatchedUpUser;

        public MatchUpViewHolder(View itemView) {
            super(itemView);

            txtMatchedUpUser = (TextView)itemView.findViewById(R.id.txtMatchedUpUser);
        }
    }
}
