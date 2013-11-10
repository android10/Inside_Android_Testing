package com.pivotallabs.tracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.pivotallabs.NotifyDataSetChangedCallbacks;
import com.pivotallabs.R;
import com.pivotallabs.ViewVisibleWhileOutstandingCallbacks;
import com.pivotallabs.api.ApiGateway;

public class RecentActivityActivity extends Activity {

    ApiGateway apiGateway = new ApiGateway();
    SignInDialog signInDialog;

    private AuthenticationGateway authenticationGateway;
    private RecentActivities recentActivities;
    private ViewVisibleWhileOutstandingCallbacks showLoadingWhileOutstanding;
    private NotifyDataSetChangedCallbacks notifyDataSetChangedCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_activity_layout);

        authenticationGateway = new AuthenticationGateway(apiGateway, this);
        recentActivities = new RecentActivities(apiGateway, authenticationGateway);

        ListView recentActivityListView = (ListView) findViewById(R.id.recent_activity_list);

        View loadingView = getLayoutInflater().inflate(R.layout.loading_view, recentActivityListView, false);
        recentActivityListView.addFooterView(loadingView, null, false);
        recentActivityListView.setFooterDividersEnabled(false);

        RecentActivityAdapter recentActivityAdapter = new RecentActivityAdapter(recentActivities, getLayoutInflater());
        recentActivityListView.setAdapter(recentActivityAdapter);

        showLoadingWhileOutstanding = new ViewVisibleWhileOutstandingCallbacks(loadingView);
        notifyDataSetChangedCallbacks = new NotifyDataSetChangedCallbacks(recentActivityAdapter);

        updateOrSignIn();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        addSignOutMenuItem(menu);
        return true;
    }

    private void updateOrSignIn() {
        if (authenticationGateway.isAuthenticated()) {
            update();
        } else {
            showSignInDialog();
        }
    }

    private void update() {
        recentActivities.update(showLoadingWhileOutstanding, notifyDataSetChangedCallbacks);
    }

    private void addSignOutMenuItem(Menu menu) {
        MenuItem signOutMenuItem = menu.add("Sign Out");
        signOutMenuItem.setEnabled(authenticationGateway.isAuthenticated());
        signOutMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                authenticationGateway.signOut();
                finish();
                return true;
            }
        });
    }

    private void showSignInDialog() {
        signInDialog = new SignInDialog(this, authenticationGateway);
        signInDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                updateOrFinish();
            }
        });
        signInDialog.show();
    }

    private void updateOrFinish() {
        if (authenticationGateway.isAuthenticated()) {
            update();
        } else {
            finish();
        }
    }
}
