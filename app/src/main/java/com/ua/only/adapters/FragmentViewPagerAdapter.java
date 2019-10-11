package com.ua.only.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;

public class FragmentViewPagerAdapter extends FragmentStateAdapter {
	private ArrayList<Fragment> listFragment = new ArrayList<>();

	public FragmentViewPagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lc) {
		super(fm, lc);
	}

	public void addFragment(Fragment fragment) {
		listFragment.add(fragment);
	}

	@Override
	public int getItemCount() {
		return listFragment.size();
	}

	@NonNull
	@Override
	public Fragment createFragment(int position) {
		return listFragment.get(position);
	}
}
