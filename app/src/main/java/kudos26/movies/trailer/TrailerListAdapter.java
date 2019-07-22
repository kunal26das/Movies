package kudos26.movies.trailer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import kudos26.movies.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static kudos26.movies.Constants.BASE_URL_YOUTUBE;

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
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) trailerHolder.itemView.getLayoutParams();
        if (position > 0) {
            layoutParams.setMargins(0, 40, 40, 40);
        } else {
            layoutParams.setMargins(40, 40, 40, 40);
        }
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        ImageView mYoutube;
        ImageView mTrailer;
        ShimmerFrameLayout mTrailerShimmer;

        TrailerHolder(@NonNull View view) {
            super(view);
            mYoutube = view.findViewById(R.id.youtube);
            mTrailer = view.findViewById(R.id.iv_trailer);
            mTrailerShimmer = view.findViewById(R.id.shimmer_trailer);
        }

        void updateTrailer(final TrailerEntity trailer) {
            mYoutube.setVisibility(GONE);
            itemView.setOnClickListener(null);
            mTrailerShimmer.setVisibility(VISIBLE);
            Picasso.get()
                    .load(BASE_URL_THUMBNAIL + trailer.getAddressKey() + DEFAULT)
                    .into(mTrailer, new Callback() {
                        @Override
                        public void onSuccess() {
                            mYoutube.setVisibility(VISIBLE);
                            mTrailerShimmer.setVisibility(GONE);
                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL_YOUTUBE + trailer.getAddressKey()));
                                    itemView.getContext().startActivity(youtubeIntent);
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }

    }
}
