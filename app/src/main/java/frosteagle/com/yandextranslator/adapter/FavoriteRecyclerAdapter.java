package frosteagle.com.yandextranslator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.realm.FavoriteHistory;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by FrostEagle on 24.04.2017.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> implements RealmChangeListener {

        private final List<FavoriteHistory> mHistories;
        private Context mContext;
        private final List<FavoriteHistory> mHistoriesCopy;
        private Realm mRealm;


        public FavoriteRecyclerAdapter(List<FavoriteHistory> histories, Context context, Realm realm) {
          //  mRealm = realm;
            mRealm = Realm.getDefaultInstance();
            this.mHistories = new ArrayList<>();
            this.mHistoriesCopy = new ArrayList<>();
            this.mHistories.addAll(histories);
            this.mHistoriesCopy.addAll(histories);
//            mHistories = histories;
//            mHistoriesCopy = histories;
//            mHistories.addChangeListener(this);
//            mHistoriesCopy.addChangeListener(this);
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvOriginalWord.setText(mHistories.get(position).getFirstWord());
            holder.tvTranslateWord.setText(mHistories.get(position).getSecondWord());
            holder.tvLocale.setText(mHistories.get(position).getLocale());
            if (mHistories.get(position).isFavorite()) {
                holder.imgHistory.setImageResource(R.drawable.ic_turned_in_yellow);
            } else {
                holder.imgHistory.setImageResource(R.drawable.ic_turned_in_grey);
            }
        }

        @Override
        public int getItemCount() {
            return mHistories.size();
        }

        @Override
        public void onChange() {
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvOriginalWord, tvTranslateWord, tvLocale;
            private ImageView imgHistory;

            public ViewHolder(View itemView) {
                super(itemView);
           //     final Realm realm = Realm.getDefaultInstance();
                tvOriginalWord = (TextView) itemView.findViewById(R.id.tv_original_word);
                tvTranslateWord = (TextView) itemView.findViewById(R.id.tv_translate_word);
                tvLocale = (TextView) itemView.findViewById(R.id.tv_language_translate);
                imgHistory = (ImageView) itemView.findViewById(R.id.img_flag);


                imgHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // добавляем в избранное
//                        if (toggle_fav.isChecked()) {
//                            RealmWorkingWithTables.addToFavouriteOrHistory(realm, text_word.getText().toString(), text_translation.getText().toString(), text_direction.getText().toString(), true, true);
//                        } else { // убираем из избранного
//                            RealmWorkingWithTables.addToFavouriteOrHistory(realm, text_word.getText().toString(), text_translation.getText().toString(), text_direction.getText().toString(), false, true);
//                        }
                    }
                });
            }
        }
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        mHistories.clear();
        if(text.isEmpty()){
            mHistories.addAll(mHistoriesCopy);
        } else{
            text = text.toLowerCase();
            for(FavoriteHistory item: mHistoriesCopy){
                if(item.getFirstWord().toLowerCase(Locale.getDefault()).contains(text)){
                    mHistories.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
    }

