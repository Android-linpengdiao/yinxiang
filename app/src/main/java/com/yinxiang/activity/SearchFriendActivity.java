package com.yinxiang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.ToastUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.yinxiang.R;
import com.yinxiang.adapter.SearchFriendAdapter;
import com.yinxiang.adapter.SearchHistoryAdapter;
import com.yinxiang.adapter.WorkAdapter;
import com.yinxiang.databinding.ActivitySearchFriendBinding;
import com.yinxiang.db.DBManager;
import com.yinxiang.model.FriendsData;
import com.yinxiang.model.WorkData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class SearchFriendActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySearchFriendBinding binding;
    private SearchFriendAdapter searchFriendAdapter;
    private static final int REQUEST_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_friend);

        binding.searchBack.setOnClickListener(this);
        binding.searchView.setOnClickListener(this);
        binding.searchContentDelete.setOnClickListener(this);
        searchFriendAdapter = new SearchFriendAdapter(this);
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.searchRecyclerView.setAdapter(searchFriendAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (null != charSequence) {
                    initSearchView(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = binding.etSearch.getText().toString().trim();
                    if (!CommonUtil.isBlank(content)) {
                        // 隐藏键盘
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        initSearchView(content);
                    } else {
                        ToastUtils.showShort(SearchFriendActivity.this, "请输入搜索内容");
                    }
                    return true;
                }
                return false;
            }
        });

//        if (checkPermissionsAll(PermissionUtils.CONTACTS, REQUEST_CONTACTS)) {
//            loadContents();
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.searchView:
                String content = binding.etSearch.getText().toString().trim();
                if (!CommonUtil.isBlank(content)) {
                    // 隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    initSearchView(content);
                } else {
                    ToastUtils.showShort(SearchFriendActivity.this, "请输入搜索内容");
                }
                break;
            case R.id.search_content_delete:
                binding.etSearch.setText("");
                initSearchView(null);
                break;
        }
    }

    private void initSearchView(String content) {
        binding.searchContentDelete.setVisibility(CommonUtil.isBlank(content) ? View.GONE : View.VISIBLE);
        binding.searchView.setVisibility(CommonUtil.isBlank(content) ? View.GONE : View.VISIBLE);
        if (!CommonUtil.isBlank(content)) {
            searchWork(content);
        } else {
            searchFriendAdapter.refreshData(new ArrayList<>());
        }
    }

    private void searchWork(String content) {

        SendRequest.friendSearch(getUserInfo().getData().getId(), content, new GenericsCallback<FriendsData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(FriendsData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData() != null) {
                    searchFriendAdapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(SearchFriendActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACTS) {
            boolean granted = true;
            for (int i = 0; i < PermissionUtils.contacts.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                loadContents();
            } else {
                PermissionUtils.openAppDetailsInit(SearchFriendActivity.this);
            }
        }
    }

    public List<UserInfo.DataBean> loadContents() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"display_name", "data1"}, (String) null, (String[]) null, (String) null);
        List<UserInfo.DataBean> list = new ArrayList();
        while (phones.moveToNext()) {
            String title = phones.getString(phones.getColumnIndex("display_name"));
            String phoneNumber = phones.getString(phones.getColumnIndex("data1"));
            phoneNumber = getFormatPhone(phoneNumber);
            UserInfo.DataBean userInfo = new UserInfo.DataBean();
            userInfo.setName(title);
            userInfo.setPhone(phoneNumber);
            list.add(userInfo);
        }
        phones.close();
        return list;
    }

    private static String getFormatPhone(String phone) {
        StringBuffer sb = new StringBuffer();
        phone = phone.replace("-", "");
        phone = phone.replace(" ", "");
        Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
        Matcher m1 = p1.matcher(phone);
        if (m1.matches()) {
            Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
            Matcher m2 = p2.matcher(phone);
            while (m2.find()) {
                m2.appendReplacement(sb, "");
            }
            m2.appendTail(sb);
        }
        return sb.toString();
    }
}
