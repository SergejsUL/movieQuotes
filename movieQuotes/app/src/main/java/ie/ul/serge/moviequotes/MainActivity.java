package ie.ul.serge.moviequotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    //private int mQuoteNr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this  ));
        recyclerView.setHasFixedSize(true);

        MovieQuoteAdapter moviewQuoteAdapter = new MovieQuoteAdapter();
        recyclerView.setAdapter(moviewQuoteAdapter);



        final FirebaseFirestore db = FirebaseFirestore.getInstance();
/*
        db.collection("moviedatabase")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                //Firebase code


                // Create a new user with a first and last name
                mQuoteNr=mQuoteNr+1;
                Map<String, Object> mq = new HashMap<>();
                mq.put("movie", "Movie Nr: " + mQuoteNr);
                mq.put("quote", "Quote Nr: " + mQuoteNr);

                // Add a new document with a generated ID
                db.collection("moviedatabase").add(mq);

                //end of firebase code*/


                Snackbar.make(view, "Added movie and quote", Snackbar.LENGTH_LONG).show();

            }
        });
    }

}
