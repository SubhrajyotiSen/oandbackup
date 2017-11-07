package dk.jens.backup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogViewer extends BaseActivity
        implements View.OnClickListener
{
    final static String TAG = OAndBackup.TAG;
    String[] textParts;
    int index;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logviewer);
        Button nextLinesButton = findViewById(R.id.next_lines_button);
        nextLinesButton.setOnClickListener(this);
        new Thread(new TextLoadRunnable()).start();
    }
    private void appendNextLines()
    {
        appendNextLines(false);
    }
    private void appendNextLines(boolean clear)
    {
        final ScrollView scroll = findViewById(R.id.scrollview);
        final int pos = scroll.getScrollY();
        TextView tv = findViewById(R.id.log_text);
        if(clear)
        {
            ProgressBar pb = findViewById(R.id.logviewer_progressbar);
            pb.setVisibility(View.GONE);
            TextView loading = findViewById(R.id.logviewer_loading_textview);
            loading.setVisibility(View.GONE);
        }
        // loading a large text file can take a long time
        // so here it is loaded little by little
        for(int i = index; i > index - 20 && i >= 0; i--)
            tv.append(textParts[i] + "\n\n");
        index -= 20;
        if(index <= 0)
        {
            Button btn = findViewById(R.id.next_lines_button);
            btn.setClickable(false);
        }
        // scroll action needs to be delayed until text is displayed on screen
        // FIXME: find a less hacky solution
        scroll.postDelayed(() -> scroll.scrollTo(0, pos), 700);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.next_lines_button:
                appendNextLines();
        }
    }
    private class TextLoadRunnable implements Runnable
    {
        public void run()
        {
            String txt = new FileReaderWriter(FileCreationHelper.defaultLogFilePath).read();
            textParts = txt.split("\n");
            index = textParts.length - 1;
            runOnUiThread(() -> appendNextLines(true));
        }
    }
}
