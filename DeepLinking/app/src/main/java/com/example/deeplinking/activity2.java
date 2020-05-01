package com.example.deeplinking;

import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class activity2 extends AppCompatActivity {

    private RecyclerView mContacts;
    private ContactsAdapter mAdapter;
    String bfd;
    ArrayList<String> arrayList;
    private ChipsView mChipsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        mContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        mContacts.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        mAdapter = new ContactsAdapter();
        mContacts.setAdapter(mAdapter);

        mChipsView = (ChipsView) findViewById(R.id.cv_contacts);
        mContacts.setVisibility(View.GONE);

        // change EditText config
        mChipsView.getEditText().setCursorVisible(true);

        mChipsView.setChipsValidator(new ChipsView.ChipValidator() {
            @Override
            public boolean isValid(Contact contact) {
                if (contact.getDisplayName().equals("asd@qwe.de")) {
                    return false;
                }
                return true;
            }
        });

        mChipsView.setChipsListener(new ChipsView.ChipsListener() {
            @Override
            public void onChipAdded(ChipsView.Chip chip) {
                for (ChipsView.Chip chipItem : mChipsView.getChips()) {
                    Log.d("ChipList", "chip: " + chipItem.toString());
                    mContacts.setVisibility(View.GONE);
                     bfd  = chipItem.getContact().getDisplayName();
                    arrayList.add(chipItem.getContact().getDisplayName());




                }

                //ArrayList<String> values=new ArrayList<String>();
                HashSet<String> hashSet = new HashSet<String>(arrayList);
                arrayList.clear();
                arrayList.addAll(hashSet);
                System.out.println("arr : " + hashSet);

            }

            @Override
            public void onChipDeleted(ChipsView.Chip chip) {
                mContacts.setVisibility(View.GONE);

            }

            @Override
            public void onTextChanged(CharSequence text) {
                mContacts.setVisibility(View.VISIBLE);
                mAdapter.filterItems(text);
            }

        });


    }

    public class ContactsAdapter extends RecyclerView.Adapter<CheckableContactViewHolder> {

        private String[] data = new String[]{
                "abc",
                "def",
                "efg",
                "fgh",
                "kalsdj"

        };

        private List<String> filteredList = new ArrayList<>();

        public ContactsAdapter() {
            Collections.addAll(filteredList, data);
        }

        @Override
        public CheckableContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(activity2.this).inflate(R.layout.item_checkable_contact, parent, false);
            return new CheckableContactViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CheckableContactViewHolder holder, int position) {
            holder.name.setText(filteredList.get(position));
        }

        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        public void filterItems(CharSequence text) {
            filteredList.clear();
            if (TextUtils.isEmpty(text)) {
                Collections.addAll(filteredList, data);
            } else {
                for (String s : data) {
                    if (s.contains(text)) {
                        filteredList.add(s);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return Math.abs(filteredList.get(position).hashCode());
        }
    }

    public class CheckableContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView name;
        public final CheckBox selection;

        public CheckableContactViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            selection = (CheckBox) itemView.findViewById(R.id.cb_contact_selection);
            selection.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selection.performClick();
                    mContacts.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void onClick(View v) {
            String email = name.getText().toString();
            //Uri imgUrl = Math.random() > .7d ? null : Uri.parse("https://robohash.org/" + Math.abs(email.hashCode()));
            Contact contact = new Contact(null, null, null, email, null);

            if (selection.isChecked()) {
                mChipsView.addChip(email,null,contact,false);
               // mChipsView.addChip(email, imgUrl, contact);
            } else {
               mChipsView.removeChipBy(contact);
            }
        }
    }
}