package hw.txtreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bifan.txtreaderlib.bean.FileReadRecordBean;
import java.util.List;

public class FileHistoryAdapter extends RecyclerView.Adapter<FileHistoryAdapter.ViewHolder> {
    private List<FileReadRecordBean> mHistory;
    private OnFileSelectedListener mListener;

    public interface OnFileSelectedListener {
        void onFileSelected(FileReadRecordBean file);
    }

    public FileHistoryAdapter(List<FileReadRecordBean> history, OnFileSelectedListener listener) {
        this.mHistory = history;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileReadRecordBean item = mHistory.get(position);
        holder.fileNameTextView.setText(item.fileName);
        holder.filePathTextView.setText(item.filePath);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFileSelected(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistory != null ? mHistory.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;
        TextView filePathTextView;

        ViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.tv_filename);
            filePathTextView = itemView.findViewById(R.id.tv_filepath);
        }
    }
}
