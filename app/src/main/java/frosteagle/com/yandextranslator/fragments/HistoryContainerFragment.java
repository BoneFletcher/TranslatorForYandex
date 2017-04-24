package frosteagle.com.yandextranslator.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import frosteagle.com.yandextranslator.MyApplication;
import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.adapter.TabsPagerAdapter;
import frosteagle.com.yandextranslator.eventBus.EventBusClearFavorite;
import frosteagle.com.yandextranslator.eventBus.EventBusDelete;


public class HistoryContainerFragment extends Fragment  {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "История", "Избранное"};
    private View view;
    private ImageView imgClearHistory;
    private AlertDialog.Builder dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_container, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("История"));
        tabLayout.addTab(tabLayout.newTab().setText("Избранное"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_history);
        final TabsPagerAdapter adapter = new TabsPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        imgClearHistory = (ImageView) view.findViewById(R.id.img_clear_history);
        imgClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog.show();
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Вы действительно хотите очистить историю");
        dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (MyApplication.openFrag==1) {
                    EventBus.getDefault().post(new EventBusClearFavorite());
                } else EventBus.getDefault().post(new EventBusDelete());
            }
        });
        dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        return view;
    }
}
