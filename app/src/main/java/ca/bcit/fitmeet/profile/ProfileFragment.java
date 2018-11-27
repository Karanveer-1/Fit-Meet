package ca.bcit.fitmeet.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import ca.bcit.fitmeet.R;
import ca.bcit.fitmeet.event.model.GlideApp;
import ca.bcit.fitmeet.profile.ProfileEventListFragment;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private final int PICK_IMAGE_REQUEST = 71;

    private View view;
    private ImageView imageView;
    private Uri filePath;
    private String name;

    private StorageReference storageReference;
    private String userToken;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialiseFireBase();
        getName();

//        Button editProfile = view.findViewById(R.id.edit_profile);
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Edit Profile");
//
//                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.profile_edit, (ViewGroup) getView(), false);
//
//                EditText firstName = viewInflated.findViewById(R.id.username_fn);
//                EditText lastName = viewInflated.findViewById(R.id.username_ln);
//
//                builder.setView(viewInflated);
//
//                builder.show();
//            }
//        });

        imageView = view.findViewById(R.id.profile_picture);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ViewPager mViewPager = view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        setEditProfileButton(view);

        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        TextView name = view.findViewById(R.id.profile_name);
        imageView.setImageResource(R.drawable.placeholder);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile").child(userToken);
        Log.e("load", "loading");


        // Temp solution...
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri downloadUrl = uri;
                GlideApp.with(getActivity()).
                        load(uri).placeholder(R.drawable.placeholder).
                        signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).
                        apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).
                        into(imageView);
            }
        });

        return view;
    }

    private void setEditProfileButton(View v) {

    }

    private void initialiseFireBase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userToken = currentUser.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("RESULT", "" + resultCode);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                Log.e("IMAGE", "SET");
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("profile/" + userToken);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    public void getName() {
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = "";

                DataSnapshot dataSnapshot1 = dataSnapshot.child("firstName");

                if (dataSnapshot1.exists()) {
                    Log.e("KEY", dataSnapshot1.getKey());
                    name += dataSnapshot1.getValue(String.class) + " " ;
                }

                DataSnapshot dataSnapshot2 = dataSnapshot.child("lastName");

                if (dataSnapshot2.exists()) {
                    Log.e("KEY", dataSnapshot2.getKey());
                    name += dataSnapshot2.getValue(String.class);
                }

                TextView profileTextView = view.findViewById(R.id.profile_name);
                profileTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userToken);
        ref.addValueEventListener(messageListener);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.e("SECTION", ""+position);
            return ProfileEventListFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
