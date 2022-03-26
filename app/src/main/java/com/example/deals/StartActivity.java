package com.example.deals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements HelpAdapter.ListItemClickListener{
RecyclerView.Adapter adapter,adaper2;
RecyclerView recyclerView,recyclerView2;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
MainAdapter mainAdapter;
ArrayList<Entity> lists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
            setContentView(R.layout.activity_start);

        firebaseAuth=FirebaseAuth.getInstance();


        //first recyclerview
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<model> list=new ArrayList<>();
        list.add(new model("Audi"));
        list.add(new model("Porsche"));
        list.add(new model("BMW"));
        list.add(new model("Sedan"));
        list.add(new model("SUV"));
        adapter=new HelpAdapter(list,this);
        recyclerView.setAdapter(adapter);

        //second recyclerview
        databaseReference=FirebaseDatabase.getInstance().getReference("allimages");
        recyclerView2=findViewById(R.id.recyclerview1);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        lists=new ArrayList<>();
        mainAdapter=new MainAdapter(this,lists);
        recyclerView2.setAdapter(mainAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Entity users=dataSnapshot.getValue(Entity.class);
                    lists.add(users);
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @Override
    public void onphoneListClick(int clickedItemIndex) {

        Intent intent;
        switch (clickedItemIndex) {
            case 0:
                intent = new Intent(StartActivity.this, Audi.class);
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(StartActivity.this, Audi.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(StartActivity.this, Audi.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(StartActivity.this, Audi.class);
                startActivity(intent);
                break;

            case 4:
                intent = new Intent(StartActivity.this, Audi.class);
                startActivity(intent);
                break;
        }


    }
}