package com.yinxiang.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.MemberAdapter;
import com.yinxiang.adapter.PagerAdapter;
import com.yinxiang.databinding.ActivityClubDetailBinding;
import com.yinxiang.fragment.ClubDescFragment;
import com.yinxiang.fragment.ClubMemberFragment;
import com.yinxiang.fragment.ClubWorkFragment;
import com.yinxiang.model.ClubData;
import com.yinxiang.model.ClubMember;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class ClubDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClubDetailActivity";
    private ActivityClubDetailBinding binding;
    private static final int REQUEST_DESC = 100;
    private static final int REQUEST_JOIN = 200;
    private ClubData.DataBean dataBean;
    private MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_club_detail);

        if (getIntent().getExtras() != null) {
            dataBean = (ClubData.DataBean) getIntent().getExtras().getSerializable("dataBean");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.editClubDesc.setOnClickListener(this);
        binding.recyclerView.setOnClickListener(this);
        binding.tvClubWorks.setOnClickListener(this);
        binding.tvClubSetting.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);

        binding.tvClubName.setText(dataBean.getName());
        GlideLoader.LoderCircleImage(this, dataBean.getLogo(), binding.ivClubLogo);
        GlideLoader.LoderBlurImage(this, dataBean.getLogo(), binding.ivClubCover);


        if (dataBean.getTourist_id() == getUserId()) {
            initCreateView();
        } else {
            initMemberView();
        }

    }

    private void initMemberView() {
        binding.clubMemberView.setVisibility(View.VISIBLE);
        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mainHomePagerAdapter.addFragment("社团作品", ClubWorkFragment.newInstance(dataBean.getId()));
        mainHomePagerAdapter.addFragment("社团简介", ClubDescFragment.newInstance(dataBean.getDesc()));
        mainHomePagerAdapter.addFragment("社团成员", ClubMemberFragment.newInstance(dataBean.getId()));
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        if (dataBean.isIs_join()) {
            binding.confirmView.setVisibility(View.GONE);
        }
    }

    private void initCreateView() {
        binding.tvClubDesc.setText(dataBean.getDesc());
        binding.clubCreateView.setVisibility(View.VISIBLE);
        binding.tvConfirm.setText("解散该社团");
        binding.tvConfirm.setBackground(getResources().getDrawable(R.drawable.button_radius_red));

        if (dataBean.getJoin() == 1) {
            binding.tvClubSetting.setText("收费入团");
        } else if (dataBean.getJoin() == 2) {
            binding.tvClubSetting.setText("免费加入");
        }

        adapter = new MemberAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        channelClubMember();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit_club_desc:
                intent = new Intent(ClubDetailActivity.this, EditTextActivity.class);
                intent.putExtra("type", "clubDesc");
                startActivityForResult(intent, REQUEST_DESC);
                break;
            case R.id.recyclerView:
                ToastUtils.showShort(ClubDetailActivity.this, "成员");
                break;
            case R.id.tv_club_works:
                intent = new Intent(ClubDetailActivity.this, ClubWorkActivity.class);
                intent.putExtra("cid", dataBean.getId());
                startActivity(intent);
                break;
            case R.id.tv_club_setting:
                intent = new Intent(ClubDetailActivity.this, ClubSettingActivity.class);
                intent.putExtra("dataBean", dataBean);
                startActivityForResult(intent, REQUEST_JOIN);
                break;
            case R.id.tv_confirm:
                if (dataBean.getTourist_id() == getUserId()) {
                    intent = new Intent(ClubDetailActivity.this, ClubDeleteActivity.class);
                    intent.putExtra("clubId", dataBean.getId());
                    startActivity(intent);
                } else {
                    if (getUserId(true) == 0) {
                        return;
                    }
                    if (dataBean.getJoin() == 1) {
                        DialogManager.showPayDialog(ClubDetailActivity.this, dataBean.getName() + "·入团交费", "确认支付" + dataBean.getJoin_token() + "金币加入该社团?", String.valueOf(getUserInfo().getData().getWallet_token()), new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:
                                        cashJoinClub(dataBean.getJoin_token());
                                        break;
                                    case R.id.tv_cancel:

                                        break;
                                    case R.id.tv_coin:
                                        openActivity(MyWalletActivity.class);
                                        break;
                                }
                            }

                            @Override
                            public void onLongClick(View view, Object object) {

                            }
                        });
                    } else if (dataBean.getJoin() == 2) {
                        channelJoinClub();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DESC:
                    if (data != null) {
                        String desc = data.getStringExtra("clubDesc");
                        channelEditClubDesc(desc);
                    }
                    break;
                case REQUEST_JOIN:
                    if (data != null) {
                        int join = data.getIntExtra("join",2);
                        int joinToken = data.getIntExtra("joinToken",0);
                        Log.i(TAG, "onActivityResult: "+joinToken);
                        dataBean.setJoin(join);
                        dataBean.setJoin_token(joinToken);
                        if (dataBean.getJoin() == 1) {
                            binding.tvClubSetting.setText("收费入团");
                        } else if (dataBean.getJoin() == 2) {
                            binding.tvClubSetting.setText("免费加入");
                        }
                    }
                    break;
            }
        }
    }

    private void channelEditClubDesc(final String value) {
        SendRequest.channelEditClubDesc(getUserInfo().getData().getId(), dataBean.getId(), value, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            binding.tvClubDesc.setText(value);
                        } else {
                            ToastUtils.showShort(ClubDetailActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                }
            }
        });
    }

    private void channelClubMember() {
        SendRequest.channelClubMember(dataBean.getId(), 10, new GenericsCallback<ClubMember>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ClubMember response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                    binding.clubMemberNum.setText("共" + response.getData().getData().size() + "人");
                } else {
                    ToastUtils.showShort(ClubDetailActivity.this, response.getMsg());
                }
            }

        });
    }

    private void channelJoinClub() {
        SendRequest.channelJoinClub(getUserInfo().getData().getId(), dataBean.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            ToastUtils.showShort(ClubDetailActivity.this, "已申请加入社团");
                        }
                    } else {
                        ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                }
            }
        });
    }

    private void cashJoinClub(int wallet_token) {
        SendRequest.cashJoinClub(getUserInfo().getData().getId(), dataBean.getId(), wallet_token, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            personInformInfo();
                            ToastUtils.showShort(ClubDetailActivity.this, "已申请加入社团");
                        } else {
                            ToastUtils.showShort(ClubDetailActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ClubDetailActivity.this, "请求失败");
                }
            }
        });
    }

}
