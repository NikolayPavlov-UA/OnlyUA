package com.ua.only;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.ua.only.utils.NavigationHost;
import com.ua.only.utils.ProgressView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class HelperActivity extends AppCompatActivity implements NavigationHost,ProgressView {
	
	// Minimum time that the spinner will stay on screen, once it is shown.
    private static final long MIN_SPINNER_MS = 750;

    private Handler handler = new Handler();
    private MaterialProgressBar progressBar;

    // Last time that the progress bar was actually shown
    private long lastShownTime = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helper_activity);
		
		// Create an indeterminate, circular progress bar in the app's theme
        progressBar = new MaterialProgressBar(this);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE);

        // Set bar to float in the center
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        // Add to the container
        FrameLayout container = findViewById(R.id.invisible_frame);
        container.addView(progressBar, params);
	}
	
	@Override
	public void showProgress() {
		if (progressBar.getVisibility() == View.VISIBLE) {
            handler.removeCallbacksAndMessages(null);
            return;
        }

        lastShownTime = System.currentTimeMillis();
        progressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideProgress() {
		doAfterTimeout(new Runnable() {
			@Override
			public void run() {
				lastShownTime = 0;
				progressBar.setVisibility(View.GONE);
			}
		});
	}
	
	private void doAfterTimeout(Runnable runnable) {
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastShownTime;

        // 'diff' is how long it's been since we showed the spinner, so in the
        // case where diff is greater than our minimum spinner duration then our
        // remaining wait time is 0.
        long remaining = Math.max(MIN_SPINNER_MS - diff, 0);

        handler.postDelayed(runnable, remaining);
    }
	
	@Override
	public void navigateTo(@NonNull Fragment fragment, @NonNull String tag,
						   boolean withTransition,
						   boolean addToBackStack) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (withTransition) {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        ft.replace(R.id.frame_container, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null).commit();
        } else {
            ft.disallowAddToBackStack().commit();
        }
	}
}
