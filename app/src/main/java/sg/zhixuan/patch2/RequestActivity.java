package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    RecyclerView rvRequests;
    RequestAdapter requestAdapter;
    List<Request> requestList;
    DatabaseReference requestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        rvRequests = (RecyclerView)findViewById(R.id.rvRequests);
        requestList = new ArrayList<Request>();

        requestRef = FirebaseDatabase.getInstance().getReference().child("requests").child(MainActivity.uid);
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.clear();
                Log.d("ZZZ", "REQUEST CHANGED");

                for (final DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {

                    DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference().child("users").child(requestSnapshot.getKey()).child("name");
                    nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot nameSnapshot) {
                            requestList.add(new Request(nameSnapshot.getValue(String.class), requestSnapshot.getKey()));

                            requestAdapter = new RequestAdapter(RequestActivity.this, requestList);
                            requestAdapter.changeList(requestList);
                            requestAdapter.notifyDataSetChanged();
                            rvRequests.setLayoutManager(new LinearLayoutManager(RequestActivity.this));
                            rvRequests.setItemAnimator(new DefaultItemAnimator());
                            rvRequests.setAdapter(requestAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
