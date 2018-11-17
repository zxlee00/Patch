package sg.zhixuan.patch2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    Context context;
    List<User> friendsList;

    public FriendsAdapter (Context c, List<User> l) {
        this.context = c;
        this.friendsList = l;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.friendsitem,
                parent,
                false
        );

        return new FriendsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        final User user = friendsList.get(position);

        holder.txtFriendName.setText(user.name);

        Glide.with(context)
                .load(user.profilePic)
                .apply(new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.imgFriendItem);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FriendInformationActivity.age = Integer.toString(user.age);
                FriendInformationActivity.gender = user.gender;
                FriendInformationActivity.name = user.name;
                FriendInformationActivity.imgurl = user.profilePic;

                String hobbyList[] = user.hobby.split(",");
                String hobby = "";
                for (int i = 0; i < hobbyList.length; i ++) {
                    hobby = hobby + "\n" + hobbyList[i];
                    FriendInformationActivity.hobby = hobby;
                }

                FriendInformationActivity.selectedFriend = user;

                context.startActivity(new Intent(context, FriendInformationActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFriendName;
        public ImageView imgFriendItem;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            txtFriendName = (TextView)itemView.findViewById(R.id.txtFriendName);
            imgFriendItem = (ImageView)itemView.findViewById(R.id.imgFriendItem);
        }
    }

    public void changeList(List<User> list) {
        this.friendsList = list;
        this.notifyDataSetChanged();
    }
}
