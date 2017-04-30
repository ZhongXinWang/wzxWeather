package weather.test.wzx.com.wzxweather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import weather.test.wzx.com.wzxweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSelectFragment extends Fragment {

    private View mView;

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

        return mView;
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
