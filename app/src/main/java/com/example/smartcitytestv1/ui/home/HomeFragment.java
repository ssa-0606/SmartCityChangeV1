package com.example.smartcitytestv1.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;
import com.example.smartcitytestv1.ui.home.adapter.HotAdapter;
import com.example.smartcitytestv1.ui.home.adapter.NewsAdapter;
import com.example.smartcitytestv1.ui.home.adapter.ServiceAdapter;
import com.example.smartcitytestv1.ui.home.beans.CityService;
import com.example.smartcitytestv1.ui.home.beans.LunBoImg;
import com.example.smartcitytestv1.ui.home.beans.NewsCategory;
import com.example.smartcitytestv1.ui.home.beans.NewsItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 热门主题，未确定，暂时这么样
 *
 */

public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    private List<LunBoImg> lunBoImgList;

    private RecyclerView service_recycler;
    private List<CityService> serviceList;
    private List<CityService> showServiceList;

    private RecyclerView hot_recycler;

    private TabLayout tabLayout;
    private RecyclerView news_recycler;

    private List<NewsCategory> newsCategories;
    private List<NewsItem> newsItemList;
    private Activity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        mainActivity = this.getActivity();

        viewFlipper = (ViewFlipper) view.findViewById(R.id.vf_lunbo);
        service_recycler = (RecyclerView) view.findViewById(R.id.service_recycler);
        hot_recycler = (RecyclerView) view.findViewById(R.id.hot_recycler);

        tabLayout = (TabLayout) view.findViewById(R.id.news_tab);
        news_recycler = (RecyclerView) view.findViewById(R.id.news_recycler);

        getLunBo();
        getService();
        getNews();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Message message = new Message();
                message.obj = tab.getPosition();
                message.what = 4;
                handler.sendMessage(message);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }



    public void getLunBo(){

        Thread thread = new Thread(()->{
            try {
                String resultJson = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/rotation/list?type=2");
                JSONObject jsonObject = new JSONObject(resultJson);
                lunBoImgList = new ArrayList<>();
                JSONArray rows = jsonObject.getJSONArray("rows");
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String advImg = data.getString("advImg");
                    int targetId = data.getInt("targetId");
                    lunBoImgList.add(new LunBoImg(id,"http://124.93.196.45:10001"+advImg,targetId));
                }
                handler.sendEmptyMessage(1);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

    public void getService(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/service/list");
                JSONObject resultJson = new JSONObject(result);
                JSONArray rows = resultJson.getJSONArray("rows");
                serviceList = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String serviceName = data.getString("serviceName");
                    String serviceType = data.getString("serviceType");
                    String imgUrl = data.getString("imgUrl");
                    int sort = data.getInt("sort");
                    serviceList.add(new CityService(id,serviceName,serviceType,"http://124.93.196.45:10001"+imgUrl,sort));
                }
                //排序  ,   这里修改了一下排序顺序  ，为了调试 “停哪儿” ， 记得修改回来
                serviceList.sort(new Comparator<CityService>() {
                    @Override
                    public int compare(CityService cityService, CityService t1) {
                        return 0;
                    }
                });
                serviceList.sort((cityService, t1) -> {
                    if (cityService.getSort()>t1.getSort())
                        return 1;
                    else return -1;
                });
                handler.sendEmptyMessage(2);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void getNews(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/press/category/list");
                JSONObject resultJson = new JSONObject(result);
                JSONArray newsTypes = resultJson.getJSONArray("data");
                newsCategories = new ArrayList<>();
                for (int i = 0; i < newsTypes.length(); i++) {
                    JSONObject data = newsTypes.getJSONObject(i);
                    int TypeId = data.getInt("id");
                    String name = data.getString("name");
                    int sort = data.getInt("sort");
                    String resultNews = MyUtils.GET("http://124.93.196.45:10001/prod-api/press/press/list?type="+TypeId);
                    JSONObject newsJson = new JSONObject(resultNews);
                    JSONArray rows = newsJson.getJSONArray("rows");
                    newsItemList = new ArrayList<>();
                    for (int j = 0; j < rows.length(); j++) {
                        JSONObject jsonObject = rows.getJSONObject(j);
                        int newsId = jsonObject.getInt("id");
                        String cover = jsonObject.getString("cover");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        String publishDate = jsonObject.getString("publishDate");
                        int commentNum = jsonObject.getInt("commentNum");
                        newsItemList.add(new NewsItem(newsId,"http://124.93.196.45:10001"+cover,title,content,publishDate,commentNum));
                    }
                    newsCategories.add(new NewsCategory(TypeId,name,sort,newsItemList));
                }
                handler.sendEmptyMessage(3);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    for (int i = 0; i < lunBoImgList.size(); i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(getContext()).load(lunBoImgList.get(i).getAdvImg()).into(imageView);
                        viewFlipper.addView(imageView);
                    }
                    break;
                case 2:
                    showServiceList = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        showServiceList.add(serviceList.get(i));
                    }
                    showServiceList.add(new CityService(0,"全部服务","全部服务",null,0));
                    service_recycler.setLayoutManager(new GridLayoutManager(getContext(),5));
                    service_recycler.setAdapter(new ServiceAdapter(R.layout.service_layout,showServiceList));
                    showServiceList = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        showServiceList.add(serviceList.get(i));
                    }
                    hot_recycler.setLayoutManager(new GridLayoutManager(getContext(),2));
                    hot_recycler.setAdapter(new HotAdapter(R.layout.hot_layout,showServiceList));
                    break;
                case 3:
                    for (int i = 0; i < newsCategories.size(); i++) {
                        tabLayout.addTab(tabLayout.newTab().setText(newsCategories.get(i).getName()).setTag(newsCategories.get(i).getId()));
                    }
                    news_recycler.setLayoutManager(new GridLayoutManager(getContext(),1));
                    news_recycler.setAdapter(new NewsAdapter(R.layout.news_layout,newsCategories.get(0).getNewsItemList()));
                    break;
                case 4:
                    int position = (int) msg.obj;
                    news_recycler.setLayoutManager(new GridLayoutManager(getContext(),1));
                    news_recycler.setAdapter(new NewsAdapter(R.layout.news_layout,newsCategories.get(position).getNewsItemList()));
                    break;
            }
        }
    };

}