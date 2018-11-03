package ie.ul.serge.moviequotes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.MoviewQuoteViewHolder>{

    private List<DocumentSnapshot> mMovieQuotesSnapshots = new ArrayList<>();
    public MovieQuoteAdapter(){
        CollectionReference moviequotesRef = FirebaseFirestore.getInstance()
                .collection(Constants.MOVIEQUOTES_COLLECTION);

        moviequotesRef.orderBy(Constants.CREATED_KEY, Query.Direction.DESCENDING).limit(50)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
               if(e!=null){
                   Log.w(Constants.TAG,"Listening failed");
                   return;
               }
               mMovieQuotesSnapshots = documentSnapshots.getDocuments();
               notifyDataSetChanged();
            }
        });
    }
    @NonNull
    @Override
    public MoviewQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate
                (R.layout.moviewquote_itemview,parent,false);
        return new MoviewQuoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviewQuoteViewHolder moviewQuoteViewHolder, int i) {
        DocumentSnapshot ds = mMovieQuotesSnapshots.get(i);
        String quote = (String)ds.get(Constants.QUOTE_KEY);
        String movie = (String)ds.get(Constants.MOVIE_KEY);

        moviewQuoteViewHolder.mQuoteTextView.setText(quote);
        moviewQuoteViewHolder.mMovieTextView.setText(movie);

    }

    @Override
    public int getItemCount() {
        return mMovieQuotesSnapshots.size();
    }

    class MoviewQuoteViewHolder extends RecyclerView.ViewHolder{
        private TextView mQuoteTextView;
        private TextView mMovieTextView;


        public MoviewQuoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            mQuoteTextView = itemView.findViewById(R.id.itemview_quote);
            mMovieTextView = itemView.findViewById(R.id.itemview_movie);
            //set on click listener to tie this card to detailed view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get snapshot of the document (identify that by adapter position)
                    DocumentSnapshot ds = mMovieQuotesSnapshots.get(getAdapterPosition());
                    //get context to pass over to the card
                    Context context = itemView.getContext();
                    //set intent to bring up detailed view for the card
                    Intent intent = new Intent(context,MovieQuoteDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_DOC_ID,ds.getId());
                    //bring up detailed view for the card
                    context.startActivity(intent);
                }
            });

        }
    }
}
