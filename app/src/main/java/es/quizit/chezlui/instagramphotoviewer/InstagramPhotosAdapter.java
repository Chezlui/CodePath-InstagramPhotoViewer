package es.quizit.chezlui.instagramphotoviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chezlui on 05/02/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this position
        InstagramPhoto photo = getItem(position);
        // check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // lookups the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvCreatedTime);
        ImageView ivUserAvatar = (ImageView) convertView.findViewById(R.id.ivUserAvatar);
        TextView tvAuthorComment1 = (TextView) convertView.findViewById(R.id.tvAuthorComment1);
        TextView tvAuthorComment2 = (TextView) convertView.findViewById(R.id.tvAuthorComment2);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

        // insert the model data into each of the view items
        tvCaption.setText(photo.caption);
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
        // insert the imageview using picasso
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.placeholder).into(ivPhoto);

        // Comments
        if (photo.comments.size() > 0) {
            Comment comment1 = photo.comments.get(photo.comments.size() - 1);
            tvAuthorComment1.setText(comment1.author);
            tvComment1.setText(comment1.text);
        }
        if (photo.comments.size() > 1) {
            Comment comment2 = photo.comments.get(photo.comments.size() - 2);
            tvAuthorComment2.setText(comment2.author);
            tvComment2.setText(comment2.text);
        }

        // Return the created item as a view
        return convertView;
    }
}
