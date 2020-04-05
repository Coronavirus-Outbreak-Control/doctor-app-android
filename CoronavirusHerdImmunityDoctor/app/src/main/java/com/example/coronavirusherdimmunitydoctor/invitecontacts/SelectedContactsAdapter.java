package com.example.coronavirusherdimmunitydoctor.invitecontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.coronavirusherdimmunitydoctor.R;
import com.example.coronavirusherdimmunitydoctor.models.Contact;

import java.util.ArrayList;

public class SelectedContactsAdapter extends RecyclerView.Adapter<SelectedContactsAdapter.ViewHolder> {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private final RequestManager glide;
    private final RemoveContactListener onContactClickListener;

    public SelectedContactsAdapter(RequestManager glide, RemoveContactListener onContactClickListener) {
        this.glide = glide;
        this.onContactClickListener = onContactClickListener;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameTextView;
        final TextView photoTextView;
        final ImageView photoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            photoImageView = itemView.findViewById(R.id.photo);
            photoTextView = itemView.findViewById(R.id.photo_text);
        }

        public void bind(final Contact contact) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onContactClickListener.onRemoveContact(contact);
                }
            });
            nameTextView.setText(contact.getName());
            final String photo = contact.getPhoto();
            if (photo != null) {
                photoImageView.setVisibility(View.VISIBLE);
                photoTextView.setVisibility(View.GONE);
                glide
                        .load(photo)
                        .centerCrop()
                        .into(photoImageView);
            } else {
                photoImageView.setVisibility(View.INVISIBLE);
                photoTextView.setVisibility(View.VISIBLE);
                photoTextView.setText(contact.getFirstLetter());
            }
        }
    }

    interface RemoveContactListener {
        void onRemoveContact(Contact contact);
    }
}
