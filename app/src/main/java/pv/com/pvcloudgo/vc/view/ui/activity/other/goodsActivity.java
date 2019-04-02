package pv.com.pvcloudgo.vc.view.ui.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.http.BaseCallback;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.HomeCampaign;
import pv.com.pvcloudgo.model.bean.User;
import pv.com.pvcloudgo.model.msg.LoginRespMsg;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.utils.DESUtil;
import pv.com.pvcloudgo.vc.base.BaseActivity;
import pv.com.pvcloudgo.vc.view.ui.fragment.dummy.TabEntity;
import pv.com.pvcloudgo.vc.view.ui.fragment.mine.OrderFragment;


public class goodsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.common_layout)
    CommonTabLayout mCommonTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    int position;


    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"食品生鲜", "居家好物", "母婴馆", "到家服务"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        initToolBar();
        initTab();
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setupToolbar(toolbar, true);

        toolbarTitle.setText("全部订单");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

//    public void login(View view) {
//
//
//        String phone = null;
//
//        String pwd = null;
//
//
//        Map<String, Object> params = new HashMap<>(2);
//        params.put("phone", phone);
//        params.put("password", DESUtil.encode(Contants.DES_KEY, pwd));
//
//        mHttpHelper.post(Contants.API.LOGIN, params, new SpotsCallBack<LoginRespMsg<User>>(this) {
//
//
//            @Override
//            public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {
//
//
//                App application = App.getInstance();
//                application.putUser(userLoginRespMsg.getData());
//
//                if (application.getIntent() == null) {
//                    setResult(RESULT_OK);
//                    finish();
//                } else {
//
//                    application.jumpToTargetActivity(mContext);
//                    finish();
//
//                }
//
//
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//            }
//
//            @Override
//            public void onServerError(Response response, int code, String errmsg) {
//
//            }
//        });
//
//
//    }

    private void initTab() {

        for (String title : mTitles) {
            mFragments.add(new OrderFragment());
        }

        mTabEntities.add(new TabEntity(mTitles[0], 0, 0));
        mTabEntities.add(new TabEntity(mTitles[1], 0, 0));
        mTabEntities.add(new TabEntity(mTitles[2], 0, 0));
        mTabEntities.add(new TabEntity(mTitles[3], 0, 0));

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tl_2();

    }


    private void initRecyclerView() {


        mHttpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>(mContext) {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                initData(homeCampaigns);
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onServerError(Response response, int code, String errmsg) {

            }

            @Override
            public void onTokenError(Response response, int code) {

            }
        });

    }


    private void initData(List<HomeCampaign> homeCampaigns) {


    }


    private void tl_2() {
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setCurrentTab(position);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
