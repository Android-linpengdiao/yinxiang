package com.yinxiang.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.View;

import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.HomeContestAdapter;
import com.yinxiang.databinding.ActivityMyWorkPkBinding;
import com.yinxiang.model.WorkPKData;
import com.yinxiang.view.ElectionPopupWindow;
import com.yinxiang.view.OnClickListener;

import org.json.JSONObject;

import okhttp3.Call;

public class MyWorkPKActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMyWorkPkBinding binding;
    private HomeContestAdapter adapter;

    private WorkPKData worksDetail;
    private WorkPKData.DataBeanX.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work_pk);

        binding.back.setOnClickListener(this);

        adapter = new HomeContestAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Intent intent;
                switch (view.getId()) {
                    case R.id.workView:
                        if (object instanceof WorkPKData.DataBeanX.DataBean) {
                            dataBean = (WorkPKData.DataBeanX.DataBean) object;
                            intent = new Intent(MyWorkPKActivity.this, WorkDetailActivity.class);
                            intent.putExtra("workId", dataBean.getContent_id());
                            startActivity(intent);
                        }
                        break;
                    case R.id.compareWorkView:
                        if (object instanceof WorkPKData.DataBeanX.DataBean) {
                            dataBean = (WorkPKData.DataBeanX.DataBean) object;
                            intent = new Intent(MyWorkPKActivity.this, WorkDetailActivity.class);
                            intent.putExtra("workId", dataBean.getCompare_content_id());
                            startActivity(intent);
                        }
                        break;
                    case R.id.work_vote:
                        if (object instanceof WorkPKData.DataBeanX.DataBean) {
                            dataBean = (WorkPKData.DataBeanX.DataBean) object;
                            homePageVideosVoteSet(dataBean.getContent_id(), dataBean.getId(), 1);
                        }
                        break;
                    case R.id.compare_work_vote:
                        if (object instanceof WorkPKData.DataBeanX.DataBean) {
                            dataBean = (WorkPKData.DataBeanX.DataBean) object;
                            homePageVideosVoteSet(dataBean.getCompare_content_id(), dataBean.getId(), 2);
                        }
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void homePageVideosVoteSet(final int workId, final int compareId, final int self) {
        SendRequest.homePageVideosVoteSet(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            Election(workId, jsonObject.optJSONObject("data").optString("wallet_token"), jsonObject.optJSONObject("data").optString("votes"), compareId, self);
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void homePageVideosPKVote(int videoId, int free, int compareId, int self) {
        SendRequest.homePageVideosPKVote(getUserInfo().getData().getId(), videoId, free, compareId, self, new StringCallback() {
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
                            if (jsonObject.optJSONObject("data").optBoolean("canVote")) {
                                ToastUtils.showShort(getApplication(), "以为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票");
                                if (worksDetail != null && dataBean != null) {
                                    dataBean.setVote_num(dataBean.getVote_num() + (free == 1 ? 1 : 3));
                                    if (worksDetail.getData().getData().indexOf(dataBean) != -1) {
                                        adapter.notifyItemInserted(worksDetail.getData().getData().indexOf(dataBean));
                                    }
                                }
                            } else {
                                ToastUtils.showShort(getApplication(), "今日以为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票，明日再来为TA投" + (free == 1 ? "1" : getVideosVoteSet().getData().getVotes()) + "票");
                            }
                        } else {
                            ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Election(final int id, final String wallet_token, final String votes, final int compareId, final int self) {
        ElectionPopupWindow electionPopupWindow = new ElectionPopupWindow(getApplication());
        electionPopupWindow.setWallet(wallet_token, votes);
        electionPopupWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                switch (view.getId()) {
                    case R.id.tv_election:
                        homePageVideosPKVote(id, 1, compareId, self);
                        break;
                    case R.id.tv_election_coin:
                        DialogManager.showPayDialog(MyWorkPKActivity.this, "为TA投" + votes + "票", "确认支付" + wallet_token + "金币为TA投" + votes + "票?", String.valueOf(getUserInfo().getData().getWallet_token()), new com.baselibrary.view.OnClickListener() {
                            @Override
                            public void onClick(View view, Object object) {
                                switch (view.getId()) {
                                    case R.id.tv_confirm:
                                        if (getUserInfo().getData().getWallet_token() < Integer.parseInt(wallet_token)) {
                                            ToastUtils.showShort(getApplication(), "你的余额不足");
                                            return;
                                        }
                                        homePageVideosPKVote(id, 2, compareId, self);
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
                        break;
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        electionPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void initData() {
        SendRequest.homePageVideosSelfPk(getUserInfo().getData().getId(), 10, new GenericsCallback<WorkPKData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(WorkPKData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    worksDetail = response;
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MyWorkPKActivity.this, response.getMsg());
                }
            }

        });
    }
}