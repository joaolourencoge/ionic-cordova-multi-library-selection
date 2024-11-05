package org.apache.cordova.camera;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.growthengineering.dev1.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
  private List<Uri> imageUris;
  private Context context;

  public ImageAdapter(Context context, List<Uri> imageUris) {
    this.context = context;
    this.imageUris = imageUris;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.image_selection_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Uri imageUri = imageUris.get(position);

    Glide.with(context)
      .load(imageUri)
      .into(holder.imageView); // Load the image into the ImageView

       if (((CustomImagePickerActivity) context).selectedImageUris.contains(imageUri)) {
        holder.selectionIndicator.setText(String.valueOf(((CustomImagePickerActivity) context).selectedImageUris.indexOf(imageUri) + 1)); // Update selection number
        holder.selectionIndicator.setVisibility(View.VISIBLE); // Show the selection indicator
    } else {
        holder.selectionIndicator.setVisibility(View.GONE); // Hide the selection indicator
    }
    
    holder.itemView.setOnClickListener(v -> {

      // Handle selection logic here
      // Check if the image is already selected
      if (!((CustomImagePickerActivity) context).selectedImageUris.contains(imageUri)) {
        // If not selected, add to the selected list
        ((CustomImagePickerActivity) context).selectedImageUris.add(imageUri);
        holder.selectionIndicator.setText(String.valueOf(((CustomImagePickerActivity) context).selectedImageUris.size())); // Update selection number
        holder.selectionIndicator.setVisibility(View.VISIBLE); // Show the selection indicator
      } else {
        // If already selected, remove from the selected list
        int unselectedIndex = ((CustomImagePickerActivity) context).selectedImageUris.indexOf(imageUri);
        ((CustomImagePickerActivity) context).selectedImageUris.remove(imageUri);
        holder.selectionIndicator.setVisibility(View.GONE); // Hide the selection indicator
        ((CustomImagePickerActivity) context).updateSelectionIndicators(unselectedIndex); // Update other selection indicators
      }
    });
 


  }

  @Override
  public int getItemCount() {
    return imageUris.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    Button cancelButton;
    Button doneButton;
    TextView selectionIndicator;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.imageView);
      cancelButton = itemView.findViewById(R.id.btnCancel);
      doneButton = itemView.findViewById(R.id.btnDone);
      selectionIndicator = itemView.findViewById(R.id.selectionIndicator);
    }
  }
}