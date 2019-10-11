package com.ua.only;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.ua.only.BaseActivity;
import com.ua.only.fragments.chat.ChatFragment;
import com.ua.only.fragments.news.NewsFragment;

public class BaseActivity extends HelperActivity {
	private static final String TAG = "BaseActivity";
	private static final int RC_SIGN_IN = 0;

    private AccountHeader navigationHeader = null;
    private Drawer navigation = null;

	private Toolbar toolbar;

	private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

	private static final int ACCOUNT_SIGN_IN = 1;
	private static final int ACCOUNT_SIGN_OUT = 2;
	private static final int NAV_NEWS = 10;
	private static final int NAV_CHAT = 11;
	private static final int NAV_ABOUT = 20;
	private static final int NAV_SETTING = 21;
	
	@NonNull
	public static Intent createIntent(@NonNull Context context) {
		return new Intent(context, BaseActivity.class);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_activity);

		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		setupNavigation(savedInstanceState);

		// Configure Google Sign In
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build();

		googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
	}

	private void setupNavigation(Bundle savedInstanceState) {

		// Create the AccountHeader
        navigationHeader = new AccountHeaderBuilder()
			.withActivity(this)
			.withCompactStyle(true)
			.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
				@Override
				public boolean onProfileChanged(View view, IProfile profile, boolean current) {
					int itemId = (int) profile.getIdentifier();
					switch(itemId) {
						case ACCOUNT_SIGN_IN:
							signIn();
							break;
						case ACCOUNT_SIGN_OUT:
							signOut();
					}
					return false;
				}
			})
			.withSavedInstance(savedInstanceState)
			.build();
			
        //Create the drawer
        navigation = new DrawerBuilder()
			.withActivity(this)
			.withToolbar(toolbar)
			.withHasStableIds(true)
			.withAccountHeader(navigationHeader)
			.addDrawerItems(
				new PrimaryDrawerItem().withName(R.string.nav_item_news)
									.withIdentifier(NAV_NEWS)
									.withIcon(CommunityMaterial.Icon2.cmd_newspaper),
				new PrimaryDrawerItem().withName(R.string.nav_item_chat)
									.withIdentifier(NAV_CHAT)
									.withIcon(CommunityMaterial.Icon.cmd_chat),
				new DividerDrawerItem(),
				new SecondaryDrawerItem().withName(R.string.nav_item_setting)
										.withIdentifier(NAV_SETTING)
										.withSelectable(false)
										.withIcon(CommunityMaterial.Icon2.cmd_settings_outline),
				new SecondaryDrawerItem().withName(R.string.nav_item_about)
										.withIdentifier(NAV_ABOUT)
										.withSelectable(false)
										.withIcon(CommunityMaterial.Icon2.cmd_information_outline)
			)
			.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
				@Override
				public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
					int itemId = (int) drawerItem.getIdentifier();
					switch (itemId) {
						case NAV_NEWS:
							navigateTo(new NewsFragment(), NewsFragment.TAG, true, false);
							break;
						case NAV_CHAT:
							navigateTo(new ChatFragment(), ChatFragment.TAG, true, false);
							break;
						case NAV_SETTING:
							break;
						case NAV_ABOUT:
					}
					return false;
				}
			})
			.withOnDrawerListener(new Drawer.OnDrawerListener() {
				@Override
				public void onDrawerOpened(View drawerView) {
					// Скрываем клавиатуру при открытии Navigation Drawer
					InputMethodManager inputMethodManager = (InputMethodManager) BaseActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(), 0);
				}

				@Override
				public void onDrawerClosed(View drawerView) {

				}

				@Override
				public void onDrawerSlide(View drawerView, float position) {

				}
			})
			.withSavedInstance(savedInstanceState)
			.withShowDrawerOnFirstLaunch(true)
			.build();

		navigation.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
		navigation.getActionBarDrawerToggle().setDrawerSlideAnimationEnabled(true);

		if (savedInstanceState == null) {
			navigation.setSelection(NAV_NEWS, true);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

                updateUI(null);
            }
        }
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgress();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						// Sign in success, update UI with the signed-in user's information
						Log.d(TAG, "signInWithCredential:success");

						updateUI(currentUser());
					} else {
						// If sign in fails, display a message to the user.
						Log.w(TAG, "signInWithCredential:failure", task.getException());

						showMessage(R.string.authentication_failed);

						updateUI(null);
					}

					hideProgress();
				}
			});
    }

	private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
			new OnCompleteListener<Void>() {
				@Override
				public void onComplete(@NonNull Task<Void> task) {
					updateUI(null);
				}
			});
    }

	private void updateUI(FirebaseUser user) {
		hideProgress();

		if (navigationHeader.getProfiles() != null) {
			navigationHeader.clear();
		}

		if (user != null) {
			final IProfile profile = new ProfileDrawerItem().withName(user.getDisplayName()).withEmail(user.getEmail());

			if (user.getPhotoUrl() != null) {
				profile.withIcon(user.getPhotoUrl());
			}

			navigationHeader.addProfiles(
			    profile,
				new ProfileSettingDrawerItem().withName(R.string.sign_out)
											.withIcon(CommunityMaterial.Icon.cmd_exit_run)
											.withIdentifier(ACCOUNT_SIGN_OUT)
			);
			navigationHeader.setActiveProfile(profile);

		} else {
			navigationHeader.addProfile(
						new ProfileSettingDrawerItem()
						.withName(R.string.sign_in)
						.withIcon(CommunityMaterial.Icon2.cmd_key_outline)
						.withIdentifier(ACCOUNT_SIGN_IN), 0);
		}
	}

	private void showMessage(@StringRes int messageRes) {
        Snackbar.make(findViewById(R.id.root_layout), messageRes, Snackbar.LENGTH_LONG).show();
    }
	
	private FirebaseUser currentUser() {
		return auth.getCurrentUser();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		updateUI(currentUser());
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		hideProgress();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState = navigation.saveInstanceState(outState);
        outState = navigationHeader.saveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onBackPressed() {
		if (navigation != null && navigation.isDrawerOpen()) {
            navigation.closeDrawer();
        } else {
            super.onBackPressed();
        }
	}
}
