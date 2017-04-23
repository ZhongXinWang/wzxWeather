package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.model.CityLab;

public class AddCityActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ListView mListView;
    private CityLab mCity;
    private ArrayAdapter<Citys> mAdapter;
    private List<Citys> mList = null;
    private SwipeRefreshLayout mSwipe_Refresh;

    private int offset = 0;
    private int num  = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_city);

        //初始化数据
        init();
       //默认取前十条
    }
    private void init() {


        mSearchView = (SearchView) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.city_list);

        mListView.setTextFilterEnabled(true);

        mCity = new CityLab(AddCityActivity.this);
        mSwipe_Refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        initList(initListData(offset,num));
        initRefresh();
        initSearch();

    }

    private void initSearch() {


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(AddCityActivity.this,"Text",Toast.LENGTH_SHORT).show();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(!TextUtils.isEmpty(newText)){

                    mListView.setFilterText(newText);

                }else{

                    mListView.clearTextFilter();

                }

                return false;
            }
        });

    }

    private void initRefresh() {


        mSwipe_Refresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipe_Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refrestCitys();

            }


        });

    }

    //获取查询数据

    private List<Citys> initListData(int offset,int num) {

        //offset 表示偏移量,到哪里开始取
        List<Citys> citys = mCity.getLimitCitys(offset,num);

        return citys;

    }
    private void  initList(List<Citys> citys){


        if(citys != null){


            mList = citys;

            mAdapter = new ArrayAdapter<Citys>(AddCityActivity.this,android.R.layout.simple_expandable_list_item_1,citys);
            mListView.setAdapter(mAdapter);


        }else{


            Toast.makeText(AddCityActivity.this,"没有数据",Toast.LENGTH_SHORT).show();


        }

    }

    private void refrestCitys() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //获取列表的最大id

               offset = mList.get(mList.size()-1).getId();
                final List<Citys> lists = initListData(offset,num);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        initList(lists);
                        mAdapter.notifyDataSetChanged();
                        mSwipe_Refresh.setRefreshing(false);
                    }
                });


            }

        }).start();


    }


    public static void startAction(Context context){


        Intent intent = new Intent(context,AddCityActivity.class);
        context.startActivity(intent);
        
    }
}
