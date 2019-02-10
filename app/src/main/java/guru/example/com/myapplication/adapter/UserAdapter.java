package guru.example.com.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.List;

import guru.example.com.myapplication.R;
import guru.example.com.myapplication.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mCtx;
    private List<User> users;

    public UserAdapter(Context mCtx, List<User> users) {
        this.mCtx = mCtx;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_users,viewGroup,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        User user = users.get(i);

        userViewHolder.textViewName.setText(user.getName());
        userViewHolder.textViewEmail.setText(user.getEmail());
        userViewHolder.textViewSchool.setText(user.getSchool());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewEmail, textViewName, textViewSchool;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSchool = itemView.findViewById(R.id.textViewSchool);


        }
    }
}
