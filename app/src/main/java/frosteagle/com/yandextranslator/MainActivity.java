package frosteagle.com.yandextranslator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;

import frosteagle.com.yandextranslator.fragments.HistoryContainerFragment;
import frosteagle.com.yandextranslator.fragments.SettingsFragment;
import frosteagle.com.yandextranslator.fragments.TranslatorFragment;

public class MainActivity extends AppCompatActivity {

    private View line1, line2, line3;
    private Fragment fragment = null;
    private Class fragmentClass = null;
    private FragmentTransaction fragTransaction;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_navigation_bar, null, false);
                container.addView(view);
                return view;
            }
        });
        fragmentClass = TranslatorFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_for_fragment, fragment).commit();
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.bottom_bar);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.translate),
                        getResources().getColor(R.color.colorWhite))
                        .build()
        );

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_turned_in_black_24dp),
                getResources().getColor(R.color.colorWhite))
                .build()
        );

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.settings),
                getResources().getColor(R.color.colorWhite))
                .build()
        );

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                switch (index) {
                    case 0:
                        fragmentClass = TranslatorFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.INVISIBLE);
                        line3.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        fragmentClass = HistoryContainerFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        line1.setVisibility(View.INVISIBLE);
                        line2.setVisibility(View.VISIBLE);
                        line3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        fragmentClass = SettingsFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        line1.setVisibility(View.INVISIBLE);
                        line2.setVisibility(View.INVISIBLE);
                        line3.setVisibility(View.VISIBLE);
                        break;
                }
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_for_fragment, fragment).commit();
            }
            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
            }
        });
        navigationTabBar.setBgColor(getResources().getColor(R.color.colorWhite));
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }
}
