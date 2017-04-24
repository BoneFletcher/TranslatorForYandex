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

import java.util.ArrayList;
import java.util.List;

import frosteagle.com.yandextranslator.MyApplication;
import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.adapter.FavoriteRecyclerAdapter;
import frosteagle.com.yandextranslator.eventBus.EventBusClearFavorite;
import frosteagle.com.yandextranslator.realm.FavoriteHistory;
import io.realm.Realm;
import io.realm.RealmResults;


public class FavoritesFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private Realm mRealm;
    private SearchView searchView;
    private FavoriteRecyclerAdapter adapter;
    private List<FavoriteHistory> list;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        list = new ArrayList<>();
        for (int i = 0; i<mRealm.allObjects(FavoriteHistory.class).size(); i++) {
            if (mRealm.allObjects(FavoriteHistory.class).get(i).isFavorite()) {
                list.add(mRealm.allObjects(FavoriteHistory.class).get(i));
            }
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_words);
        adapter = new FavoriteRecyclerAdapter(list, getContext(), mRealm);
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
        MyApplication.openFrag=0;
    }
    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onModelReturn(EventBusClearFavorite event) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                list.clear();
                RealmResults<FavoriteHistory> rowFavourite = realm.where(FavoriteHistory.class).equalTo("favorite", true).findAll();
                for (int i = 0; i<rowFavourite.size(); i++) {
                    FavoriteHistory row = rowFavourite.get(i);
                    row.setFavorite(false);
                    realm.copyToRealmOrUpdate(row);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        MyApplication.openFrag=1;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



}
