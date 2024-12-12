package hw.txtreader;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bifan.txtreaderlib.bean.FileReadRecordBean;
import com.bifan.txtreaderlib.main.FileReadRecordDB;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import java.io.File;
import java.util.List;

public class FileHistoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FileReadRecordDB mFileReadRecordDB;
    private FileHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_history);

        // Initialize views and database
        mRecyclerView = findViewById(R.id.recycler_view);
        mFileReadRecordDB = new FileReadRecordDB(this);

        // Create database table if not exists
        mFileReadRecordDB.createTable();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<FileReadRecordBean> history = mFileReadRecordDB.getAllFileRecord();

        mAdapter = new FileHistoryAdapter(history, new FileHistoryAdapter.OnFileSelectedListener() {
            @Override
            public void onFileSelected(FileReadRecordBean file) {
                if (file != null && new File(file.filePath).exists()) {
                    HwTxtPlayActivity.loadTxtFile(FileHistoryActivity.this, file.filePath);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFileReadRecordDB != null) {
            mFileReadRecordDB.closeTable();
        }
    }
}
