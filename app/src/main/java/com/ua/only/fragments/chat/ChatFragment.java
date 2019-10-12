package com.ua.only.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ua.only.R;
import com.ua.only.fragments.BaseFragment;

public class ChatFragment extends BaseFragment {
	public static final String TAG = "ChatFragment";
	
	@LayoutRes
	public int getChatContent() {
		return R.layout.chat_fragment;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getChatContent(), container, false);
	}
}
