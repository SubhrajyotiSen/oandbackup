package dk.jens.backup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import dk.jens.backup.OAndBackup;
import dk.jens.backup.R;
import dk.jens.backup.ui.FileBrowser;

public class FileListAdapter extends ArrayAdapter<File>
{
    final static String TAG = OAndBackup.TAG;

    private Context context;
    private ArrayList<File> items;
    private int layout;
    public FileListAdapter(Context context, int layout, ArrayList<File> items)
    {
        super(context, layout, items);
        this.context = context;
        this.layout = layout;
        this.items = items;
    }
    public void addAll(ArrayList<File> list)
    {
        items.addAll(list);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.filename = convertView.findViewById(R.id.filename);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        File file = getItem(pos);
        if (file != null) {
            if(file instanceof FileBrowser.ParentFile)
                viewHolder.filename.setText("..");
            else
                viewHolder.filename.setText(file.getAbsolutePath() + "/");
        }
        return convertView;
    }
    static class ViewHolder
    {
        TextView filename;
    }
}