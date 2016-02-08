package es.quizit.chezlui.instagramphotoviewer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chezlui on 06/02/16.
 */
public class Comment implements Parcelable {
    public String author;
    public String text;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.text);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.author = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

