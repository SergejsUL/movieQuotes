package ie.ul.serge.moviequotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MovieQuoteDetailActivity extends AppCompatActivity {

    private DocumentSnapshot mDocSnap;
    private DocumentReference mDocRef;

    private TextView mQuoteTextView;
    private TextView mMovieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_quote_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuoteTextView = findViewById(R.id.detail_view_quote);
        mMovieTextView = findViewById(R.id.detail_view_movie);

        //receive intent from the activity that passed it
        Intent receivedIntent = getIntent();
        //pull string by key
        String docId = receivedIntent.getStringExtra(Constants.EXTRA_DOC_ID);
        //navigete to collection and document in firebase
        mDocRef = FirebaseFirestore.getInstance()
                .collection(Constants.MOVIEQUOTES_COLLECTION).document(docId);
        //Set eventListenet to pull shapshot and details and populate values.
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.w(Constants.TAG,"Listen failed");
                    return;
                }
                if (documentSnapshot.exists()){
                    mDocSnap = documentSnapshot;
                    mQuoteTextView.setText((String)documentSnapshot.get(Constants.QUOTE_KEY));
                    mMovieTextView.setText((String)documentSnapshot.get(Constants.MOVIE_KEY));

                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialogue();
               }
        });
    }

    private void showEditDialogue() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.moviequote_dialogue,null,false);
            builder.setView(view);
            final TextView editQuoteText = view.findViewById(R.id.input_quote);
            final TextView editMovieText = view.findViewById(R.id.input_movie);

        editQuoteText.setText((String)mDocSnap.get(Constants.QUOTE_KEY));
        editMovieText.setText((String)mDocSnap.get(Constants.MOVIE_KEY));
            builder.setTitle("Edit this quote");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Map<String, Object> mq = new HashMap<>();

                    mq.put(Constants.QUOTE_KEY, editQuoteText.getText().toString() );
                    mq.put(Constants.MOVIE_KEY, editMovieText.getText().toString() );
                    mq.put(Constants.CREATED_KEY, new Date());
                    mDocRef.update(mq);

                }
            });
            builder.setNegativeButton(android.R.string.cancel,null);

            builder.create().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
             mDocRef.delete();
             //close this activity
             finish();
             return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
