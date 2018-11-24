package sg.zhixuan.patch2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Context context;
    List<Request> requestList;
    DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("requests");
    DatabaseReference matchUpRef = FirebaseDatabase.getInstance().getReference().child("matchup");
    DatabaseReference contactRef = FirebaseDatabase.getInstance().getReference().child("contacts");
    DatabaseReference moveMessagesMatchUpRef = FirebaseDatabase.getInstance().getReference().child("matchup").child("messages");
    DatabaseReference moveMessagesChatsRef = FirebaseDatabase.getInstance().getReference().child("messages");

    public RequestAdapter(Context c, List<Request> l) {
        this.context = c;
        this.requestList = l;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.requestitem,
                parent,
                false
        );

        return new RequestViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
        final Request request = requestList.get(position);

        holder.txtRequestUserName.setText(request.name);

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Accept")
                        .setMessage("Are you sure to accept this request from " + request.name + "?")
                        .setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestRef.child(MainActivity.uid).child(request.uid).removeValue();
                                requestRef.child(request.uid).child(MainActivity.uid).removeValue();
                                //Todo: Move messages from matchup to here before removing the child nodes
                                matchUpRef.child(MainActivity.uid).child(request.uid).removeValue();
                                matchUpRef.child(request.uid).child(MainActivity.uid).removeValue();
                                contactRef.child(MainActivity.uid).child(request.uid).child("type").setValue("User");
                                contactRef.child(request.uid).child(MainActivity.uid).child("type").setValue("User");
                                notifyDataSetChanged();

                                moveMessagesMatchUpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot messagesSnapshot : dataSnapshot.child(MainActivity.uid + "_" + request.uid).getChildren()) {
                                            moveMessagesChatsRef.child(MainActivity.uid + "_" + request.uid).child(messagesSnapshot.getKey()).setValue(messagesSnapshot.getValue());
                                        }

                                        for (DataSnapshot messageSnapshot : dataSnapshot.child(request.uid + "_" + MainActivity.uid).getChildren()) {
                                            moveMessagesChatsRef.child(request.uid + "_" + MainActivity.uid).child(messageSnapshot.getKey()).setValue(messageSnapshot.getValue());
                                        }

                                        moveMessagesMatchUpRef.child(MainActivity.uid + "_" + request.uid).removeValue();
                                        moveMessagesMatchUpRef.child(request.uid + "_" + MainActivity.uid).removeValue();

                                        requestList.remove(position);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Toast.makeText(context, "All your previous texts with " + request.name + " will be moved over to the 'CHATS' page.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Decline")
                        .setMessage("Are you sure to decline this request from " + request.name + "?\nThis action cannot be undone.")
                        .setPositiveButton("DECLINE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestRef.child(MainActivity.uid).child(request.uid).removeValue();
                                requestList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    public void changeList(List<Request> l) {
        this.requestList = l;
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRequestUserName;
        public Button btnAccept, btnDecline;

        public RequestViewHolder(View itemView) {
            super(itemView);

            txtRequestUserName = (TextView)itemView.findViewById(R.id.txtRequestUserName);
            btnAccept = (Button)itemView.findViewById(R.id.btnAccept);
            btnDecline = (Button)itemView.findViewById(R.id.btnDecline);
        }
    }
}
