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

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final ArrayList<Contact> contacts;
    private final RequestManager glide;
    private final OnContactClick onContactClickListener;

    public ContactsAdapter(ArrayList<Contact> contacts, RequestManager glide, OnContactClick onContactClickListener) {
        this.contacts = contacts;
        this.glide = glide;
        this.onContactClickListener = onContactClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
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
        final TextView phoneTextView;
        final TextView photoTextView;
        final ImageView photoImageView;
        final ImageView checkedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            phoneTextView = itemView.findViewById(R.id.phone);
            photoImageView = itemView.findViewById(R.id.photo);
            photoTextView = itemView.findViewById(R.id.photo_text);
            checkedImageView = itemView.findViewById(R.id.checked);
        }

        public void bind(final Contact contact) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onContactClickListener.onContactClicked(contact);
                    bind(contact);
                }
            });
            nameTextView.setText(contact.getName());
            phoneTextView.setText(contact.getPhone());
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
            if (contact.isSelected()) {
                checkedImageView.setVisibility(View.VISIBLE);
            } else {
                checkedImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    interface OnContactClick {
        void onContactClicked(Contact contact);
    }
}
