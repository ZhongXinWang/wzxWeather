package weather.test.wzx.com.wzxweather.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.HashMap;
import java.util.Map;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.util.SharedPreferencesUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetingFragment extends Fragment {

    private View mView;
    private Switch mSwith;
    private SharedPreferencesUtil share;
    public SetingFragment() {
        // Required empty public constructor
    }

    public static SetingFragment newInstance() {
        Bundle args = new Bundle();
        SetingFragment fragment = new SetingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        share = new SharedPreferencesUtil(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_seting, container, false);
        init();
        return mView;
    }
    private void init() {

        mSwith = (Switch) mView.findViewById(R.id.update_switch);
        if(!share.readSharePreference(StaticVariable.IS_AUTO_UPDATE).equals("") && share.readSharePreference(StaticVariable.IS_AUTO_UPDATE).equals("1")){
            mSwith.setChecked(true);
        }
        mSwith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Map<String,String> m = new HashMap<String, String>();
                if(isChecked){

               //     Toast.makeText(getActivity(),"open",Toast.LENGTH_SHORT).show();

                    m.put(StaticVariable.IS_AUTO_UPDATE,"1");
                    share.insertSharePreference(m);
                }else{

              //     Toast.makeText(getActivity(),"close",Toast.LENGTH_SHORT).show();
                    m.put(StaticVariable.IS_AUTO_UPDATE,"0");
                    share.insertSharePreference(m);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar action = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(action != null){

            action.setDisplayHomeAsUpEnabled(true);
            action.setHomeButtonEnabled(true);
            action.setTitle("设置");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
