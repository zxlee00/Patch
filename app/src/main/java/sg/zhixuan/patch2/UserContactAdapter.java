package sg.zhixuan.patch2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;

public class UserContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    Context context;
    Activity activity;
    List<User> contactNameList;
    static String apptPartySelected;
    static String apptPartyIDSelected;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference;
    static String apptPartyImageURLSelected;

    public UserContactAdapter(Context c, Activity activity, List<User> l) {
        this.context = c;
        this.activity = activity;
        this.contactNameList = l;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.apptgroupitem,
                parent,
                false
        );

        return new ContactViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {
        holder.txtContactName.setText(contactNameList.get(position).name);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(contactNameList.get(position).uid).child("profilePic");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);

                if(url!= null){
                    Glide.with(context)
                            .load(url)
                            .apply(new RequestOptions()
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(holder.imgContactPhoto);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apptPartyIDSelected = contactNameList.get(position).uid;
                apptPartySelected = contactNameList.get(position).name;
                apptPartyImageURLSelected = contactNameList.get(position).profilePic;
                context.startActivity(new Intent(context, CreateApptDetails.class));
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactNameList.size();
    }
}
