package br.com.collaboratorsapp.collaborator.collaborators;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.collaboratorsapp.R;
import br.com.collaboratorsapp.commons.ItemListListener;
import br.com.collaboratorsapp.data.entity.Collaborator;

class CollaboratorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private Activity mContext;
    private CopyOnWriteArrayList<Collaborator> mDataList;
    private ItemListListener<Collaborator> mItemListListener;

    CollaboratorsAdapter(Activity context, List<Collaborator> collaboratorList,
                         ItemListListener<Collaborator> itemListListener) {

        this.mContext = context;
        this.mDataList = new CopyOnWriteArrayList<>();
        this.mDataList.addAll(0, collaboratorList);
        this.mItemListListener = itemListListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mInflater) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        switch (viewType) {
            case TYPE_EMPTY:
                View emptyView = mInflater.inflate(R.layout.item_empty, parent, false);
                return new EmptyViewHolder(emptyView);

            case TYPE_ITEM:
                View collaboratorView = mInflater.inflate(R.layout.item_collaborator, parent, false);
                return new CollaboratorsAdapter.ItemViewHolder(collaboratorView, mItemListListener);

            default:
                emptyView = mInflater.inflate(R.layout.item_empty, parent, false);
                return new EmptyViewHolder(emptyView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CollaboratorsAdapter.ItemViewHolder) {

            CollaboratorsAdapter.ItemViewHolder itemViewHolder = (CollaboratorsAdapter.ItemViewHolder) holder;
            Collaborator collaborator = mDataList.get(position);

            itemViewHolder.tvCollaboratorName.setText(collaborator.getName() != null ?
                    collaborator.getName() : mContext.getString(R.string.notprovided_message));
            itemViewHolder.tvCollaboratorLogin.setText(collaborator.getLogin() != null ?
                    collaborator.getLogin() : mContext.getString(R.string.notprovided_message));
            itemViewHolder.tvCollaboratorProfile.setText(collaborator.getProfile() != null ?
                    collaborator.getProfile().toString() : mContext.getString(R.string.notprovided_message));

        } else {
            CollaboratorsAdapter.EmptyViewHolder emptyViewHolder = (CollaboratorsAdapter.EmptyViewHolder) holder;
            emptyViewHolder.tvEmptyMessage.setText(mContext.getString(R.string.nocollaborators_message));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.isEmpty() ? 1 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.isEmpty() ? TYPE_EMPTY : TYPE_ITEM;
    }

    public Collaborator getItem(int position) {
        return this.mDataList.get(position);
    }

    public void addData(List<Collaborator> collaboratorList) {
        int size = mDataList.size();
        if (size > 0) {
            this.mDataList.clear();
            notifyItemRangeRemoved(0, size);
        }

        this.mDataList.addAll(collaboratorList);
        notifyDataSetChanged();
    }

    public CopyOnWriteArrayList<Collaborator> getList() {
        return this.mDataList;
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEmptyMessage;

        private EmptyViewHolder(View itemView) {
            super(itemView);
            tvEmptyMessage = (TextView) itemView.findViewById(R.id.tv_empty_message);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        private ItemListListener<Collaborator> mItemListener;

        private TextView tvCollaboratorName;
        private TextView tvCollaboratorLogin;
        private TextView tvCollaboratorProfile;

        private ItemViewHolder(View itemView, final ItemListListener<Collaborator> itemListListener) {
            super(itemView);
            this.mItemListener = itemListListener;

            tvCollaboratorName = (TextView) itemView.findViewById(R.id.tv_collaborator_name);
            tvCollaboratorLogin = (TextView) itemView.findViewById(R.id.tv_collaborator_login);
            tvCollaboratorProfile = (TextView) itemView.findViewById(R.id.tv_collaborator_profile);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onClickedItem(getItem(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

}
