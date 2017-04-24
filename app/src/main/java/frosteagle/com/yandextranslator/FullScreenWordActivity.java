package frosteagle.com.yandextranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FullScreenWordActivity extends AppCompatActivity {
    private TextView tvMainWord;
    private ImageView imgClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_word);
        tvMainWord = (TextView) findViewById(R.id.tv_word);
        tvMainWord.setText(getIntent().getStringExtra("word"));
        imgClose = (ImageView) findViewById(R.id.img_open_full);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
