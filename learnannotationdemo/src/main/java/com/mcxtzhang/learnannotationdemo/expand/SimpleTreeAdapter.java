package com.mcxtzhang.learnannotationdemo.expand;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxtzhang.learnannotationdemo.R;
import com.mcxtzhang.learnannotationdemo.expand.helper.Node;
import com.mcxtzhang.learnannotationdemo.expand.helper.TreeListViewAdapter;

import java.util.List;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T>
{  
  
    public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
                             int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException  
    {  
        super(mTree, context, datas, defaultExpandLevel);  
    }  
  
    @Override  
    public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
    {  
          
        ViewHolder viewHolder = null;  
        if (convertView == null)  
        {  
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();  
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);  
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);  
            convertView.setTag(viewHolder);  
  
        } else  
        {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
  
        if (node.getIcon() == -1)  
        {  
            viewHolder.icon.setVisibility(View.INVISIBLE);  
        } else  
        {  
            viewHolder.icon.setVisibility(View.VISIBLE);  
            viewHolder.icon.setImageResource(node.getIcon());  
        }  
        viewHolder.label.setText(node.getName());  
          
        return convertView;  
    }  
  
    private final class ViewHolder  
    {  
        ImageView icon;  
        TextView label;  
    }  
  
}  