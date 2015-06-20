package koemdzhiev.com.blinkmessage.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import koemdzhiev.com.blinkmessage.R;
import koemdzhiev.com.blinkmessage.utils.MD5Util;

/**
 * Created by koemdzhiev on 03/06/2015.
 */
public class UserAdapter extends ArrayAdapter<ParseUser> {
    private static final String TAG = UserAdapter.class.getSimpleName();
    protected Context mContext;
    protected List<ParseUser> mUsers;

    public UserAdapter(Context context, List<ParseUser> users) {
        super(context, R.layout.message_item, users);
        mContext = context;
        mUsers = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
            holder = new ViewHolder();
            holder.userImageView = (ImageView) convertView.findViewById(R.id.userImageView);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ParseUser user = mUsers.get(position);
        String email = user.getEmail().toLowerCase();

        if(email.equals("")){
            holder.userImageView.setImageResource(R.mipmap.avatar_empty);
        }else {
            String hash = MD5Util.md5Hex(email);
            String gravatarUrl = "http://www.gravatar.com/avatar/" + hash + "?s=204&d=404";

            Picasso.with(mContext).load(gravatarUrl)
                    .placeholder(R.mipmap.avatar_empty)
                    .into(holder.userImageView);
        }
        Date createdAt = user.getCreatedAt();

        Long now = new Date().getTime();
        String convertedData = DateUtils.getRelativeTimeSpanString(
                createdAt.getTime(),
                now,
                DateUtils.SECOND_IN_MILLIS).toString();
//
//        if(user.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
//            holder.iconImageView.setImageResource(R.mipmap.ic_picture);
//        }else{
//            holder.iconImageView.setImageResource(R.mipmap.ic_video);
//        }
       // holder.createdAtLabel.setText(convertedData);
        //Log.i(MessageAdapter.class.getSimpleName(),user.getCreatedAt().toString());
        holder.nameLabel.setText(user.getUsername());

       return convertView;
    }

    private static class ViewHolder{
        ImageView userImageView;
        TextView nameLabel;
    }
    //refill, this method prevents the adapter to go to the top of the list
    public void refill(List<ParseUser> users){
        users.clear();
        users.addAll(users);
        notifyDataSetChanged();
    }
}
