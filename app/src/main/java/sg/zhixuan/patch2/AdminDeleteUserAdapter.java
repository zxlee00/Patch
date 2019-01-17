package sg.zhixuan.patch2;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminDeleteUserAdapter extends RecyclerView.Adapter<AdminDeleteUserAdapter.AdminDeleteUserViewHolder> {

    Context context;
    List<User> userList;
    String name;
    String date;
    private static final String TAG = "AdminDeleteUserAdapter";

    public AdminDeleteUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdminDeleteUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_delete_user_item, parent, false);
        return new AdminDeleteUserViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDeleteUserViewHolder holder, final int position) {
        final User user = userList.get(position);

        name = user.getName();
        date = user.getLastSignedIn();
        holder.tvName.setText(name);
        holder.tvDate.setText(date);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_delete_user);

                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                TextView tvName = dialog.findViewById(R.id.tvName);
                TextView tvDate = dialog.findViewById(R.id.tvDate);

                tvName.setText(user.getName());
                tvDate.append(user.getLastSignedIn());

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference matchupRef = FirebaseDatabase.getInstance().getReference().child("matchup").child("lookingForMatchup");
                        matchupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                userList.remove(userList.indexOf(user));
                                AdminDeleteUserActivity.userList = userList;
                                AdminDeleteUserActivity.adapter.notifyDataSetChanged();
                                dataSnapshot.child(user.getUid()).getRef().removeValue();
                                dialog.dismiss();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class AdminDeleteUserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDate;
        Button btnDelete;

        public AdminDeleteUserViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
