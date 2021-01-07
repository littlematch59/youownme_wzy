package com.example.youownme.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youownme.R;
import com.example.youownme.dataprocess.DataBank;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private GroupViewHolder groupViewHolder;
    private ChildViewHolder childViewHolder;


    class GroupViewHolder {
        TextView tv_group;
        ImageView iv_indicator;
    }
    class ChildViewHolder {
        TextView tv_child;
    }
    //用于存放Indicator的集合
    private SparseArray<ImageView> mIndicators;

    //根据分组的展开闭合状态设置指示器
    public void setIndicatorState(int groupPosition, boolean isExpanded){
        if(isExpanded){
            mIndicators.get(groupPosition).setImageResource(R.mipmap.icon_expand);//展开状态
        }
        else{
            mIndicators.get(groupPosition).setImageResource(R.mipmap.icon_collapse);
        }
    }
    public MyExpandableListAdapter(Context context){
        this.context = context;
        mIndicators = new SparseArray<>();
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
    @Override
    public int getGroupCount() {
        return DataBank.getYearGroup().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DataBank.getYearChild().get(groupPosition).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return DataBank.getYearGroup().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DataBank.getYearChild().get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //一级菜单视图
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.first_list_layout,null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_group = convertView.findViewById(R.id.tv_group);
            groupViewHolder.iv_indicator = convertView.findViewById(R.id.iv_indicator);
            convertView.setTag(groupViewHolder);
        }
        else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置显示数据
        groupViewHolder.tv_group.setText(DataBank.getYearGroup().get(groupPosition)+"");
        //添加位置和图标到Map
        mIndicators.put(groupPosition,groupViewHolder.iv_indicator);

        //设置Indicator
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //二级菜单视图
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.second_list_layout,null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_child = convertView.findViewById(R.id.tv_child);

            convertView.setTag(childViewHolder);
        }
        else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv_child.setText(
                DataBank.getYearChild().get(groupPosition).get(childPosition).toString()
        );
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
