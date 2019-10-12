package com.ua.only.fragments.news;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.itemanimators.ScaleUpAnimator;
import com.ua.only.R;
import com.ua.only.dummy.NewsDummyData;
import com.ua.only.fragments.BaseFragment;
import com.ua.only.models.NewsItem;

public class NewsFragment extends BaseFragment {
	public static final String TAG = "NewsFragment";
	
	private FastItemAdapter<NewsItem> newsItemAdapter;
	private RecyclerView rv;
	
	@LayoutRes
	public int getNewsContent() {
		return R.layout.news_fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getNewsContent(), container, false);
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
		
		rv = view.findViewById(R.id.rv);
		rv.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv.setItemAnimator(new ScaleUpAnimator());
		rv.setAdapter(newsItemAdapter);
		
		newsItemAdapter.add(NewsDummyData.getNewsItems());
		newsItemAdapter.withSavedInstanceState(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState = newsItemAdapter.saveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.news_toolbar_menu, menu);
		menu.findItem(R.id.item_add).setIcon(new IconicsDrawable(getActivity(), CommunityMaterial.Icon2.cmd_plus_circle_outline)
												.color(Color.BLACK).actionBar());
		menu.findItem(R.id.item_delete).setIcon(new IconicsDrawable(getActivity(), CommunityMaterial.Icon2.cmd_minus_circle_outline)
												.color(Color.BLACK).actionBar());
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int firstVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findFirstVisibleItemPosition();
		int lastVisiblePosition = ((LinearLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
		
		switch(item.getItemId()) {
			case R.id.item_add:
				newsItemAdapter.add(firstVisiblePosition + 1, NewsDummyData.getDummyItem());
				return true;
			case R.id.item_delete:
				newsItemAdapter.remove(lastVisiblePosition);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
