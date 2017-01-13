package hl.iss.whu.edu.laboratoryproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hl.iss.whu.edu.laboratoryproject.R;
import hl.iss.whu.edu.laboratoryproject.constant.Constant;
import hl.iss.whu.edu.laboratoryproject.entity.Chatter;
import hl.iss.whu.edu.laboratoryproject.entity.Group;
import hl.iss.whu.edu.laboratoryproject.glide.GlideRoundTransform;
import hl.iss.whu.edu.laboratoryproject.ui.view.AnimatedExpandableListView;
import hl.iss.whu.edu.laboratoryproject.utils.ImageFactory;
import hl.iss.whu.edu.laboratoryproject.utils.UiUtils;

/**
 * Created by fate on 2016/11/26.
 */

public class ExpandableContactAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private ArrayList<Group> data;

    public ExpandableContactAdapter(ArrayList<Group> data) {
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        int presenceNumber = data.get(groupPosition).getPresence().size();
        if (childPosition < presenceNumber)
            return data.get(groupPosition).getPresence().get(childPosition);
        else return data.get(groupPosition).getAbsence().get(childPosition - presenceNumber);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHoldder viewHoldder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.item_expandable_contact_group, null);
            viewHoldder = new GroupViewHoldder();
            viewHoldder.name = ButterKnife.findById(convertView, R.id.tv_group_name);
            viewHoldder.number = ButterKnife.findById(convertView, R.id.tv_contacts_number);
            convertView.setTag(viewHoldder);
        }
        else {
            viewHoldder = (GroupViewHoldder) convertView.getTag();
        }
        Group group = data.get(groupPosition);
        viewHoldder.name.setText(group.getName());
        viewHoldder.number.setText(group.getPresence().size() + "/" + getRealChildrenCount(groupPosition));
        return convertView;
    }

//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        if (convertView ==null) {
//            convertView = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.item_expandable_chapter_child, null);
//            TextView title = ButterKnife.findById(convertView, R.id.tv_child_title);
//            Chapter.Lesson lesson = data.get(groupPosition).getLessons().get(childPosition);
//            title.setText(lesson.getTitle());
//        }
//        return convertView;
//    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.item_expandable_contact_child, null);
            viewHolder = new ChildViewHolder();
            viewHolder.name = ButterKnife.findById(convertView, R.id.tv_contact_name);
            viewHolder.signiture = ButterKnife.findById(convertView, R.id.tv_contact_signiture);
            viewHolder.image = ButterKnife.findById(convertView, R.id.iv_contact_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        Chatter chatter;
        int presenceNumber = data.get(groupPosition).getPresence().size();
        if (childPosition < presenceNumber)
            chatter = data.get(groupPosition).getPresence().get(childPosition);
        else chatter = data.get(groupPosition).getAbsence().get(childPosition - presenceNumber);
        viewHolder.name.setText(chatter.getName());
        viewHolder.signiture.setText(chatter.getSigniture());
        Glide.with(UiUtils.getContext())
                .load(chatter.getImage())
                .dontAnimate()
                .placeholder(R.drawable.ic_account_circle_blue_600_24dp)
                .into(viewHolder.image);
        ButterKnife.findById(convertView, R.id.ll_presence).setVisibility(chatter.getState() == 0 ? View.VISIBLE : View.GONE);
        ButterKnife.findById(convertView, R.id.ll_absence).setVisibility(chatter.getState() == 1 ? View.VISIBLE : View.GONE);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return data.get(groupPosition).getPresence().size() + data.get(groupPosition).getAbsence().size();
    }

    static class ChildViewHolder {
        TextView name;
        TextView signiture;
        ImageView image;
    }
    static class GroupViewHoldder {
        TextView name;
        TextView number;

    }
}
