package org.cnodejs;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.cnodejs.api.Constants;
import org.cnodejs.api.GsonRequest;
import org.cnodejs.api.model.TopicList;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private SwipeRefreshLayout swipingLayout;
    private RecyclerView topicsView;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupSwipingLayout();
        setupTopicsView();

        // 初始化 Volley 请求队列
        queue = Volley.newRequestQueue(this);

        loadTopics();
    }

    private void setupSwipingLayout() {
        swipingLayout = (SwipeRefreshLayout) findViewById(R.id.swiping);
    }

    private void setupTopicsView() {
        topicsView = (RecyclerView) findViewById(R.id.topics);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        topicsView.setLayoutManager(layoutManager);
    }

    private void loadTopics() {
        swipingLayout.setRefreshing(true);

        queue.add(new GsonRequest<TopicList>(
                Request.Method.GET, Constants.API_V1 + "/topics", TopicList.class,
                new Response.Listener<TopicList>() {
                    @Override
                    public void onResponse(TopicList response) {
                        Log.d(TAG, "loaded " + response.size() + " topics");
                        swipingLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "error loading topics", error);
                        swipingLayout.setRefreshing(false);
                    }
                }
        ));
    }

}
