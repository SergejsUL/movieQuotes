package ie.ul.serge.moviequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.MoviewQuoteViewHolder>{

    @NonNull
    @Override
    public MoviewQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate
                (R.layout.moviewquote_itemview,parent,false);
        return new MoviewQuoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviewQuoteViewHolder moviewQuoteViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MoviewQuoteViewHolder extends RecyclerView.ViewHolder{
        private TextView mQuoteTextView;
        private TextView mMovieTextView;


        public MoviewQuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuoteTextView = itemView.findViewById(R.id.itemview_movie);
            mMovieTextView = itemView.findViewById(R.id.itemview_quote);

        }
    }
}
