package weather.test.wzx.com.wzxweather.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.activity.WeatherActivity;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.model.CityLab;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCityFragment extends Fragment {

    private SearchView mSearchView;
    private ListView mListView;
    private CityLab mCity;
    private CitySelectLab mCitySelectLab;
    private ArrayAdapter<Citys> mAdapter;
    private List<Citys> mList = null;
    private SwipeRefreshLayout mSwipe_Refresh;

    private int offset = 0;
    private int num  = 20;
    private View mView;

    public AddCityFragment() {
        // Required empty public constructor
    }

    public static AddCityFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_add_city, container, false);

        return mView;
        
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //初始化数据
        init();
        //默认取前十条
    }

    private void init() {


        mSearchView = (SearchView) mView.findViewById(R.id.search);
        mListView = (ListView) mView.findViewById(R.id.city_list);

        mListView.setTextFilterEnabled(true);

        mCity = new CityLab(getActivity());
        mCitySelectLab = new CitySelectLab(getActivity());
        mSwipe_Refresh = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        //初始化查询列表
        initList(initListData(offset,num));
        //初始化list监听事件
        initListEvent();
        //刷新的初始化
        initRefresh();
        //初始化查询
        initSearch();

    }

    private void initListEvent() {


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Citys citys = mAdapter.getItem(i);

                //

                AlertDialog.Builder build = new AlertDialog.Builder(getActivity());

                build.setTitle("操作提示")
                        .setMessage("是否添加"+citys.getCityName()+"到常用城市列表").setIcon(R.drawable.alert);

                build.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //判断改地区是否已经在列表中了,如果在就不插入,弹出提示,不在就插入,以id来判断

                        if(!mCitySelectLab.isExistsCityId(citys.getId())){


                            //不存在的话就添加数据,并且跳到展示界面
                            CitySelect select = new CitySelect();
                            select.setCityId(citys.getId());
                            select.setCityName(citys.getCityName());
                            select.setIsSelect(0);
                           boolean flag =  mCitySelectLab.insertCity(select);

                            if(flag){

                                WeatherActivity.toAction(getActivity(),citys.getCityName());
                                //结束当前的activity
                                getActivity().finish();


                            }else{

                                Toast.makeText(getActivity(),R.string.fail,Toast.LENGTH_SHORT).show();

                            }

                        }else{

                            //存在的话就弹出提示

                            Toast.makeText(getActivity(),R.string.success,Toast.LENGTH_SHORT).show();


                        }

                        dialogInterface.dismiss();

                    }
                });

                build.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });


                build.create().show();

            }
        });

    }

    private void initSearch() {


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getActivity(),"Text",Toast.LENGTH_SHORT).show();


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

            mAdapter = new ArrayAdapter<Citys>(getActivity(),android.R.layout.simple_expandable_list_item_1,citys);
            mListView.setAdapter(mAdapter);


        }else{


            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();


        }

    }

    private void refrestCitys() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //获取列表的最大id

                offset = mList.get(mList.size()-1).getId();
                final List<Citys> lists = initListData(offset,num);

                //返回主线程

                getActivity().runOnUiThread(new Runnable() {
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


}
