package frosteagle.com.yandextranslator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import frosteagle.com.yandextranslator.R;
import frosteagle.com.yandextranslator.models.languageModel.LanguageCustomModel;


public class LanguageRecycleAdapter extends RecyclerView.Adapter<LanguageRecycleAdapter.LanguageViewHolder> {

    private List<LanguageCustomModel> languageList;
    private List<LanguageCustomModel> languageListCopy;
    public static mAdapterListener onClickListener;
    private Context context;

    public interface mAdapterListener {
        void onAnswerClick(View v, int position, LanguageCustomModel model);
    }


    public LanguageRecycleAdapter(List<LanguageCustomModel> list, Context context) {
        this.languageList = new ArrayList<>();
        this.languageListCopy = new ArrayList<>();
        this.languageList.addAll(list);
        this.languageListCopy.addAll(list);
        this.context = context;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_language, parent, false);
        return new LanguageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LanguageViewHolder holder, final int position) {
        holder.tvName.setText(languageList.get(position).getLanguageTitle());
        holder.rlBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onAnswerClick(v, holder.getAdapterPosition(), languageList.get(position));
            }
        });
    }
    public void setOnItemClickListener(mAdapterListener clickListener) {
        LanguageRecycleAdapter.onClickListener = clickListener;
    }
    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return languageList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        RelativeLayout rlBg;
        LanguageViewHolder(View itemView) {
            super(itemView);
             rlBg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
             tvName = (TextView) itemView.findViewById(R.id.tv_language);

        }
    }
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        languageList.clear();
        if(text.isEmpty()){
            languageList.addAll(languageListCopy);
        } else{
            text = text.toLowerCase();
            for(LanguageCustomModel item: languageListCopy){
                if(item.getLanguageTitle().toLowerCase(Locale.getDefault()).contains(text)){
                    languageList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
