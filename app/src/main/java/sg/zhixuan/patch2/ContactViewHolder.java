package sg.zhixuan.patch2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactViewHolder extends RecyclerView.ViewHolder{

    public TextView txtContactName;
    public ImageView imgContactPhoto;

    public ContactViewHolder(View itemView) {
        super(itemView);

        txtContactName = (TextView)itemView.findViewById(R.id.txtContactName);
        imgContactPhoto = (ImageView)itemView.findViewById(R.id.imgContactPhoto);
    }
}
