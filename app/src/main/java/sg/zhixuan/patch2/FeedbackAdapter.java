package sg.zhixuan.patch2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    Context context;
    List<String> feedbackList;

    public FeedbackAdapter (Context c, List<String> l) {
        this.context = c;
        this.feedbackList = l;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.feedbackitem,
                parent,
                false
        );

        return new FeedbackViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.txtFeedbackDetails.setText(feedbackList.get(position));

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
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFeedbackDetails;

        public FeedbackViewHolder(View itemView) {
            super(itemView);

            txtFeedbackDetails = (TextView)itemView.findViewById(R.id.txtFeedbackDetails);
        }
    }
}
