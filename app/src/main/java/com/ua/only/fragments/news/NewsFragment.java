package com.ua.only.fragments.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.ua.only.R;
import com.ua.only.dummy.NewsDummyData;
import com.ua.only.fragments.BaseFragment;
import com.ua.only.models.NewsItem;

public class NewsFragment extends BaseFragment {
	public static final String TAG = "NewsFragment";
	
	private FastItemAdapter<NewsItem> newsItemAdapter;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.news_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		newsItemAdapter = new FastItemAdapter<>();
		
		newsItemAdapter.withOnClickListener(new OnClickListener<NewsItem>() {
				@Override
				public boolean onClick(View v, IAdapter<NewsItem> adapter, @NonNull NewsItem item, int position) {
					Toast.makeText(v.getContext(), item.title, Toast.LENGTH_SHORT).show();
					return false;
				}
		});
		
		RecyclerView rv = view.findViewById(R.id.rv);
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv.setItemAnimator(new DefaultItemAnimator());
		rv.setAdapter(newsItemAdapter);
		
		newsItemAdapter.add(NewsDummyData.getNewsItems());
		newsItemAdapter.withSavedInstanceState(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState = newsItemAdapter.saveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}
}
