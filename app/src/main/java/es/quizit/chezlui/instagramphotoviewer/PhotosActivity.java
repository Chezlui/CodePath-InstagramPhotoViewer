package es.quizit.chezlui.instagramphotoviewer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    InstagramPhotosAdapter aPhotos;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // SwipeRefreshLayout
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });
        // SEND OUT API REQUEST to POPULAR PHOTOS
        photos = new ArrayList<>();
        // create the adapter linking it to the sources
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // attach to the adapter
        lvPhotos.setAdapter(aPhotos);
        // fetch the popular photos
        fetchPopularPhotos();
    }

    // Trigger API request
    public void fetchPopularPhotos() {
        /*
        CLIENT_ID = e05c462ebd86446ea48a5af73769b602
        API Map
        URLs
        - Popular https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
        */

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        // Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSucess (worked 200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSON object

                JSONArray photosJson = null;
                aPhotos.clear();
                try {
                    photosJson = response.getJSONArray("data");
                    // Iterate array of photos
                    for (int i = 0; i < photosJson.length(); i++) {
                        // get the JSON object
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        // decode the attributes of Json
                        InstagramPhoto photo = new InstagramPhoto();
                        // - Author name { data => [x] => "user" =>"username" }
                        photo.userName = photoJson.getJSONObject("user").getString("username");
                        // - TYPE { data => [x] => "type" } ("image" or "video")
                        // - URL { data => [x] => "images" => "standard_resolution" => "url" }
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution")
                                .getString("url");
                        // height
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution")
                                .getInt("height");
                        // user profile image
                        photo.userProfileImageUrl = photoJson.getJSONObject("user").getString("profile_picture");
                        // creation Time
                        photo.creationTime = photoJson.getLong("created_time");
                        // likes
                        photo.likesCount = photoJson.getJSONObject("likes").getInt("count");
                        // - Caption { data => [x] => "caption" => "text" }
                        photo.caption = photoJson.getJSONObject("caption").getString("text");
                        // add decode object to photos
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
                // Callback
                aPhotos.notifyDataSetChanged();
            }

            // onFailure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
