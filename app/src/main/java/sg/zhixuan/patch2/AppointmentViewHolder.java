package sg.zhixuan.patch2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView txtApptTitle, txtApptParty, txtApptDate, txtApptTime, txtApptLocation;
    public ImageView imgApptParty;

    public AppointmentViewHolder(View itemView) {
        super(itemView);

        txtApptParty = (TextView)itemView.findViewById(R.id.txtApptParty);
        txtApptTitle = (TextView)itemView.findViewById(R.id.txtApptTitle);
        txtApptDate = (TextView)itemView.findViewById(R.id.txtApptDate);
        txtApptTime = (TextView)itemView.findViewById(R.id.txtApptTime);
        txtApptLocation = (TextView)itemView.findViewById(R.id.txtApptLocation);
        imgApptParty = (ImageView)itemView.findViewById(R.id.imgApptParty);
    }
}