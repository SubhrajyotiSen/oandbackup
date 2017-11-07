package dk.jens.backup.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import dk.jens.backup.AppInfo;
import dk.jens.backup.R;

public class BatchAdapter extends AppInfoAdapter
{
    private Context context;
    private ArrayList<AppInfo> items;
    private int layout;

    public BatchAdapter(Context context, int layout, ArrayList<AppInfo> items)
    {
        super(context, layout, items);
        this.context = context;
        this.items = new ArrayList<>(items);
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cb = convertView.findViewById(R.id.cb);
            viewHolder.tv = convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AppInfo appInfo = getItem(pos);
        if (appInfo != null) {
            viewHolder.cb.setText(appInfo.getLabel());
            viewHolder.tv.setText(appInfo.getPackageName());
            viewHolder.cb.setChecked(appInfo.isChecked());
            if (appInfo.isInstalled()) {
                int color = appInfo.isSystem() ? Color.rgb(198, 91, 112) : Color.rgb(14, 158, 124);
                if(appInfo.isDisabled())
                    color = Color.rgb(7, 87, 117);
                viewHolder.cb.setTextColor(Color.WHITE);
                viewHolder.tv.setTextColor(color);
            } else {
                viewHolder.cb.setTextColor(Color.GRAY);
                viewHolder.tv.setTextColor(Color.GRAY);
            }
        }
        return convertView;
    }
    static class ViewHolder
    {
        CheckBox cb;
        TextView tv;
    }
}
