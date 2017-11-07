package dk.jens.backup.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dk.jens.backup.R;
import dk.jens.backup.Tools;

public class ToolsAdapter extends ArrayAdapter
{
    private Context context;
    private int layout;
    private ArrayList<Tools.Pair> items;

    public ToolsAdapter(Context context, int layout, ArrayList<Tools.Pair> items)
    {
        super(context, layout, items);
        this.context = context;
        this.layout = layout;
        this.items = items;
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
            viewHolder.title = convertView.findViewById(R.id.tools_title);
            viewHolder.description = convertView.findViewById(R.id.tools_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Tools.Pair pair = (Tools.Pair) getItem(pos);
        if (pair != null) {
            viewHolder.title.setText(pair.title);
            viewHolder.description.setText(pair.description);
        }
        return convertView;
    }
    static class ViewHolder
    {
        TextView title;
        TextView description;
    }
}