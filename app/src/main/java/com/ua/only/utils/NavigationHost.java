package com.ua.only.utils;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

public interface NavigationHost {
	void navigateTo(@NonNull Fragment fragment, @NonNull String tag,
					boolean withTransition,
					boolean addToBackStack)
}
