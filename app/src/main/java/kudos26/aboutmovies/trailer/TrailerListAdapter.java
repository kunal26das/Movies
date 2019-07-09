package kudos26.aboutmovies.trailer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import kudos26.aboutmovies.R;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerHolder> {

    private static final String BASE_URL_THUMBNAIL = "https://img.youtube.com/vi/";
    private static final String DEFAULT = "/maxresdefault.jpg";
    private final LayoutInflater mLayoutInflater;
    private List<TrailerEntity> mTrailers = Collections.emptyList();

    public TrailerListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setTrailers(List<TrailerEntity> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerListAdapter.TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trailer = mLayoutInflater.inflate(R.layout.item_trailer, parent, false);
        return new TrailerHolder(trailer);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerListAdapter.TrailerHolder trailerHolder, int position) {
        trailerHolder.updateTrailer(mTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        ImageView mTrailerImageView;

        TrailerHolder(@NonNull View view) {
            super(view);
            mTrailerImageView = view.findViewById(R.id.iv_trailer);
        }

        void updateTrailer(TrailerEntity trailer) {
            Picasso.get()
                    .load(BASE_URL_THUMBNAIL + trailer.getAddressKey() + DEFAULT)
                    .into(mTrailerImageView);
        }

    }
}
