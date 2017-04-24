package frosteagle.com.yandextranslator.fragmentsHistory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import frosteagle.com.yandextranslator.MyApplication;
import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.adapter.HistoryRecyclerAdapter;
import frosteagle.com.yandextranslator.db.HistoryTranslateDataSource;
import frosteagle.com.yandextranslator.eventBus.EventBusDelete;
import frosteagle.com.yandextranslator.realm.FavoriteHistory;
import io.realm.Realm;
import io.realm.RealmResults;


public class HistoryFragment extends Fragment {
    private View view;
    private HistoryTranslateDataSource dbFavoriteForecasters;
    private RecyclerView mRecyclerView;
    private Realm mRealm;
    private SearchView searchView;
    private HistoryRecyclerAdapter adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_words);
        adapter = new HistoryRecyclerAdapter(mRealm.allObjects(FavoriteHistory.class), getContext(), mRealm);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        searchView = (SearchView) view.findViewById(R.id.search_history);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onModelReturn(EventBusDelete event) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FavoriteHistory> rows = realm.where(FavoriteHistory.class).findAll();
                rows.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        MyApplication.openFrag=0;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
