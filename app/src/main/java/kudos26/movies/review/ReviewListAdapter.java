package kudos26.movies.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import kudos26.movies.R;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewHolder> {

    private final LayoutInflater mLayoutInflater;
    private List<ReviewEntity> mReviews = Collections.emptyList();

    public ReviewListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setReviews(List<ReviewEntity> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View review = mLayoutInflater.inflate(R.layout.item_review, parent, false);
        return new ReviewHolder(review);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int position) {
        reviewHolder.updateReview(mReviews.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        TextView mReviewNumber;
        TextView mReviewContent;
        ImageView mExpandCollapse;

        ReviewHolder(@NonNull View view) {
            super(view);
            mReviewNumber = view.findViewById(R.id.tv_review_number);
            mReviewContent = view.findViewById(R.id.tv_review_content);
            mExpandCollapse = view.findViewById(R.id.iv_expand_collapse);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mExpandCollapse.setRotation(mExpandCollapse.getRotation() + 180);
                    int maxLines = mReviewContent.getMaxLines();
                    if (maxLines == 3) {
                        mReviewContent.setMaxLines(100);
                    } else {
                        mReviewContent.setMaxLines(3);
                    }
                }
            });
        }

        void updateReview(ReviewEntity review, int number) {
            String serialNumber = "#" + number;
            mReviewNumber.setText(serialNumber);
            mReviewContent.setText(review.getContent().trim());
        }
    }
}
