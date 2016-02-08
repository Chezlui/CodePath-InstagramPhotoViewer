package es.quizit.chezlui.instagramphotoviewer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 07/02/16.
 */
public class CommentsDialog extends DialogFragment {
    @Bind (R.id.tvAuthorName) TextView tvAuthor;
    @Bind (R.id.tvComment) TextView tvComment;

    public CommentsDialog() {

    }

    public static CommentsDialog newInstance(ArrayList<Comment> comments) {
        CommentsDialog frag = new CommentsDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("comments", comments);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView;
        rootView = (ViewGroup) inflater.inflate(R.layout.dialog_comments, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ArrayList<Comment> comments = getArguments().getParcelableArrayList("comments");
        Iterator<Comment> itComments = comments.iterator();

        View commentRowView;

        while(itComments.hasNext()) {
            commentRowView = getLayoutInflater(savedInstanceState).inflate(R.layout.row_comment, null);
            ButterKnife.bind(this, commentRowView);

            Comment comment = itComments.next();
            tvAuthor.setText(comment.author);
            tvComment.setText(comment.text);

            ((ViewGroup) view).addView(commentRowView);
        }
    }
}
