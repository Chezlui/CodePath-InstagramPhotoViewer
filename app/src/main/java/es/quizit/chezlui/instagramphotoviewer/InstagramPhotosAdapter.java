package es.quizit.chezlui.instagramphotoviewer;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 05/02/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    // lookups the views for populating the data (image, caption)
    @Bind (R.id.tvAuthorPhotoCaption) TextView tvAuthorPhotoCaption;
    @Bind (R.id.tvCaption) TextView tvCaption;
    @Bind (R.id.tvUserName) TextView tvUserName;
    @Bind (R.id.ivPhoto) ImageView ivPhoto;
    @Bind (R.id.tvLikes) TextView tvLikes;
    @Bind (R.id.tvCreatedTime)TextView tvCreatedTime;
    @Bind (R.id.ivUserAvatar) ImageView ivUserAvatar;
    @Bind (R.id.tvAuthorName1) TextView tvAuthorName1;
    @Bind (R.id.tvAuthorName2) TextView tvAuthorName2;
    @Bind (R.id.tvComment1) TextView tvComment1;
    @Bind (R.id.tvComment2) TextView tvComment2;
    @Bind (R.id.tvViewMoreComments) TextView tvViewMoreComments;
    @Bind (R.id.vvVideo) VideoView vvVideo;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this position
        final InstagramPhoto photo = getItem(position);
        // check if we are using a recycled view, if not we need to inflate
        if (photo.type == 0) {
            // create a new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);

        }

        ButterKnife.bind(this, convertView);
        if (photo.type == 1) {
            vvVideo.setVideoPath(photo.videoUrl);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(vvVideo);
            vvVideo.setMediaController(mediaController);
            vvVideo.requestFocus();
            vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    vvVideo.start();
                }
            });
        } else {
            // insert the imageview using picasso
            Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.placeholder).into(ivPhoto);
        }

        // insert the model data into each of the view items
        tvCaption.setText(photo.caption);
        tvAuthorPhotoCaption.setText(photo.userName);
        tvUserName.setText(photo.userName);
        tvLikes.setText(photo.likesCount + " likes");
        tvCreatedTime.setText(String.valueOf(DateUtils.
                getRelativeTimeSpanString(photo.creationTime * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE)));

        // clear out the avatar view
        ivUserAvatar.setImageResource(0);

        // insert the imageview using picasso
        Picasso.with(getContext()).load(photo.userProfileImageUrl)
                .resize(150,150).placeholder(R.drawable.placeholder_avatar).into(ivUserAvatar);
        // clear out the image view
        ivPhoto.setImageResource(0);

        // Comments
        if (photo.comments.size() > 0) {
            Comment comment1 = photo.comments.get(photo.comments.size() - 1);
            tvAuthorName1.setText(comment1.author);
            tvComment1.setText(comment1.text);
        }
        if (photo.comments.size() > 1) {
            Comment comment2 = photo.comments.get(photo.comments.size() - 2);
            tvAuthorName2.setText(comment2.author);
            tvComment2.setText(comment2.text);
        }

        if (photo.comments.size() > 2) {
            tvViewMoreComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentsDialog(photo);
                }
            });
        } else {
            tvViewMoreComments.setVisibility(View.GONE);
        }

        // Return the created item as a view
        return convertView;
    }

    private void showCommentsDialog(InstagramPhoto photo) {
        FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        CommentsDialog commentsDialog = CommentsDialog.newInstance(photo.comments);
        commentsDialog.show(fm, "fragment_edit_name");

    }
}
