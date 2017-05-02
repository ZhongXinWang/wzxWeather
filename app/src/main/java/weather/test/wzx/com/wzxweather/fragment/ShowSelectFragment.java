package weather.test.wzx.com.wzxweather.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.activity.AddCityActivity;
import weather.test.wzx.com.wzxweather.activity.WeatherActivity;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;
import weather.test.wzx.com.wzxweather.util.LogUtil;

import static weather.test.wzx.com.wzxweather.R.id.group;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSelectFragment extends Fragment {

    private View mView;
    private TextView mText;
    private ListView mListView;
    private FloatingActionButton mActionButton;
    private ArrayAdapter<CitySelect> adapter;
    private CitySelectLab citySelect;
    private RadioGroup radio_group;
    private RadioButton radio_btn = null;
    public ShowSelectFragment() {
        // Required empty public constructor
    }
    public static ShowSelectFragment newInstance() {
        
        Bundle args = new Bundle();
        ShowSelectFragment fragment = new ShowSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citySelect = new CitySelectLab(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_show_select, container, false);
        //设置事件可用
        setHasOptionsMenu(true);
        ActionBar action = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(action != null){
            action.setTitle("已添加城市");
            action.setDisplayHomeAsUpEnabled(true);
            action.setHomeButtonEnabled(true);
        }
        init();
        return mView;
    }
    private void init() {

        mListView = (ListView) mView.findViewById(R.id.list_city);
        mActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_add);
        mText = (TextView) mView.findViewById(R.id.message);
        //获取select的值
        List<CitySelect> mList = citySelect.getAllCitySelects();
        if(mList == null){

            mText.setText("没有添加城市");
        }else {
            adapter = new ArrayAdapter<CitySelect>(getActivity(), android.R.layout.simple_list_item_1, mList);
            mListView.setAdapter(adapter);
            setOnclickEvent();
            setOnLongClickEvent();
        }
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCityActivity.startAction(getActivity());
            }
        });
    }

    private void setOnclickEvent() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                WeatherActivity.toAction(getActivity(),adapter.getItem(position).getCityName());
                //结束当前的activity
                getActivity().finish();
            }
        });
    }
    private void setOnLongClickEvent() {

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,  int position, long id) {
                final int cityId = adapter.getItem(position).getId();
               AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
                build.setTitle("操作提示").setIcon(R.drawable.alert);
                build.setCancelable(false);
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_dialog_radio_item,null,false);
                build.setView(v);
                radio_group = (RadioGroup) v.findViewById(group);
                radio_btn = (RadioButton) v.findViewById(R.id.set_city);
                radio_btn.setChecked(true);
                radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        radio_btn = (RadioButton) group.findViewById(checkedId);
                    }
                });
                build.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(radio_btn.getText().equals("设置默认城市")){

                            if(citySelect.updateById(cityId)){
                                LogUtil.d("hello",cityId+"");
                              //  Toast.makeText(getActivity(),cityId,Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                                init();
                            }
                        }else if(radio_btn.getText().equals("删除")){

                            if(citySelect.deleteById(cityId)){

                                adapter.notifyDataSetChanged();
                                init();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                build.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                build.create().show();
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:

                getActivity().finish();
                return  true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
